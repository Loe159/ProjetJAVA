package com.monstredepoche.entities.monsters;

import com.monstredepoche.utils.RandomUtils;

public class WaterMonster extends Monster {
    private static final double FLOOD_CHANCE = 0.3;
    private static final double SLIP_CHANCE = 0.3;
    private int floodTurnsRemaining;

    public WaterMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.WATER, maxHp, attack, defense, speed);
        this.floodTurnsRemaining = 0;
    }

    @Override
    public void useSpecialAbility(Monster defender) {
        if (RandomUtils.tryChance(FLOOD_CHANCE)) {
            if (isInFloodedTerrain()) return;

            floodTurnsRemaining = RandomUtils.getRandomInt(1, 3); // 1 à 3 tours
            setInFloodedTerrain(true);
            System.out.println(getName() + " inonde le terrain pour " + floodTurnsRemaining + " tours !");
        }
    }

    @Override
    public void startTurn() {
        super.startTurn();
        if (isInFloodedTerrain() && floodTurnsRemaining > 0) {
            floodTurnsRemaining--;
            if (floodTurnsRemaining <= 0) {
                setInFloodedTerrain(false);
                System.out.println("Le terrain n'est plus inondé !");
            }
        }
    }

    public static boolean shouldSlip(Monster attacker) {
        return attacker.getType() != MonsterType.WATER && 
               attacker.isInFloodedTerrain() && 
               RandomUtils.tryChance(SLIP_CHANCE);
    }
} 