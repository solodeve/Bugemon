package ulb.configuration;

/**
 * Shared constants for resource and filesystem paths used throughout the game.
 */
public abstract class Configuration {
    /** Classpath root for read-only bundled game data (usable with {@code getResourceAsStream}). */
    public static final String JSON_RESOURCE_PATH = "/ulb/assets/json";
    public static final String SQL_QUERY_RESOURCE_PATH = "ulb/sql";
    public static final String PNG_RESOURCE_PATH = "ulb/assets/png";
    public static final String OBJECTS_SPRITES_PATH = "ulb/assets/png/objectsSprites";

    /** Filesystem paths for writable user data (teams and unlocked Bugemons). */
    public static final String NO_NAME_PNG_PATH = "ulb/assets/png/noname";
    public static final String BUGEMONS_SPRITES_PATH = "ulb/assets/png/BugemonsSprites";
    public static final String BUGEMONS_EVOLVED_SPRITES_PATH = "ulb/assets/png/BugemonsSpritesEvolutions";
    public static final String BUGEMONS_SPRITES_BACK_PATH = "ulb/assets/png/BugemonsSpritesBack";
    public static final String BUGEMONS_EVOLUTIONS_PATH = "ulb/assets/video/BugemonsEvolutions";
    public static final String ICONS_PATH = "ulb/assets/png/icons";
    public static final String VIEW_PATH = "/ulb/view/fxml";
    public static final String DATABASE_PROPERTIES_RESOURCE = "ulb/database.properties";
    public static final String MISSING_DATABASE_CONFIGURATION_MESSAGE =
            "Database configuration is missing. Expected db_url, db_user, and db_password "
                    + "from environment variables or " + DATABASE_PROPERTIES_RESOURCE + ".";
    public static final String[] SEED_RESOURCES = {
            "ulb/seed/attaques.sql",
            "ulb/seed/bugemons.sql",
            "ulb/seed/objets.sql",
            "ulb/seed/effet.sql",
            "ulb/seed/team.sql",
            "ulb/seed/user.sql",
            "ulb/seed/domain_expansion.sql",
            "ulb/seed/level_reward.sql",
            "ulb/seed/floor.sql",
            "ulb/seed/skill_tree.sql",
            "ulb/seed/leaderboard.sql",
            "ulb/seed/leaderboard_migration.sql",
            "ulb/seed/data_seed.sql"
    };
    public static final String DEFAULT_BATTLE_BACKGROUND = pngResourcePath("backgrounds/gameBackground.png");
    public static final String LOGIN_VIEW_PATH = viewResourcePath("LoginView.fxml");
    public static final String MAIN_MENU_VIEW_PATH = viewResourcePath("MainMenuView.fxml");
    public static final String TEAM_SELECTION_VIEW_PATH = viewResourcePath("TeamSelectionView.fxml");
    public static final String TEAM_EDITION_VIEW_PATH = viewResourcePath("TeamEdition.fxml");
    public static final String BATTLE_VIEW_PATH = viewResourcePath("BattleView.fxml");
    public static final String NO_VIEW_PATH = viewResourcePath("NOView.fxml");
    public static final String LEADERBOARD_VIEW_PATH = viewResourcePath("LeaderBoardView.fxml");
    public static final String END_BATTLE_VIEW_PATH = viewResourcePath("EndBattleView.fxml");
    public static final String PARTY_VIEW_PATH = viewResourcePath("PartyView.fxml");
    public static final String LEVELUP_VIEW_PATH = viewResourcePath("LevelupView.fxml");
    public static final String INVENTORY_VIEW_PATH = viewResourcePath("InventoryView.fxml");
    public static final String REWARDS_VIEW_PATH = viewResourcePath("RewardsView.fxml");
    public static final String SETTINGS_VIEW_PATH = viewResourcePath("SettingsView.fxml");
    public static final String RENAME_ICON_PATH = iconResourcePath("rename.png");
    public static final String BIN_ICON_PATH = iconResourcePath("bin.png");
    public static final String FIRE_NO_NAME_SPRITE_PATH = resourcePath(NO_NAME_PNG_PATH, "fire-1815425300.png");
    public static final String WATER_NO_NAME_SPRITE_PATH = resourcePath(NO_NAME_PNG_PATH, "water-2444182015.png");
    public static final String GRASS_NO_NAME_SPRITE_PATH = resourcePath(NO_NAME_PNG_PATH, "grass-1259462660.png");
    public static final String DEFAULT_BUGEMON_SPRITE_PATH = resourcePath(BUGEMONS_SPRITES_PATH, "bouldax.png");
    public static final String DEFAULT_BUGEMON_BACK_SPRITE_PATH = resourcePath(BUGEMONS_SPRITES_BACK_PATH, "bouldax.png");

    public static final String PHYSIQUE_SOUND_PATH = "/ulb/assets/sound/attackPhysique.mp3";
    public static final String BOOST_SOUND_PATH = "/ulb/assets/sound/boost.mp3";
    public static final String BUTTON_SOUND_PATH = "/ulb/assets/sound/buttonClick.mp3";
    public static final String DEATH_SOUND_PATH = "/ulb/assets/sound/death.mp3";
    public static final String EVOLUTION_SOUND_PATH = "/ulb/assets/sound/evolution.mp3";
    public static final String DEFEAT_SOUND_PATH = "/ulb/assets/sound/gameOver.mp3";
    public static final String HEALING_ITEM_SOUND_PATH = "/ulb/assets/sound/healing.mp3";
    public static final String IMPACT_SOUND_PATH = "/ulb/assets/sound/impact.mp3";
    public static final String MAGIC_SOUND_PATH = "/ulb/assets/sound/magic.mp3";
    public static final String MENU_SOUND_PATH = "/ulb/assets/sound/menu.mp3";
    public static final String SWITCH_SOUND_PATH = "/ulb/assets/sound/switch.mp3";
    public static final String VICTORY_SOUND_PATH = "/ulb/assets/sound/victory.mp3";

    public static final String FONT_PATH = "/ulb/assets/font";

    public static String viewResourcePath(String fileName) {
        return resourcePath(VIEW_PATH, fileName);
    }

    public static String iconResourcePath(String fileName) {
        return resourcePath(ICONS_PATH, fileName);
    }

    public static String bugemonSpriteResourcePath(String fileName) {
        return resourcePath(BUGEMONS_SPRITES_PATH, fileName);
    }

    public static String bugemonBackSpriteResourcePath(String fileName) {
        return resourcePath(BUGEMONS_SPRITES_BACK_PATH, fileName);
    }

    public static String bugemonEvolvedSpriteResourcePath(String fileName) {
        return resourcePath(BUGEMONS_EVOLVED_SPRITES_PATH, fileName);
    }

    public static String bugemonEvolutionVideoResourcePath(String fileName) {
        return resourcePath(BUGEMONS_EVOLUTIONS_PATH, fileName);
    }

    public static String bugemonEvolutionVideoResourcePathForId(String bugemonId) {
        return bugemonEvolutionVideoResourcePath(bugemonId + ".mp4");
    }

    public static String objectSpriteResourcePath(String fileName) {
        return resourcePath(OBJECTS_SPRITES_PATH, fileName);
    }

    public static String pngResourcePath(String fileName) {
        return resourcePath(PNG_RESOURCE_PATH, fileName);
    }

    public static String resourcePath(String directoryPath, String fileName) {
        String normalizedDirectory = normalizeResourceDirectory(directoryPath);
        return normalizedDirectory + "/" + fileName;
    }

    private static String normalizeResourceDirectory(String directoryPath) {
        if (directoryPath == null || directoryPath.isBlank()) {
            throw new IllegalArgumentException("Resource directory cannot be blank.");
        }
        String normalized = directoryPath.startsWith("/") ? directoryPath : "/" + directoryPath;
        return normalized.endsWith("/") ? normalized.substring(0, normalized.length() - 1) : normalized;
    }
}
