package com.monstredepoche.entities.items.potions.boosts;

import com.monstredepoche.entities.items.ItemType;
import com.monstredepoche.entities.items.potions.Potion;
import com.monstredepoche.entities.monsters.Monster;

public abstract class BoostPotion extends Potion {
    private final int boostAmount;

    protected BoostPotion(String name, ItemType type, int boostAmount) {
        super(name, type, boostAmount);
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