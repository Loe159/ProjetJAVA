package com.monstredepoche.entities.monsters;

import com.monstredepoche.entities.MonsterType;
import com.monstredepoche.entities.StatusEffect;

public class InsectMonster extends Monster {
    private static final double POISON_CHANCE = 0.3;

    public InsectMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.INSECT, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility() {
        if (getRandom().nextDouble() < POISON_CHANCE) {
            setStatus(StatusEffect.POISONED);
            System.out.println(getName() + " empoisonne sa cible !");
        }
    }
} 