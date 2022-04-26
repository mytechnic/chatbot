package com.example.event.client.websocket;

import com.example.event.client.websocket.ChatEventWebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatEventWebSocketHandler extends TextWebSocketHandler {
    private final ChatEventWebSocketService chatEventWebSocketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        chatEventWebSocketService.register(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        chatEventWebSocketService.destroy(session);
        super.afterConnectionClosed(session, status);
    }
}
