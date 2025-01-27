package com.monstredepoche.entities;

import com.monstredepoche.entities.monsters.Monster;
import com.monstredepoche.entities.item.Item;
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

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
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

        item.use(target);
        return true;
    }

    public void switchMonster(int index) {
        if (index >= 0 && index < monsters.size() && monsters.get(index) != activeMonster && !monsters.get(index).isDead()) {
            activeMonster = monsters.get(index);
        }
    }

    public boolean hasLost() {
        return getMonsters().isEmpty();
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