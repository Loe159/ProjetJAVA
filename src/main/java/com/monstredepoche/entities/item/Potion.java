package com.monstredepoche.entities.item;

public class Potion extends HealingItem {
    public Potion(String name, int healAmount) {
        super(name, ItemType.POTION, healAmount);
    }
} 