package com.monstredepoche.entities.monsters;

import com.monstredepoche.entities.Attack;
import com.monstredepoche.entities.MonsterType;
import com.monstredepoche.entities.StatusEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Monster {
    private final String name;
    private final MonsterType type;
    private final int maxHp;
    private int currentHp;
    private int attack;
    private int defense;
    private int speed;
    private StatusEffect status;
    private int turnsInStatus;
    private final List<Attack> attacks;
    private boolean isInFloodedTerrain;
    private final Random random;

    protected Monster(String name, MonsterType type, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.status = StatusEffect.NORMAL;
        this.turnsInStatus = 0;
        this.attacks = new ArrayList<>();
        this.isInFloodedTerrain = false;
        this.random = new Random();
    }

    public void addAttack(Attack attack) {
        if (attacks.size() < 4) {
            attacks.add(attack);
        }
    }

    public void heal(int amount) {
        if (amount > 0) {
            currentHp = Math.min(currentHp + amount, maxHp);
        }
    }

    public void boostAttack(int amount) {
        if (amount > 0) {
            attack += amount;
        }
    }

    public void boostDefense(int amount) {
        defense += amount;
        if (defense < 0) {
            defense = 0;
        }
    }

    public void boostSpeed(int amount) {
        if (amount > 0) {
            speed += amount;
        }
    }

    public void takeDamage(int damage) {
        if (damage > 0) {
            currentHp = Math.max(0, currentHp - damage);
        }
    }

    public void setStatus(StatusEffect status) {
        this.status = status;
    }

    public void startTurn() {
        if (status != StatusEffect.NORMAL) {
            turnsInStatus++;
            if (status.tryRecovery(turnsInStatus)) {
                status = StatusEffect.NORMAL;
                turnsInStatus = 0;
            }
        }
        status.applyEffect(this);
        
        if (isInFloodedTerrain) {
            if (status.isHealableByWater()) {
                status = StatusEffect.NORMAL;
                System.out.println(name + " est guéri par l'eau !");
            }
            
            if (type == MonsterType.PLANT) {
                int heal = maxHp / 20;
                heal(heal);
                System.out.println(name + " récupère " + heal + " PV grâce à l'eau !");
            }
        }
    }

    public boolean canAttack() {
        return !isDead() && status != StatusEffect.PARALYZED;
    }

    public boolean isDead() {
        return currentHp <= 0;
    }

    public abstract void useSpecialAbility();

    public String getName() {
        return name;
    }

    public MonsterType getType() {
        return type;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public StatusEffect getStatus() {
        return status;
    }

    public List<Attack> getAttacks() {
        return new ArrayList<>(attacks);
    }

    public boolean isInFloodedTerrain() {
        return isInFloodedTerrain;
    }

    public void setInFloodedTerrain(boolean isInFloodedTerrain) {
        this.isInFloodedTerrain = isInFloodedTerrain;
    }

    protected Random getRandom() {
        return random;
    }

    public int calculateBasicDamage(Monster target) {
        double coef = 0.85 + random.nextDouble() * 0.15;
        return (int)(20 * ((double)attack / target.getDefense()) * coef);
    }

    public int calculateDamage(Attack attack, Monster target) {
        if (attack == null) {
            return calculateBasicDamage(target);
        }

        double effectiveness = type.getEffectivenessAgainst(target.getType());
        double coef = 0.85 + random.nextDouble() * 0.15;
        
        // Formule des consignes
        double baseDamage = (11.0 * attack.getPower()) / (25.0 * target.getDefense());
        double damage = (baseDamage + 2) * effectiveness * coef;
        
        if (type == attack.getType()) {
            damage *= 1.5; // STAB (Same Type Attack Bonus)
        }
        
        return (int)damage;
    }

    public static double calculateAdvantage(MonsterType attackerType, MonsterType defenderType) {
        if (attackerType == defenderType) return 1.0;

        return switch (attackerType) {
            case THUNDER -> switch (defenderType) {
                case WATER -> 2.0;
                case EARTH -> 0.5;
                default -> 1.0;
            };
            case WATER -> switch (defenderType) {
                case FIRE -> 2.0;
                case PLANT -> 0.5;
                default -> 1.0;
            };
            case EARTH -> switch (defenderType) {
                case THUNDER -> 2.0;
                case WATER -> 0.5;
                default -> 1.0;
            };
            case FIRE -> switch (defenderType) {
                case PLANT, INSECT -> 2.0;
                case WATER -> 0.5;
                default -> 1.0;
            };
            case PLANT -> switch (defenderType) {
                case WATER, EARTH -> 2.0;
                case FIRE, INSECT -> 0.5;
                default -> 1.0;
            };
            case INSECT -> switch (defenderType) {
                case PLANT -> 2.0;
                case FIRE -> 0.5;
                default -> 1.0;
            };
        };
    }
} 