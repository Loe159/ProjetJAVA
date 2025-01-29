package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class EarthAttack extends Attack {
    public EarthAttack(String name, int power, int maxUses, double failRate) {
        super(name, AttackType.EARTH, power, maxUses, failRate);
    }

    @Override
    public double getEffectivenessAgainst(MonsterType attackerType, MonsterType defenderType) {
        return switch (defenderType) {
            case THUNDER -> 2.0;  // Super efficace contre la foudre
            case WATER -> 0.5;    // Peu efficace contre l'eau
            default -> 1.0;       // Efficacité normale
        };
    }
} 