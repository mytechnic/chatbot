package com.example.bot.config.server;

import com.example.core.utils.ObjectMapperUtils;
import com.example.domain.dto.bot.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BotConfigPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(String channelName, BotConfig botConfig) {
        redisTemplate.convertAndSend(channelName, ObjectMapperUtils.serializeAsString(botConfig, null));
    }
}