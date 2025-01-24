package com.monstredepoche.entities.monsters;

import com.monstredepoche.entities.MonsterType;

public class PlantMonster extends Monster {
    private static final int HEAL_AMOUNT = 20;

    public PlantMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.PLANT, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility() {
        // La guérison est gérée dans startTurn
    }

    @Override
    public void startTurn() {
        super.startTurn();
        if (isInFloodedTerrain()) {
            heal(HEAL_AMOUNT);
        }
    }
} 