package com.monstredepoche.entities.monsters;

import com.monstredepoche.entities.MonsterType;
import com.monstredepoche.entities.StatusEffect;

public class ThunderMonster extends Monster {
    private static final double PARALYZE_CHANCE = 0.3;

    public ThunderMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.THUNDER, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility() {
        if (getRandom().nextDouble() < PARALYZE_CHANCE) {
            setStatus(StatusEffect.PARALYZED);
            System.out.println(getName() + " paralyse sa cible !");
        }
    }
} 