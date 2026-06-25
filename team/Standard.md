## Objectif
Définir des règles simples et efficaces pour garantir :
- Une collaboration fluide
- Un code propre et maintenable
- Une gestion de projet claire

## Git Workflow
### Création de branche
- Toujours créer une branche depuis `dev`
- Nommage (si vous voulez) :
    - `feature/nom-feature`
    - `bugfix/nom-bug`
    - `hotfix/nom-urgent`
### Commits
- Faire des commits petits et fréquents
- Faire des messages clair 
### Merge
- Notifier le groupe avant de merge
- Merge dès que le travail est fini (même si la tâche n’est pas terminée)
- Ne pas attendre la fin totale d’une grosse tâche
- Objectif :
    - éviter les conflits
    - garder `dev` à jour

## Code
### Bonnes pratiques
- Code lisible >>> code complexe
- Respecter les conventions du projet
- Nommer clairement variables et fonctions
- Éviter les fonctions trop longues
- Respecter la loi de Demeter (éviter les chaînes d’appels, limiter les dépendances entre objets)
- Commenter vos code
### Tests
- Tester son code avant merge
- Vérifier les cas principaux

## Organisation & Suivi
### Discipline de travail
- Ne prendre une tâche que si vous allez travailler dessus directement
- Finir la tâche le plus rapidement possible
- Respecter les 8h de travail par semaine
- Éviter de tout faire à la dernière minute
### Fin d’itération
- L’itération se termine le dimanche
- Ne pas décaler au lundi
- Objectif : permettre une release propre et calme le lundi
- Éviter les situations des 2 itérations précédentes
### Jira
- Mettre à jour la tâche :
    - `In Progress` quand commencée
    - `Done` quand terminée
### Excel
- Mettre à jour les heures

## Process quotidien
1. Récupérer les dernières modifications (`pull`)
2. Travailler sur sa branche
3. Commit régulièrement
4. Mettre à jour :
    - Excel
    - Jira
5. Merge dès que le travail est prêt

## Règles importantes
- Toujours synchroniser avant de commencer
- Ne jamais laisser une branche diverger trop longtemps
- Prioriser la simplicité
- Communiquer en cas de blocage