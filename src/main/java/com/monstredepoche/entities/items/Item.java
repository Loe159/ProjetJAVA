package com.monstredepoche.entities.items;

import com.monstredepoche.entities.monsters.Monster;

public abstract class Item {
    private final String name;
    private final ItemType type;

    protected Item(String name, ItemType type) {
        this.name = name;
        this.type = type;
    }

    public abstract void use(Monster target);

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public String getDescription() {
        return type.getDescription();
    }
} 