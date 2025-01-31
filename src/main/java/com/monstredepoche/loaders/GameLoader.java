package com.monstredepoche.loaders;

import com.monstredepoche.entities.attacks.Attack;
import com.monstredepoche.entities.attacks.AttackType;
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
                System.out.println("\t" + monster.getName() + " - " + monster.getType());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des monstres: " + e.getMessage());
        }

        try {
            // Chargement des attaques
            availableAttacks = attackLoader.loadEntities();
            System.out.println("Attaques chargées: " + availableAttacks.size());
            for (Attack attack : availableAttacks) {
                System.out.println("\t" + attack.getName() + " - " + attack.getType());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des attaques: " + e.getMessage());
        }

        try {
            // Chargement des objets
            availableItems = itemLoader.loadEntities();
            System.out.println("Objets chargés: " + availableItems.size());
            for (Item item : availableItems) {
                System.out.println("\t" + item.getName() + " - " + item.getDescription());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des objets: " + e.getMessage());
        }

        System.out.println("\nChargement terminé avec succès !\n");
    }

    /**
     * Retourne la liste des monstres disponibles.
     *
     * @return Liste de monstres
     */
    public List<Monster> getAvailableMonsters() {
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
                .filter(attack -> attack.getType().getCorrespondingType().equals(monster.getType()) || attack.getType().equals(AttackType.NORMAL))
                .toList();
    }

    /**
     * Retourne la liste des objets disponibles.
     *
     * @return Liste d'objets
     */
    public List<Item> getAvailableItems() {
        return new ArrayList<>(availableItems);
    }
}