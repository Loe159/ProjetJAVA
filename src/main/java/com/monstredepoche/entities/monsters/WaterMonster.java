package com.monstredepoche.entities.monsters;

import com.monstredepoche.entities.MonsterType;

public class WaterMonster extends Monster {
    private static final double FLOOD_CHANCE = 0.3;

    public WaterMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.WATER, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility() {
        if (getRandom().nextDouble() < FLOOD_CHANCE) {
            setInFloodedTerrain(true);
            System.out.println(getName() + " inonde le terrain !");
        }
    }
} 