package ulb.service.battle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Element;
import ulb.model.domain.Skill;
import ulb.model.domain.Statistics;
import org.junit.jupiter.api.Test;

public class TestEnemyBattleAi {

    @Test
    public void chooseSkillReturnsEmptyWhenEnemyHasNoSkill() {
        EnemyBattleAi ai = new EnemyBattleAi(new FixedRandom(0.0, 0), 0.5);
        Bugemon enemy = new Bugemon(
                "enemy",
                "Enemy",
                Element.FIRE,
                new Statistics(20, 10, 10, 10, 10, 10),
                "enemy.png",
                false
        );

        assertTrue(ai.chooseSkill(enemy).isEmpty());
    }

    @Test
    public void chooseSkillUsesInjectedRandom() {
        EnemyBattleAi ai = new EnemyBattleAi(new FixedRandom(0.0, 1), 0.5);
        Bugemon enemy = new Bugemon(
                "enemy",
                "Enemy",
                Element.FIRE,
                new Statistics(20, 10, 10, 10, 10, 10),
                "enemy.png",
                false
        );
        Skill first = new Skill("s1", "First", Element.FIRE, "first hit", 10, 1, 0, false);
        Skill second = new Skill("s2", "Second", Element.FIRE, "second hit", 10, 1, 0, false);
        enemy.learnSkill(first);
        enemy.learnSkill(second);

        assertEquals(second, ai.chooseSkill(enemy).orElseThrow());
    }

    @Test
    public void shouldUseDomainRespectsAvailabilityAndChance() {
        EnemyBattleAi alwaysUse = new EnemyBattleAi(new FixedRandom(0.0, 0), 0.5);
        EnemyBattleAi neverUse = new EnemyBattleAi(new FixedRandom(0.9, 0), 0.5);

        assertTrue(alwaysUse.shouldUseDomain(true));
        assertFalse(alwaysUse.shouldUseDomain(false));
        assertFalse(neverUse.shouldUseDomain(true));
    }

    private static class FixedRandom extends Random {
        private final double nextDouble;
        private final int nextInt;

        private FixedRandom(double nextDouble, int nextInt) {
            this.nextDouble = nextDouble;
            this.nextInt = nextInt;
        }

        @Override
        public double nextDouble() {
            return nextDouble;
        }

        @Override
        public int nextInt(int bound) {
            return Math.min(nextInt, bound - 1);
        }
    }
}
