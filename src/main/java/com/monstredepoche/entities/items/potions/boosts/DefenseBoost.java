package com.monstredepoche.entities.items.potions.boosts;

import com.monstredepoche.entities.items.ItemType;
import com.monstredepoche.entities.monsters.Monster;

public class DefenseBoost extends BoostPotion {
    public DefenseBoost(String name, int boostAmount) {
        super(name, ItemType.DEFENSE_BOOST, boostAmount);
    }

    @Override
    public void applyEffect(Monster target) {
        target.boostDefense(getBoostAmount());
    }
} 