package com.monstredepoche.entities.items.potions.boosts;

import com.monstredepoche.entities.items.ItemType;
import com.monstredepoche.entities.monsters.Monster;

public class SpeedBoost extends BoostPotion {
    public SpeedBoost(String name, int boostAmount) {
        super(name, ItemType.SPEED_BOOST, boostAmount);
    }

    @Override
    public void applyEffect(Monster target) {
        target.boostSpeed(getBoostAmount());
    }
} 