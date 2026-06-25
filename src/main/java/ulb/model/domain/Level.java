package ulb.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores the current level and experience of a Bugemon instance.
 */
public class Level {

    private static final int INITIAL_LEVEL = 1;
    private static final long INITIAL_XP = 0;

    public static final int MAX_LEVEL = 50;

    private static final int BASE_XP_PER_KILL = 30;
    
    private static final int DEFAULT_FLOOR = 1;
    private static final int DEFAULT_MULTIPLICATOR_TYPE = 1;
    private static final int DEFAULT_NUMBER_OF_ENEMIES = 3;
    
    private static final int XP_BASE = 2000;
    private static final double XP_EXPONENT = 1.5;
    
    private static final double XP_FORMULA_DIVISOR = 50;
    private static final int XP_FORMULA_OFFSET = 5;
    private static final double EPSILON = 1e-9;
    
    @JsonProperty("currentLevel")
    private int level;
    @JsonProperty("currentXp")
    private long xp;

    public Level() {
        this.level = 1;
        this.xp = 0;
    }
    
    
    public Level(Integer level_, Integer xp_) {
        this.level = level_ != null ? level_ : INITIAL_LEVEL;
        this.xp = xp_ != null ? xp_ : INITIAL_XP;
    }
    
    
    public int getCurrentLevel() {
        return level;
    }
    
    public long getCurrentXp() {
        return xp;
    }

    /**
     * Computes the XP granted by a battle based on simple battle parameters.
     */
    public int calculateXp(int floor, int multiplicatorType, int numberOfEnemies) {
        return BASE_XP_PER_KILL * floor * multiplicatorType * numberOfEnemies;
    }

    /**
     * Computes the XP threshold required for the next level.
     */
    public int calculateXpForNextLevel(int N, int b, double e) {
        double xpThreshold = b * Math.pow(N, e) / Math.pow(XP_FORMULA_DIVISOR, e) - XP_FORMULA_OFFSET;
        return (int) Math.floor(xpThreshold + EPSILON);
    }

    /**
     * Applies the default XP reward and performs every resulting level-up.
     */
    public void addXpAndApplyLevelUps() {
        long xpNeeded = calculateXpForNextLevel(getCurrentLevel(), 2000, 1.5);

        xp += calculateXp(DEFAULT_FLOOR, DEFAULT_MULTIPLICATOR_TYPE, DEFAULT_NUMBER_OF_ENEMIES);

        while (xp >= xpNeeded) {
            xp -= xpNeeded;

            levelUp();
            xpNeeded = calculateXpForNextLevel(getCurrentLevel(), XP_BASE, XP_EXPONENT);
        }
    }

    /**
     * Returns the XP threshold required to reach the next level from the current level.
     */
    public long xpForNextLevel() {
        return Math.max(1, calculateXpForNextLevel(getCurrentLevel(), XP_BASE, XP_EXPONENT));
    }

    /**
     * Returns the current XP progress toward the next level, clamped between 0 and 1.
     */
    public double getXpProgress() {
        return Math.max(0.0, Math.min(1.0, (double) xp / xpForNextLevel()));
    }

    /**
     * Increments the current level by one.
     */
    public void levelUp() {
        level++;

    }

}
