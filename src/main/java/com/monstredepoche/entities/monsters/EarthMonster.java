package com.monstredepoche.entities.monsters;

public class EarthMonster extends Monster {
    private static final double UNDERGROUND_CHANCE = 0.3;
    private boolean isUnderground;
    private int turnsUnderground;
    private int originalDefense;

    public EarthMonster(String name, int maxHp, int attack, int defense, int speed) {
        super(name, MonsterType.EARTH, maxHp, attack, defense, speed);
        this.isUnderground = false;
        this.turnsUnderground = 0;
        this.originalDefense = defense;
    }

    @Override
    public void useSpecialAbility() {
        if (!isUnderground && getRandom().nextDouble() < UNDERGROUND_CHANCE) {
            goUnderground();
        }
    }

    private void goUnderground() {
        isUnderground = true;
        originalDefense = getDefense();
        boostDefense(originalDefense); // Double la défense
        turnsUnderground = getRandom().nextInt(2) + 1; // 1 ou 2 tours
        System.out.println(getName() + " s'enfonce sous terre !");
    }

    @Override
    public void startTurn() {
        super.startTurn();
        if (isUnderground) {
            turnsUnderground--;
            if (turnsUnderground <= 0) {
                isUnderground = false;
                boostDefense(-originalDefense); // Réinitialise la défense
                System.out.println(getName() + " refait surface !");
            }
        }
    }

    @Override
    public boolean canAttack() {
        return super.canAttack() && !isUnderground;
    }
} 