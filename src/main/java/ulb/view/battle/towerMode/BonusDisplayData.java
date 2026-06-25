package ulb.view.battle.towerMode;

/**
 * View-only representation of a level-up bonus.
 *
 * <p>It stores both the numeric values needed for previewing stat changes
 * and the formatted text displayed on the bonus button.</p>
 */

public record BonusDisplayData(
        int hp,
        int attack,
        int defense,
        int initiative,
        String text
) {}