package ulb.view.battle.state;

import ulb.model.domain.Bugemon;
import ulb.model.domain.Element;
import java.util.Optional;

/**
 * Immutable snapshot of the battle passed to the view during a dialogue sequence.
 * Either side's card may be {@code null} when that Bugemon has fainted and no
 * replacement has been sent out yet. Prefer {@link #of} over the canonical constructor
 * to ensure {@link #special} is never {@code null}.
 */
public record BattleUiState(
        BugemonCardState player,
        BugemonCardState enemy,
        boolean isFreeBattle,
        String backgroundSpritePath,
        SpecialUiState special
) {
    public String playerId() {
        return player != null ? player.id() : null;
    }

    public String enemyId() {
        return enemy != null ? enemy.id() : null;
    }

    /**
     * Preferred factory: accepts raw {@link Bugemon} references (nullable), clamps HP,
     * and replaces a {@code null} {@code special} with {@link SpecialUiState#hidden()}.
     */
    public static BattleUiState of(
            Bugemon player,
            Bugemon enemy,
            boolean isFreeBattle,
            String backgroundSpritePath,
            SpecialUiState special
    ) {
        return new BattleUiState(
                BugemonCardState.from(player).orElse(null),
                BugemonCardState.from(enemy).orElse(null),
                isFreeBattle,
                backgroundSpritePath,
                special == null ? SpecialUiState.hidden() : special
        );
    }

    /**
     * Read-only projection of a Bugemon for the UI layer.
     * HP values are clamped to {@code [0, maxHp]} so components never receive
     * out-of-range values from a mid-turn model state.
     */
    public record BugemonCardState(
            String id,
            String name,
            int level,
            Element element,
            int currentHp,
            int maxHp,
            String sprite
    ) {
        public String getElementDisplayName() {
            return element != null ? element.displayName() : "-";
        }

        private static Optional<BugemonCardState> from(Bugemon bugemon) {
            if (bugemon == null) {
                return Optional.empty();
            }

            int currentHp = Math.max(0, bugemon.getHp());
            int maxHp = Math.max(0, bugemon.getMaxHp());

            return Optional.of(new BugemonCardState(
                    bugemon.getId(),
                    bugemon.getName(),
                    bugemon.getLevel(),
                    bugemon.getElement(),
                    currentHp,
                    maxHp,
                    bugemon.getSprite()
            ));
        }
    }
}
