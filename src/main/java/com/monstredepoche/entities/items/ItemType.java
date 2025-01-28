package com.monstredepoche.entities.items;

public enum ItemType {
    HEALING("Restaure %d PV"),
    ATTACK_BOOST("Augmente l'attaque de %d"),
    DEFENSE_BOOST("Augmente la défense de %d"),
    SPEED_BOOST("Augmente la vitesse de %d"),
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