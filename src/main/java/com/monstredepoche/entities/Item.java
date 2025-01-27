package com.monstredepoche.entities;

public class Item {
    private final String name;
    private final ItemType type;
    private final int value;
    private int quantity;

    public Item(String name, ItemType type, int value, int quantity) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.quantity = quantity;
    }

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

    public void use() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public void addQuantity(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public String getDescription() {
        return String.format(this.type.getDescription() + " - %d restants.", this.value, this.quantity);
    }

    @Override
    public String toString() {
        return String.format(" (0) - : 0", name, quantity, this.getDescription());
    }
}