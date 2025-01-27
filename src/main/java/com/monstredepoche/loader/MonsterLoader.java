package com.monstredepoche.loader;

import com.monstredepoche.entities.monsters.*;

public class MonsterLoader extends AbstractLoader<Monster> {
    private static final String MONSTERS_FILE = "src/main/resources/monsters.txt";

    public MonsterLoader() {
        super(MONSTERS_FILE, "EndMonster", "Monstre");
    }

    @Override
    protected Monster parseEntityData(String data) throws Exception {
        String name = null;
        MonsterType type = null;
        int hp = 0, attack = 0, defense = 0, speed = 0;

        for (String line : data.split("\n")) {
            line = line.trim();
            if (line.startsWith("Name")) {
                name = line.substring(5).trim();
            } else if (line.startsWith("Type")) {
                type = MonsterType.valueOf(line.substring(5).trim().toUpperCase());
            } else if (line.startsWith("HP")) {
                hp = Integer.parseInt(line.substring(3).trim());
            } else if (line.startsWith("Attack")) {
                attack = Integer.parseInt(line.substring(7).trim());
            } else if (line.startsWith("Defense")) {
                defense = Integer.parseInt(line.substring(8).trim());
            } else if (line.startsWith("Speed")) {
                speed = Integer.parseInt(line.substring(6).trim());
            }
        }

        if (name == null || type == null) {
            throw new Exception("Données de monstre incomplètes");
        }

        return switch (type) {
            case THUNDER -> new ThunderMonster(name, hp, attack, defense, speed);
            case WATER -> new WaterMonster(name, hp, attack, defense, speed);
            case FIRE -> new FireMonster(name, hp, attack, defense, speed);
            case PLANT -> new PlantMonster(name, hp, attack, defense, speed);
            case EARTH -> new EarthMonster(name, hp, attack, defense, speed);
            case INSECT -> new InsectMonster(name, hp, attack, defense, speed);
        };
    }

    @Override
    protected String getEntityName(Monster monster) {
        return monster.getName();
    }
}
