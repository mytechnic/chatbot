package com.example.gateway.app.chat;

import com.example.client.app.bot.BotClient;
import com.example.core.exception.BusinessException;
import com.example.domain.dto.bot.component.UtteranceMessage;
import com.example.domain.dto.event.ChatEventClientMessage;
import com.example.domain.error.ConnectUserError;
import com.example.domain.error.ServiceError;
import com.example.domain.rest.ApiResponse;
import com.example.domain.rest.bot.BotPrimaryKey;
import com.example.domain.rest.bot.BotUtteranceMessageRequest;
import com.example.domain.rest.bot.CurrentBotMessageListByCidRequest;
import com.example.domain.rest.bot.CurrentBotMessageListResponse;
import com.example.domain.rest.gateway.*;
import com.example.domain.type.BotMessageEvent;
import com.example.domain.type.Channel;
import com.example.domain.type.ConnectServerStatus;
import com.example.domain.type.ConnectTargetId;
import com.example.event.server.ClientEventPublisher;
import com.example.gateway.config.AppConfig;
import com.example.rds.service.entity.ConnectHistoryEntity;
import com.example.rds.service.entity.ConnectServerEntity;
import com.example.rds.service.entity.ConnectUserEntity;
import com.example.rds.service.entity.ServiceManagerEntity;
import com.example.rds.service.repository.ConnectHistoryRepository;
import com.example.rds.service.repository.ConnectServerRepository;
import com.example.rds.service.repository.ConnectUserRepository;
import com.example.rds.service.repository.ServiceManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayService {
    private final AppConfig appConfig;
    private final ServiceManagerRepository serviceManagerRepository;
    private final ConnectUserRepository connectUserRepository;
    private final ConnectServerRepository connectServerRepository;
    private final ConnectHistoryRepository connectHistoryRepository;
    private final ClientEventPublisher clientEventPublisher;
    private final BotClient botClient;

    public void textUtterance(TextUtteranceByConnectIdRequest request) {

        ServiceManagerEntity serviceManagerEntity = serviceManagerRepository.findByServiceId(request.getServiceId())
                .orElseThrow(() -> new BusinessException(ServiceError.NOT_FOUND));

        ConnectUserEntity connectUserEntity = connectUserRepository
                .findByServiceNoAndConnectId(serviceManagerEntity.getServiceNo(), request.getConnectId())
                .orElseThrow(() -> new BusinessException(ConnectUserError.NOT_FOUND));

        sendUtteranceToBot(request.getServiceId(), connectUserEntity.getCid(), request.getChannel(), request.getChannelId(), request.getMessage());
    }

    public void textUtterance(TextUtteranceRequest request) {

        ServiceManagerEntity serviceManagerEntity = serviceManagerRepository.findByServiceId(request.getServiceId())
                .orElseThrow(() -> new BusinessException(ServiceError.NOT_FOUND));

        ConnectUserEntity connectUserEntity = connectUserRepository
                .findByServiceNoAndUid(serviceManagerEntity.getServiceNo(), request.getUid())
                .orElseThrow(() -> new BusinessException(ConnectUserError.NOT_FOUND));

        sendUtteranceToBot(request.getServiceId(), connectUserEntity.getCid(), request.getChannel(), request.getChannelId(), request.getMessage());
    }

    private void sendUtteranceToBot(String serviceId, String cid, Channel channel, String channelId, String message) {

        BotPrimaryKey botPrimaryKey = new BotPrimaryKey();
        botPrimaryKey.setServiceId(serviceId);
        botPrimaryKey.setCid(cid);
        botPrimaryKey.setChannel(channel);
        botPrimaryKey.setChannelId(channelId);

        BotUtteranceMessageRequest messageRequest = new BotUtteranceMessageRequest();
        messageRequest.setKey(botPrimaryKey);
        messageRequest.setUtterance(new UtteranceMessage(message));
        botClient.utterance(messageRequest);
    }

    public void botEvent(BotEventRequest request) {

        ServiceManagerEntity serviceManagerEntity = serviceManagerRepository.findByServiceId(request.getServiceId())
                .orElseThrow(() -> new BusinessException(ServiceError.NOT_FOUND));

        ConnectUserEntity connectUserEntity = connectUserRepository
                .findByServiceNoAndCid(serviceManagerEntity.getServiceNo(), request.getCid())
                .orElseThrow(() -> new BusinessException(ConnectUserError.NOT_FOUND));

        if (request.getEventType() == BotMessageEvent.TIMEOUT
                || request.getEventType() == BotMessageEvent.TIMEOUT_ONLY) {
            expiredConnectServer(connectUserEntity.getUserNo());
        }

        if (request.getChannel().isWebSocket()) {
            if (request.getEventType() == BotMessageEvent.MESSAGE
                    || request.getEventType() == BotMessageEvent.TIMEOUT) {
                sendClientEvent(request, connectUserEntity);
            }
        }
    }

    private void sendClientEvent(BotEventRequest event, ConnectUserEntity connectUserEntity) {

        ChatEventClientMessage chatEventClientMessage = new ChatEventClientMessage(
                event.getServiceId(),
                connectUserEntity.getConnectId(),
                event.getChannel(),
                event.getChannelId(),
                event.getEventType()
        );
        clientEventPublisher.publish(appConfig.getTopic().getChatEvent(), chatEventClientMessage);
    }

    @Transactional
    public String getConnectIdByUid(ConnectIdByUidRequest request) {

        ServiceManagerEntity serviceManagerEntity = serviceManagerRepository.findByServiceId(request.getServiceId())
                .orElseThrow(() -> new BusinessException(ServiceError.NOT_FOUND));

        ConnectUserEntity connectUserEntity = connectUserRepository.findByServiceNoAndUid(serviceManagerEntity.getServiceNo(), request.getUid())
                .orElse(null);
        if (connectUserEntity == null) {
            connectUserEntity = new ConnectUserEntity();
            connectUserEntity.setServiceNo(serviceManagerEntity.getServiceNo());
            connectUserEntity.setConnectId(getGenerateConnectId());
            connectUserEntity.setUid(request.getUid());
            connectUserEntity.setCid(getGenerateCid());
            connectUserEntity.setCreated(new Date());
            connectUserRepository.save(connectUserEntity);
        }

        ConnectServerEntity connectServerEntity = connectServerRepository.findByUserNo(connectUserEntity.getUserNo())
                .orElse(null);
        if (connectServerEntity == null) {
            connectServerEntity = new ConnectServerEntity();
            createConnectServer(request, connectUserEntity, connectServerEntity);
            saveConnectHistory(connectServerEntity);
        } else if (connectServerEntity.getStatus() != ConnectServerStatus.CONNECTED) {
            updateConnectServer(request, connectUserEntity, connectServerEntity);
            saveConnectHistory(connectServerEntity);
        }

        return connectUserEntity.getConnectId();
    }

    private void updateConnectServer(ConnectIdByUidRequest request,
                                     ConnectUserEntity connectUserEntity,
                                     ConnectServerEntity connectServerEntity) {
        connectServerEntity.setChannel(request.getChannel());
        connectServerEntity.setChannelId(request.getChannelId());
        connectServerEntity.setTargetId(ConnectTargetId.CHATBOT);
        connectServerEntity.setTargetConnectId(getTargetConnectId(request, connectUserEntity.getCid()));
        connectServerEntity.setStatus(ConnectServerStatus.CONNECTED);
        connectServerRepository.save(connectServerEntity);
    }

    private void createConnectServer(ConnectIdByUidRequest request,
                                     ConnectUserEntity connectUserEntity,
                                     ConnectServerEntity connectServerEntity) {
        connectServerEntity.setUserNo(connectUserEntity.getUserNo());
        connectServerEntity.setChannel(request.getChannel());
        connectServerEntity.setChannelId(request.getChannelId());
        connectServerEntity.setTargetId(ConnectTargetId.CHATBOT);
        connectServerEntity.setTargetConnectId(getTargetConnectId(request, connectUserEntity.getCid()));
        connectServerEntity.setStatus(ConnectServerStatus.CONNECTED);
        connectServerEntity.setCreated(new Date());
        connectServerRepository.save(connectServerEntity);
    }

    private void saveConnectHistory(ConnectServerEntity connectServerEntity) {
        ConnectHistoryEntity connectHistoryEntity = new ConnectHistoryEntity();
        connectHistoryEntity.setConnectNo(connectServerEntity.getConnectNo());
        connectHistoryEntity.setChannel(connectServerEntity.getChannel());
        connectHistoryEntity.setChannelId(connectServerEntity.getChannelId());
        connectHistoryEntity.setTargetId(connectServerEntity.getTargetId());
        connectHistoryEntity.setTargetConnectId(connectServerEntity.getTargetConnectId());
        connectHistoryEntity.setStatus(connectServerEntity.getStatus());
        connectHistoryEntity.setCreated(new Date());
        connectHistoryRepository.save(connectHistoryEntity);
    }

    private String getGenerateConnectId() {
        return "CON:" + UUID.randomUUID().toString().replace("-", "");
    }

    private String getGenerateCid() {
        return "CID:" + UUID.randomUUID().toString().replace("-", "");
    }

    private String getTargetConnectId(ConnectIdByUidRequest request, String cid) {

        BotPrimaryKey botSessionIdRequest = new BotPrimaryKey();
        botSessionIdRequest.setServiceId(request.getServiceId());
        botSessionIdRequest.setChannel(request.getChannel());
        botSessionIdRequest.setChannelId(request.getChannelId());
        botSessionIdRequest.setCid(cid);
        ApiResponse<String> res = botClient.getSessionId(botSessionIdRequest);
        if (res.isFail()) {
            throw new BusinessException(res.getCode(), res.getMessage());
        }
        return res.getResult();
    }

    private void expiredConnectServer(Long userNo) {

        ConnectServerEntity connectServerEntity = connectServerRepository
                .findByUserNoAndStatus(userNo, ConnectServerStatus.CONNECTED)
                .orElse(null);

        if (connectServerEntity == null) {
            return;
        }

        connectServerEntity.setStatus(ConnectServerStatus.EXPIRED);
        connectServerEntity.setUpdated(new Date());
        connectServerRepository.save(connectServerEntity);

        saveConnectHistory(connectServerEntity);
    }

    public CurrentBotMessageListResponse getCurrentBotMessagesListByConnectId(CurrentBotMessageListByConnectIdRequest request) {

        ServiceManagerEntity serviceManagerEntity = serviceManagerRepository.findByServiceId(request.getServiceId())
                .orElseThrow(() -> new BusinessException(ServiceError.NOT_FOUND));

        ConnectUserEntity connectUserEntity = connectUserRepository
                .findByServiceNoAndConnectId(serviceManagerEntity.getServiceNo(), request.getConnectId())
                .orElseThrow(() -> new BusinessException(ConnectUserError.NOT_FOUND));

        CurrentBotMessageListByCidRequest currentBotMessageListByCidRequest = new CurrentBotMessageListByCidRequest();
        currentBotMessageListByCidRequest.setServiceId(request.getServiceId());
        currentBotMessageListByCidRequest.setCid(connectUserEntity.getCid());
        currentBotMessageListByCidRequest.setLatestMessageNo(request.getLatestMessageNo());
        ApiResponse<CurrentBotMessageListResponse> res = botClient.getCurrentBotMessagesListByCid(currentBotMessageListByCidRequest);
        if (res.isFail()) {
            throw new BusinessException(res.getCode(), res.getMessage());
        }
        return res.getResult();
    }
}
