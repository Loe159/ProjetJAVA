package com.monstredepoche.entities.item;

import com.monstredepoche.entities.monsters.Monster;

public abstract class StatBoostItem extends Item {
    private final int boostAmount;

    protected StatBoostItem(String name, ItemType type, int boostAmount) {
        super(name, type);
        this.boostAmount = boostAmount;
    }

    protected int getBoostAmount() {
        return boostAmount;
    }

    public abstract void applyEffect(Monster target);

    @Override
    public void use(Monster target) {
        applyEffect(target);
    }
} 