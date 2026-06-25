# USER STORIES - Itération 1

Ce document répertorie les histoires utilisateur sélectionnées pour la troisième itération du projet Bugémon. L'estimation est basée sur le principe vue en cours suivant :
**1 point = 1 heure idéal**.

---

## Objectif principal de l'itération
* **Objectif principal** : Implémenter un moteur de combat au tour par tour fonctionnel.
* **Points totaux de l'itération** : 82 points.

---

## Détails des User Stories

### Histoire 1 : Constituer une équipe (Adaptation client)
* **Description** : En tant que joueur, je souhaite constituer une équipe pour le combat.
* **Modifications client** : Limitation à **3 Bugémons** maximum par équipe.
* **Estimation** : 21 points.

### Histoire 2 : Sauvegarder et charger une équipe
* **Description** : Le joueur doit pouvoir sauvegarder une équipe de Bugémons déjà constituée en lui donnant un nom et dans le menu constitution d'équipe, charger une équipe sauvegardée précédemment.
* **Modifications client** : N/A
* **Estimation** : 7 points.

### Histoire 3 : Modifier équipes sauvegardées
* **Description** : Le joueur doit pouvoir modifier, supprimer et renommer les équipes déjà sauvegardées précédemment depuis un nouveau menu. Depuis ce menu, il peut aussi directement lancer une partie avec une équipe déjà créée ou simplement créer une nouvelle équipe à partir de zéro.
* **Modifications client** : N/A
* **Estimation** : 7 points.

### Histoire 4 & 5 (Combo) : Création du système de combat et actions du Bugémon (Adaptation client)
* **Description** : Mise en place du moteur de combat face à l'IA avec interface graphique.
* **Modifications client** :
  - **Contrôle manuel direct** : Le combat n'est jamais automatisé, l'utilisateur choisit chaque action.
  - **Séparation** : Distinction nette entre la phase de préparation (création/chargement) et le combat.
* **Estimation** : 47 points.

---

# USER STORIES - Itération 2

## Objectif principal de l'itération
* **Objectif principal** : Affiner le modèle de combat et ajouter des fonctionnalités de qualité de vie.
* **Points totaux de l'itération** : 82 points.

---

## Détails des User Stories

### Histoire 6 : Refonte du système de combat
* **Description** : Le moteur de jeu prend en compte les statistiques des Bugémons, les types d'attaques, les avantages de types.
* **Modifications client** : Le type de l'attaque est pris en compte pour calculer les dégâts plutôt que le type du Bugemon.
* **Estimation** : 7 points.

### Histoire 7 : Montée de niveau et expérience
* **Description** : Les Bugémons gagnent de l’expérience (XP) après chaque combat remporté. Lorsqu’un Bugémon accumule suffisamment d’XP, il monte de niveau.
* **Modifications client** : Le joueur peut alors répartir des points de statistiques entre les différentes statistiques du Bugémon en plus d'une amélioration automatique des statistiques.
* **Estimation** : 17 points.

### Histoire 8 : Statuts
* **Description** : Gérer les effets de statut soin et réduction des statistiques présentes dans attaques.json lors des attaques.
* **Modifications client** :
  - **Modificateurs** : Les modificateurs sont maintenant des multiplicateurs et non plus des additions fixes de statistiques.
  - **Nouvelles statistiques** : Il y a deux nouvelles statistiques dans un Bugemon : l'attaque et la défense magique.
  - **Priorité** : Les attaques ont maintenant une priorité qui permet de bypass la statistique d'initiative des bugémons.
* **Estimation** : 26.5 points.

### Histoire 10 : utilisation des objets
* **Description** : Un dresseur doit pouvoir utiliser un objet au cours d’un combat. Une liste non-exhaustive d’objets est fournie dans objets.json.
* **Modifications client** : Créer des objets qui permettent de modifier les statistiques magiques et physiques des bugémons.
* **Estimation** : 12.5 points.

---

---

# USER STORIES - Itération 3

## Objectif principal de l'itération
* **Objectif principal** : Améliorer le design et enrichissement du jeu.
* **Points totaux de l'itération** : 82 points.

---

## Détails des User Stories

### Design
* **Description** : Écran de combat, de création d'équipe et de changement de bugémon durant les combats ainsi que l'amélioration de la navigation au clavoer.
* **Estimation** : 23 points.

### Histoire 9 : Tour du NO
* **Description** : La Tour NO est composée de neuf étages (NO2 à NO9, puis le Boss final).
* **Modificatins client** : Pas de récompense et nombre d'étages paramétrables.
* **Estimation** : 15 points.

### Histoire 12 : Bugedex
* **Description** : Au départ seulement les trois Bugémons starter (Florachu, Exceflam et Commmitide) sont disponibles (ainsi que leurs attaques uniquement). Lorsqu’un Bugémon est vaincu, il est ajouté au Bugédex et peut dés lors être choisi dans son équipe de départ.
* **Modifications groupe 2** : On débloque les bugémons en les affrontant et les battant dans la tour du NO (approuvée par le client).
* **Modificatins client** : Certains bugemons spéciaux peuvent avoir des conditions de déblocage particulières.
* **Estimation** : 17 points.

### Enrichissement du jeu
* **Description** : Donner une identité plus personnelle au jeu via de nouveaux objets, attaques, types... et une nouvelle identité visuelle. Création d'une DB pas encore finie (sera poursuivie dans la dernière itération).
* **Bonus** : Animation de combat avec effet de choc coloré et boule d'énergie en fonction de l'attaque.
* **Estimation** : 7 points.

### Refactoring
* **Description** : Respect du MVC, des bonnes pratiques de programmation, création de classes pour respecter la relation cohésion/cohérence, suppression de code inutilisé.
* **Estimation** : 20 points

---

---

# USER STORIES - Itération 4

## Objectif principal de l'itération

* **Objectif principal** : Finaliser le contenu du jeu, améliorer l’expérience utilisateur et préparer une version stable et complète du projet Bugémon.
* **Points totaux de l’itération** : 82 points.

---

## Détails des User Stories

### Histoire 24 : Ambiance sonore et effets audio

* **Description** : Ajouter des musiques et effets sonores afin d’améliorer l’immersion du joueur durant les combats et la navigation.
* **Estimation** : 15 points.

### Histoire 28 : Leaderboard et système de score

* **Description** : Ajouter un système de score et un classement basé sur les performances du joueur dans la Tour du NO.
* **Estimation** : 7 points.

### Histoire bonus : Nouveaux types de Bugémons

* **Description** : Ajouter de nouveaux types, attaques, Bugémons et boss afin d’augmenter la diversité stratégique du jeu.
* **Estimation** : 25 points.

### Histoire bonus : Extension du territoire

* **Description** : Certains Bugémons peuvent activer une extension du territoire en combat afin d’obtenir différents bonus de statistiques et effets spéciaux.
* **Estimation** : 20 points.

### Histoire bonus : Amélioration

* **Description** : Corriger les problèmes visuels et améliorer l’ergonomie générale du jeu avec de nouvelles animations, transformations et améliorations de l’interface.
* **Estimation** : 15 points.
