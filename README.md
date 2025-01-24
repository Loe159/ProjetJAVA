# Pocket Monster

Un jeu de combat de monstres inspiré de Pokémon, développé en Java.

## Fonctionnalités

- 6 types de monstres : Foudre, Eau, Terre, Feu, Plante et Insecte
- Système de combat au tour par tour
- Effets de statut : Paralysie, Brûlure, Poison
- Capacités spéciales pour chaque type
- Système d'avantages/désavantages entre les types
- Mode de configuration manuelle ou aléatoire

## Comment jouer

1. Lancez le jeu en exécutant la classe `Game`
2. Choisissez le mode de configuration :
   - Configuration manuelle : Choisissez vos monstres et leurs attaques
   - Configuration aléatoire : Les monstres et attaques sont choisis automatiquement

### Configuration manuelle

1. Pour chaque joueur :
   - Choisissez 3 monstres parmi la liste disponible
   - Pour chaque monstre, choisissez 3 attaques compatibles avec son type

### Combat

- À chaque tour :
  1. L'état des monstres est affiché
  2. Chaque joueur choisit une attaque
  3. Le monstre le plus rapide attaque en premier
  4. Les effets de statut sont appliqués
  5. Si un monstre est K.O., le joueur doit en choisir un autre

## Types et avantages

- Foudre : Efficace contre Eau, faible contre Terre
- Eau : Efficace contre Feu, faible contre Plante
- Terre : Efficace contre Foudre, faible contre Eau
- Feu : Efficace contre Plante et Insecte, faible contre Eau
- Plante : Efficace contre Eau et Terre, faible contre Feu et Insecte
- Insecte : Efficace contre Plante, faible contre Feu

## Capacités spéciales

- Foudre : Chance de paralyser (25% de chance de ne pas pouvoir attaquer)
- Eau : Chance d'inonder le terrain (guérit les monstres Plante)
- Terre : Chance de se cacher sous terre (défense doublée)
- Feu : Chance de brûler (dégâts continus)
- Plante : Se soigne sur terrain inondé
- Insecte : Chance d'empoisonner (dégâts continus importants)

## Compilation et exécution

```bash
# Compilation
javac -d bin src/main/java/com/pocketmonster/**/*.java

# Exécution
java -cp bin com.pocketmonster.game.Game
``` 