package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class ThunderAttack extends Attack {
    public ThunderAttack(String name, int power, int maxUses, double failRate) {
        super(name, AttackType.THUNDER, power, maxUses, failRate);
    }

    @Override
    public double getEffectivenessAgainst(MonsterType attackerType, MonsterType defenderType) {
        return switch (defenderType) {
            case WATER -> 2.0;  // Super efficace contre l'eau
            case EARTH -> 0.5;  // Peu efficace contre la terre
            default -> 1.0;     // Efficacité normale
        };
    }

    @Override
    public void applySpecialEffect() {
        // Les effets spéciaux sont gérés par le monstre
    }
} 