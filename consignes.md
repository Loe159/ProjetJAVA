## TP - Monstre de poche

- Sommaire Sommaire
- Introduction
- Rendu
- Les monstres
   - Monstre de type foudre
   - Monstre de type eau
   - Monstre de type terre
   - Monstre de type feu
   - Monstre de type nature
      - Monstre de type plante
      - Monstre de type insecte
- Les attaques
- Les objets
- Le combat
- Chargement du jeu
   - Chargement des monstres
   - Chargement des attaques


## Introduction

Le but du jeu est de créer un petit jeu de combat en tour par tour avec deux joueurs. A
l’instar de Pokémon, chaque joueur aura une liste de monstres qu’il pourra invoquer.
Chaque monstre peut utiliser des attaques et le joueur peut également choisir d’utiliser des
objets.

Chaque chapitre de ce TP détaille les mécanismes de ce jeu. **Il est donc important de lire
le TP dans sa globalité avant de commencer**.

Ce TP est une adaptation en Java du TP C++ proposé par Aurélien Texier.

## Rendu

A l’issu de ce TP est attendue une archive .zip contenant les fichiers sources de votre projet
joint d’un fichier README détaillant comment jouer au jeu.


## Les monstres

Les éléments principaux de ce jeu sont les monstres. Chaque monstre appartient à un type,
ce qui lui confère certains pouvoirs. Les types sont :
● la terre,
● la foudre,
● l’eau,
● le feu,
● la nature,

Chaque type à un rapport de force/faiblesse par rapport aux autres :

Chaque monstre a un nom et plusieurs caractéristiques :
● Points de vie
● Attaque
● Défense
● Vitesse

Également, un monstre peut avoir jusqu’à 4 attaques différentes (définies plus tard). Chacun
leur tour, ils pourront utiliser une de leurs attaques ou bien attaquer les mains nues si
aucune attaque n’est disponible.

En attaquant les mains nues, un monstre inflige un montant de dégâts au monstre adverse
défini comme suit :


```
𝐷é𝑔â𝑡𝑠= 20 × 𝑑é𝑓𝑒𝑛𝑠𝑒𝑎 𝑑𝑡𝑡𝑒𝑎 𝑙𝑞'𝑎𝑢𝑑𝑒𝑣𝑒𝑟𝑠𝑎𝑖𝑟𝑒 ×𝑐𝑜𝑒𝑓
```
avec 𝑐𝑜𝑒𝑓 un nombre aléatoire entre 0.85 et 1.

Dans le cas d’une attaque contre un adversaire ayant un type faible, un avantage lui est
conféré, ce qui double les dégâts infligés. A l’inverse, contre un adversaire ayant un type
fort, un malus est donné réduisant de moitié les dégâts.

Pendant leur combat, chaque monstre possède un état. Un monstre peut être empoisonné,
paralysé ou brûlé (détaillés plus tard). Un monstre ne peut qu’un seul état à la fois (il ne peut
pas être empoisonné et brûlé). L’état n’est pas changé en cas de changement de monstre,
mais est conservé. Autrement dit, si un monstre est brûlé, il ne subit pas les dégâts tant qu’il
est dans la main du joueur, mais reprendra ses brûlures s’il revient sur le terrain.

Enfin, chaque monstre possède une capacité spéciale pouvant se déclencher à la suite
d’une attaque (qui n’est pas de type normal).

### Monstre de type foudre

Après une attaque, les monstres de type foudre ont une chance de paralyser leur adversaire
pendant un certain nombre de tour. Un monstre déjà paralysé peut-être de nouveau
paralysé.

(En plus des caractéristiques de base, sera alors prévu une caractéristique représentant les
chances de paralyser et un moyen de savoir si l’adversaire a été paralysé).

Sous l’état de paralysie, un monstre a une chance sur quatre de réussir une attaque. Un
monstre peut sortir de cet état au début de chaque tour avec une probabilité correspondant
au nombre de tours passé dans cet état divisé par six (les chances de ne plus être paralysé
au 6e tour sont donc de 100%).

Les montres de type foudre sont forts contre les monstres de type eau et faibles contre les
monstres de type terre.


### Monstre de type eau

Les monstres de type eau ont la possibilité d’inonder le terrain pendant un à trois tours.
Quand le terrain est inondé, l’adversaire a une chance de glisser pendant son attaque.
L’attaque est alors annulée et ce dernier subit des dégâts équivalents aux quart de sa propre
attaque.

L’inondation n’affecte pas les monstres de type eau et est automatiquement retirée lorsque
le monstre l’ayant déclenché quitte le terrain.

Pour implémenter cela, deux caractéristiques supplémentaires seront ajoutées aux monstres
d’eau : la probabilité d’inonder le terrain et la probabilité de faire chuter l’adversaire. Tout
comme la paralysie, prévoyez une méthode pour savoir si l’adversaire a chuté.

Les montres de type eau sont forts contre les monstres de type feu et faibles contre les
monstres de type foudre.

### Monstre de type terre

Les monstres de type terre ont la possibilité de se cacher sous terre pendant un à trois tours
(nombre aléatoire). Cela double leur défense, ce qui les rend alors plus résistants aux
attaques de leur adversaire. Cela se déclenche à chaque fois que le monstre utilise une
attaque spéciale (type terre).

Les montres de type terre sont forts contre les monstres de type foudre et faibles contre les
monstres de type nature.

### Monstre de type feu

Les monstres de type feu ont la possibilité de brûler leur adversaire. Quand un monstre est
brûlé, il subit au début de chaque tour un dixième de son attaque. Cela se déclenche à
chaque fois que le monstre utilise une attaque spéciale (type feu). La seule possibilité de
soigner un monstre d’une brûlure est de se trouver dans un terrain inondé.

Les montres de type feu sont forts contre les monstres de type nature et faibles contre les
monstres de type eau.


### Monstre de type nature

Le type ‘nature’ est une catégorie de types qui réunit le type plante et le type insecte. Les
monstres de type nature ont la faculté de récupérer un vingtième de leurs points de vie au
début de chaque tour s’ils se trouvent sur un terrain inondé.

### Monstre de type plante

Les monstres de type plante ont une chance, à la fin d’une attaque, de se soigner, ce qui
supprime les altérations d’état (empoisonnement, paralysie, brûlure). Cela vient compléter le
soin inhérent du type nature.

#### Monstre de type insecte

A l’instar des monstres de type feu, les monstres de type insecte peuvent empoisonner leur
cible. Une cible empoisonnée subit alors un dixième de son attaque au début de son tour et
l’état d’empoisonnement est retiré quand le monstre se trouve dans un terrain inondé. Cela
se déclenche à chaque fois que le monstre utilise une attaque spéciale (type nature).

## Les attaques

Pour se combattre, les monstres ont à disposition plusieurs attaques. Les attaques ont les
caractéristiques suivantes :
● Un nom unique pour les identifier
● Un type qui est soit un des cinq types précédents (foudre, eau, terre, feu, nature) ou
de type normal.
○ Les attaques de type normal peuvent être attribuées à n’importe quel monstre
○ Les attaques de type spécial peuvent uniquement être attribuées à un
monstre du même type
● Un nombre d’utilisation qui limite l’utilisation de l’attaque au cours d’un combat
● Une puissance d’attaque influant les dégâts infligés à l’adversaire
● Une probabilité d’échec qui signifie que le monstre rate son attaque. Quand un
monstre rate son attaque, l’effet spécial du type ne peut pas avoir lieu

Les dégâts infligés par une attaque sont définis par la formule suivante :

```
𝐷é𝑔â𝑡𝑠 = (^11 × 2 5 𝑎 ×𝑡𝑡 𝑎𝑑𝑞é𝑢𝑓𝑒𝑒^ 𝑛×𝑠^ 𝑒𝑝 𝑢𝑑𝑖𝑒𝑠 𝑙𝑠'𝑎𝑎𝑛𝑑𝑐𝑣𝑒𝑒^ 𝑑𝑟𝑠'𝑎𝑎𝑡𝑖𝑡𝑟𝑎𝑒𝑞𝑢𝑒 + 2 )×𝑎𝑣𝑎𝑛𝑡𝑎𝑔𝑒× 𝑐𝑜𝑒𝑓
```

avec 𝑐𝑜𝑒𝑓 étant un nombre entre 0.85 et 1 et 𝑎𝑣𝑎𝑛𝑡𝑎𝑔𝑒 prenant pour valeur :
● 1 s’il n’y a aucun rapport force/faiblesse,
● 0.5 si l’attaque est faible contre l’adversaire,
● 2 si l’attaque est forte contre l’adversaire

## Les objets

En plus des monstres et de leurs attaques, le joueur peut utiliser des objets
(consommables). Ils en existent deux types :
● Les potions, qui affectent les caractéristiques d’un monstre (régénération de points
de vie, augmentation de l’attaque...)
● Les médicaments, qui affectent un état (guérison de la brûlure, assèchement du
terrain...)

Les objets et leurs effets sont totalement libres.

## Le combat

Une fois tous ces éléments implémentés, il reste à définir la mécanique de combat. Un
joueur peut avoir jusqu’à trois monstres et cinq objets. Chacun leur tour, les joueurs
sélectionnent une action à réaliser. Ils peuvent alors :
● Choisir l’attaque à lancer par le monstre qui leur appartient sur le terrain,
● Utiliser un objet
● Changer de monstre sur le terrain

Les deux joueurs choisissent leur action en même temps. Les actions sélectionnées sont
réalisées dans l’ordre suivant :
● Les changements de monstres sont réalisés en premier,
● Ensuite, les objets sont utilisés
● Puis les monstres attaquent. Si les deux monstres attaquent, le monstre avec la plus
grande vitesse attaque obtient l’initiative

Un joueur perd le combat dès que tous ces monstres n’ont plus de points de vie.


## Chargement du jeu

Pour diversifier les parties, il est plus intéressant de pouvoir sélectionner les monstres et les
attaques parmi une base de données fournie au jeu. Cette base de données doit suivre le
schéma décrit. Il est néanmoins possible d’ajouter de nouveaux champs pour apporter de
potentiels améliorations.

### Chargement des monstres

Vous pourrez charger les monstres depuis un fichier texte. Chaque monstre sera défini entre
deux champs Monster et EndMonster.

Exemples :
Monster
   Name Pikachu
   Type Electric
   HP 110 141
   Speed 110 141
   Attack 75 106
   Defense 50 82
   Paralysis 0.
EndMonster

Monster
Name Cerapuca
   Type Water
   HP 119 150
   Attack 68 99
   Speed 63 94
   Defense 85 116
   Flood 0.
   Fall 0.
EndMonster

Les champs peuvent être entrés dans un ordre différent. Pour les champs ayant plusieurs
valeurs, les deux valeurs indiquent les bornes minimales et maximales pouvant être prises
par le champ en question.

Chaque élément Monster ne correspond pas réellement à un monstre, mais un monstre
peut être créé à partir de chaque élément (en ayant une vitesse comprise entre SpeedMin

et SpeedMax par exemple). Quand un joueur choisit ses monstres, vous pouvez soit utiliser


des caractéristiques aléatoires dans l’intervalle donné, ou bien laisser l’utilisateur modifier
les caractéristiques (librement, ou par un système d’équilibrage de points). A vous trouver le
meilleur moyen de stocker ces informations.

Vous pouvez ajouter les monstres que vous souhaitez. Vous trouverez une liste non
exhaustive de monstres ici.

### Chargement des attaques

Les attaques seront aussi stockées dans un fichier texte prenant la même forme que celui
des monstres. Les attaques seront définies entre les balises Attack et EndAttack.

Exemples :
Attack
   Name Eclair
   Type Electric
   Power 40
   NbUse 10
   Fail 0.
EndAttack

Attack
   Name Charge
   Type Normal
   Power 35
   NbUse 15
   Fail 0.
EndAttack

Lorsque vous créez un monstre, vous pouvez lui associer quatre attaques parmi celles à
disposition du même type ou du type normal.

Une liste non exhaustive d’attaques est disponible ici.


