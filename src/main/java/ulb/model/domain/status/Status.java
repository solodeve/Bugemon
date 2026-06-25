package ulb.model.domain.status;

import java.util.EnumMap;
import java.util.Map;

/**
 * Tracks temporary battle modifiers applied to a Bugemon's stats.
 */
public class Status {
    private static final int MAX_STAT_STAGE = 4;
    private static final int MIN_STAT_STAGE = -4;
    private static final float DEFAULT_MULTIPLIER = 1.0f;

    public static final Stat ATTACK = Stat.ATTACK;
    public static final Stat DEFENSE = Stat.DEFENSE;
    public static final Stat ATTACK_MAGIC = Stat.ATTACK_MAGIC;
    public static final Stat DEFENSE_MAGIC = Stat.DEFENSE_MAGIC;
    public static final Stat INITIATIVE = Stat.INITIATIVE;

    private static final Map<Integer, Float> STATUS_MULTIPLIERS = Map.of(
            4, 3.0f,
            3, 2.5f,
            2, 2.0f,
            1, 1.5f,
            0, 1.0f,
            -1, 0.66f,
            -2, 0.5f,
            -3, 0.33f,
            -4, 0.25f
    );

    private final Map<Stat, Integer> statStages = new EnumMap<>(Stat.class);

    public Status() {
        statStages.put(Stat.ATTACK, 0);
        statStages.put(Stat.DEFENSE, 0);
        statStages.put(Stat.ATTACK_MAGIC, 0);
        statStages.put(Stat.DEFENSE_MAGIC, 0);
        statStages.put(Stat.INITIATIVE, 0);
    }

    /**
     * Applies a stage modification to the given stat while keeping the value in range.
     */
    public void applyStatus(Stat stat, int modify) {
        if (stat == null || !statStages.containsKey(stat)) {
            return;
        }

        int currentValue = statStages.get(stat);
        int newValue = Math.max(MIN_STAT_STAGE, Math.min(MAX_STAT_STAGE, currentValue + modify));
        statStages.put(stat, newValue);
    }

    /**
     * Returns the current multiplier associated with the requested stat.
     */
    public float getMultiplier(Stat stat) {
        if (stat == null || !statStages.containsKey(stat)) {
            return DEFAULT_MULTIPLIER;
        }

        return STATUS_MULTIPLIERS.getOrDefault(statStages.get(stat), DEFAULT_MULTIPLIER);
    }

    /**
     * Clears all temporary stat stages back to their neutral value.
     */
    public void reset() {
        statStages.replaceAll((stat, value) -> 0);
    }
}
