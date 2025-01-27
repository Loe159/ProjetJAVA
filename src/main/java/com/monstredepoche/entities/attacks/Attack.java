package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public class Attack {
    private final String name;
    private final AttackType type;
    private final int power;
    private final int maxUses;
    private int remainingUses;
    private final double failRate;

    public Attack(String name, AttackType type, int power, int maxUses, double failRate) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.maxUses = maxUses;
        this.remainingUses = maxUses;
        this.failRate = failRate;
    }

    public boolean canUse() {
        return remainingUses > 0;
    }

    public void use() {
        if (canUse()) {
            remainingUses--;
        }
    }

    public boolean tryHit() {
        return Math.random() > failRate;
    }

    public void restore() {
        this.remainingUses = this.maxUses;
    }

    // Getters
    public String getName() { return name; }
    public AttackType getType() { return type; }
    public int getPower() { return power; }
    public int getMaxUses() { return maxUses; }
    public int getRemainingUses() { return remainingUses; }
    public double getFailRate() { return failRate; }

    public double getEffectivenessAgainst(MonsterType attacker, MonsterType target) {
        if (this.getType() == AttackType.NORMAL || this.getType() == AttackType.BAREHANDED) return 1.0;

        return switch (attacker) {
            case THUNDER -> switch (target) {
                case WATER -> 2.0;
                case EARTH -> 0.5;
                default -> 1.0;
            };
            case WATER -> switch (target) {
                case FIRE -> 2.0;
                case PLANT -> 0.5;
                default -> 1.0;
            };
            case EARTH -> switch (target) {
                case FIRE -> 2.0;
                case PLANT -> 0.5;
                default -> 1.0;
            };
            case FIRE -> switch (target) {
                case PLANT -> 2.0;
                case WATER -> 0.5;
                default -> 1.0;
            };
            case PLANT -> switch (target) {
                case WATER -> 2.0;
                case FIRE -> 0.5;
                default -> 1.0;
            };
            case INSECT -> switch (target) {
                case PLANT -> 2.0;
                case FIRE -> 0.5;
                default -> 1.0;
            };
            default -> 1.0;
        };
    }
} 