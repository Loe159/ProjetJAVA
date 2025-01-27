package com.monstredepoche.loader;

import com.monstredepoche.entities.attacks.Attack;
import com.monstredepoche.entities.item.Item;
import com.monstredepoche.entities.monsters.Monster;
import com.monstredepoche.entities.monsters.MonsterType;

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
            availableMonsters = monsterLoader.loadMonsters();
            System.out.println(availableMonsters.size() + " monstres chargés.");

            // Chargement des attaques
            availableAttacks = attackLoader.loadAttacks();
            System.out.println(availableAttacks.size() + " attaques chargées.");

            // Chargement des objets
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