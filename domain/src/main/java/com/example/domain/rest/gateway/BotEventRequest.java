package com.example.domain.rest.gateway;

import com.example.domain.type.BotMessageEvent;
import com.example.domain.type.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotEventRequest {

    @Schema(description = "이벤트 타입")
    private BotMessageEvent eventType;

    @Schema(description = "서비스 ID")
    private String serviceId;

    @Schema(description = "내부용 고객 식별 ID")
    private String cid;

    @Schema(description = "인입 채널")
    private Channel channel;

    @Schema(description = "인입 채널 ID")
    private String channelId;
}
