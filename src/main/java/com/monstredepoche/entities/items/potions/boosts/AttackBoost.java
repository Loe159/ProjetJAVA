package com.monstredepoche.entities.items.potions.boosts;

import com.monstredepoche.entities.items.ItemType;
import com.monstredepoche.entities.monsters.Monster;

public class AttackBoost extends BoostPotion {
    public AttackBoost(String name, int boostAmount) {
        super(name, ItemType.ATTACK_BOOST, boostAmount);
    }

    @Override
    public void applyEffect(Monster target) {
        target.boostAttack(getBoostAmount());
    }
} 