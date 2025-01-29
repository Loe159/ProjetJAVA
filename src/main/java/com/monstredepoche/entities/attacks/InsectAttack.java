package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class InsectAttack extends Attack {
    public InsectAttack(String name, int power, int maxUses, double failRate) {
        super(name, AttackType.INSECT, power, maxUses, failRate);
    }

    @Override
    public double getEffectivenessAgainst(MonsterType attackerType, MonsterType defenderType) {
        return switch (defenderType) {
            case PLANT -> 2.0;  // Super efficace contre les plantes
            case FIRE -> 0.5;   // Peu efficace contre le feu
            default -> 1.0;     // Efficacité normale
        };
    }
} 