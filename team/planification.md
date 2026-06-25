# Rapport de suivi de planification et de conception - Groupe 02

## 1. Organisation et suivi de la planification

Pour cette dernière itération du projet Bugemon, l'équipe s'est abstenue d'appliquer une organisation inspirée de l'Extreme Programming. Le travail est découpé en user stories estimées en points, avec l'équivalence utilisée dans le projet : 1 point correspond à 1 heure idéale. L'itération courante représente 82 points et couvre principalement l'amélioration du design, la Tour du NO, le Bugedex, l'enrichissement du jeu et le refactoring. Pour assurer une architecture propre et structuree, il a ete choisi d'implementer des designs patterns tels que les singletons et les DAOs (Data Access Objects).

La planification est suivie dans Jira. Chaque user story est décomposée en tickets techniques, puis en sous-tâches quand la granularité devient trop large. Les tickets passent par les états `To Do`, `In Progress`, `Blocked` et `Done`, ce qui permet de garder une vision claire de l'avancement. De plus, les heures de travail sont encodées dans le fichier Excel `BurndownChart.xlsx`, utilisé pour produire le burndown chart du livrable.

Les réunions de synchronisation ont lieu régulièrement (~ tous les 3-4 jours). Elles servent à faire le point sur l'état des tickets, identifier les blocages, réévaluer certaines estimations si nécessaire et redistribuer le travail restant. Ces réunions sont également d'avoir des explications supplémentaires à propos de la codebase. Les règles internes définies dans `Standard.md` structurent également le travail au quotidien : partir de la branche `dev`, synchroniser avant de commencer, faire des commits fréquents, mettre Jira et Excel à jour, puis merger rapidement les changements terminés afin de limiter les conflits.

Le pair programming reste utilisé pour les parties importantes du code Java. Les binômes alternent entre les rôles de pilote et de copilote. En cas de blocage, l'équipe privilégie d'abord l'entraide, la relecture du code et la rotation des binômes avant de laisser une tâche bloquée trop longtemps.

## 2. État d'avancement des fonctionnalités et conformité

Les fonctionnalités de l'itération 3 sont organisées autour des user stories de `stories.md` et des demandes du client.

### Design et interface

Le design des écrans principaux a été amélioré, notamment les écrans de combat, de création/modification d'équipe, de sélection de Bugemon, de récompenses et de navigation. Une attention particulière a été portée à l'ergonomie de la navigation au clavier et à la séparation entre les fichiers FXML, CSS, vues JavaFX et controllers.

### Tour du NO

La Tour du NO est présente dans le projet avec une progression par étages, des salles et des combats successifs. Le nombre d'étages est configurable dans le modèle, ce qui respecte l'adaptation demandée par le client. Les récompenses automatiques ne sont pas forcées à chaque étage, conformément aux modifications client mentionnées dans les user stories.

### Bugedex et déblocage

Le déblocage progressif des Bugemons est géré via la progression du joueur. Le choix retenu par le groupe, et validé avec le client, consiste à débloquer les Bugemons affrontés et vaincus dans la Tour du NO. Cette fonctionnalité s'appuie sur la persistance utilisateur pour que les Bugemons débloqués restent disponibles dans les parties suivantes.

### Enrichissement du jeu

Le jeu a été enrichi avec de nouveaux objets, attaques, types, sprites, fonds, sons et animations. Les données de jeu sont chargées depuis les ressources JSON et SQL du projet. Le projet utilise désormais une base de données PostgreSQL locale pour la persistance des utilisateurs, équipes, résultats et données associées. Le README documente la configuration attendue du fichier `database.properties`.

### Refactoring et qualité

Un effort important a été consacré au respect du MVC, à la réduction du couplage et à la clarification des responsabilités. Les vues exposent des interfaces de listener pour notifier les controllers, tandis que les controllers orchestrent les interactions entre vue, services et modèle. Les repositories ont été regroupés dans le package `ulb.repository`, avec notamment `GameRepository`, `TeamDatabaseRepository`, `ScoreRepository` et `LeaderboardRepository`.

Le projet contient également une suite de tests JUnit 5 couvrant les principaux composants du modèle, des services, des repositories et de certains controllers. Les tests restent un point de vigilance : ils doivent passer de manière fiable sur toutes les branches avant chaque rendu ou merge important.

## 3. Choix de conception et design patterns

L'architecture générale suit le modèle MVC imposé par le cours :

- `ulb.model` contient les entités métier, les statistiques, les Bugemons, les équipes, les objets, les étages de la Tour du NO et les concepts de combat.
- `ulb.view` contient les vues JavaFX, les états d'affichage, les animations et la logique strictement liée à l'interface.
- `ulb.controller` coordonne les actions utilisateur, la navigation, les services et les mises à jour de vue.
- `ulb.service` contient la logique applicative partagée, comme le combat, la progression de la Tour du NO, l'audio, le leaderboard ou la gestion d'équipe.
- `ulb.repository` isole l'accès aux données et évite de mélanger la persistance avec les controllers ou les vues.

Le pattern Repository est utilisé pour séparer l'accès aux données du reste de l'application. Cela permet au code métier de manipuler des équipes, utilisateurs ou scores sans dépendre directement des requêtes SQL ou de la structure de stockage.

Le pattern Strategy est utilisé dans plusieurs zones du jeu, notamment pour séparer les comportements liés aux modes de jeu et aux traitements de combat. Les effets de combat sont modélisés par une interface `Effect` et des implémentations spécialisées comme les soins, les modifications de statistiques ou la suppression d'effets.

Le pattern Observer est appliqué via des interfaces de listener entre les vues et les controllers, ainsi que pour certains événements de combat comme la fin de bataille, les messages et les montées de niveau. Cette approche rend les dépendances plus explicites que l'utilisation directe de callbacks anonymes.

Enfin, plusieurs états du jeu sont représentés par des énumérations, comme `BattleState` et `GameMode`. Ces enums permettent de mieux encadrer les transitions possibles et d'éviter de disperser des chaînes de caractères dans le code.

## 4. Prise en compte des remarques de review

Les reviews précédentes ont mis en évidence plusieurs points d'attention qui guident la suite du travail.

Le respect du MVC s'est amélioré, mais certains controllers restent encore trop chargés. L'objectif est de continuer à déplacer la logique métier vers le modèle ou les services quand le controller fait autre chose que de l'orchestration.

La gestion des exceptions reste un point à surveiller. Les erreurs techniques doivent être capturées au bon niveau, transformées en exceptions explicites si nécessaire et gérées par les controllers ou services responsables du flux applicatif. Les `println`, les catches trop génériques et les exceptions utilisées pour masquer des bugs doivent être évités.

La taille de certaines classes, comme les controllers principaux ou les composants de combat, doit rester sous contrôle. Quand une classe accumule trop de responsabilités, elle doit être découpée en services plus spécialisés.

La documentation doit rester synchronisée avec le code. Les commentaires obsolètes, les références à d'anciennes classes et les noms qui ne correspondent plus au projet doivent être corrigés. Le README doit aussi rester cohérent avec l'état réel de la base de données et des prérequis.

Les tests doivent être exécutés avant les merges importants. Les reviews ont déjà signalé que des tests échouaient sur certaines branches ; pour être conformes, le projet doit viser une suite de tests stable, reproductible et utilisable comme garde-fou avant livraison.