package ulb.service.battle;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;

/**
 * Decides which action the enemy should take during battle.
 */
public class EnemyBattleAi {
    private static final double DEFAULT_DOMAIN_USE_CHANCE = 0.5;


    private final Random random;
    private final double domainUseChance;

    public EnemyBattleAi() {
        this(new Random(), DEFAULT_DOMAIN_USE_CHANCE);
    }

    public EnemyBattleAi(Random random) {
        this(random, DEFAULT_DOMAIN_USE_CHANCE);
    }

    public EnemyBattleAi(Random random, double domainUseChance) {
        this.random = random == null ? new Random() : random;
        this.domainUseChance = Math.max(0.0, Math.min(1.0, domainUseChance));
    }

    public boolean shouldUseDomain(boolean canUseDomain) {
        return canUseDomain && random.nextDouble() < domainUseChance;
    }

    public Optional<Skill> chooseSkill(Bugemon enemy) {
        if (enemy == null) {
            return Optional.empty();
        }

        List<Skill> skills = enemy.getSkills();
        if (skills.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(skills.get(random.nextInt(skills.size())));
    }
}
