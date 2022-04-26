package com.example.domain.type;

public enum Channel {
    PC, WEB, MOBILE;

    public boolean isWebSocket() {
        return this == PC || this == WEB || this == MOBILE;
    }
}
