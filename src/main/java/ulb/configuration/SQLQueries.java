package ulb.configuration;

/**
 * Centralized SQL resource access used across repositories and infrastructure.
 */
public abstract class SQLQueries {
    public static final String TEAM_SELECT_BY_USER_AND_TYPE = load("team/select_by_user_and_type.sql");
    public static final String TEAM_SELECT_BY_USER_AND_TEAMTYPE = load("team/select_by_user_and_teamtype.sql");
    public static final String TEAM_SELECT_BY_USER = load("team/select_by_user.sql");
    public static final String TEAM_SELECT_BY_TYPE_AND_USER = load("team/select_by_type_and_user.sql");
    public static final String TEAM_SELECT_BY_TYPE = load("team/select_by_type.sql");
    public static final String TEAM_MEMBERS_SELECT = load("team/members_select.sql");
    public static final String DELETE_USERTEAM_BY_USER_AND_TYPE = load("team/delete_userteam_by_user_and_type.sql");
    public static final String DELETE_USERTEAM_BY_USER_AND_TEAMIDS = load("team/delete_userteam_by_user_and_teamids.sql");
    public static final String DELETE_USERTEAM_BY_USER = load("team/delete_userteam_by_user.sql");
    public static final String DELETE_TEAM_MEMBERS_ALL = load("team/delete_members_all.sql");
    public static final String DELETE_TEAM_ALL = load("team/delete_all.sql");
    public static final String INSERT_TEAM_WITH_TYPE = load("team/insert_with_type.sql");
    public static final String DELETE_TEAM_MEMBERS_BY_TEAM_ID = load("team/delete_members_by_team_id.sql");
    public static final String INSERT_TEAM_SIMPLE = load("team/insert_simple.sql");
    public static final String INSERT_TEAM_MEMBER = load("team/insert_member.sql");
    public static final String INSERT_USERTEAM_WITH_TYPE = load("team/insert_userteam_with_type.sql");
    public static final String INSERT_USERTEAM_SIMPLE = load("team/insert_userteam_simple.sql");
    public static final String INSERT_USER_CONFLICT = load("user/insert_conflict.sql");
    public static final String DELETE_ORPHAN_TEAMS = load("team/delete_orphan_teams.sql");
    public static final String DELETE_TEAM_BY_NAME = load("team/delete_by_name.sql");
    public static final String DELETE_TEAM_MEMBERS_BY_TYPE = load("team/delete_members_by_type.sql");
    public static final String DELETE_TEAM_BY_TYPE = load("team/delete_by_type.sql");
    public static final String DELETE_USER = load("user/delete.sql");
    public static final String INSERT_ATTAQUE = load("importer/insert_attaque.sql");
    public static final String INSERT_EFFET = load("importer/insert_effet.sql");
    public static final String INSERT_EFFET_ATTAQUE = load("importer/insert_effet_attaque.sql");
    public static final String INSERT_OBJECT = load("importer/insert_object.sql");
    public static final String INSERT_BUGEMON = load("importer/insert_bugemon.sql");
    public static final String INSERT_LEADERBOARD_ENTRY = load("leaderboard/insert_entry.sql");
    public static final String SELECT_LEADERBOARD_TOP = load("leaderboard/select_top.sql");
    public static final String SELECT_LEADERBOARD_BEST_FOR_PLAYER = load("leaderboard/select_best_for_player.sql");
    public static final String UPDATE_LEADERBOARD_ENTRY = load("leaderboard/update_entry.sql");
    public static final String INSERT_DOMAIN_EXPANSION = load("importer/insert_domain_expansion.sql");
    public static final String INSERT_DOMAIN_EXPANSION_BONUS = load("importer/insert_domain_expansion_bonus.sql");
    public static final String INSERT_LEVEL_REWARD = load("importer/insert_level_reward.sql");
    public static final String INSERT_FLOOR = load("importer/insert_floor.sql");
    public static final String INSERT_FLOOR_TEAM = load("importer/insert_floor_team.sql");
    public static final String INSERT_FLOOR_TEAM_BUGEMON = load("importer/insert_floor_team_bugemon.sql");
    public static final String INSERT_SKILL_TREE_NODE = load("importer/insert_skill_tree_node.sql");
    public static final String INSERT_SKILL_TREE_PREREQUIS = load("importer/insert_skill_tree_prerequis.sql");
    public static final String INSERT_SKILL_TREE_EFFET = load("importer/insert_skill_tree_effet.sql");
    public static final String INSERT_BUGEMON_ATTAQUE = load("importer/insert_bugemon_attaque.sql");
    public static final String INSERT_TEAM = load("team/insert.sql");
    public static final String INSERT_TEAM_MEMBER_ONCONFLICT = load("team/insert_member_onconflict.sql");
    public static final String TEAM_SELECT_ALL = load("team/select_all.sql");
    public static final String TEAM_MEMBERS_SELECT_ALL = load("team/members_select_all.sql");
    public static final String USERTEAM_SELECT_ALL = load("team/userteam_select_all.sql");
    public static final String SELECT_BUGEMONS = load("bugemon/select_all.sql");
    public static final String SELECT_BUGEMON_ATTACK_IDS = load("bugemon/select_attack_ids.sql");
    public static final String SELECT_USER_UNLOCKED_BUGEMONS = load("bugemon/select_unlocked_by_user.sql");
    public static final String INSERT_USER_UNLOCKED_BUGEMON = load("bugemon/insert_unlocked_for_user.sql");
    public static final String SELECT_ATTAQUES = load("attaque/select_all.sql");
    public static final String SELECT_ATTAQUE_EFFECTS = load("attaque/select_effects_by_attack_id.sql");
    public static final String SELECT_LEVEL_REWARDS_WITH_ATTACK = load("level_reward/select_all_with_attack.sql");
    public static final String SELECT_DOMAIN_EXPANSIONS = load("domain_expansion/select_all.sql");
    public static final String SELECT_DOMAIN_EXPANSION_BONUSES = load("domain_expansion/select_bonuses_by_id.sql");
    public static final String SELECT_OBJECTS_WITH_EFFECT = load("object/select_all_with_effect.sql");
    public static final String SELECT_STARTING_INVENTORY = load("object/select_starting_inventory.sql");
    public static final String SELECT_FLOOR_TEAMS = load("floor/select_all_teams.sql");
    public static final String SELECT_FLOOR_TEAM_BUGEMONS = load("floor/select_team_bugemons_by_team_id.sql");
    public static final String INSERT_EFFET_OBJECT = load("importer/insert_effet_object.sql");
    public static final String USER_FIND_BY_ID = load("user/find_by_id.sql");
    public static final String USER_FIND_BY_USERNAME = load("user/find_by_username.sql");
    public static final String USER_FIND_BY_USERNAME_WITH_PASSWORD = load("user/find_by_username_with_password.sql");
    public static final String USER_INSERT_RETURNING = load("user/insert_returning.sql");
    public static final String DELETE_USERTEAM_BY_TEAM_ID = load("team/delete_userteam_by_team_id.sql");
    public static final String DELETE_TEAM_BY_ID = load("team/delete_by_id.sql");

    private static final String COUNT_ROWS_TEMPLATE = load("meta/count_rows.sql");
    private static final String TEAM_FIND_BY_LOWER_NAMES_TEMPLATE = load("team/find_by_lower_names_template.sql");

    public static String countRows(String tableName) {
        return SQLResourceLoader.renderIdentifier(COUNT_ROWS_TEMPLATE, "table", tableName);
    }

    public static String findTeamsByLowerNames(int parameterCount) {
        return SQLResourceLoader.renderValue(
                TEAM_FIND_BY_LOWER_NAMES_TEMPLATE,
                "placeholders",
                SQLResourceLoader.buildPlaceholders(parameterCount)
        );
    }

    private static String load(String resourcePath) {
        return SQLResourceLoader.loadQuery(resourcePath);
    }
}
