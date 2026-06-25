package ulb.model.domain;

/**
 * Represents the mutable statistics of an individual Bugemon.
 * This class allows for stat growth as the creature levels up.
 */
public class Statistics {
    private int hp;
    private int attack;
    private int defense;
    private int defenseMagic;
    private int initiative;
    private int maxHp;
    private int attackMagic;

    public Statistics() {
    }

    /**
     * Copy constructor used when a Bugemon must duplicate its runtime stats.
     */
    public Statistics(Statistics other) {
        this.hp = other.hp;
        this.attack = other.attack;
        this.defense = other.defense;
        this.initiative = other.initiative;
        this.defenseMagic = other.defenseMagic;
        this.maxHp = other.maxHp;
        this.attackMagic = other.attackMagic;
    }

    /**
     * Backward-compatible constructor: defaults {@code attackMagic} to {@code attack}.
     */
    public Statistics(int hp, int attack, int defense, int initiative, int defenseMagic) {
        this(hp, attack, defense, initiative, defenseMagic, attack);
    }

    public Statistics(int hp, int attack, int defense, int initiative, int defenseMagic, int attackMagic) {
        validatePositive(hp, "HP");
        validatePositive(attack, "Attack");
        validatePositive(defense, "Defense");
        validatePositive(initiative, "Initiative");
        validatePositive(defenseMagic, "DefenseMagic");
        validatePositive(attackMagic, "AttackMagic");

        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.initiative = initiative;
        this.defenseMagic = defenseMagic;
        this.maxHp = hp;
        this.attackMagic = attackMagic;
    }

    /**
     * This method ensures that the stats are not negative at the construction of the bugemon.
     */
    private void validatePositive(int value, String statName) {
        if (value < 0) {
            throw new IllegalArgumentException(statName + " cannot be negative.");
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        validatePositive(attack, "Attack");
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        validatePositive(defense, "Defense");
        this.defense = defense;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        validatePositive(initiative, "Initiative");
        this.initiative = initiative;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getDefenseMagic() {
        return defenseMagic;
    }

    public int getAttackMagic() {
        return attackMagic;
    }

    public void restoreHp() {
        this.hp = this.maxHp;
    }

    public int getMaxHp() {
        return this.maxHp;
    }
}
