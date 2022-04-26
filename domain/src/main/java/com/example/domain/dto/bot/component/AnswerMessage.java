package com.example.domain.dto.bot.component;

import com.example.domain.dto.bot.component.organisms.Row;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AnswerMessage {
    private String answerId;
    private String classId;
    private List<Row> rows = new ArrayList<>();
}
