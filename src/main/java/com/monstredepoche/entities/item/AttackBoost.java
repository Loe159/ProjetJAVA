package com.monstredepoche.entities.item;

import com.monstredepoche.entities.monsters.Monster;

public class AttackBoost extends StatBoostItem {
    public AttackBoost(String name, int boostAmount) {
        super(name, ItemType.ATTACK_BOOST, boostAmount);
    }

    @Override
    public void applyEffect(Monster target) {
        target.boostAttack(getBoostAmount());
    }
} 