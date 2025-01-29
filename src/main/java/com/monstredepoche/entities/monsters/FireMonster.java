package com.monstredepoche.entities.monsters;

import com.monstredepoche.entities.StatusEffect;

public class FireMonster extends Monster {
    private static final double BURN_CHANCE = 1.0;

    public FireMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.FIRE, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility() {
        if (getRandom().nextDouble() < BURN_CHANCE) {
            setStatus(StatusEffect.BURNED);
            System.out.println(getName() + " inflige une brûlure !");
        }
    }
} 