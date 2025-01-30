package com.monstredepoche.entities.monsters;

import com.monstredepoche.entities.StatusEffect;

public class FireMonster extends Monster {
    private static final double BURN_CHANCE = 0.3;

    public FireMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.FIRE, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility(Monster defender) {
        if (getRandom().nextDouble() < BURN_CHANCE) {
            defender.setStatus(StatusEffect.BURNED);
            System.out.println(getName() + " inflige une brûlure !");
        }
    }
} 