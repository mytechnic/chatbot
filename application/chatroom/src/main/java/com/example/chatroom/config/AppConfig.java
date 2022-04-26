package com.example.chatroom.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Setter
@Getter
@ToString
public class AppConfig {
    private Topic topic;

    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class Topic {
        private String chatEvent;
        private String chatRoomEvent;
    }
}
