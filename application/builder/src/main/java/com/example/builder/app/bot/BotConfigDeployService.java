package com.example.builder.app.bot;

import com.example.bot.config.server.BotConfigPublisher;
import com.example.builder.config.AppConfig;
import com.example.domain.dto.bot.BotConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BotConfigDeployService {
    private final AppConfig appConfig;
    private final BotConfigPublisher botConfigPublisher;

    public void deploy() {
        BotConfig botConfig = new BotConfig();
        botConfigPublisher.publish(appConfig.getTopic().getBotConfig(), botConfig);
    }
}
