package com.example.domain.rest.bot;

import com.example.domain.dto.bot.component.UtteranceMessage;
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
public class BotUtteranceMessageRequest {

    @Schema(description = "식별 키")
    private BotPrimaryKey key;

    @Schema(description = "발화 메시지")
    private UtteranceMessage utterance;
}
