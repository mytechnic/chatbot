package com.example.domain.dto.bot;

import com.example.domain.dto.bot.component.AnswerMessage;
import com.example.domain.dto.bot.component.UtteranceMessage;
import com.example.domain.type.MessageWriteType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BotMessage {
    private Long messageNo;
    private MessageWriteType writeType;
    private UtteranceMessage utterance;
    private AnswerMessage answer;
    private Date created;
}
