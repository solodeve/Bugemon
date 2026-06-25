package ulb.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import ulb.configuration.SQLQueries;
import ulb.model.domain.Bugemon;
import ulb.model.domain.effect.Effect;
import ulb.model.domain.Element;
import ulb.model.domain.effect.HealingEffect;
import ulb.model.domain.Level;
import ulb.model.domain.effect.RemoveStatusEffect;
import ulb.model.domain.effect.ResetMalusEffect;
import ulb.model.domain.Skill;
import ulb.model.domain.status.Stat;
import ulb.model.domain.effect.StatModifierEffect;
import ulb.model.domain.Statistics;
import ulb.model.domain.effect.Target;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.model.inventory.Item;
import ulb.model.special.DomainBonus;
import ulb.model.special.DomainExpansion;

/**
 * Loads and caches the static game catalogs: skills, Bugemons, items, and domain expansions.
 *
 * <p>Call {@link #load()} once after construction before using any query methods.</p>
 */
public class BugemonCatalogRepository implements IBugemonCatalogRepository {

    private final Map<String, Skill> skillCatalog = new HashMap<>();
    private final Map<Integer, Skill> levelRewards = new HashMap<>();
    private final Map<String, Bugemon> bugedex = new HashMap<>();
    private final List<Item> items = new ArrayList<>();
    private final Map<String, DomainExpansion> domainCatalog = new HashMap<>();
    private final Map<Item, Integer> startingInventory = new HashMap<>();

    private final DatabaseBugemon databaseBugemon;

    public BugemonCatalogRepository(DatabaseBugemon databaseBugemon) {
        this.databaseBugemon = databaseBugemon;
    }

    /** Clears and reloads all catalogs from the database. */
    public void load() throws IOException {
        if (!databaseBugemon.isConfigured()) {
            throw new IOException("Database is not configured. Cannot load game catalog.");
        }
        skillCatalog.clear();
        levelRewards.clear();
        bugedex.clear();
        items.clear();
        domainCatalog.clear();
        startingInventory.clear();
        try {
            loadSkillsFromDatabase();
            loadLevelRewardsFromDatabase();
            loadDomainExpansionsFromDatabase();
            loadBugemonsFromDatabase();
            loadItemsFromDatabase();
            loadStartingInventoryFromDatabase();
        } catch (SQLException e) {
            throw new IOException("Failed to load game catalog from database.", e);
        }
    }

    @Override public boolean containsBugemon(String bugemonId) { return bugedex.containsKey(bugemonId); }
    @Override public Optional<Skill> getSkillById(String id)   { return Optional.ofNullable(skillCatalog.get(id)); }
    @Override public Optional<Skill> getLevelRewardByLevel(int level) { return Optional.ofNullable(levelRewards.get(level)); }
    @Override public Map<Integer, Skill> getAllLevelRewards()   { return Map.copyOf(levelRewards); }
    @Override public Optional<Bugemon> getBugemonById(String id){ return Optional.ofNullable(bugedex.get(id)); }
    @Override public List<Bugemon> getAllBugemons()             { return List.copyOf(bugedex.values()); }

    /** Returns copies of all Bugemons at the given level, applying level rewards 1..{@code level} in order. */
    @Override
    public List<Bugemon> getAllBugemonsAtLevel(int level) {
        return getAllBugemons().stream()
                .map(Bugemon::copy)
                .peek(b -> {
                    b.setLevel(new Level(level, 0));
                    for (int l = 1; l <= level; l++) getLevelRewardByLevel(l).ifPresent(b::learnSkill);
                })
                .toList();
    }

    @Override
    public Optional<Item> getItemById(String id) {
        return items.stream().filter(item -> item.id().equals(id)).findFirst();
    }

    @Override
    public Optional<DomainExpansion> getDomainExpansionById(String id) {
        return Optional.ofNullable(domainCatalog.get(id));
    }

    @Override public Map<Item, Integer> getStartingInventory() { return Map.copyOf(startingInventory); }

    private void loadSkillsFromDatabase() throws SQLException {
        try (Connection conn = databaseBugemon.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_ATTAQUES);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                Skill skill = buildSkillFromResultSet(rs);
                loadSkillEffectsFromDatabase(conn, skill, id);
                skillCatalog.put(id, skill);
            }
        }
    }

    private void loadSkillEffectsFromDatabase(Connection conn, Skill skill, String attackId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_ATTAQUE_EFFECTS)) {
            stmt.setString(1, attackId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Effect effect = buildEffectFromDb(
                            rs.getString("type"), rs.getString("cible"), rs.getString("stat"),
                            (Integer) rs.getObject("modificateur"), (Integer) rs.getObject("valeur"));
                    if (effect != null) skill.addEffect(effect);
                }
            }
        }
    }

    private void loadLevelRewardsFromDatabase() throws SQLException {
        try (Connection conn = databaseBugemon.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_LEVEL_REWARDS_WITH_ATTACK);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int level = rs.getInt("niveau");
                String id = rs.getString("id");
                Skill skill = buildSkillFromResultSet(rs);
                loadSkillEffectsFromDatabase(conn, skill, id);
                levelRewards.put(level, skill);
            }
        }
    }

    private void loadDomainExpansionsFromDatabase() throws SQLException {
        try (Connection conn = databaseBugemon.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_DOMAIN_EXPANSIONS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                List<DomainBonus> bonuses = loadDomainBonusesFromDatabase(conn, id);
                DomainExpansion de = new DomainExpansion(
                        id, rs.getString("nom"), rs.getString("description"),
                        Objects.requireNonNullElse(rs.getString("background_sprite"), ulb.configuration.Configuration.DEFAULT_BATTLE_BACKGROUND),
                        Element.fromString(rs.getString("boosted_element")),
                        rs.getInt("cost"), bonuses);
                domainCatalog.put(id, de);
            }
        }
    }

    private List<DomainBonus> loadDomainBonusesFromDatabase(Connection conn, String expansionId) throws SQLException {
        List<DomainBonus> bonuses = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_DOMAIN_EXPANSION_BONUSES)) {
            stmt.setString(1, expansionId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bonuses.add(new DomainBonus(parseStatAlias(rs.getString("stat")), rs.getInt("modificateur")));
                }
            }
        }
        return bonuses;
    }

    private void loadBugemonsFromDatabase() throws SQLException {
        try (Connection conn = databaseBugemon.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_BUGEMONS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Statistics stats = new Statistics(
                        rs.getInt("pv"), rs.getInt("attaque"), rs.getInt("defense"),
                        rs.getInt("initiative"), rs.getInt("magic_defense"), rs.getInt("magic_attaque"));
                Bugemon bugemon = new Bugemon(
                        rs.getString("id"), rs.getString("nom"),
                        Element.fromString(rs.getString("type")),
                        stats, rs.getString("sprite"), rs.getBoolean("starter"));
                bugemon.setDomainExpansionId(rs.getString("domain_expansion_id"));
                loadBugemonSkillsFromDatabase(conn, bugemon);
                bugedex.put(bugemon.getId(), bugemon);
            }
        }
    }

    private void loadBugemonSkillsFromDatabase(Connection conn, Bugemon bugemon) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_BUGEMON_ATTACK_IDS)) {
            stmt.setString(1, bugemon.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) getSkillById(rs.getString("attaque_id")).ifPresent(bugemon::learnSkill);
            }
        }
    }

    private void loadItemsFromDatabase() throws SQLException {
        try (Connection conn = databaseBugemon.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_OBJECTS_WITH_EFFECT);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String effectType = rs.getString("effet_type");
                Effect effect = effectType == null ? null : buildEffectFromDb(
                        effectType, rs.getString("effet_cible"), rs.getString("effet_stat"),
                        (Integer) rs.getObject("effet_modificateur"), (Integer) rs.getObject("effet_valeur"));
                items.add(new Item(rs.getString("id"), rs.getString("nom"), rs.getString("description"),
                        rs.getString("categorie"), effect, rs.getString("sprite")));
            }
        }
    }

    private void loadStartingInventoryFromDatabase() throws SQLException {
        try (Connection conn = databaseBugemon.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_STARTING_INVENTORY);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String itemId = rs.getString("item_id");
                int quantite = rs.getInt("quantite");
                getItemById(itemId).ifPresent(item -> startingInventory.put(item, quantite));
            }
        }
    }

    private Skill buildSkillFromResultSet(ResultSet rs) throws SQLException {
        return new Skill(
                rs.getString("id"), rs.getString("nom"),
                Element.fromString(rs.getString("type")),
                rs.getString("description"),
                rs.getInt("puissance"),
                (float) rs.getDouble("precision"),
                rs.getInt("priorite"),
                rs.getBoolean("est_magique"));
    }

    private Effect buildEffectFromDb(String type, String cible, String stat, Integer modificateur, Integer valeur) {
        if (type == null) return null;
        Target target = parseTargetFromDb(cible);
        return switch (type) {
            case "stat_modifier" -> {
                if (stat == null || modificateur == null) yield null;
                yield new StatModifierEffect(target, parseStatAlias(stat), modificateur);
            }
            case "heal"          -> new HealingEffect(target, valeur != null ? valeur : 0);
            case "reset_malus"   -> new ResetMalusEffect(target);
            case "remove_status" -> new RemoveStatusEffect(target);
            default              -> null;
        };
    }

    private Target parseTargetFromDb(String cible) {
        if (cible == null) return Target.CASTER;
        return switch (cible.toLowerCase(Locale.ROOT)) {
            case "opponent" -> Target.OPPONENT;
            case "team"     -> Target.TEAM;
            default         -> Target.CASTER;
        };
    }

    private Stat parseStatAlias(String rawStat) {
        if (rawStat == null || rawStat.isBlank()) throw new IllegalArgumentException("Stat cannot be null or blank");
        String n = rawStat.trim().toUpperCase(Locale.ROOT).replace('-', '_').replace(' ', '_');
        return switch (n) {
            case "HP"                                                            -> Stat.HP;
            case "ATTACK"                                                        -> Stat.ATTACK;
            case "DEFENSE"                                                       -> Stat.DEFENSE;
            case "INITIATIVE", "SPEED"                                           -> Stat.INITIATIVE;
            case "PRECISION", "ACCURACY"                                         -> Stat.PRECISION;
            case "DEFENSE_MAGIC", "DEFENSEMAGIC", "MAGIC_DEFENSE", "MAGICDEFENSE"-> Stat.DEFENSE_MAGIC;
            case "ATTACK_MAGIC",  "ATTACKMAGIC",  "MAGIC_ATTACK",  "MAGICATTACK" -> Stat.ATTACK_MAGIC;
            default -> throw new IllegalArgumentException("Unknown stat: " + rawStat);
        };
    }
}
