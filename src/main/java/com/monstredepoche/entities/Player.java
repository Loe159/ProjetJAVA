package com.monstredepoche.entities;

import com.monstredepoche.entities.monsters.Monster;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Monster> monsters;
    private final List<Item> items;
    private Monster activeMonster;

    public Player(String name) {
        this.name = name;
        this.monsters = new ArrayList<>();
        this.items = new ArrayList<>();
        this.activeMonster = null;
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
        if (activeMonster == null) {
            activeMonster = monster;
        }
    }

    public void addItem(Item item) {
        if (items.size() < 5) {  // Maximum 5 objets par joueur selon les consignes
            items.add(item);
        }
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public boolean useItem(Item item, Monster target) {
        if (!items.contains(item) || item.getQuantity() <= 0) {
            return false;
        }

        boolean used = false;
        switch (item.getType()) {
            case POTION -> {
                if (target.getCurrentHp() < target.getMaxHp()) {
                    target.heal(item.getValue());
                    used = true;
                }
            }
            case BOOST_ATTACK -> {
                target.boostAttack(item.getValue());
                used = true;
            }
            case BOOST_DEFENSE -> {
                target.boostDefense(item.getValue());
                used = true;
            }
            case BOOST_SPEED -> {
                target.boostSpeed(item.getValue());
                used = true;
            }
            case ANTIDOTE -> {
                if (target.getStatus() == StatusEffect.POISONED) {
                    target.setStatus(StatusEffect.NORMAL);
                    used = true;
                }
            }
            case ANTI_BURN -> {
                if (target.getStatus() == StatusEffect.BURNED) {
                    target.setStatus(StatusEffect.NORMAL);
                    used = true;
                }
            }
        }

        if (used) {
            item.use();
            if (item.getQuantity() <= 0) {
                items.remove(item);
            }
        }

        return used;
    }

    public void switchMonster(int index) {
        if (index >= 0 && index < monsters.size() && monsters.get(index) != activeMonster && !monsters.get(index).isDead()) {
            activeMonster = monsters.get(index);
        }
    }

    public List<Monster> getAliveMonsters() {
        return monsters.stream()
                .filter(monster -> !monster.isDead())
                .collect(java.util.stream.Collectors.toList());
    }

    public boolean hasLost() {
        return getAliveMonsters().isEmpty();
    }

    public String getName() {
        return name;
    }

    public List<Monster> getMonsters() {
        return new ArrayList<>(monsters);
    }

    public Monster getActiveMonster() {
        return activeMonster;
    }
} 