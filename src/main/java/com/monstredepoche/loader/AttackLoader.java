package com.monstredepoche.loader;

import com.monstredepoche.entities.Attack;
import com.monstredepoche.entities.MonsterType;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AttackLoader {
    private static final String ATTACKS_FILE = "src/main/resources/attacks.txt";

    public List<Attack> loadAttacks() {
        List<Attack> attacks = new ArrayList<>();
        Path path = Paths.get(ATTACKS_FILE);
        System.out.println("Chargement des attaques depuis: " + path.toAbsolutePath());

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            StringBuilder attackData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                attackData.append(line).append("\n");

                if (line.equals("EndAttack")) {
                    try {
                        Attack attack = parseAttackData(attackData.toString());
                        if (attack != null) {
                            attacks.add(attack);
                            System.out.println("Attaque chargée: " + attack.getName());
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur lors du parsing d'une attaque: " + e.getMessage());
                    }
                    attackData = new StringBuilder();
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier des attaques: " + e.getMessage());
        }

        return attacks;
    }

    private Attack parseAttackData(String data) throws Exception {
        String name = null;
        MonsterType type = null;
        int power = 0, nbUse = 0;
        double fail = 0.0;

        for (String line : data.split("\n")) {
            line = line.trim();
            if (line.startsWith("Name")) {
                name = line.substring(5).trim();
            } else if (line.startsWith("Type")) {
                type = MonsterType.valueOf(line.substring(5).trim().toUpperCase());
            } else if (line.startsWith("Power")) {
                power = Integer.parseInt(line.substring(6).trim());
            } else if (line.startsWith("NbUse")) {
                nbUse = Integer.parseInt(line.substring(6).trim());
            } else if (line.startsWith("Fail")) {
                fail = Double.parseDouble(line.substring(5).trim());
            }
        }

        if (name == null || type == null) {
            throw new Exception("Données d'attaque incomplètes");
        }

        return new Attack(name, type, power, nbUse, fail);
    }
}
