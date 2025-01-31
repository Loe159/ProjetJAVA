package com.monstredepoche.battle;

import com.monstredepoche.entities.attacks.Attack;
import com.monstredepoche.entities.attacks.AttackType;
import com.monstredepoche.entities.items.Item;
import com.monstredepoche.entities.Player;
import com.monstredepoche.entities.monsters.Monster;
import com.monstredepoche.entities.monsters.WaterMonster;
import com.monstredepoche.utils.RandomUtils;
import com.monstredepoche.utils.FormatUtils;

import java.util.List;
import java.util.Scanner;

public class Battle {
    private final Player player1;
    private final Player player2;
    private final Scanner scanner;

    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println(FormatUtils.formatBattleHeader("Début du combat"));
        
        while (!isOver()) {
            displayStatus();
            executeTurn();
        }
        
        announceWinner();
    }

    private void displayStatus() {
        System.out.println(FormatUtils.formatBattleHeader("ÉTAT DU COMBAT"));
        System.out.println(FormatUtils.colorize("╔═════════════════════════════════════════════════════════════════════╗", FormatUtils.BLUE));
        displayMonsterStatus(player1);
        System.out.println(FormatUtils.colorize("║                   VS                                                ║", FormatUtils.BLUE));
        displayMonsterStatus(player2);
        System.out.println(FormatUtils.colorize("╚═════════════════════════════════════════════════════════════════════╝\n", FormatUtils.BLUE));
    }

    private void displayMonsterStatus(Player player) {
        Monster monster = player.getActiveMonster();
        String healthBar = FormatUtils.formatHealthBar(monster.getCurrentHp(), monster.getMaxHp());
        String playerName = FormatUtils.colorizePlayer(player.getName(), player == player1);
        String monsterInfo = String.format("%-17s %-20s %-5s %-3s",
            playerName,
            FormatUtils.colorize(monster.getName(), FormatUtils.BOLD),
            healthBar,
            FormatUtils.formatStatus(monster.getStatus().toString())
        );
        System.out.println(FormatUtils.colorize("║", FormatUtils.BLUE) + " " + monsterInfo +
            FormatUtils.colorize("              ║", FormatUtils.BLUE));
    }

    private void displayMenu(String playerName) {
        System.out.println(FormatUtils.formatBattleHeader("TOUR DE " + playerName));
        System.out.println(FormatUtils.colorize("1. ", FormatUtils.BLUE) + "Attaquer");
        System.out.println(FormatUtils.colorize("2. ", FormatUtils.BLUE) + "Utiliser un objet");
        System.out.println(FormatUtils.colorize("3. ", FormatUtils.BLUE) + "Changer de monstre");
    }

    private void executeTurn() {
        Player[] players = {player1, player2};
        Attack[] selectedAttacks = new Attack[2];
        Item[] selectedItem = new Item[2];
        int[] switchChoices = new int[2];

        // Phase de sélection
        for (int i = 0; i < 2; i++) {
            boolean actionSelected = false;
            while (!actionSelected) {
                displayMenu(players[i].getName());
                String playerColor = players[i] == player1 ? FormatUtils.CYAN : FormatUtils.PURPLE;
                System.out.print(playerColor + "[" + players[i].getName() + "]" + FormatUtils.RESET + " Votre choix (1-3): ");

                int choice = getIntInput(1, 3);
                switch (choice) {
                    case 1 -> {
                        Attack attack = selectAttack(players[i]);
                        if (attack != null) {
                            selectedAttacks[i] = attack;
                            actionSelected = true;
                        }
                    }
                    case 2 -> {
                        selectedItem[i] = selectItem(players[i]);
                        if (selectedItem[i] != null) {
                            actionSelected = true;
                        }
                    }
                    case 3 -> {
                        int switchChoice = selectMonsterToSwitch(players[i]);
                        if (switchChoice >= 0) {
                            switchChoices[i] = switchChoice;
                            actionSelected = true;
                        }
                    }
                }
            }
        }

        // 1. Exécution des changements de monstres
        for (int i = 0; i < 2; i++) {
            if (switchChoices[i] >= 0) {
                players[i].switchMonster(switchChoices[i]);
                System.out.println(players[i].getActiveMonster().getName() + ", à toi !");
            }
        }

        // 2. Utilisation des objets
        for (int i = 0; i < 2; i++) {
            if (selectedItem[i] != null) {
                useItem(players[i], selectedItem[i]);
            }
        }

        // 3. Exécution des attaques selon la vitesse
        if (selectedAttacks[0] != null || selectedAttacks[1] != null) {
            Monster monster1 = player1.getActiveMonster();
            Monster monster2 = player2.getActiveMonster();
            boolean monster1First = monster1.getSpeed() >= monster2.getSpeed();

            int attackerIndex = monster1First ? 0 : 1;
            for (int i = 0; i < 2; i++) {

                Player attacker = players[attackerIndex];
                Player defender = players[1 - attackerIndex];
                Attack attack = selectedAttacks[attackerIndex];

                if (attack != null) {
                    executeAttack(attacker.getActiveMonster(), defender.getActiveMonster(), attack);
                }

                // Si un monstre est K.O. on arrête les attaques pour ce tour
                if (handleDeadMonster(defender)) {
                    break;
                }

                // Changement d'attaquant
                attackerIndex = 1 - attackerIndex;
            }
        }

        // 4. Application des effets de statut
        for (int i = 0; i < 2; i++) {
            Monster monster = players[i].getActiveMonster();
            if (monster != null && !monster.isDead()) {
                monster.startTurn();
            }
        }
    }

    private Item selectItem(Player player) {
        List<Item> items = player.getItems();
        if (items.isEmpty()) {
            System.out.println(FormatUtils.colorize("Vous n'avez plus d'objets !", FormatUtils.RED));
            return null;
        }

        System.out.println("\nChoisissez un objet :");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.println(FormatUtils.colorize((i + 1) + ". ", FormatUtils.BLUE) +
                FormatUtils.formatItemInfo(item.getName(), item.getDescription())
            );
        }

        System.out.print(FormatUtils.formatActionPrompt(player.getName(), player == player1) +
            "Votre choix (1-" + items.size() + ", 0 pour annuler): ");
        int choice = getIntInput(0, items.size());
        if (choice == 0) {
            return null;
        }
        return items.get(choice - 1);
    }

    private void useItem(Player player, Item item) {
        Monster monster = player.getActiveMonster();
        player.useItem(item, player.getActiveMonster());

        System.out.println("\n" + FormatUtils.YELLOW + player.getName() + " utilise " + item.getName() + " sur " + monster.getName() + FormatUtils.RESET + "\n");
    }

    private Attack selectAttack(Player player) {
        Monster monster = player.getActiveMonster();
        List<Attack> availableAttacks = monster.getAttacks();
        String playerColor = player == player1 ? FormatUtils.CYAN : FormatUtils.PURPLE;

        System.out.println("\nChoisissez une attaque pour " + monster.getName() + ":");
        for (int i = 0; i < availableAttacks.size(); i++) {
            Attack attack = availableAttacks.get(i);
            System.out.printf(FormatUtils.colorize((i + 1) + ". ", FormatUtils.BLUE) + "%s (Puissance: %d, Utilisations: %d, Taux d'échec: %.1f%%)%n",
                    attack.getName(),
                    attack.getPower(),
                    attack.getRemainingUses(),
                    attack.getFailRate() * 100);
        }

        System.out.print(FormatUtils.formatActionPrompt(player.getName(), player == player1) +
            "Votre choix (1-" + availableAttacks.size() + ", 0 pour annuler): ");
        int choice = getIntInput(0, availableAttacks.size());
        if (choice == 0) {
            return null;
        }
        return availableAttacks.get(choice - 1);
    }

    private void executeAttack(Monster attacker, Monster defender, Attack attack) {
        String attackerName = FormatUtils.colorize(attacker.getName(), FormatUtils.BOLD);
        String defenderName = FormatUtils.colorize(defender.getName(), FormatUtils.BOLD);

        if (attack.getType() == AttackType.BAREHANDED) {
            System.out.println(FormatUtils.colorize("\n" + attackerName + " attaque à la main !", FormatUtils.YELLOW));
        } else {
            System.out.println(FormatUtils.colorize("\n" + attackerName + " utilise " +
                FormatUtils.BOLD + attack.getName() + FormatUtils.RESET + " !", FormatUtils.YELLOW));
        }
        
        if (WaterMonster.shouldSlip(attacker)) {
            int selfDamage = attacker.calculateDamage(attack, attacker) / 4;
            attacker.takeDamage(selfDamage);
            System.out.println(FormatUtils.colorize(attackerName + " glisse sur le terrain inondé et se blesse !", FormatUtils.RED));
            System.out.println(FormatUtils.colorize(attackerName + " perd " + selfDamage + " PV !", FormatUtils.RED));
            return;
        }
        
        if (RandomUtils.tryChance(attack.getFailRate())) {
            System.out.println(FormatUtils.colorize("L'attaque a échoué !", FormatUtils.RED));
            return;
        }

        int damage = attacker.calculateDamage(attack, defender);
        defender.takeDamage(damage);
        attack.use();

        System.out.println(FormatUtils.colorize(attackerName + " inflige " + damage + " dégâts à " + defenderName + " !",
            FormatUtils.PURPLE));
        System.out.println(FormatUtils.formatHealthBar(defender.getCurrentHp(), defender.getMaxHp()) +
            FormatUtils.colorize(" PV:" + defender.getCurrentHp() + "/" + defender.getMaxHp(), FormatUtils.GREEN));

        if (attack.isSpecial()) {
            attacker.useSpecialAbility(defender);
        }

        if (defender.isDead()) {
            System.out.println(FormatUtils.colorize(defenderName + " est K.O. !", FormatUtils.RED));
        }
    }

    private boolean handleDeadMonster(Player player) {
        if (player.getActiveMonster().isDead() && !player.hasLost()) {
            player.removeMonster(player.getActiveMonster());
            System.out.println("\n" + player.getName() + ", votre monstre est K.O. !");
            System.out.println("Choisissez un nouveau monstre :");

            List<Monster> monsters = player.getMonsters();
            String playerColor = player == player1 ? FormatUtils.CYAN : FormatUtils.PURPLE;

            for (int i = 0; i < monsters.size(); i++) {
                Monster monster = monsters.get(i);
                System.out.printf("%d. %s (PV: %d/%d)%n",
                        i + 1,
                        monster.getName(),
                        monster.getCurrentHp(),
                        monster.getMaxHp());
            }

            System.out.print(playerColor + "[" + player.getName() + "]" + FormatUtils.RESET + " Votre choix (1-" + monsters.size() + "): ");
            int choice = getIntInput(1, monsters.size()) - 1;
            Monster selected = monsters.get(choice);
            player.switchMonster(choice);
            System.out.println(selected.getName() + ", à toi !");
            return true;
        }
        return false; // Le monstre n'est pas mort
    }

    private boolean isOver() {
        return player1.hasLost() || player2.hasLost();
    }

    private void announceWinner() {
        Player winner = player1.hasLost() ? player2 : player1;
        System.out.println(FormatUtils.formatBattleHeader("VICTOIRE DE " + winner.getName()));
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
            } catch (NumberFormatException e) {
                // Ignore
            }
            System.out.println("Choix invalide, réessayez.");
        }
    }

    private int selectMonsterToSwitch(Player player) {
        System.out.println("\nChoisissez un nouveau monstre :");
        for (int i = 0; i < player.getMonsters().size(); i++) {
            Monster monster = player.getMonsters().get(i);
            System.out.println(FormatUtils.colorize((i + 1) + ". ", FormatUtils.BLUE) +
                FormatUtils.formatMonsterInfo(
                    monster.getName(),
                    monster.getType(),
                    monster.getCurrentHp(),
                    monster.getAttack(),
                    monster.getDefense(),
                    monster.getSpeed()
                )
            );
        }

        System.out.print(FormatUtils.formatActionPrompt(player.getName(), player == player1) +
            "Votre choix (1-" + player.getMonsters().size() + "): ");
        int choice = getIntInput(1, player.getMonsters().size());
        return choice - 1;
    }
} 