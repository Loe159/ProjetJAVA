package com.monstredepoche.entities.items;

import com.monstredepoche.entities.StatusEffect;
import com.monstredepoche.entities.items.medications.StatusHealItem;
import com.monstredepoche.entities.items.potions.boosts.AttackBoost;
import com.monstredepoche.entities.items.potions.boosts.DefenseBoost;
import com.monstredepoche.entities.items.potions.HealingPotion;
import com.monstredepoche.entities.items.potions.boosts.SpeedBoost;

public class ItemFactory {
    public static Item createItem(String name, ItemType type, int value) {
        return switch (type) {
            case HEALING -> new HealingPotion(name, value);
            case ATTACK_BOOST -> new AttackBoost(name, value);
            case DEFENSE_BOOST -> new DefenseBoost(name, value);
            case SPEED_BOOST -> new SpeedBoost(name, value);
            case ANTIDOTE -> new StatusHealItem(name, type, StatusEffect.POISONED);
            case ANTI_BURN -> new StatusHealItem(name, type, StatusEffect.BURNED);
        };
    }
} 