package ulb.view.battle.state;

/**
 * Names of the three skill slots shown on the battle action panel.
 * Slots for which no skill exists are filled with the placeholder {@code "-"}.
 */
public record BattleSkillsState(
        String skill1Name,
        String skill2Name,
        String skill3Name
) {
    private static final String DEFAULT_SKILL_NAME = "-";

    public static BattleSkillsState empty() {
        return new BattleSkillsState(DEFAULT_SKILL_NAME, DEFAULT_SKILL_NAME, DEFAULT_SKILL_NAME);
    }
}
