# Bugémon

**Cours :** INFO-F-307 - Génie Logiciel et gestion de projets  
**Université :** Université Libre de Bruxelles (ULB)

## Pré-requis

Avant d'exécuter le projet, installez les outils suivants :

- JDK (Java Development Kit) version 21 ou supérieure.
- Apache Maven version 3.6 ou supérieure.
- PostgreSQL pour la base de données locale.
- FFmpeg, nécessaire pour la lecture des vidéos d'évolution.

### Homebrew (macOS)

Toutes les commandes macOS ci-dessous utilisent Homebrew.

#### Installation

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

Puis, activez Homebrew dans votre shell (choisissez la commande adaptée à votre machine) :

```bash
# Apple Silicon
echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
eval "$(/opt/homebrew/bin/brew shellenv)"

# Intel
echo 'eval "$(/usr/local/bin/brew shellenv)"' >> ~/.zprofile
eval "$(/usr/local/bin/brew shellenv)"
```

### Installation de FFmpeg

FFmpeg est requis pour tout le monde.

#### Linux / Windows 

```bash
sudo apt update
sudo apt install ffmpeg
```

#### macOS (Homebrew)

```bash
brew update
brew install ffmpeg
```

### Configuration audio pour WSL uniquement

```bash
sudo apt install pulseaudio -y
pulseaudio --start
```

Les utilisateurs Linux natif, Windows natif ou macOS n'ont pas besoin de cette étape.

## Configuration de PostgreSQL en local

### 1. Installer PostgreSQL

#### Linux / Windows

```bash
sudo apt update
sudo apt install postgresql
sudo service postgresql start
```

#### macOS (Homebrew)

```bash
brew update
brew install postgresql
brew services start postgresql
```

Pour vérifier que PostgreSQL tourne correctement :

#### Linux / Windows

```bash
sudo service postgresql status
```

#### macOS (Homebrew)

```bash
brew services list
```

### 2. Créer la base de données et l'utilisateur

Ouvrez PostgreSQL avec l'utilisateur administrateur :

#### Linux / Windows

```bash
sudo -u postgres psql
```

#### macOS (Homebrew)

```bash
psql postgres
```

Puis exécutez les commandes suivantes **une par une** (pour windows/linux et macos) :

```sql
CREATE DATABASE bugemondb;
CREATE USER bugemon_user WITH PASSWORD 'Group2Bugemon';
GRANT ALL PRIVILEGES ON DATABASE bugemondb TO bugemon_user;
```

Connectez-vous ensuite à la base créée :

```sql
\c bugemondb
```

Donnez les droits sur le schema `public` :

```sql
GRANT ALL ON SCHEMA public TO bugemon_user;
```

Quittez PostgreSQL :

```sql
\q
```

> Dans la plupart des cas, ces commandes suffisent. Si une erreur du type `permission denied for schema public` apparaît lors des tests ou du lancement de l'application, exécutez aussi :
>
> ```sql
> ALTER DATABASE bugemondb OWNER TO bugemon_user;
> \c bugemondb
> ALTER SCHEMA public OWNER TO bugemon_user;
> ```

### 3. Mettre à jour `database.properties`

Le fichier `src/main/resources/ulb/database.properties` est exclu du dépôt git (`.gitignore`). Vous devez le créer manuellement avant de lancer l'application.

Utilisez la configuration locale suivante :

```properties
db_url=jdbc:postgresql://localhost:5432/bugemondb
db_user=bugemon_user
db_password=Group2Bugemon
```


## Lancement de l'application

1. Clonez le dépôt sur votre machine locale.
2. Créez le fichier `database.properties` comme indiqué ci-dessus.
3. Ouvrez un terminal à la racine du projet, c'est-à-dire dans le dossier `2026-groupe-02`.
4. Lancez l'application :

```bash
mvn clean javafx:run
```

### Données de jeu (seed automatique)

**Aucune action manuelle n'est requise.** À chaque lancement, l'application exécute automatiquement les fichiers SQL de seed situés dans `src/main/resources/ulb/seed/`. Ces fichiers créent les tables si elles n'existent pas et insèrent toutes les données du jeu (Bugémons, attaques, objets, niveaux, étages, etc.) — les données déjà présentes ne sont jamais écrasées.

Si vous souhaitez **repartir d'une base vide** (ex. après un changement de schéma), supprimez et recréez la base :

#### Linux / Windows

```bash
sudo -u postgres psql
```

#### macOS (Homebrew)

```bash
psql postgres
```

Puis exécutez les commandes suivantes **une par une** (pour windows/linux et macos) :

```sql
DROP DATABASE bugemondb;
CREATE DATABASE bugemondb;
GRANT ALL PRIVILEGES ON DATABASE bugemondb TO bugemon_user;
\c bugemondb
GRANT ALL ON SCHEMA public TO bugemon_user;
\q
```

Puis relancez `mvn clean javafx:run` : le seed se réexécute automatiquement.

## Lancement des tests

Pour vérifier que tout fonctionne :

```bash
mvn clean test
```

Si les tests échouent avec une erreur liée aux permissions PostgreSQL, vérifiez que les droits sur le schema `public` ont bien été donnés :

#### Linux / Windows

```bash
sudo -u postgres psql
```

#### macOS (Homebrew)

```bash
psql postgres
```

Puis exécutez les commandes suivantes **une par une** (pour windows/linux et macos) :

```sql
\c bugemondb
GRANT ALL ON SCHEMA public TO bugemon_user;
ALTER SCHEMA public OWNER TO bugemon_user;
\q
```

Puis relancez :

```bash
mvn clean test
```

## Auteurs - groupe 2

Project réalisé dans le cadre du cours d'info-f307 à l'ULB en collaboration avec un groupe de 10 étudiants.