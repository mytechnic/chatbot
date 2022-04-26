package com.example.bot.config.client;

import com.example.core.utils.ObjectMapperUtils;
import com.example.domain.dto.bot.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BotConfigRegister {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final BotConfigCacheRepository botConfigCacheRepository;

    public void register(String channelName) {
        redisMessageListenerContainer.addMessageListener((message, pattern) -> {
            try {
                if (message.getBody().length == 0) {
                    return;
                }

                BotConfig botConfig = ObjectMapperUtils.deserialize(message.getBody(), BotConfig.class);
                log.info("botConfig: {}", botConfig);
                botConfigCacheRepository.setBotConfig(botConfig);
            } catch (Exception e) {
                log.warn("redis chat listener error = {}", e.getMessage());
                log.info("", e);
            }
        }, new ChannelTopic(channelName));
    }
}