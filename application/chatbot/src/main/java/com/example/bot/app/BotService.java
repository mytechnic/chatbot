package com.example.bot.app;

import com.example.client.app.gateway.GatewayClient;
import com.example.core.utils.ObjectMapperUtils;
import com.example.domain.dto.bot.BotMessage;
import com.example.domain.dto.bot.component.AnswerMessage;
import com.example.domain.dto.bot.component.UtteranceMessage;
import com.example.domain.dto.dm.BotContext;
import com.example.domain.dto.dm.NlpContext;
import com.example.domain.rest.bot.BotPrimaryKey;
import com.example.domain.rest.bot.BotUtteranceMessageRequest;
import com.example.domain.rest.bot.CurrentBotMessageListByCidRequest;
import com.example.domain.rest.bot.CurrentBotMessageListResponse;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotService {
    private final BotSessionRepository botSessionRepository;
    private final MessageRepository messageRepository;
    private final GatewayClient gatewayClient;

    public String getSessionId(BotPrimaryKey request) {

        BotSessionEntity botSessionEntity = getCurrentBotSessionEntity(request);

        return botSessionEntity.getSessionId();
    }

    private BotSessionEntity getCurrentBotSessionEntity(BotPrimaryKey request) {
        BotSessionEntity botSessionEntity = botSessionRepository.findByServiceIdAndCid(request.getServiceId(), request.getCid())
                .orElse(null);

        if (botSessionEntity == null) {
            botSessionEntity = new BotSessionEntity();
            botSessionEntity.setServiceId(request.getServiceId());
            botSessionEntity.setCid(request.getCid());
            botSessionEntity.setSessionId(getGenerateSessionId());
            botSessionEntity.setChannel(request.getChannel());
            botSessionEntity.setChannelId(request.getChannelId());
            botSessionEntity.setStatus(BotFlowStatus.STARTED);
            botSessionEntity.setContext(null);
            botSessionEntity.setMessageCreated(null);
            botSessionEntity.setMessageUpdated(null);
            botSessionEntity.setCreated(new Date());
            botSessionRepository.save(botSessionEntity);
        } else if (botSessionEntity.getStatus() != BotFlowStatus.STARTED) {
            botSessionEntity.setSessionId(getGenerateSessionId());
            botSessionEntity.setChannel(request.getChannel());
            botSessionEntity.setChannelId(request.getChannelId());
            botSessionEntity.setStatus(BotFlowStatus.STARTED);
            botSessionEntity.setContext(null);
            botSessionEntity.setMessageCreated(null);
            botSessionEntity.setMessageUpdated(null);
            botSessionEntity.setUpdated(new Date());
            botSessionRepository.save(botSessionEntity);
        }
        return botSessionEntity;
    }

    private String getGenerateSessionId() {
        return "SES:" + UUID.randomUUID().toString().replace("-", "");
    }

    public void utterance(BotUtteranceMessageRequest request) {

        BotSessionEntity botSessionEntity = getCurrentBotSessionEntity(request.getKey());

        BotContext botContext = getBoContext(request);
        botSessionEntity.setContext(ObjectMapperUtils.serializeAsString(botContext, null));
        if (botSessionEntity.getMessageCreated() == null) {
            botSessionEntity.setMessageCreated(new Date());
        }
        botSessionEntity.setMessageUpdated(new Date());
        botSessionRepository.save(botSessionEntity);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setServiceId(request.getKey().getServiceId());
        messageEntity.setCid(request.getKey().getCid());
        messageEntity.setSessionId(botSessionEntity.getSessionId());
        messageEntity.setScenarioType(botContext.getNlpContext().getScenarioType());
        messageEntity.setScenario(botContext.getNlpContext().getScenario());
        messageEntity.setBlockType(botContext.getNlpContext().getBlockType());
        messageEntity.setBlock(botContext.getNlpContext().getBlock());
        messageEntity.setChannel(request.getKey().getChannel());
        messageEntity.setChannelId(request.getKey().getChannelId());
        messageEntity.setWriterType(MessageWriteType.USER);
        messageEntity.setMessage(ObjectMapperUtils.serializeAsString(request.getUtterance(), null));
        messageEntity.setCreated(new Date());
        messageRepository.save(messageEntity);

        utteranceSaveEvent(botSessionEntity);
    }

    private void utteranceSaveEvent(BotSessionEntity entity) {
        BotEventRequest request = new BotEventRequest();
        request.setEventType(BotMessageEvent.MESSAGE);
        request.setServiceId(entity.getServiceId());
        request.setChannel(entity.getChannel());
        request.setChannelId(entity.getChannelId());
        request.setCid(entity.getCid());
        gatewayClient.botEvent(request);
    }

    // TODO: NLP 컨텍스트 가져오는 로직 추가
    private BotContext getBoContext(BotUtteranceMessageRequest request) {
        BotContext botContext = new BotContext();

        NlpContext nlpContext = new NlpContext();
        nlpContext.setIntentId("int.0001");
        nlpContext.setScenarioType(ScenarioType.CUSTOM);
        nlpContext.setScenario("TEST");
        nlpContext.setBlockType(BlockType.CUSTOM);
        nlpContext.setBlock("TEST");
        nlpContext.setResponseId("res.0001");
        nlpContext.setMessage(request.getUtterance().getText());
        botContext.setNlpContext(nlpContext);

        return botContext;
    }

    public CurrentBotMessageListResponse getCurrentBotMessagesListByCid(CurrentBotMessageListByCidRequest request) {

        CurrentBotMessageListResponse response = new CurrentBotMessageListResponse();

        BotSessionEntity botSessionEntity = botSessionRepository.findByServiceIdAndCid(request.getServiceId(), request.getCid())
                .orElse(null);
        if (botSessionEntity == null) {
            return response;
        }

        List<MessageEntity> messageEntityList;
        if (request.getLatestMessageNo() != null) {
            messageEntityList = messageRepository.findByServiceIdAndCidAndMessageNoGreaterThanOrderByMessageNoDesc(
                    request.getServiceId(), botSessionEntity.getCid(), request.getLatestMessageNo());
        } else {
            messageEntityList = messageRepository.findByServiceIdAndCidAndSessionIdOrderByMessageNoDesc(
                    request.getServiceId(), botSessionEntity.getCid(), botSessionEntity.getSessionId());
        }

        response.setMessages(messageEntityList.stream().map(this::getBotMessage).collect(Collectors.toList()));
        return response;
    }

    private BotMessage getBotMessage(MessageEntity entity) {
        BotMessage botMessage = new BotMessage();
        botMessage.setMessageNo(entity.getMessageNo());
        botMessage.setWriteType(entity.getWriterType());
        if (entity.getWriterType() == MessageWriteType.USER) {
            botMessage.setUtterance(ObjectMapperUtils.deserialize(entity.getMessage(), UtteranceMessage.class, null));
        }
        if (entity.getWriterType() == MessageWriteType.BOT) {
            botMessage.setAnswer(ObjectMapperUtils.deserialize(entity.getMessage(), AnswerMessage.class, null));
        }
        botMessage.setCreated(entity.getCreated());
        return botMessage;
    }
}

