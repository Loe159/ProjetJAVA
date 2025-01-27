package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public enum AttackType {
    NORMAL(null),
    THUNDER(MonsterType.THUNDER),
    WATER(MonsterType.WATER),
    EARTH(MonsterType.EARTH),
    FIRE(MonsterType.FIRE),
    PLANT(MonsterType.PLANT),
    INSECT(MonsterType.INSECT),
    BAREHANDED(null);

    private final MonsterType correspondingType;

    AttackType(MonsterType type) {
        this.correspondingType = type;
    }

    public MonsterType getCorrespondingType() {
        return correspondingType;
    }
}