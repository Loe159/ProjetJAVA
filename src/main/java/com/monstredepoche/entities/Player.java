package com.monstredepoche.entities;

import com.monstredepoche.entities.items.Item;
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
        if (monsters.size() < 3) {
            monsters.add(monster);
            if (activeMonster == null) {
                activeMonster = monster;
            }
        }
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }

    public void addItem(Item item) {
        if (items.size() < 5) {  // Maximum 5 objets par joueur selon les consignes
            items.add(item);
        }
    }

    public void useItem(Item item, Monster target) {
        if (items.contains(item)) {
            item.use(target);
            items.remove(item);
        }
    }

    public void switchMonster(int index) {
        if (index >= 0 && index < monsters.size()) {
            Monster selectedMonster = monsters.get(index);
            if (!selectedMonster.isDead() && selectedMonster != activeMonster) {
                activeMonster = selectedMonster;
            }
        }
    }

    public List<Monster> getAliveMonsters() {
        return monsters.stream()
                .filter(monster -> !monster.isDead())
                .toList();
    }

    public boolean hasLost() {
        return getAliveMonsters().isEmpty();
    }

    public String getName() {
        return name;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Item> getItems() {
        return items;
    }

    public Monster getActiveMonster() {
        return activeMonster;
    }
} 