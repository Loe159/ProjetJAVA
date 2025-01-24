package com.monstredepoche.loader;

import com.monstredepoche.entities.Item;
import com.monstredepoche.entities.ItemType;

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
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(":");
                if (parts.length == 4) {
                    try {
                        String name = parts[0].trim();
                        ItemType type = ItemType.valueOf(parts[1].trim());
                        int value = Integer.parseInt(parts[2].trim());
                        int quantity = Integer.parseInt(parts[3].trim());

                        items.add(new Item(name, type, value, quantity));
                    } catch (Exception e) {
                        System.err.println("Erreur lors du parsing d'un objet: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier des objets: " + e.getMessage());
        }

        return items;
    }
}