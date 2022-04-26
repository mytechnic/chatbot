package com.example.domain.dto.dm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotContext {
    private List<ScenarioContext> scenario = new ArrayList<>();
    private Integer scenarioPosition;
    private Integer blockPosition;
    private NlpContext nlpContext;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ScenarioContext {
        private List<BlockContext> block = new ArrayList<>();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class BlockContext {
        private Map<String, ParameterValue> parameter = new HashMap<>();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ParameterValue {
        private String type;
        private Object value;
    }
}
