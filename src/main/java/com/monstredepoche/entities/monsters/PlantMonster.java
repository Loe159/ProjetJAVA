package com.monstredepoche.entities.monsters;

import com.monstredepoche.entities.StatusEffect;

public class PlantMonster extends NatureMonster {
    private static final double HEAL_CHANCE = 0.3;

    public PlantMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.PLANT, maxHp, attack, defense, speed);
    }

    @Override
    public void startTurn() {
        super.startTurn();

        if (getStatus() == StatusEffect.NORMAL);

        if (getRandom().nextDouble() < HEAL_CHANCE) {
            setStatus(StatusEffect.NORMAL);
            System.out.println(getName() + " s'est soigné !");
        }
    }
}