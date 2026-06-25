package ulb.view.battle.state;

/**
 * State of the special-move panel.
 *
 * <p>{@code visible} controls the whole panel; {@code buttonVisible} can hide the button
 * independently while the charge bar stays on screen. {@code ready} gates the button:
 * it becomes active only when {@code charge >= cost}. {@code progress} is the pre-computed
 * fill ratio {@code charge / (double) cost} in {@code [0.0, 1.0]}.
 *
 * <p>Use {@link #hidden()} when the active Bugemon has no special move.
 */
public record SpecialUiState(
        boolean visible,
        boolean buttonVisible,
        boolean ready,
        double progress,
        int charge,
        int cost
) {
    public static SpecialUiState hidden() {
        return new SpecialUiState(false, false, false, 0.0, 0, 0);
    }
}
