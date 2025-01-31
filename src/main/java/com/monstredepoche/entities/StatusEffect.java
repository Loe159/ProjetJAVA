package com.monstredepoche.entities;

import com.monstredepoche.entities.monsters.Monster;
import com.monstredepoche.utils.RandomUtils;

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
            return RandomUtils.tryChance(0.75); // 25% de chance de ne pas pouvoir attaquer
        }
        
        @Override
        public boolean tryRecovery(int turnsInStatus) {
                double probability = (double) turnsInStatus / 6; // probabilité = nombre de tours passé dans cet état divisé par six
                return RandomUtils.tryChance(probability);
        }

        @Override
        public boolean isHealableByWater() {
            return true;
        }
    },
    BURNED {
        @Override
        public void applyEffect(Monster monster) {
            int damageTaken = monster.getAttack() / 10; // 1/10 de l'attaque
            monster.takeDamage(damageTaken);
            System.out.println(monster.getName() + " perd " + damageTaken + " PV à cause de ses brûlures !");
        }
        
        @Override
        public boolean canAttack() {
            return true;
        }
        
        @Override
        public boolean tryRecovery(int turnsInStatus) {
            return RandomUtils.tryChance(0.2); // 20% de chance de récupérer
        }

        @Override
        public boolean isHealableByWater() {
            return true;
        }
    },
    POISONED {
        @Override
        public void applyEffect(Monster monster) {
            int damageTaken = monster.getAttack() / 10; // 1/10 de l'attaque
            monster.takeDamage(damageTaken);
            System.out.println(monster.getName() + " perd " + damageTaken + " PV à cause du poison !");
        }
        
        @Override
        public boolean canAttack() {
            return true;
        }
        
        @Override
        public boolean tryRecovery(int turnsInStatus) {
            return RandomUtils.tryChance(0.15); // 15% de chance de récupérer
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