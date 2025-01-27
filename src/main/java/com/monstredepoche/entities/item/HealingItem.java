package com.monstredepoche.entities.item;

import com.monstredepoche.entities.ItemType;
import com.monstredepoche.entities.monsters.Monster;

public class HealingItem extends Item {
    public HealingItem(String name, int healAmount, int quantity) {
        super(name, ItemType.POTION, healAmount, quantity);
    }

    @Override
    public void use(Monster target) {
        if (getQuantity() > 0) {
            target.heal(getValue());
            decrementQuantity();
        }
    }
} 