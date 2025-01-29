package com.monstredepoche.entities.monsters;

public class NatureMonster extends Monster {
    public NatureMonster(String name, MonsterType type, int maxHp, int attack, int defense, int speed) {
        super(name, type, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility() {
        // La guérison est gérée dans startTurn
    }

    @Override
    public void startTurn() {
        super.startTurn();
        if (isInFloodedTerrain()) {
            int heal = this.getMaxHp() / 20;
            heal(heal);
            System.out.println(this.getName() + " récupère " + heal + " PV grâce à l'eau !");
        }
    }
} 