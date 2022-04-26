package com.example.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotResourceType {
    WELCOME("welcome"),
    RETRY("retry"),
    FALLBACK("fallback"),
    TIMEOUT("timeout");

    private String responseId;
}
