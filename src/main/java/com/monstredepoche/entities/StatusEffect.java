package com.monstredepoche.entities;

import com.monstredepoche.entities.monsters.Monster;

public enum StatusEffect {
    NORMAL {
        @Override
        public void applyEffect(Monster monster) {
            // Pas d'effet
        }
        
        @Override
        public boolean canAttack() {
            return true;
        }
        
        @Override
        public boolean tryRecovery(int turnsInStatus) {
            return false; // Pas besoin de récupération
        }

        @Override
        public boolean isHealableByWater() {
            return false;
        }
    },
    PARALYZED {
        @Override
        public void applyEffect(Monster monster) {
            // Effet géré par canAttack()
        }
        
        @Override
        public boolean canAttack() {
            return Math.random() > 0.25; // 25% de chance de ne pas pouvoir attaquer
        }
        
        @Override
        public boolean tryRecovery(int turnsInStatus) {
            return Math.random() < 0.3; // 30% de chance de récupérer
        }

        @Override
        public boolean isHealableByWater() {
            return true;
        }
    },
    BURNED {
        @Override
        public void applyEffect(Monster monster) {
            monster.takeDamage(monster.getMaxHp() / 16); // 1/16 des PV max
        }
        
        @Override
        public boolean canAttack() {
            return true;
        }
        
        @Override
        public boolean tryRecovery(int turnsInStatus) {
            return Math.random() < 0.2; // 20% de chance de récupérer
        }

        @Override
        public boolean isHealableByWater() {
            return true;
        }
    },
    POISONED {
        @Override
        public void applyEffect(Monster monster) {
            monster.takeDamage(monster.getMaxHp() / 8); // 1/8 des PV max
        }
        
        @Override
        public boolean canAttack() {
            return true;
        }
        
        @Override
        public boolean tryRecovery(int turnsInStatus) {
            return Math.random() < 0.15; // 15% de chance de récupérer
        }

        @Override
        public boolean isHealableByWater() {
            return true;
        }
    };

    public abstract void applyEffect(Monster monster);
    public abstract boolean canAttack();
    public abstract boolean tryRecovery(int turnsInStatus);
    public abstract boolean isHealableByWater();
} 