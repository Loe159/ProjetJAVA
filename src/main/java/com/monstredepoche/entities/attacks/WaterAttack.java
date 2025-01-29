package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class WaterAttack extends Attack {
    public WaterAttack(String name, int power, int maxUses, double failRate) {
        super(name, AttackType.WATER, power, maxUses, failRate);
    }

    @Override
    public double getEffectivenessAgainst(MonsterType attackerType, MonsterType defenderType) {
        return switch (defenderType) {
            case FIRE -> 2.0;   // Super efficace contre le feu
            case THUNDER -> 0.5;  // Peu efficace contre la foudre
            default -> 1.0;     // Efficacité normale
        };
    }
} 