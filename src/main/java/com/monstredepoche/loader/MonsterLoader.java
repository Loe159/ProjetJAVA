package com.monstredepoche.loader;

import com.monstredepoche.entities.monsters.MonsterType;
import com.monstredepoche.entities.monsters.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MonsterLoader {
    private static final String MONSTERS_FILE = "src/main/resources/monsters.txt";

    public List<Monster> loadMonsters() {
        List<Monster> monsters = new ArrayList<>();
        Path path = Paths.get(MONSTERS_FILE);
        System.out.println("Chargement des monstres depuis: " + path.toAbsolutePath());

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            StringBuilder monsterData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                monsterData.append(line).append("\n");

                if (line.equals("EndMonster")) {
                    try {
                        Monster monster = parseMonsterData(monsterData.toString());
                        monsters.add(monster);
                        System.out.println("Monstre chargé: " + monster.getName());
                    } catch (Exception e) {
                        System.err.println("Erreur lors du parsing d'un monstre: " + e.getMessage());
                    }
                    monsterData = new StringBuilder();
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier des monstres: " + e.getMessage());
        }

        return monsters;
    }

    private Monster parseMonsterData(String data) throws Exception {
        String name = null;
        MonsterType type = null;
        int hpMin = 0, hpMax = 0, attackMin = 0, attackMax = 0, defenseMin = 0, defenseMax = 0, speedMin = 0, speedMax = 0;

        for (String line : data.split("\n")) {
            line = line.trim();
            if (line.startsWith("Name")) {
                name = line.substring(5).trim();
            } else if (line.startsWith("Type")) {
                type = MonsterType.valueOf(line.substring(5).trim().toUpperCase());
            } else if (line.startsWith("HP")) {
                String[] values = line.substring(3).trim().split(" ");
                hpMin = Integer.parseInt(values[0]);
                hpMax = hpMin;
                if (values.length == 2) {
                    hpMax = Integer.parseInt(values[1]);
                }
            } else if (line.startsWith("Attack")) {
                String[] values = line.substring(7).trim().split(" ");
                attackMin = Integer.parseInt(values[0]);
                attackMax = attackMin;
                if (values.length == 2) {
                    attackMax = Integer.parseInt(values[1]);
                }
            } else if (line.startsWith("Defense")) {
                String[] values = line.substring(8).trim().split(" ");
                defenseMin = Integer.parseInt(values[0]);
                defenseMax = defenseMin;
                if (values.length == 2) {
                    defenseMax = Integer.parseInt(values[1]);
                }
            } else if (line.startsWith("Speed")) {
                String[] values = line.substring(6).trim().split(" ");
                speedMin = Integer.parseInt(values[0]);
                speedMax = speedMin;
                if (values.length == 2) {
                    speedMax = Integer.parseInt(values[1]);
                }
            }
        }

        if (name == null || type == null) {
            throw new Exception("Données de monstre incomplètes");
        }

        return createMonster(name, type, hpMin, hpMax, attackMin, attackMax, defenseMin, defenseMax, speedMin, speedMax);
    }

    private Monster createMonster(String name, MonsterType type, int hpMin, int hpMax, int attackMin, int attackMax, int defenseMin, int defenseMax, int speedMin, int speedMax) {
        int hp = getRandomBetween(hpMin, hpMax);
        int attack = getRandomBetween(attackMin, attackMax);
        int defense = getRandomBetween(defenseMin, defenseMax);
        int speed = getRandomBetween(speedMin, speedMax);

        return switch (type) {
            case THUNDER -> new ThunderMonster(name, hp, attack, defense, speed);
            case WATER -> new WaterMonster(name, hp, attack, defense, speed);
            case EARTH -> new EarthMonster(name, hp, attack, defense, speed);
            case FIRE -> new FireMonster(name, hp, attack, defense, speed);
            case PLANT -> new PlantMonster(name, hp, attack, defense, speed);
            case INSECT -> new InsectMonster(name, hp, attack, defense, speed);
        };
    }

    private int getRandomBetween(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
}
