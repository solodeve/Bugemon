package ulb.service.battle;

import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.model.domain.status.Stat;
import ulb.model.special.DomainExpansion;

public class DamageCalculator {

    // DAMAGE CALCULATION CONSTANTS
    private static final float CRITICAL_HIT_CHANCE = 0.1f;
    private static final float CRITICAL_HIT_MULTIPLIER = 1.5f;
    private static final float RANDOM_FACTOR_MIN = 0.85f;
    private static final float RANDOM_FACTOR_RANGE = 0.15f;
    private static final float DAMAGE_DIVISOR = 5.0f;
    private static final float EVOLVED_ATTACK_MULTIPLIER  = 3.0f;
    private static final float EVOLVED_DEFENSE_MULTIPLIER = 0.1f;

    /**
     * Calculates and applies damage from an attack.
     *
     * <p>Formula: {@code damage = floor((power × attack / defense / 5.0) × modifiers)}<br>
     * Modifiers: randomFactor [0.85-1.0] × typeEffectiveness × critical (10% → 1.5×) × 
     * domainBoost × evolvedAttack (3.0×) × evolvedDefense (0.1×)</p>
     *
     * <p>Uses ATTACK/DEFENSE for physical skills, ATTACK_MAGIC/DEFENSE_MAGIC for magical.
     * Accuracy is checked unless {@code guaranteedHit=true}.
     * Defender's HP is immediately reduced.</p>
     *
     * @param skill the attack skill (must have power, element, precision)
     * @param attacker the attacker (stats + evolved status)
     * @param defender the defender (HP is modified)
     * @param activeDomain optional domain expansion boost
     * @param guaranteedHit if true, skip accuracy check
     * @return damage dealt, critical flag, type effectiveness, miss flag
    */
    public static DamageDetails calculateDamage(Skill skill, Bugemon attacker, Bugemon defender, DomainExpansion activeDomain, boolean guaranteedHit) {
        if (!areParametersValid(skill, attacker, defender)) {
            return new DamageDetails(0, CRITICAL_HIT_CHANCE, 1.0f, true);
        }

        if (!guaranteedHit && !checkHitAccuracy(skill)) {
            return new DamageDetails(0, CRITICAL_HIT_CHANCE, 1.0f, true);
        }

        int damage = computeDamage(skill, attacker, defender, activeDomain);
        int remainingHp = Math.max(0, defender.getHp() - damage);
        defender.setHp(remainingHp);

        float critical = Math.random() < CRITICAL_HIT_CHANCE ? CRITICAL_HIT_MULTIPLIER : 1.0f;
        float typeEffectiveness = TypeChart.getEffectiveness(skill.getElement(), defender.getElement());

        return new DamageDetails(damage, critical, typeEffectiveness, false);
    }

    private static boolean areParametersValid(Skill skill, Bugemon attacker, Bugemon defender) {
        return skill != null && attacker != null && defender != null;
    }

    private static boolean checkHitAccuracy(Skill skill) {
        float hitChance = normalizePrecision(skill.getPrecision());
        return Math.random() <= hitChance;
    }

    private static int computeDamage(Skill skill, Bugemon attacker, Bugemon defender, DomainExpansion activeDomain) {
        if (skill.getPower() == 0) {
            return 0;
        }

        float attack = getAttackStat(skill, attacker);
        float defense = getDefenseStat(skill, defender);
        float randomFactor = RANDOM_FACTOR_MIN + (float) Math.random() * RANDOM_FACTOR_RANGE;
        float typeEffectiveness = TypeChart.getEffectiveness(skill.getElement(), defender.getElement());
        float critical = Math.random() < CRITICAL_HIT_CHANCE ? CRITICAL_HIT_MULTIPLIER : 1.0f;
        float domainMultiplier = activeDomain == null ? 1.0f : activeDomain.getDamageMultiplier(skill.getElement());
        float evolvedAttack = attacker.isEvolved() ? EVOLVED_ATTACK_MULTIPLIER : 1.0f;
        float evolvedDefense = defender.isEvolved() ? EVOLVED_DEFENSE_MULTIPLIER : 1.0f;

        float damageFactor = randomFactor * typeEffectiveness * critical * domainMultiplier * evolvedAttack * evolvedDefense;
        float rawDamage = skill.getPower() * attack / defense;

        return (int) Math.floor(rawDamage / DAMAGE_DIVISOR * damageFactor);
    }

    private static float getAttackStat(Skill skill, Bugemon attacker) {
        if (skill.getAttackMagic()) {
            return Math.max(1, attacker.getAttackMagic() * attacker.getStatusMultiplier(Stat.ATTACK_MAGIC));
        }
        return Math.max(1, attacker.getAttack() * attacker.getStatusMultiplier(Stat.ATTACK));
    }

    private static float getDefenseStat(Skill skill, Bugemon defender) {
        if (skill.getAttackMagic()) {
            return Math.max(1, defender.getDefenseMagic() * defender.getStatusMultiplier(Stat.DEFENSE_MAGIC));
        }
        return Math.max(1, defender.getDefense() * defender.getStatusMultiplier(Stat.DEFENSE));
    }

    private static float normalizePrecision(float precision) {
        if (precision <= 0f) {
            return 0f;
        }
        return Math.min(precision, 1f);
    }
}
