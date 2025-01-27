package com.monstredepoche.entities.attacks;

public class AttackFactory {
    public static Attack createAttack(String name, AttackType type, int power, int maxUses, double failRate) {
        return switch (type) {
            case THUNDER -> new ThunderAttack(name, power, maxUses, failRate);
            case WATER -> new WaterAttack(name, power, maxUses, failRate);
            case FIRE -> new FireAttack(name, power, maxUses, failRate);
            case PLANT -> new PlantAttack(name, power, maxUses, failRate);
            case EARTH -> new EarthAttack(name, power, maxUses, failRate);
            case INSECT -> new InsectAttack(name, power, maxUses, failRate);
            default -> throw new IllegalArgumentException("Type d'attaque non supporté : " + type);
        };
    }
} 