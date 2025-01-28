package com.monstredepoche.entities.items.potions;

import com.monstredepoche.entities.items.Item;
import com.monstredepoche.entities.items.ItemType;
import com.monstredepoche.entities.monsters.Monster;

public abstract class Potion extends Item {
    private final int value;

    protected Potion(String name, ItemType type, int value) {
        super(name, type);
        this.value = value;
    }

    protected int getValue() {
        return value;
    }

    @Override
    public void use(Monster target) {
        target.heal(value);
    }

    @Override
    public String getDescription() {
        return String.format(getType().getDescription(), getValue());
    }
} 