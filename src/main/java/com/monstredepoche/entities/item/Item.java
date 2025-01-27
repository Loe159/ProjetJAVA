package com.monstredepoche.entities.item;

import com.monstredepoche.entities.monsters.Monster;
import com.monstredepoche.entities.ItemType;

public abstract class Item implements Cloneable {
    private final String name;
    private final ItemType type;
    private final int value;
    private int quantity;

    protected Item(String name, ItemType type, int value, int quantity) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.quantity = quantity;
    }

    public abstract void use(Monster target);

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public String getDescription() {
        return String.format(type.getDescription(), value);
    }

    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
} 