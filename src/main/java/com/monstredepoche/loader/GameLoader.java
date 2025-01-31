package com.monstredepoche.loader;

import com.monstredepoche.entities.attacks.Attack;
import com.monstredepoche.entities.items.Item;
import com.monstredepoche.entities.monsters.Monster;

import java.util.ArrayList;
import java.util.List;

public class GameLoader {
    private final MonsterLoader monsterLoader;
    private final AttackLoader attackLoader;
    private final ItemLoader itemLoader;
    private List<Monster> availableMonsters;
    private List<Attack> availableAttacks;
    private List<Item> availableItems;

    public GameLoader() {
        this.monsterLoader = new MonsterLoader();
        this.attackLoader = new AttackLoader();
        this.itemLoader = new ItemLoader();
        loadGameData();
    }

    /**
     * Charge tous les fichiers nécessaires (monstres, attaques, objets) à l'initialisation.
     */
    private void loadGameData() {
        System.out.println("Début du chargement des données du jeu...");

        try {
            // Chargement des monstres
            availableMonsters = monsterLoader.loadEntities();
            System.out.println("Monstres chargés: " + availableMonsters.size());
            for (Monster monster : availableMonsters) {
                System.out.println(monster.getName() + " - " + monster.getType());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des monstres: " + e.getMessage());
            availableMonsters = List.of();
        }

        try {
            // Chargement des attaques
            availableAttacks = attackLoader.loadEntities();
            System.out.println("\nAttaques chargées: " + availableAttacks.size());
            for (Attack attack : availableAttacks) {
                System.out.println(attack.getName() + " - " + attack.getType());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des attaques: " + e.getMessage());
            availableAttacks = List.of();
        }

        try {
            // Chargement des objets
            availableItems = itemLoader.loadEntities();
            System.out.println("\nObjets chargés: " + availableItems.size());
            for (Item item : availableItems) {
                System.out.println(item.getName() + " - " + item.getDescription());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des objets: " + e.getMessage());
            availableItems = List.of();
        }

        System.out.println("Chargement terminé avec succès !");
    }

    /**
     * Retourne la liste des monstres disponibles.
     *
     * @return Liste de monstres
     */
    public List<Monster> getAvailableMonsters() {
        if (availableMonsters.isEmpty()) {
            System.err.println("ATTENTION: Aucun monstre disponible !");
        }
        return new ArrayList<>(availableMonsters);
    }

    /**
     * Retourne les attaques compatibles avec le type d'un monstre.
     *
     * @param monster Le monstre pour lequel trouver les attaques compatibles
     * @return Liste d'attaques compatibles
     */
    public List<Attack> getCompatibleAttacks(Monster monster) {
        return availableAttacks.stream()
                .filter(attack -> attack.getType().toString().equals(monster.getType().toString()))
                .toList();
    }

    /**
     * Retourne la liste des objets disponibles.
     *
     * @return Liste d'objets
     */
    public List<Item> getAvailableItems() {
        if (availableItems.isEmpty()) {
            System.err.println("ATTENTION: Aucun objet disponible !");
        }
        return new ArrayList<>(availableItems);
    }
}