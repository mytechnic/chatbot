package com.example.event.server;

import com.example.core.utils.ObjectMapperUtils;
import com.example.domain.dto.event.ChatEventClientMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClientEventPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(String channelName, ChatEventClientMessage message) {
        redisTemplate.convertAndSend(channelName, ObjectMapperUtils.serializeAsString(message, null));
    }
}