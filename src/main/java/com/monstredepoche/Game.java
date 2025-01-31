package com.monstredepoche;

import com.monstredepoche.entities.attacks.Attack;
import com.monstredepoche.entities.items.Item;
import com.monstredepoche.entities.Player;
import com.monstredepoche.entities.monsters.Monster;
import com.monstredepoche.battle.Battle;
import com.monstredepoche.loaders.GameLoader;
import com.monstredepoche.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final GameLoader gameLoader;
    private final Scanner scanner;

    public Game() {
        System.out.println("Initialisation du jeu...");
        this.gameLoader = new GameLoader();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
            System.out.println("=== Pocket Monster ===");
            System.out.println("1. Configuration manuelle");
            System.out.println("2. Configuration aléatoire rapide");
            System.out.print("Votre choix (1-2): ");

            int choice = getIntInput(1, 2);
            Player player1, player2;

            List<Monster> availableMonsters = new ArrayList<>(gameLoader.getAvailableMonsters());
            if (availableMonsters.isEmpty()) {
                throw new RuntimeException("Aucun monstre n'a été chargé !");
            }

            if (choice == 1) {
                player1 = createPlayer("Joueur 1");
                player2 = createPlayer("Joueur 2");
            } else {
                player1 = createRandomPlayer("Joueur 1");
                player2 = createRandomPlayer("Joueur 2");
            }

            // Distribution des objets
            List<Item> items = gameLoader.getAvailableItems();
            if (!items.isEmpty()) {
                if (choice == 1) {
                    distributeItems(player1);
                    distributeItems(player2);
                } else {
                    distributeRandomItems(player1);
                    distributeRandomItems(player2);
                }
            }

            if (player1.getMonsters().isEmpty() || player2.getMonsters().isEmpty()) {
                throw new RuntimeException("Les joueurs n'ont pas de monstres !");
            }

            Battle battle = new Battle(player1, player2);
            battle.start();
    }

    private Player createPlayer(String name) {
        try {
            Player player = new Player(name);
            System.out.println("\nConfiguration de " + name);

            List<Monster> availableMonsters = new ArrayList<>(gameLoader.getAvailableMonsters());
            if (availableMonsters.isEmpty()) {
                throw new RuntimeException("Aucun monstre disponible pour " + name);
            }

            for (int i = 0; i < 3 && !availableMonsters.isEmpty(); i++) {
                System.out.println("\nChoisissez le monstre " + (i + 1) + ":");
                displayMonsterList(availableMonsters);

                Monster selectedMonster = selectMonster(availableMonsters).clone();
                List<Attack> compatibleAttacks = new ArrayList<>(gameLoader.getCompatibleAttacks(selectedMonster));

                if (compatibleAttacks.isEmpty()) {
                    throw new RuntimeException("Aucune attaque compatible pour " + selectedMonster.getName());
                }

                System.out.println("\nChoisissez 4 attaques pour " + selectedMonster.getName() + ":");
                for (int j = 0; j < 4 && !compatibleAttacks.isEmpty(); j++) {
                    displayAttackList(compatibleAttacks);
                    Attack selectedAttack = selectAttack(compatibleAttacks);
                    selectedMonster.addAttack(selectedAttack);
                    compatibleAttacks.remove(selectedAttack);
                }

                player.addMonster(selectedMonster);
                System.out.println(selectedMonster.getName() + " ajouté à l'équipe de " + name);
            }

            return player;
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du joueur " + name + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Player createRandomPlayer(String name) {
            Player player = new Player(name);
            List<Monster> availableMonsters = new ArrayList<>(gameLoader.getAvailableMonsters());

            if (availableMonsters.size() < 3) {
                throw new RuntimeException("Pas assez de monstres disponibles pour " + name);
            }

            System.out.println("\nCréation aléatoire de l'équipe de " + name);
            for (int i = 0; i < 3 && !availableMonsters.isEmpty(); i++) {
                int monsterIndex = RandomUtils.getRandomInt(0, availableMonsters.size() - 1);
                Monster monster = availableMonsters.get(monsterIndex).clone();
                List<Attack> compatibleAttacks = new ArrayList<>(gameLoader.getCompatibleAttacks(monster));

                if (compatibleAttacks.isEmpty()) {
                    System.err.println("Attention: Aucune attaque compatible pour " + monster.getName());
                    continue;
                }

                for (int j = 0; j < 3 && !compatibleAttacks.isEmpty(); j++) {
                    int attackIndex = RandomUtils.getRandomInt(0, compatibleAttacks.size() - 1);
                    Attack attack = compatibleAttacks.get(attackIndex);
                    monster.addAttack(attack);
                    compatibleAttacks.remove(attack);
                }

                player.addMonster(monster);
                availableMonsters.remove(monsterIndex);
                System.out.println(monster.getName() + " ajouté à l'équipe de " + name);
            }

            if (player.getMonsters().isEmpty()) {
                throw new RuntimeException("Impossible de créer une équipe pour " + name);
            }

            return player;
    }

    private void distributeItems(Player player) {
        System.out.println("\nDistribution des objets pour " + player.getName() + ":");
        System.out.println("Vous pouvez choisir jusqu'à 5 objets.");

        List<Item> availableItems = new ArrayList<>(gameLoader.getAvailableItems());


        for (int i = 0; i < 5 && !availableItems.isEmpty(); i++) {
            displayItemList(availableItems);

            System.out.print("Votre choix (1-" + availableItems.size() + ", 0 pour terminer): ");
            
            int choice = getIntInput(0, availableItems.size());
            if (choice == 0) break;
            
            Item selectedItem = availableItems.get(choice - 1);
            player.addItem(selectedItem);
            availableItems.remove(selectedItem);
            System.out.println(selectedItem.getName() + " ajouté à l'inventaire de " + player.getName());
        }
    }

    private void distributeRandomItems(Player player) {
        List<Item> availableItems = new ArrayList<>(gameLoader.getAvailableItems());

        System.out.println("\nDistribution aléatoire des objets pour " + player.getName());

        for (int i = 0; i < 5 && !availableItems.isEmpty(); i++) {
            int index = RandomUtils.getRandomInt(0, availableItems.size() - 1);
            Item selectedItem = availableItems.get(index);
            player.addItem(selectedItem);
            System.out.println(selectedItem.getName() + " ajouté à l'inventaire de " + player.getName());
        }
    }

    private void displayItemList(List<Item> items) {
        System.out.println("\nObjets disponibles:");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s %s%n", i + 1, items.get(i).getName(), items.get(i).getDescription());
        }
    }

    private void displayMonsterList(List<Monster> monsters) {
        for (int i = 0; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            System.out.printf("%d. %s (Type: %s, PV: %d, Attaque: %d, Défense: %d, Vitesse: %d)%n",
                i + 1,
                monster.getName(),
                monster.getType(),
                monster.getMaxHp(),
                monster.getAttack(),
                monster.getDefense(),
                monster.getSpeed());
        }
    }

    private void displayAttackList(List<Attack> attacks) {
        for (int i = 0; i < attacks.size(); i++) {
            Attack attack = attacks.get(i);
            System.out.printf("%d. %s (Puissance: %d, Utilisations: %d, Taux d'échec: %.1f%%)%n",
                i + 1,
                attack.getName(),
                attack.getPower(),
                attack.getMaxUses(),
                attack.getFailRate() * 100);
        }
    }

    private Monster selectMonster(List<Monster> monsters) {
        System.out.print("Votre choix (1-" + monsters.size() + "): ");
        int choice = getIntInput(1, monsters.size());
        Monster monster = monsters.get(choice - 1);
        monsters.remove(choice - 1);
        return monster;
    }

    private Attack selectAttack(List<Attack> attacks) {
        System.out.print("Votre choix (1-" + attacks.size() + ", 0 pour annuler): ");
        int choice = getIntInput(0, attacks.size());
        if (choice == 0) {
            return null;
        }
        return attacks.get(choice - 1);
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // Ignore
            }
            System.out.println("Choix invalide, réessayez.");
        }
    }
} 