package com.example.domain.dto.dm;

import com.example.domain.type.BlockType;
import com.example.domain.type.ScenarioType;
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
public class NlpContext {
    private String intentId;
    private ScenarioType scenarioType;
    private String scenario;
    private BlockType blockType;
    private String block;
    private String responseId;
    private String message;
}
