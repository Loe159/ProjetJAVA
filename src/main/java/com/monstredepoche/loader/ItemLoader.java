package com.monstredepoche.loader;

import com.monstredepoche.entities.ItemType;
import com.monstredepoche.entities.item.Item;
import com.monstredepoche.entities.item.ItemFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ItemLoader {
    private static final String ITEMS_FILE = "src/main/resources/items.txt";

    public List<Item> loadItems() {
        List<Item> items = new ArrayList<>();
        Path path = Paths.get(ITEMS_FILE);
        System.out.println("Chargement des objets depuis: " + path.toAbsolutePath());

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            StringBuilder itemData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                itemData.append(line).append("\n");

                if (line.equals("EndItem")) {
                    try {
                        Item item = parseItemData(itemData.toString());
                        if (item != null) {
                            items.add(item);
                            System.out.println("Item chargé: " + item.getName());
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur lors du parsing d'un objet: " + e.getMessage());
                    }
                    itemData = new StringBuilder();
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier des objets: " + e.getMessage());
        }

        return items;
    }

    private Item parseItemData(String data) throws Exception {
        String name = null;
        ItemType type = null;
        int value = 0, quantity = 0;

        for (String line : data.split("\n")) {
            line = line.trim();
            if (line.startsWith("Name")) {
                name = line.substring(5).trim();
            } else if (line.startsWith("Type")) {
                type = ItemType.valueOf(line.substring(5).trim().toUpperCase());
            } else if (line.startsWith("Value")) {
                value = Integer.parseInt(line.substring(6).trim());
            } else if (line.startsWith("Quantity")) {
                quantity = Integer.parseInt(line.substring(9).trim());
            }
        }

        if (name == null || type == null) {
            throw new Exception("Données d'item incomplètes");
        }

        return ItemFactory.createItem(name, type, value, quantity);
    }
}