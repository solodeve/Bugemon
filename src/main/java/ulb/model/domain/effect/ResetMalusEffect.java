package ulb.model.domain.effect;

import ulb.event.BattleMessage;
import ulb.event.BattleMessageType;
import ulb.model.domain.Bugemon;

/**
 * An effect that removes all negative stat modifiers from the target. Absent from the JSON files !
 */
public record ResetMalusEffect(Target target) implements Effect {
    @Override
    public BattleMessage getEffectDescription(Bugemon affectedBugemon) {
        return new BattleMessage(affectedBugemon.getName() + "'s negative status effects are reset !", BattleMessageType.STATUS_RESET);
    }
}
