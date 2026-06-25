package ulb.service.battle;

import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;

/**
 * Immutable description of one battle action scheduled for a turn.
 *
 * @param source the Bugemon performing the move
 * @param target the Bugemon targeted by the move
 * @param skill the skill to execute
 */
public record PlannedMove(Bugemon source, Bugemon target, Skill skill) {
    public boolean isSourceHealthy() {
        return source != null && source.isHealthy();
    }

    public boolean isTargetHealthy() {
        return target != null && target.isHealthy();
    }
}
