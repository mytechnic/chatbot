package com.example.domain.dto.bot;

import com.example.domain.dto.bot.component.AnswerMessage;
import com.example.domain.type.BotResourceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BotConfig {
    private String serviceId = "star-chatbot";
    private Integer sessionTimeoutSec = 30;
    private BotResource resource = new BotResource();

    public AnswerMessage getResponseMessage(BotResourceType type) {
        return resource.getResponseMap().get(type.getResponseId());
    }
}
