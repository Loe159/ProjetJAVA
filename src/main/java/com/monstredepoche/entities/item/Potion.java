package com.monstredepoche.entities.item;

import com.monstredepoche.entities.monsters.Monster;

public class Potion extends Item {
    private final int healAmount;

    public Potion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Monster target) {
        target.heal(healAmount);
    }
} 