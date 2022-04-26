package com.example.event.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

@Builder
@Setter
@Getter
@ToString
public class ChatEventWebSocketSession {
    private String serviceId;
    private String connectId;
    private WebSocketSession session;
}
