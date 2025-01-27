package com.monstredepoche.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLoader<T> {
    private final String filePath;
    private final String endMarker;
    private final String entityName;

    protected AbstractLoader(String filePath, String endMarker, String entityName) {
        this.filePath = filePath;
        this.endMarker = endMarker;
        this.entityName = entityName;
    }

    public List<T> loadEntities() {
        List<T> entities = new ArrayList<>();
        Path path = Paths.get(filePath);
        System.out.println("Chargement des " + entityName + "s depuis: " + path.toAbsolutePath());

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            StringBuilder entityData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                entityData.append(line).append("\n");

                if (line.equals(endMarker)) {
                    try {
                        T entity = parseEntityData(entityData.toString());
                        if (entity != null) {
                            entities.add(entity);
                            System.out.println(entityName + " chargé: " + getEntityName(entity));
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur lors du parsing d'un " + entityName + ": " + e.getMessage());
                    }
                    entityData = new StringBuilder();
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier des " + entityName + "s: " + e.getMessage());
        }

        return entities;
    }

    protected abstract T parseEntityData(String data) throws Exception;
    protected abstract String getEntityName(T entity);
} 