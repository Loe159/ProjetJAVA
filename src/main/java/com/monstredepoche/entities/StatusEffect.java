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
                double probability = (double) turnsInStatus / 6; // probabilité = nombre de tours passé dans cet état divisé par six
                return Math.random() < probability;
        }

        @Override
        public boolean isHealableByWater() {
            return true;
        }
    },
    BURNED {
        @Override
        public void applyEffect(Monster monster) {
            monster.takeDamage(monster.getMaxHp() / 16); // TODO Subit au début de chaque tour un dixième de son attaque
        }
        
        @Override
        public boolean canAttack() {
            return true;
        }
        
        @Override
        public boolean tryRecovery(int turnsInStatus) {
            return false;
        }

        @Override
        public boolean isHealableByWater() {
            return true;
        }
    },
    POISONED {
        @Override
        public void applyEffect(Monster monster) {
            monster.takeDamage(monster.getMaxHp() / 8); // TODO Subit au début de chaque tour un dixième de son attaque
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