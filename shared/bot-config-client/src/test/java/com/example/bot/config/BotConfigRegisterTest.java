package com.example.bot.config;

import com.example.BotConfigApplicationTests;
import com.example.bot.config.client.BotConfigRegister;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BotConfigRegisterTest extends BotConfigApplicationTests {

    @Autowired
    private BotConfigRegister botConfigRegister;

    @Test
    void register() {
        System.out.println(botConfigRegister);
    }
}