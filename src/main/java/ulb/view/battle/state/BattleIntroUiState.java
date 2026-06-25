package ulb.view.battle.state;

/**
 * Carries the context the battle view needs to render the opening dialogue.
 * Use {@link #defaultIntro()} for standard tower encounters and
 * {@link #noTowerRoom} for named rooms outside the tower.
 */
public record BattleIntroUiState(Type type, boolean bossRoom, String roomDisplayName) {

    /** Distinguishes a standard tower encounter from an out-of-tower room encounter. */
    public enum Type {
        DEFAULT,
        NO_TOWER
    }

    public static BattleIntroUiState defaultIntro() {
        return new BattleIntroUiState(Type.DEFAULT, false, null);
    }

    /**
     * @param bossRoom        triggers distinct dialogue and music cues
     * @param roomDisplayName human-readable name shown in the opening line; may be {@code null}
     */
    public static BattleIntroUiState noTowerRoom(boolean bossRoom, String roomDisplayName) {
        return new BattleIntroUiState(Type.NO_TOWER, bossRoom, roomDisplayName);
    }
}
