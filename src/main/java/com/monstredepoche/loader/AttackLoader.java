package com.monstredepoche.loader;

import com.monstredepoche.entities.attacks.Attack;
import com.monstredepoche.entities.attacks.AttackFactory;
import com.monstredepoche.entities.attacks.AttackType;

public class AttackLoader extends AbstractLoader<Attack> {
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

        return AttackFactory.createAttack(name, type, power, nbUse, fail);
    }

    @Override
    protected String getEntityName(Attack attack) {
        return attack.getName();
    }
}
