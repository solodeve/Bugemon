package ulb.model.domain.effect;

import ulb.event.BattleMessage;
import ulb.event.BattleMessageType;
import ulb.model.domain.Bugemon;

/**
 * Applies a healing effect to a Bugemon during battle.
 * The healed amount is capped by the target's max HP.
 *
 * @param target the team member(s) to heal
 * @param value  the number of HP to restore
 */
public record HealingEffect(Target target, int value) implements Effect {

    public HealingEffect {
        if (value < 0) {
            throw new IllegalArgumentException("Healing value cannot be negative: " + value);
        }
        if (target == Target.OPPONENT) {
            throw new IllegalArgumentException("Healing target cannot be opponent");
        }
    }

    @Override
    public BattleMessage getEffectDescription(Bugemon affectedBugemon) {
        return new BattleMessage(
                affectedBugemon.getName() + " heals for " + value + " HP !",
                "black",
                null,
                false,
                false,
                true,
                BattleMessageType.PLAYER_ADVANTAGE,
                false,
                false,
                false,
                false
        );
    }
}
