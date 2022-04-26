package com.example.bot.scheduler.task;

import com.example.bot.config.client.BotConfigCacheRepository;
import com.example.client.app.gateway.GatewayClient;
import com.example.core.utils.ObjectMapperUtils;
import com.example.domain.dto.bot.BotConfig;
import com.example.domain.rest.gateway.BotEventRequest;
import com.example.domain.type.*;
import com.example.rds.bot.entity.BotSessionEntity;
import com.example.rds.bot.entity.MessageEntity;
import com.example.rds.bot.repository.BotSessionRepository;
import com.example.rds.bot.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotService {
    private final BotSessionRepository botSessionRepository;
    private final MessageRepository messageRepository;
    private final GatewayClient gatewayClient;
    private final BotConfigCacheRepository botConfigCacheRepository;

    public void expireMessageSession() {

        BotConfig botConfig = botConfigCacheRepository.getBotConfig();
        String serviceId = botConfig.getServiceId();
        Date thresholdDate = new Date(new Date().getTime() - botConfig.getSessionTimeoutSec());
        List<BotSessionEntity> botSessionEntityList
                = botSessionRepository.findByServiceIdAndStatusAndCreatedLessThan(serviceId, BotFlowStatus.STARTED, thresholdDate);
        for (BotSessionEntity sessionEntity : botSessionEntityList) {
            boolean isExistsMessage = sessionEntity.getMessageCreated() != null;
            BotMessageEvent eventType;
            if (isExistsMessage) {
                eventType = BotMessageEvent.TIMEOUT;
                sessionEntity.setMessageUpdated(new Date());
            } else {
                eventType = BotMessageEvent.TIMEOUT_ONLY;
            }
            sessionEntity.setStatus(BotFlowStatus.EXPIRED);
            sessionEntity.setUpdated(new Date());
            botSessionRepository.save(sessionEntity);

            if (isExistsMessage) {
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setServiceId(sessionEntity.getServiceId());
                messageEntity.setCid(sessionEntity.getCid());
                messageEntity.setSessionId(sessionEntity.getSessionId());
                messageEntity.setScenarioType(ScenarioType.BASIC);
                messageEntity.setScenario(ScenarioType.BASIC.name());
                messageEntity.setBlockType(BlockType.EXIT);
                messageEntity.setBlock(BlockType.EXIT.name());
                messageEntity.setChannel(sessionEntity.getChannel());
                messageEntity.setChannelId(sessionEntity.getChannelId());
                messageEntity.setWriterType(MessageWriteType.BOT);
                messageEntity.setMessage(ObjectMapperUtils.serializeAsString(botConfig.getResponseMessage(BotResourceType.TIMEOUT), null));
                messageEntity.setCreated(new Date());
                messageRepository.save(messageEntity);
            }

            botTimeoutEvent(eventType, sessionEntity);
        }
    }

    private void botTimeoutEvent(BotMessageEvent eventType, BotSessionEntity entity) {
        BotEventRequest request = new BotEventRequest();
        request.setEventType(eventType);
        request.setServiceId(entity.getServiceId());
        request.setChannel(entity.getChannel());
        request.setChannelId(entity.getChannelId());
        request.setCid(entity.getCid());
        gatewayClient.botEvent(request);
    }
}

