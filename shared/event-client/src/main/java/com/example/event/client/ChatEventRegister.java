package com.example.event.client;

import com.example.core.utils.ObjectMapperUtils;
import com.example.domain.dto.event.ChatEventClientMessage;
import com.example.event.client.websocket.ChatEventWebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatEventRegister {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final ChatEventWebSocketService chatEventWebSocketService;

    public void register(String channelName) {
        redisMessageListenerContainer.addMessageListener((message, pattern) -> {
                    try {
                        if (message.getBody().length == 0) {
                            return;
                        }
                        log.debug("source message: {}", message);

                        ChatEventClientMessage clientMessage = ObjectMapperUtils.deserialize(message.getBody(), ChatEventClientMessage.class, null);
                        log.debug("chat event message: {}", clientMessage);
                        if (clientMessage == null) {
                            return;
                        }
                        chatEventWebSocketService.serverToClientGroupMessage(clientMessage);
                    } catch (Exception e) {
                        log.warn("redis chat listener error = {}", e.getMessage());
                        log.info("", e);
                    }
                },
                new ChannelTopic(channelName));
    }
}