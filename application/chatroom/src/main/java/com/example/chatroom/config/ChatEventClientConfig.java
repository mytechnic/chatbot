package com.example.chatroom.config;

import com.example.event.client.ChatEventRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ChatEventClientConfig {
    private final AppConfig appConfig;
    private final ChatEventRegister chatEventRegister;

    @PostConstruct
    private void load() {
        chatEventRegister.register(appConfig.getTopic().getChatEvent());
    }
}
