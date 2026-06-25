package ulb.view.battle.state;

import java.util.Optional;
import ulb.event.BattleMessageType;
import ulb.model.domain.Element;

/**
 * A dialogue line bundled with the rendering metadata the view needs to display and sound it.
 *
 * <p>Flag semantics:
 * <ul>
 *   <li>{@code mustAppearAlone} — discards pending messages so this one plays next</li>
 *   <li>{@code attackElement} — non-null triggers the matching elemental animation</li>
 *   <li>{@code playerAttacking} — which side fires the projectile</li>
 *   <li>{@code attackMagic} — magic vs physical animation variant</li>
 *   <li>{@code healingSound} / {@code deathSound} / {@code impactSound} / {@code switchSound} /
 *       {@code boostSound} — each fires its dedicated SFX</li>
 * </ul>
 */
public record ColouredMessage(String text, String color, Optional<BattleUiState> battleUiState,
                               boolean mustAppearAlone, Element attackElement, boolean playerAttacking,
                               boolean attackMagic, boolean healingSound, BattleMessageType type,
                               boolean deathSound, boolean impactSound, boolean switchSound, boolean boostSound) {
    private static final String DEFAULT_COLOR = "black";

    public ColouredMessage(String text, Optional<BattleUiState> battleUiState) {
        this(text, DEFAULT_COLOR, battleUiState, false, null, false, false, false,
                BattleMessageType.NEUTRAL, false, false, false, false);
    }

    public ColouredMessage(String text, String color, Optional<BattleUiState> battleUiState) {
        this(text, color, battleUiState, false, null, false, false, false,
                BattleMessageType.NEUTRAL, false, false, false, false);
    }

    public String getText() {
        return text;
    }

    public BattleUiState getBattleUiState() {
        return battleUiState.orElse(null);
    }
}
