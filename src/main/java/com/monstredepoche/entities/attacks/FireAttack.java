package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class FireAttack extends Attack {
    public FireAttack(String name, int power, int maxUses, double failRate) {
        super(name, AttackType.FIRE, power, maxUses, failRate);
    }

    @Override
    public double getEffectivenessAgainst(MonsterType attackerType, MonsterType defenderType) {
        return switch (defenderType) {
            case PLANT, INSECT -> 2.0;  // Super efficace contre les plantes et les insectes
            case WATER -> 0.5;          // Peu efficace contre l'eau
            default -> 1.0;             // Efficacité normale
        };
    }

    @Override
    public void applySpecialEffect() {
        // Les effets spéciaux sont gérés par le monstre
    }
} 