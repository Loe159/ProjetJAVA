package com.monstredepoche.entities.attacks;

import com.monstredepoche.entities.monsters.MonsterType;

public abstract class Attack {
    private final String name;
    private final AttackType type;
    private final int power;
    private final int maxUses;
    private int remainingUses;
    private final double failRate;

    protected Attack(String name, AttackType type, int power, int maxUses, double failRate) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.maxUses = maxUses;
        this.remainingUses = maxUses;
        this.failRate = failRate;
    }

    public abstract double getEffectivenessAgainst(MonsterType attackerType, MonsterType defenderType);
    public abstract void applySpecialEffect();

    public void use() {
        if (remainingUses > 0) {
            remainingUses--;
        }
    }

    public String getName() {
        return name;
    }

    public AttackType getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    public double getFailRate() {
        return failRate;
    }

    @Override
    public String toString() {
        return String.format("%s (Type: %s, Puissance: %d, Utilisations: %d/%d)", 
            name, type, power, remainingUses, maxUses);
    }
} 