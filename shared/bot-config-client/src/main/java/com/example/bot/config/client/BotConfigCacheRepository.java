package com.example.bot.config.client;

import com.example.domain.dto.bot.BotConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Setter
@Getter
public class BotConfigCacheRepository {
    private BotConfig botConfig = new BotConfig();
}
