package com.monstredepoche.entities.item;

import com.monstredepoche.entities.StatusEffect;

public class ItemFactory {
    public static Item createItem(String name, ItemType type, int value) {
        return switch (type) {
            case POTION -> new Potion(name, value);
            case ATTACK_BOOST -> new AttackBoost(name, value);
            case DEFENSE_BOOST -> new DefenseBoost(name, value);
            case SPEED_BOOST -> new SpeedBoost(name, value);
            case ANTIDOTE -> new StatusHealItem(name, type, StatusEffect.POISONED);
            case ANTI_BURN -> new StatusHealItem(name, type, StatusEffect.BURNED);
        };
    }
} 