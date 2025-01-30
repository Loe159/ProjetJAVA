package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class BareHandedAttack extends Attack {
    public BareHandedAttack() {
        super("BareHanded", AttackType.BAREHANDED, 0, 1, 0.0);
    }

    @Override
    public double getEffectivenessAgainst(MonsterType attackerType, MonsterType defenderType) {
        return 1.0;
    }
} 