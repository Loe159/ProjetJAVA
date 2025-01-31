package com.monstredepoche.battle;

import com.monstredepoche.entities.attacks.Attack;
import com.monstredepoche.entities.attacks.AttackType;
import com.monstredepoche.entities.attacks.BareHandedAttack;
import com.monstredepoche.entities.items.Item;
import com.monstredepoche.entities.Player;
import com.monstredepoche.entities.StatusEffect;
import com.monstredepoche.entities.monsters.Monster;
import com.monstredepoche.entities.monsters.WaterMonster;
import com.monstredepoche.utils.RandomUtils;

import java.util.List;
import java.util.Scanner;

public class Battle {
    // Codes couleur ANSI
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    private final Player player1;
    private final Player player2;
    private final Scanner scanner;

    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        displayBanner();
        
        while (!isOver()) {
            displayStatus();
            executeTurn();
        }
        
        announceWinner();
    }

    private void displayBanner() {
        System.out.println(YELLOW + """
        ╔══════════════════════════════════════╗
        ║            Début du combat           ║
        ╚══════════════════════════════════════╝""" + RESET);
    }

    private void displayStatus() {
        System.out.println(BLUE + "\n╔═══════════════ ÉTAT DU COMBAT ══════════════════════════════════════╗" + RESET);
        displayMonsterStatus(player1);
        System.out.println(BLUE + "║                   VS                                                ║" + RESET);
        displayMonsterStatus(player2);
        System.out.println(BLUE + "╚═════════════════════════════════════════════════════════════════════╝\n" + RESET);
    }

    private void displayMonsterStatus(Player player) {
        Monster monster = player.getActiveMonster();
        String healthBar = createHealthBar(monster.getCurrentHp(), monster.getMaxHp());
        String playerName = player == player1 ? CYAN + player.getName() + RESET : PURPLE + player.getName() + RESET;
        System.out.printf(BLUE + "║" + RESET + " %-30s %-15s %s %s " + BLUE + "║\n" + RESET,
            playerName,
            monster.getName(),
            healthBar,
            formatStatus(monster.getStatus()));
    }

    private String createHealthBar(int current, int max) {
        int barLength = 20;
        int filledLength = (int)((double)current / max * barLength);
        StringBuilder bar = new StringBuilder("[");
        
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append(GREEN + "█" + RESET);
            } else {
                bar.append(RED + "░" + RESET);
            }
        }
        bar.append(String.format("] %d/%d", current, max));
        return bar.toString();
    }

    private String formatStatus(StatusEffect status) {
        return switch (status) {
            case NORMAL -> "";
            case PARALYZED -> YELLOW + "(PAR)" + RESET;
            case BURNED -> RED + "(BRU)" + RESET;
            case POISONED -> PURPLE + "(PSN)" + RESET;
        };
    }

    private void displayMenu(String playerName) {
        System.out.println(CYAN + String.format("""
        ╔═══════════ TOUR DE %s ══════════╗
        ║ 1. Attaquer                           ║
        ║ 2. Utiliser un objet                  ║
        ║ 3. Changer de monstre                 ║
        ╚═══════════════════════════════════════╝""", playerName) + RESET);
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
                String playerColor = players[i] == player1 ? CYAN : PURPLE;
                System.out.print(playerColor + "[" + players[i].getName() + "]" + RESET + " Votre choix (1-3): ");

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

                executeAttack(attacker.getActiveMonster(), defender.getActiveMonster(), attack);

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
            System.out.println("Vous n'avez pas d'objets !");
            return null;
        }

        System.out.println("\nObjets disponibles :");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, items.get(i).getName(), items.get(i).getDescription());
        }

        System.out.print("Votre choix (1-" + items.size() + ", 0 pour annuler): ");
        int choice = getIntInput(0, items.size());

        if (choice == 0) {
            return null;
        }

        return items.get(choice - 1);
    }

    private void useItem(Player player, Item item) {
        Monster monster = player.getActiveMonster();
        player.useItem(item, player.getActiveMonster());

        System.out.println("\n" + YELLOW + player.getName() + " utilise " + item.getName() + " sur " + monster.getName() + RESET + "\n");
    }

    private Attack selectAttack(Player player) {
        Monster monster = player.getActiveMonster();
        String playerColor = player == player1 ? CYAN : PURPLE;

        List<Attack> attacks = monster.getAttacks();
        List<Attack> availableAttacks = attacks.stream()
                .filter(a -> a.getRemainingUses() > 0)
                .toList();

        // Plus d'attaques disponibles, attaque à la main,
        if (availableAttacks.isEmpty()) {
            System.out.println("\n" + RED + "Aucune attaque disponible pour " + monster.getName() + " !" + RESET);
            System.out.println(YELLOW + "Vous pouvez attaquer à la main (dégâts de base)" + RESET);
            System.out.print(playerColor + "[" + player.getName() + "]" + RESET + " Votre choix (1 pour attaquer à la main, 0 pour annuler): ");
            int choice = getIntInput(0, 1);
            if (choice == 0) {
                return null;
            }
            return new BareHandedAttack();
        }

        System.out.println("\nChoisissez une attaque pour " + monster.getName() + ":");
        for (int i = 0; i < availableAttacks.size(); i++) {
            Attack attack = availableAttacks.get(i);
            System.out.printf("%d. %s (Puissance: %d, Utilisations: %d/%d)%n",
                    i + 1,
                    attack.getName(),
                    attack.getPower(),
                    attack.getRemainingUses(),
                    attack.getMaxUses());
        }

        System.out.print(playerColor + "[" + player.getName() + "]" + RESET + " Votre choix (1-" + availableAttacks.size() + ", 0 pour annuler): ");
        int choice = getIntInput(0, availableAttacks.size());
        if (choice == 0) {
            return null;
        }
        return availableAttacks.get(choice - 1);
    }

    private void executeAttack(Monster attacker, Monster defender, Attack attack) {
        if (attack.getType() == AttackType.BAREHANDED) {
            System.out.println(YELLOW + "\n" + attacker.getName() + " attaque à la main !" + RESET);
        }

        else System.out.println(YELLOW + "\n" + attacker.getName() + " utilise " + attack.getName() + " !" + RESET);
        
        // Vérification du glissement sur terrain inondé
        if (WaterMonster.shouldSlip(attacker)) {
            System.out.println(RED + attacker.getName() + " glisse sur le terrain inondé et se blesse, son attaque échoue !" + RESET);
            return;
        }
        
        if (RandomUtils.tryChance(attack.getFailRate())) {
            System.out.println(RED + "L'attaque a échoué !" + RESET);
            return;
        }

        int damage = attacker.calculateDamage(attack, defender);
        defender.takeDamage(damage);
        attack.use();

        System.out.printf(PURPLE + "%s inflige %d dégâts à %s !%n" + RESET, 
            attacker.getName(), damage, defender.getName());
        System.out.printf(GREEN + "Il reste %d/%d PV à %s%n" + RESET, 
            defender.getCurrentHp(), defender.getMaxHp(), defender.getName());

        // Activation de la capacité spéciale après une attaque réussie
        if (attack.isSpecial()) {
            attacker.useSpecialAbility(defender);
        }

        if (defender.isDead()) {
            System.out.println(RED + defender.getName() + " est K.O. !" + RESET);
        }
    }

    private boolean handleDeadMonster(Player player) {
        if (player.getActiveMonster().isDead() && !player.hasLost()) {
            player.removeMonster(player.getActiveMonster());
            System.out.println("\n" + player.getName() + ", votre monstre est K.O. !");
            System.out.println("Choisissez un nouveau monstre :");

            List<Monster> monsters = player.getMonsters();
            String playerColor = player == player1 ? CYAN : PURPLE;

            for (int i = 0; i < monsters.size(); i++) {
                Monster monster = monsters.get(i);
                System.out.printf("%d. %s (PV: %d/%d)%n",
                        i + 1,
                        monster.getName(),
                        monster.getCurrentHp(),
                        monster.getMaxHp());
            }

            System.out.print(playerColor + "[" + player.getName() + "]" + RESET + " Votre choix (1-" + monsters.size() + "): ");
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
        String banner = String.format(YELLOW + """
        ╔══════════════════════════════════════╗
        ║             VICTOIRE DE              ║
        ║           %15s            ║
        ╚══════════════════════════════════════╝""" + RESET, winner.getName());
        System.out.println(banner);
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
        String playerColor = player == player1 ? CYAN : PURPLE;
        System.out.println(playerColor + "[" + player.getName() + "]" + RESET + " choisissez un nouveau monstre:");
        for (int i = 0; i < player.getMonsters().size(); i++) {
            Monster monster = player.getMonsters().get(i);
            System.out.printf("%d. %s (PV: %d/%d)%n", 
                i + 1, monster.getName(), monster.getCurrentHp(), monster.getMaxHp());
        }

        while (true) {
            try {
                System.out.print(playerColor + "[" + player.getName() + "]" + RESET + " Votre choix (1-" + player.getMonsters().size() + ", 0 pour annuler): ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) {
                    return -1;
                }
                choice--;
                if (choice >= 0 && choice < player.getMonsters().size()) {
                    Monster selected = player.getMonsters().get(choice);
                    if (!selected.isDead() && selected != player.getActiveMonster()) {
                        return choice;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide");
            } catch (Exception e) {
                System.err.println("Erreur lors du changement de monstre: " + e.getMessage());
            }
            System.out.println("Choix invalide, réessayez.");
        }
    }
} 