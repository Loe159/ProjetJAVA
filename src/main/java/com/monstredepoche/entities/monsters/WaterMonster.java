package com.monstredepoche.entities.monsters;

import com.monstredepoche.utils.RandomUtils;

public class WaterMonster extends Monster {
    private static final double FLOOD_CHANCE = 0.3;

    public WaterMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.WATER, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility(Monster defender) {
        if (RandomUtils.tryChance(FLOOD_CHANCE)) {
            setInFloodedTerrain(true);
            System.out.println(getName() + " inonde le terrain !");
        }
    }
} 