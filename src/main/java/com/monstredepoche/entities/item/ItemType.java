package com.monstredepoche.entities.item;

public enum ItemType {
    POTION("Restaure les PV"),
    ATTACK_BOOST("Augmente l'attaque"),
    DEFENSE_BOOST("Augmente la défense"),
    SPEED_BOOST("Augmente la vitesse"),
    ANTIDOTE("Soigne le poison"),
    ANTI_BURN("Soigne les brûlures");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 