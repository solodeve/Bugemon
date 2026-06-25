package ulb.model.domain.effect;

import ulb.event.BattleMessage;
import ulb.event.BattleMessageType;
import ulb.model.domain.Bugemon;
import ulb.model.domain.status.Stat;

/**
 * An effect that alters a specific statistic of a target.
 */
public record StatModifierEffect(
        Target target,
        Stat stat,
        int modifier
) implements Effect {
    @Override
    public BattleMessage getEffectDescription(Bugemon affectedBugemon) {
        String statName = stat.name().toLowerCase();
        String change = modifier > 0 ? "increased" : "decreased";
        return new BattleMessage(
                affectedBugemon.getName()
                        + "'s " + statName
                        + " is " + change
                        + " by " + Math.abs(modifier)
                        + " !",
                BattleMessageType.SYSTEM,
                false,
                modifier > 0
        );
    }
}
