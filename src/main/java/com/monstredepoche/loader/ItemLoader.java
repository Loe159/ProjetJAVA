package com.monstredepoche.loader;

import com.monstredepoche.entities.item.*;
import com.monstredepoche.entities.item.ItemType;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ItemLoader extends AbstractLoader<Item> {
    private static final String ITEMS_FILE = "src/main/resources/items.txt";

    public ItemLoader() {
        super(ITEMS_FILE, "EndItem", "Objet");
    }

    @Override
    protected Item parseEntityData(String data) throws Exception {
        String name = null;
        ItemType type = null;
        int value = 0;

        for (String line : data.split("\n")) {
            line = line.trim();
            if (line.startsWith("Name")) {
                name = line.substring(5).trim();
            } else if (line.startsWith("Type")) {
                type = ItemType.valueOf(line.substring(5).trim().toUpperCase());
            } else if (line.startsWith("Value")) {
                value = Integer.parseInt(line.substring(6).trim());
            }
        }

        if (name == null || type == null) {
            throw new Exception("Données d'objet incomplètes");
        }

        return ItemFactory.createItem(name, type, value);
    }

    @Override
    protected String getEntityName(Item item) {
        return item.getName();
    }
}