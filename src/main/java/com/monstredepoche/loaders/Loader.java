package com.monstredepoche.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Loader<T> {
    private final String filePath;
    private final String endMarker;
    private final String entityName;

    protected Loader(String filePath, String endMarker, String entityName) {
        this.filePath = filePath;
        this.endMarker = endMarker;
        this.entityName = entityName;
    }

    public List<T> loadEntities() throws IOException {
        List<T> entities = new ArrayList<>();
        File file = new File(filePath);
        System.out.println("\nChargement des " + entityName + "s depuis: " + file.getAbsolutePath());

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            StringBuilder entityData = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                entityData.append(line).append("\n");

                if (line.equals(endMarker)) {
                    try {
                        T entity = parseEntityData(entityData.toString());
                        if (entity != null) {
                            entities.add(entity);
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur lors du parsing d'un " + entityName + ": " + e.getMessage());
                    }
                    entityData = new StringBuilder();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erreur : Fichier introuvable !");
        }

        return entities;
    }


    protected abstract T parseEntityData(String data) throws Exception;
    protected abstract String getEntityName(T entity);
} 