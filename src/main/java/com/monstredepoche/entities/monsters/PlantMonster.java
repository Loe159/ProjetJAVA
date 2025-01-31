package com.monstredepoche.entities.monsters;

public class PlantMonster extends Monster {
    private static final int HEAL_AMOUNT = 20;

    public PlantMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.PLANT, maxHp, attack, defense, speed);
    }

    @Override
    public void useSpecialAbility(Monster defender) {
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