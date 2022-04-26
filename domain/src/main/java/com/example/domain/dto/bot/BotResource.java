package com.example.domain.dto.bot;

import com.example.domain.dto.bot.component.AnswerMessage;
import com.example.domain.dto.bot.component.molecules.Text;
import com.example.domain.dto.bot.component.organisms.Cell;
import com.example.domain.dto.bot.component.organisms.CellType;
import com.example.domain.dto.bot.component.organisms.Row;
import com.example.domain.type.BotResourceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@ToString
public class BotResource {
    private String fallbackId;
    private String timeoutId;
    private Map<String, AnswerMessage> responseMap = new HashMap<>();

    public BotResource() {
        responseMap.put(BotResourceType.WELCOME.getResponseId(), getWelcomeResponseMessage());
        responseMap.put(BotResourceType.FALLBACK.getResponseId(), getFallbackResponseMessage());
        responseMap.put(BotResourceType.TIMEOUT.getResponseId(), getTimeoutResponseMessage());
        responseMap.put(BotResourceType.RETRY.getResponseId(), getRetryResponseMessage());
    }

    private AnswerMessage getWelcomeResponseMessage() {
        return getTextResponseMessage(BotResourceType.WELCOME, new String[]{
                "안녕하세요~ 방가 방가~",
                "어서오세요"
        });
    }

    private AnswerMessage getFallbackResponseMessage() {
        return getTextResponseMessage(BotResourceType.FALLBACK, "답변을 찾을 수 없습니다.");
    }

    private AnswerMessage getTimeoutResponseMessage() {
        return getTextResponseMessage(BotResourceType.TIMEOUT, "챗봇을 종료합니다. 이용해 주셔서 감사합니다.");
    }

    private AnswerMessage getRetryResponseMessage() {
        return getTextResponseMessage(BotResourceType.RETRY, "챗봇을 종료합니다. 이용해 주셔서 감사합니다.");
    }

    private AnswerMessage getTextResponseMessage(BotResourceType type, String[] messages) {
        return getTextResponseMessage(type, List.of(messages), true);
    }

    private AnswerMessage getTextResponseMessage(BotResourceType type, String message) {
        return getTextResponseMessage(type, Collections.singletonList(message), false);
    }

    private AnswerMessage getTextResponseMessage(BotResourceType type, List<String> messages, boolean isRandom) {

        AnswerMessage responseMessage = new AnswerMessage();
        responseMessage.setAnswerId(type.getResponseId());

        List<Cell> cells = new ArrayList<>();
        for (String message : messages) {
            Cell cell = new Cell();
            cell.setType(CellType.TEXT);

            Text text = new Text();
            text.setMessage(message);
            cell.setText(text);

            cells.add(cell);
        }
        Row row = new Row();
        row.setRandom(isRandom);
        row.setCells(cells);
        responseMessage.setRows(Collections.singletonList(row));
        return responseMessage;
    }
}
