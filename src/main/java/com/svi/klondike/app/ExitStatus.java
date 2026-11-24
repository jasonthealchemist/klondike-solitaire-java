package com.svi.klondike.app;

public enum ExitStatus {
    SUCCESS(0),
    UNEXPECTED_ERROR(1),
    CONFIGURATION_ERROR(2),
    DECK_ERROR(3),
    FILE_ERROR(4);

    private final int code;

    ExitStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}