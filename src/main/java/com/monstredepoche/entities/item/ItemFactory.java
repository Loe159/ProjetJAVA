package com.monstredepoche.entities.item;

import com.monstredepoche.entities.ItemType;
import com.monstredepoche.entities.StatusEffect;

public class ItemFactory {
    public static Item createItem(String name, ItemType type, int value, int quantity) {
        return switch (type) {
            case POTION -> new HealingItem(name, value, quantity);
            case BOOST_ATTACK, BOOST_DEFENSE, BOOST_SPEED -> new StatBoostItem(name, type, value, quantity);
            case ANTIDOTE -> new StatusHealItem(name, type, StatusEffect.POISONED, quantity);
            case ANTI_BURN -> new StatusHealItem(name, type, StatusEffect.BURNED, quantity);
        };
    }
} 