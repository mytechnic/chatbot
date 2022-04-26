package com.example.domain.dto.bot.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UtteranceMessage {
    private String text;

    public UtteranceMessage(String text) {
        this.text = text;
    }
}
