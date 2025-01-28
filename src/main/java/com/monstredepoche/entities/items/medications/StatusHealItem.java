package com.monstredepoche.entities.items.medications;

import com.monstredepoche.entities.StatusEffect;
import com.monstredepoche.entities.items.Item;
import com.monstredepoche.entities.items.ItemType;
import com.monstredepoche.entities.monsters.Monster;

public class StatusHealItem extends Item {
    private final StatusEffect targetStatus;

    public StatusHealItem(String name, ItemType type, StatusEffect targetStatus) {
        super(name, type);
        if (type != ItemType.ANTIDOTE && type != ItemType.ANTI_BURN) {
            throw new IllegalArgumentException("Type d'objet invalide pour un soin de statut");
        }
        this.targetStatus = targetStatus;
    }

    @Override
    public void use(Monster target) {
        if (target.getStatus() == targetStatus) {
            target.setStatus(StatusEffect.NORMAL);
        }
    }
} 