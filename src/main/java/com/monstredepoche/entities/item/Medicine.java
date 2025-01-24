package com.monstredepoche.entities.item;

import com.monstredepoche.entities.monsters.Monster;
import com.monstredepoche.entities.StatusEffect;

public class Medicine extends Item {
    private final StatusEffect targetStatus;

    public Medicine(String name, StatusEffect targetStatus) {
        super(name);
        this.targetStatus = targetStatus;
    }

    @Override
    public void use(Monster target) {
        if (target.getStatus() == targetStatus) {
            target.setStatus(StatusEffect.NORMAL);
        }
    }
} 