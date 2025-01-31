package com.monstredepoche.loaders;

import com.monstredepoche.entities.StatusEffect;
import com.monstredepoche.entities.items.Item;
import com.monstredepoche.entities.items.ItemType;
import com.monstredepoche.entities.items.medications.StatusHealItem;
import com.monstredepoche.entities.items.potions.HealingPotion;
import com.monstredepoche.entities.items.potions.boosts.AttackBoost;
import com.monstredepoche.entities.items.potions.boosts.DefenseBoost;
import com.monstredepoche.entities.items.potions.boosts.SpeedBoost;

public class ItemLoader extends Loader<Item> {
    private static final String ITEMS_FILE = "src/main/resources/items.txt";

    public ItemLoader() {
        super(ITEMS_FILE, "EndItem", "Objet");
    }

    @Override
    protected Item parseEntityData(String data) throws Exception {
        String name = null;
        ItemType type = null;
        int value = 0;

        for (String line : data.split("\n")) {
            line = line.trim();
            if (line.startsWith("Name")) {
                name = line.substring(5).trim();
            } else if (line.startsWith("Type")) {
                type = ItemType.valueOf(line.substring(5).trim().toUpperCase());
            } else if (line.startsWith("Value")) {
                value = Integer.parseInt(line.substring(6).trim());
            }
        }

        if (name == null || type == null) {
            throw new Exception("Données d'objet incomplètes");
        }

        return switch (type) {
            case HEALING -> new HealingPotion(name, value);
            case ATTACK_BOOST -> new AttackBoost(name, value);
            case DEFENSE_BOOST -> new DefenseBoost(name, value);
            case SPEED_BOOST -> new SpeedBoost(name, value);
            case ANTIDOTE -> new StatusHealItem(name, type, StatusEffect.POISONED);
            case ANTI_BURN -> new StatusHealItem(name, type, StatusEffect.BURNED);
        };
    }

    @Override
    protected String getEntityName(Item item) {
        return item.getName();
    }
}