package com.svi.klondike.config;

public enum ShuffleType {
    RANDOM("Random shuffle", false),
    FARO_OUT("Out Faro shuffle", true),
    FARO_IN("In Faro shuffle", true),
    NONE("No shuffle", false);

    private final String label;
    private final boolean faro;

    ShuffleType(String label, boolean faro) {
        this.label = label;
        this.faro = faro;
    }

    public String getLabel() {
        return label;
    }

    public boolean isFaro() {
        return faro;
    }
}
