package ulb.service.battle;

import ulb.event.BattleMessage;
import ulb.event.BattleMessageType;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.model.domain.status.Stat;
import ulb.model.special.DomainExpansion;

import java.util.ArrayList;
import java.util.List;

/**
 * Stateless utility that resolves combat moves and turn order.
 *
 * <p>All methods are pure with respect to BattleSystem state: they receive their
 * inputs as parameters and return a {@link MoveResult} instead of posting
 * messages or mutating BattleSystem directly. The caller (BattleSystem) is
 * responsible for consuming the result (posting messages, triggering KO logic).</p>
 */
public class TurnResolver {

    private static final float NEUTRAL_TYPE_EFFECTIVENESS  = 1.0f;
    private static final float NORMAL_CRITICAL_MULTIPLIER  = 1.0f;
    private static final float EVOLVED_INITIATIVE_MULTIPLIER = 2.0f;

    private TurnResolver() {}

    /**
     * Executes a single move between source and target.
     *
     * <p>Applies damage via {@link DamageCalculator}, collects all result messages,
     * and signals whether the target fainted. The HP of the target is mutated as a
     * side effect of {@link DamageCalculator#calculateDamage}.</p>
     *
     * @param source           the attacking Bugemon
     * @param target           the defending Bugemon
     * @param skill            the skill being used
     * @param isPlayerAttacking true if source belongs to the player's side (for animation)
     * @return a {@link MoveResult} containing messages and faint status
     */
    public static void resolveMove(Bugemon source, Bugemon target, Skill skill, boolean isPlayerAttacking, DomainExpansion activeDomain, boolean guaranteedHit, java.util.function.Consumer<BattleMessage> messageConsumer, Runnable onTargetFainted) {
        List<BattleMessage> messages = new ArrayList<>();
        BattleMessageType attackerType = isPlayerAttacking ? BattleMessageType.PLAYER_ADVANTAGE : BattleMessageType.ENEMY_ADVANTAGE;
        BattleMessageType defenderType = isPlayerAttacking ? BattleMessageType.ENEMY_ADVANTAGE : BattleMessageType.PLAYER_ADVANTAGE;
        DamageDetails damageDetails = DamageCalculator.calculateDamage(skill, source, target, activeDomain, guaranteedHit);

        messages.add(new BattleMessage(source.getName() + " used " + skill.getName() + " on " + target.getName() + " !", "black", skill.getElement(), isPlayerAttacking, skill.getAttackMagic(), false, attackerType, false, !damageDetails.isMissed(), false, false));
        if (damageDetails.isMissed()) {
            messageConsumer.accept(new BattleMessage("The attack missed its target !", defenderType));
            return;}
        addDamageFeedbackMessages(messages, damageDetails, attackerType, defenderType, target);
        skill.applyEffects(source, target, messages);
        messages.forEach(messageConsumer);
        boolean targetFainted = !target.isHealthy();
        if (targetFainted) {
            messageConsumer.accept(new BattleMessage(target.getName() + " has been defeated !", "black", null, false, false, false, attackerType, true, false, false, false));
            onTargetFainted.run();}
    }

    private static void addDamageFeedbackMessages(List<BattleMessage> messages, DamageDetails damageDetails, BattleMessageType attackerType, BattleMessageType defenderType, Bugemon target) {
        messages.add(new BattleMessage(target.getName() + " took " + damageDetails.getDamage() + " damage !", attackerType));
        if (damageDetails.getCritical() > NORMAL_CRITICAL_MULTIPLIER) {
            messages.add(new BattleMessage("Critical Hit !", BattleMessageType.SYSTEM));
        }
        if (damageDetails.getTypeEffectiveness() > NEUTRAL_TYPE_EFFECTIVENESS) {
            messages.add(new BattleMessage("It works like a charm!", attackerType));
        } else if (damageDetails.getTypeEffectiveness() < NEUTRAL_TYPE_EFFECTIVENESS) {
            messages.add(new BattleMessage("That's not very effective...", defenderType));
        }
    }

    /**
     * Determines which side acts first based on skill priority then initiative.
     *
     * @return true if the player should act before the enemy
     */
    public static boolean shouldPlayerActFirst(Skill playerSkill, Skill enemySkill,
                                               Bugemon activePlayer, Bugemon activeEnemy) {
        if (playerSkill.getPriority() != enemySkill.getPriority()) {
            return playerSkill.getPriority() > enemySkill.getPriority();
        }

        float playerInitiative = activePlayer.getInitiative()
                * activePlayer.getStatusMultiplier(Stat.INITIATIVE)
                * (activePlayer.isEvolved() ? EVOLVED_INITIATIVE_MULTIPLIER : 1.0f);
        float enemyInitiative = activeEnemy.getInitiative()
                * activeEnemy.getStatusMultiplier(Stat.INITIATIVE)
                * (activeEnemy.isEvolved() ? EVOLVED_INITIATIVE_MULTIPLIER : 1.0f);
        return playerInitiative >= enemyInitiative;
    }

    /**
     * Returns true if the move's source is alive and the target is still standing.
     */
    public static boolean canStillAct(PlannedMove move) {
        return move != null
                && move.source() != null
                && move.target() != null
                && move.isSourceHealthy()
                && move.isTargetHealthy();
    }


}
