package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class WaterAttack extends Attack {
    public WaterAttack(String name, int power, int maxUses, double failRate) {
        super(name, AttackType.WATER, power, maxUses, failRate);
    }

    @Override
    public double getEffectivenessAgainst(MonsterType defenderType) {
        return switch (defenderType) {
            case FIRE -> 2.0;   // Super efficace contre le feu
            case PLANT -> 0.5;  // Peu efficace contre les plantes
            default -> 1.0;     // Efficacité normale
        };
    }
} 