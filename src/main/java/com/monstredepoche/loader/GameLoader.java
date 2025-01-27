package com.monstredepoche.loader;

import com.monstredepoche.entities.attacks.Attack;
import com.monstredepoche.entities.Item;
import com.monstredepoche.entities.monsters.Monster;

import java.util.ArrayList;
import java.util.List;

public class GameLoader {
    private List<Monster> availableMonsters;
    private List<Attack> availableAttacks;
    private List<Item> availableItems;

    public GameLoader() {
        availableMonsters = new ArrayList<>();
        availableAttacks = new ArrayList<>();
        availableItems = new ArrayList<>();
        loadGameData();
    }

    /**
     * Charge tous les fichiers nécessaires (monstres, attaques, objets) à l'initialisation.
     */
    private void loadGameData() {
        System.out.println("Début du chargement des données du jeu...");

        try {
            // Chargement des monstres
            MonsterLoader monsterLoader = new MonsterLoader();
            availableMonsters = monsterLoader.loadMonsters();
            System.out.println(availableMonsters.size() + " monstres chargés.");

            // Chargement des attaques
            AttackLoader attackLoader = new AttackLoader();
            availableAttacks = attackLoader.loadAttacks();
            System.out.println(availableAttacks.size() + " attaques chargées.");

            // Chargement des objets
            ItemLoader itemLoader = new ItemLoader();
            availableItems = itemLoader.loadItems();
            System.out.println(availableItems.size() + " objets chargés.");

            System.out.println("Chargement terminé avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des données du jeu: " + e.getMessage());
            e.printStackTrace();
        }
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
     * Retourne la liste des attaques disponibles.
     *
     * @return Liste d'attaques
     */
    public List<Attack> getAvailableAttacks() {
        if (availableAttacks.isEmpty()) {
            System.err.println("ATTENTION: Aucune attaque disponible !");
        }
        return new ArrayList<>(availableAttacks);
    }

    /**
     * Retourne les attaques compatibles avec le type d'un monstre.
     *
     * @param monster Le monstre pour lequel trouver les attaques compatibles
     * @return Liste d'attaques compatibles
     */
    public List<Attack> getCompatibleAttacks(Monster monster) {
        List<Attack> compatibleAttacks = new ArrayList<>();
        for (Attack attack : availableAttacks) {
            if (attack.getType().name() == monster.getType().name()) {
                compatibleAttacks.add(attack);
            }
        }

        if (compatibleAttacks.isEmpty()) {
            System.err.println("ATTENTION: Aucune attaque compatible trouvée pour " + monster.getName());
        }
        return compatibleAttacks;
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