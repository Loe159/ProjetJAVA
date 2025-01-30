package com.monstredepoche.entities.monsters;

import com.monstredepoche.entities.StatusEffect;

public class InsectMonster extends Monster {
    private static final double POISON_CHANCE = 0.3;

    public InsectMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.INSECT, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility(Monster defender) {
        if (getRandom().nextDouble() < POISON_CHANCE) {
            defender.setStatus(StatusEffect.POISONED);
            System.out.println(getName() + " empoisonne sa cible !");
        }
    }
} 