package com.monstredepoche.entities.items.potions;

import com.monstredepoche.entities.items.ItemType;

public class HealingPotion extends Potion {
    public HealingPotion(String name, int healAmount) {
        super(name, ItemType.HEALING, healAmount);
    }
} 