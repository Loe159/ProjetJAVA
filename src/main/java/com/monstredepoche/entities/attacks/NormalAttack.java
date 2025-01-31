package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class NormalAttack extends Attack {
    public NormalAttack(String name, int power, int maxUses, double failRate) {
        super(name, AttackType.NORMAL, power, maxUses, failRate);
    }

    @Override
    public double getEffectivenessAgainst(MonsterType defenderType) {
        return 1.0;            // Efficacité normale
    }
} 