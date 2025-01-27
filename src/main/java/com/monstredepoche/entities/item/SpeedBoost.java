package com.monstredepoche.entities.item;

import com.monstredepoche.entities.monsters.Monster;

public class SpeedBoost extends StatBoostItem {
    public SpeedBoost(String name, int boostAmount) {
        super(name, ItemType.SPEED_BOOST, boostAmount);
    }

    @Override
    public void applyEffect(Monster target) {
        target.boostSpeed(getBoostAmount());
    }
} 