package com.example.domain.dto.event;

import com.example.domain.type.BotMessageEvent;
import com.example.domain.type.Channel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ChatEventClientMessage {
    private String serviceId;
    private String connectId;
    private Body data;

    public ChatEventClientMessage(String serviceId, String connectId, Channel channel, String channelId, BotMessageEvent eventType) {
        this.serviceId = serviceId;
        this.connectId = connectId;
        this.data = new Body(channel, channelId, eventType);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class Body {
        private Channel channel;
        private String channelId;
        private BotMessageEvent eventType;
    }
}
