package ulb.model.domain.effect;

import ulb.event.BattleMessage;
import ulb.event.BattleMessageType;
import ulb.model.domain.Bugemon;

/**
 * An effect that removes all negative status conditions from the target.
 */
public record RemoveStatusEffect(Target target) implements Effect {
    @Override
    public BattleMessage getEffectDescription(Bugemon affectedBugemon) {
        return new BattleMessage(affectedBugemon.getName() + " is cured of its status effects !", BattleMessageType.STATUS_CURE);
    }
}
