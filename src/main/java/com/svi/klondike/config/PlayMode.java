package com.svi.klondike.config;

public enum PlayMode {
    SINGLE_GAME("Play one game"),
    SEVERAL_GAMES("Play several games");

    private final String label;

    PlayMode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
