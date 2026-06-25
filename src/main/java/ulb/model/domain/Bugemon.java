package ulb.model.domain;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ulb.model.domain.effect.HealingEffect;
import ulb.model.domain.effect.Effect;
import ulb.model.domain.effect.StatModifierEffect;
import ulb.model.domain.status.Bonus;
import ulb.model.domain.status.Stat;
import ulb.model.domain.status.Status;

/**
 * Represents a Bugemon creature used in battles.
 * This class serves as the primary domain model for creatures, holding their identity,
 * statistics, elemental type, and the list of skills they can perform during combat.
 * It also contains the core logic for damage calculation and health management.
 */
public class Bugemon {
    
    private static final int MAX_SKILLS = 3;

    private String id;
    private String name;
    private Element element;
    private Statistics stats;
    private final List<Skill> skills;
    private final List<Skill> knownSkills;
    private String sprite;
    private String domainExpansionId;
    private boolean isStarter;
    private final Status status;
    private Level level;
    private boolean evolved = false;

    /**
     * Default constructor initializing an empty list of skills.
     */
    public Bugemon() {
        this.status = new Status();
        this.skills = new ArrayList<>();
        this.knownSkills = new ArrayList<>();
        this.level = new Level();
    }

    /*
    A Bugemon constructor that takes another Bugemon object as an argument and copies its values.
    */
    public Bugemon(Bugemon other) {
        this.id = other.id;
        this.name = other.name;
        this.element = other.element;
        this.stats = other.stats == null ? null : new Statistics(other.stats);
        this.skills = new ArrayList<>(other.skills);
        this.knownSkills = new ArrayList<>(other.knownSkills);
        this.sprite = other.sprite;
        this.domainExpansionId = other.domainExpansionId;
        this.isStarter = other.isStarter;
        this.level = new Level(other.getLevel(), other.getXP());
        this.status = new Status();
        this.evolved = other.evolved;
    }

    /**
     * Constructs a new Bugemon with specific identity and base attributes.
     *
     * @param id The unique identifier for the Bugemon species.
     * @param name The display name of the bugemon.
     * @param element The elemental type of the bugemon.
     * @param stats The container for HP, Attack, Defence and other statistics.
     * @param sprite The file path for the bugemon's visual representation.
     * @param isStarter Indicates if this Bugemon is available as a beginning choice for players.
     */
    public Bugemon(String id, String name, Element element, Statistics stats, String sprite, boolean isStarter) {
        this.id = id;
        this.name = name;
        this.element = element;
        this.stats = stats;
        this.sprite = sprite;
        this.domainExpansionId = null;
        this.isStarter = isStarter;
        this.skills = new ArrayList<>();
        this.knownSkills = new ArrayList<>();
        this.level = new Level();
        this.status = new Status();
    }

    /**
     * Adds a new skill to the Bugemon's known skills and equips it if there's room.
     *
     * @param skill The skill to be learned.
     */
    public void learnSkill(Skill skill) {
        if (skill == null) {
            return;
        }

        boolean alreadyKnown = false;
        for (Skill s : knownSkills) {
            if (s.getId().equals(skill.getId())) {
                alreadyKnown = true;
                break;
            }
        }
        if (!alreadyKnown) {
            knownSkills.add(skill);

            if (skills.size() < MAX_SKILLS) {
                equipSkill(skill);
            }
        }
    }

    public boolean equipSkill(Skill skill) {
        if (skill == null || skills.size() >= MAX_SKILLS) return false;
        for (Skill s : skills) {
            if (s.getId().equals(skill.getId())) return false; // already equipped
        }
        skills.add(skill);
        return true;
    }

    public void unequipSkill(Skill skill) {
        skills.removeIf(s -> s.getId().equals(skill.getId()));
    }

    public int getLevel() {
        return level.getCurrentLevel();
    }

    public void setLevel(Level newLevel) {
        this.level = newLevel == null ? new Level() : newLevel;
    }

    public void setLevelMax() {
        this.level = new Level(Level.MAX_LEVEL, 0);
    }

    public int getXP() {
        return (int) level.getCurrentXp();
    }

    public void addXpAndApplyLevelUps() {
        if (level.getCurrentLevel() < Level.MAX_LEVEL) {
            level.addXpAndApplyLevelUps();
        }
    }

    public double getXpProgress() {
        return level == null ? 0.0 : level.getXpProgress();
    }

    public long getCurrentXp() {
        return level == null ? 0L : level.getCurrentXp();
    }

    public long getXpForNextLevel() {
        return level == null ? 0L : level.xpForNextLevel();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Element getElement() {
        return element;
    }

    public String getElementDisplayName() {
        return element == null ? "-" : element.displayName();
    }

    public Statistics getStats() {
        return stats;
    }

    public int getHp() {
        return stats == null ? 0 : stats.getHp();
    }

    public void setHp(int hp) {
        if (stats != null) {
            stats.setHp(hp);
        }
    }

    public int getAttack() {
        return stats == null ? 0 : stats.getAttack();
    }

    public int getDefense() {
        return stats == null ? 0 : stats.getDefense();
    }

    public int getInitiative() {
        return stats == null ? 0 : stats.getInitiative();
    }

    public int getDefenseMagic() {
        return stats == null ? 0 : stats.getDefenseMagic();
    }

    public int getAttackMagic() {
        return stats == null ? 0 : stats.getAttackMagic();
    }

    public int getMaxHp() {
        return stats == null ? 0 : stats.getMaxHp();
    }


    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    /**
     * Returns an immutable copy of the skills currently known by the Bugemon.
     *
     * @return A list of skills.
     */
    public List<Skill> getSkills() {
        return List.copyOf(skills);
    }

    public List<Skill> getKnownSkills() {
        return List.copyOf(knownSkills);
    }


    public float getStatusMultiplier(Stat stat) {
        return status.getMultiplier(stat);
    }

    public String getSprite() {
        return sprite;
    }

    public String getDomainExpansionId() {
        return domainExpansionId;
    }

    public void setDomainExpansionId(String domainExpansionId) {
        this.domainExpansionId = (domainExpansionId == null || domainExpansionId.isBlank()) ? null : domainExpansionId;
    }

    public boolean isStarter() {
        return isStarter;
    }

    /**
     * Checks if the Bugemon is still capable of fighting.
     *
     * @return true if HP is greater than 0, false otherwise.
     */
    @JsonIgnore
    public boolean isEvolved() { return evolved; }

    public void setEvolved(boolean evolved) { this.evolved = evolved; }

    @JsonIgnore
    public boolean isHealthy() {
        return stats != null && stats.getHp() > 0;
    }


    public void applyLevelUpBonus(Bonus bonus) {
        if (stats == null || bonus == null) {
            return;
        }

        stats.setMaxHp(stats.getMaxHp() + bonus.hp());
        stats.setHp(stats.getHp() + bonus.hp());
        stats.setAttack(stats.getAttack() + bonus.attack());
        stats.setDefense(stats.getDefense() + bonus.defense());
        stats.setInitiative(stats.getInitiative() + bonus.initiative());
    }

    public void restoreHp() {
        if (stats != null) {
            stats.restoreHp();
        }
    }

    public void resetBattleState() {
        status.reset();
    }

    public Bugemon copy() {
        return new Bugemon(this);
    }

    /**
     * Applies a given effect to this Bugemon.
     * Evaluates the type of effect using pattern matching.
     */
    public void applyEffect(Effect effect) {
        if (effect == null || stats == null) {
            return;
        }

        if (effect instanceof HealingEffect healingEffect) {
            int currentHp = stats.getHp();
            int maxHp = stats.getMaxHp();
            stats.setHp(Math.min(currentHp + healingEffect.value(), maxHp));
        } else if (effect instanceof StatModifierEffect statModifierEffect) {
            status.applyStatus(statModifierEffect.stat(), statModifierEffect.modifier());
        }
    }
}
