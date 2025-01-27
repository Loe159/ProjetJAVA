package com.monstredepoche.entities.item;

import com.monstredepoche.entities.ItemType;
import com.monstredepoche.entities.monsters.Monster;

public class StatBoostItem extends Item {
    public StatBoostItem(String name, ItemType type, int boostAmount, int quantity) {
        super(name, type, boostAmount, quantity);
        if (type != ItemType.BOOST_ATTACK && type != ItemType.BOOST_DEFENSE && type != ItemType.BOOST_SPEED) {
            throw new IllegalArgumentException("Type d'objet invalide pour un boost de statistique");
        }
    }

    @Override
    public void use(Monster target) {
        if (getQuantity() > 0) {
            switch (getType()) {
                case BOOST_ATTACK -> target.boostAttack(getValue());
                case BOOST_DEFENSE -> target.boostDefense(getValue());
                case BOOST_SPEED -> target.boostSpeed(getValue());
                default -> throw new IllegalStateException("Type d'objet non géré: " + getType());
            }
            decrementQuantity();
        }
    }
} 