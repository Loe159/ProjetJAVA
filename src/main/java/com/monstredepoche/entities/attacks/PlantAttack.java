package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class PlantAttack extends Attack {
    public PlantAttack(String name, int power, int maxUses, double failRate) {
        super(name, AttackType.PLANT, power, maxUses, failRate);
    }

    @Override
    public double getEffectivenessAgainst(MonsterType defenderType) {
        return switch (defenderType) {
            case WATER, EARTH -> 2.0;  // Super efficace contre l'eau et la terre
            case FIRE, INSECT -> 0.5;  // Peu efficace contre le feu et les insectes
            default -> 1.0;            // Efficacité normale
        };
    }
} 