package ulb.model.domain.effect;

import ulb.event.BattleMessage;
import ulb.model.domain.*;

/**
 * Sealed domain contract for all battle effects.
 * <p>
 * This interface is intentionally sealed to restrict the effect hierarchy.
 * Only the explicitly permitted types are allowed to implement it.
 * <p>
 * Effects are modeled as immutable records.
 */
public sealed interface Effect
        permits StatModifierEffect, HealingEffect, ResetMalusEffect, RemoveStatusEffect {
    Target target();

    BattleMessage getEffectDescription(Bugemon affectedBugemon);
}
