package com.monstredepoche.entities;

public enum ItemType {
    POTION("Restaure les PV"),
    BOOST_ATTACK("Augmente l'attaque"),
    BOOST_DEFENSE("Augmente la défense"),
    BOOST_SPEED("Augmente la vitesse"),
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