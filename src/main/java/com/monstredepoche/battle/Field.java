package com.monstredepoche.battle;

public class Field {
    private boolean isFlooded;

    public Field() {
        this.isFlooded = false;
    }

    public boolean isFlooded() {
        return isFlooded;
    }

    public void setFlooded(boolean flooded) {
        this.isFlooded = flooded;
    }
} 