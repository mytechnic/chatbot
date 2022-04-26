package com.example.domain.rest.bot;

import com.example.domain.dto.bot.BotMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Schema
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CurrentBotMessageListResponse {

    @Schema(description = "메시지 목록")
    private List<BotMessage> messages = new ArrayList<>();
}
