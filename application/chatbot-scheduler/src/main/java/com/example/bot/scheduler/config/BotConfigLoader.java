package com.example.bot.scheduler.config;

import com.example.bot.config.client.BotConfigRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class BotConfigLoader {
    private final AppConfig appConfig;
    private final BotConfigRegister botConfigRegister;

    @PostConstruct
    private void load() {
        botConfigRegister.register(appConfig.getTopic().getBotConfig());
    }
}
