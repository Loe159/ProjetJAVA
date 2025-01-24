package com.monstredepoche.entities;

public class Attack {
    private final String name;
    private final MonsterType type;
    private final int power;
    private final int maxUses;
    private int remainingUses;
    private final double failRate;

    public Attack(String name, MonsterType type, int power, int maxUses, double failRate) {
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
    public MonsterType getType() { return type; }
    public int getPower() { return power; }
    public int getMaxUses() { return maxUses; }
    public int getRemainingUses() { return remainingUses; }
    public double getFailRate() { return failRate; }
} 