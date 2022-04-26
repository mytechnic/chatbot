package com.example.domain.rest.gateway;

import com.example.domain.type.Channel;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TextUtteranceByConnectIdRequest {

    @Schema(description = "서비스 ID")
    private String serviceId;

    @Schema(description = "클라이언트용 고객 식별 ID")
    private String connectId;

    @Schema(description = "인입 채널")
    private Channel channel;

    @Schema(description = "인입 채널 ID")
    private String channelId;

    @Schema(description = "발화 메시지")
    private String message;
}
