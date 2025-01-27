package com.monstredepoche.entities;

public enum ItemType {
    POTION("Restaure %d PV"),
    BOOST_ATTACK("Augmente l'attaque de %d"),
    BOOST_DEFENSE("Augmente la défense de %d"),
    BOOST_SPEED("Augmente la vitesse de %d"),
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