package com.monstredepoche.entities.item;

import com.monstredepoche.entities.monsters.Monster;

public class DefenseBoost extends StatBoostItem {
    public DefenseBoost(String name, int boostAmount) {
        super(name, ItemType.DEFENSE_BOOST, boostAmount);
    }

    @Override
    public void applyEffect(Monster target) {
        target.boostDefense(getBoostAmount());
    }
} 