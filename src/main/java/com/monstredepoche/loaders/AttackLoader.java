package com.monstredepoche.loaders;

import com.monstredepoche.entities.attacks.*;

public class AttackLoader extends Loader<Attack> {
    private static final String ATTACKS_FILE = "src/main/resources/attacks.txt";

    public AttackLoader() {
        super(ATTACKS_FILE, "EndAttack", "Attaque");
    }

    @Override
    protected Attack parseEntityData(String data) throws Exception {
        String name = null;
        AttackType type = null;
        int power = 0, nbUse = 0;
        double fail = 0.0;

        for (String line : data.split("\n")) {
            line = line.trim();
            if (line.startsWith("Name")) {
                name = line.substring(5).trim();
            } else if (line.startsWith("Type")) {
                type = AttackType.valueOf(line.substring(5).trim().toUpperCase());
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

        return switch (type) {
            case THUNDER -> new ThunderAttack(name, power, nbUse, fail);
            case WATER -> new WaterAttack(name, power, nbUse, fail);
            case FIRE -> new FireAttack(name, power, nbUse, fail);
            case PLANT -> new PlantAttack(name, power, nbUse, fail);
            case EARTH -> new EarthAttack(name, power, nbUse, fail);
            case INSECT -> new InsectAttack(name, power, nbUse, fail);
            case NORMAL -> new NormalAttack(name, power, nbUse, fail);
            default -> throw new IllegalArgumentException("Type d'attaque non supporté : " + type);
        };
    }

    @Override
    protected String getEntityName(Attack attack) {
        return attack.getName();
    }
}
