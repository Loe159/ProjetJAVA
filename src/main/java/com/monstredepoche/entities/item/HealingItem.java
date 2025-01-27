package com.monstredepoche.entities.item;

import com.monstredepoche.entities.monsters.Monster;

public abstract class HealingItem extends Item {
    private final int healAmount;

    protected HealingItem(String name, ItemType type, int healAmount) {
        super(name, type);
        this.healAmount = healAmount;
    }

    protected int getHealAmount() {
        return healAmount;
    }

    @Override
    public void use(Monster target) {
        target.heal(healAmount);
    }
} 