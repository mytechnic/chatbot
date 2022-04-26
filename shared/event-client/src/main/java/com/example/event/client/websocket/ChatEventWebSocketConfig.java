package com.example.event.client.websocket;

import com.example.event.client.config.ChatEventConst;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class ChatEventWebSocketConfig implements WebSocketConfigurer {
    private final ChatEventWebSocketHandler chatEventWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatEventWebSocketHandler, ChatEventConst.WEBSOCKET_CONNECT_CHANNEL)
                .withSockJS();
    }
}
