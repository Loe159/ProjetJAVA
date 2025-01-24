package com.monstredepoche.entities;

public enum MonsterType {
    THUNDER, WATER, EARTH, FIRE, PLANT, INSECT;

    public double getEffectivenessAgainst(MonsterType target) {
        return switch (this) {
            case THUNDER -> switch (target) {
                case WATER -> 2.0;
                case EARTH -> 0.5;
                default -> 1.0;
            };
            case WATER -> switch (target) {
                case FIRE -> 2.0;
                case PLANT -> 0.5;
                default -> 1.0;
            };
            case EARTH -> switch (target) {
                case FIRE -> 2.0;
                case PLANT -> 0.5;
                default -> 1.0;
            };
            case FIRE -> switch (target) {
                case PLANT -> 2.0;
                case WATER -> 0.5;
                default -> 1.0;
            };
            case PLANT -> switch (target) {
                case WATER -> 2.0;
                case FIRE -> 0.5;
                default -> 1.0;
            };
            case INSECT -> switch (target) {
                case PLANT -> 2.0;
                case FIRE -> 0.5;
                default -> 1.0;
            };
        };
    }
} 