package com.example.event.client.websocket;

import com.example.domain.dto.event.ChatEventClientMessage;
import com.example.event.client.dto.ChatEventWebSocketSession;
import com.example.websocket.utils.WebSocketUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


@Slf4j
@Component
@RequiredArgsConstructor
public class ChatEventWebSocketService {
    private final Map<String, ChatEventWebSocketSession> connections = new ConcurrentHashMap<>();
    private final Map<String, List<ChatEventWebSocketSession>> subscribers = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void register(WebSocketSession session) {
        ChatEventWebSocketSession chatSession = getWebSocketEventSession(session);
        String topic = chatSession.getConnectId();
        if (!subscribers.containsKey(topic)) {
            subscribers.put(topic, new CopyOnWriteArrayList<>());
        }
        subscribers.get(topic).add(chatSession);
        connections.put(chatSession.getSession().getId(), chatSession);
        log.debug("subscribers size: {}, connections size: {}", subscribers.size(), connections.size());
    }

    public void serverToClientGroupMessage(ChatEventClientMessage message) throws JsonProcessingException {
        String topic = message.getConnectId();
        List<ChatEventWebSocketSession> subscriberSessions = subscribers.get(topic);
        if (subscribers.get(topic).isEmpty()) {
            return;
        }

        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(message.getData()));
        for (ChatEventWebSocketSession subscriber : subscriberSessions) {
            try {
                subscriber.getSession().sendMessage(textMessage);
            } catch (IOException e) {
                log.warn("websocket publish error = {}", e.getMessage());
                log.info("", e);
            }
        }
    }

    public void destroy(WebSocketSession session) {
        ChatEventWebSocketSession chatSession = connections.get(session.getId());
        if (chatSession == null) {
            return;
        }
        String topic = chatSession.getConnectId();
        subscribers.get(topic).remove(chatSession);
        if (subscribers.get(topic).isEmpty()) {
            subscribers.remove(topic);
        }
        connections.remove(session.getId());
    }

    private ChatEventWebSocketSession getWebSocketEventSession(WebSocketSession session) {
        Map<String, String> parameterMap = WebSocketUtils.getParameterMap(session.getUri());
        String serviceId = parameterMap.get("serviceId");
        String connectId = parameterMap.get("connectId");

        return ChatEventWebSocketSession.builder()
                .serviceId(serviceId)
                .connectId(connectId)
                .session(session)
                .build();
    }
}
