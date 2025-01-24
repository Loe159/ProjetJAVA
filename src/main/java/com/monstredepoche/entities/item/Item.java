package com.monstredepoche.entities.item;

import com.monstredepoche.entities.monsters.Monster;

public abstract class Item {
    private final String name;

    protected Item(String name) {
        this.name = name;
    }

    public abstract void use(Monster target);

    public String getName() {
        return name;
    }
} 