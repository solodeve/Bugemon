package ulb.model.domain;

import org.junit.jupiter.api.Test;
import ulb.model.domain.effect.StatModifierEffect;
import ulb.model.domain.effect.Target;
import ulb.model.domain.status.Stat;
import ulb.model.domain.status.Status;
import ulb.service.battle.DamageCalculator;
import ulb.service.battle.DamageDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBugemon {
    @Test
    public void shouldStartWithNeutralStatusMultipliers() {
        Bugemon bugemon = new Bugemon(
                "id",
                "Bug",
                Element.ICE,
                new Statistics(80, 40, 40, 40, 10, 40),
                "sprite.png",
                false
        );

        assertEquals(1.0f, bugemon.getStatusMultiplier(Status.ATTACK));
        assertEquals(1.0f, bugemon.getStatusMultiplier(Status.DEFENSE));
        assertEquals(1.0f, bugemon.getStatusMultiplier(Status.ATTACK_MAGIC));
        assertEquals(1.0f, bugemon.getStatusMultiplier(Status.DEFENSE_MAGIC));
        assertEquals(1.0f, bugemon.getStatusMultiplier(Status.INITIATIVE));
    }

    @Test
    public void shouldApplyAttackStatusModifierEffect() {
        Bugemon bugemon = new Bugemon(
                "id",
                "Bug",
                Element.ICE,
                new Statistics(80, 40, 40, 40, 10, 40),
                "sprite.png",
                false
        );

        bugemon.applyEffect(new StatModifierEffect(Target.CASTER, Stat.ATTACK, 2));

        assertEquals(2.0f, bugemon.getStatusMultiplier(Status.ATTACK));
    }

    @Test
    public void shouldClampStatusModifierToMaximumStage() {
        Bugemon bugemon = new Bugemon(
                "id",
                "Bug",
                Element.ICE,
                new Statistics(80, 40, 40, 40, 10, 40),
                "sprite.png",
                false
        );

        bugemon.applyEffect(new StatModifierEffect(Target.CASTER, Stat.DEFENSE, 4));
        bugemon.applyEffect(new StatModifierEffect(Target.CASTER, Stat.DEFENSE, 3));

        assertEquals(3.0f, bugemon.getStatusMultiplier(Status.DEFENSE));
    }

    @Test
    public void shouldClampStatusModifierToMinimumStage() {
        Bugemon bugemon = new Bugemon(
                "id",
                "Bug",
                Element.ICE,
                new Statistics(80, 40, 40, 40, 10, 40),
                "sprite.png",
                false
        );

        bugemon.applyEffect(new StatModifierEffect(Target.CASTER, Stat.DEFENSE_MAGIC, -4));
        bugemon.applyEffect(new StatModifierEffect(Target.CASTER, Stat.DEFENSE_MAGIC, -2));

        assertEquals(0.25f, bugemon.getStatusMultiplier(Status.DEFENSE_MAGIC));
    }

    @Test
    void shouldNotExceedThreeSkills() {
        Statistics stats = new Statistics(80, 40, 40, 40, 10, 40);
        Bugemon bugemon = new Bugemon("id", "Bug", Element.ICE, stats, "sprite.png", false);
        Skill skill = new Skill("s", "Skill", Element.ICE, "desc", 10, 10, 0, true);

        bugemon.learnSkill(skill); // 1
        bugemon.learnSkill(new Skill("Skill1", "Skill1", Element.PLANT, "desc", 1, 1, 1, false)); // 2
        bugemon.learnSkill(new Skill("Skill2", "Skill2", Element.PLANT, "desc", 1, 1, 1, false)); // 3
        bugemon.learnSkill(new Skill("Skill3", "Skill3", Element.PLANT, "desc", 1, 1, 1, false)); // 4
        bugemon.learnSkill(new Skill("Skill4", "Skill4", Element.PLANT, "desc", 1, 1, 1, false)); // 5
        bugemon.learnSkill(new Skill("Skill5", "Skill5", Element.PLANT, "desc", 1, 1, 1, false)); // 6

        // Max equipped skills should be 3
        assertEquals(3, bugemon.getSkills().size());
        // All known skills are still retained
        assertEquals(6, bugemon.getKnownSkills().size());
    }

    @Test
    public void shouldUseSkillPowerWhenComputingDamage() {
        Bugemon attacker = new Bugemon(
                "attacker",
                "Attacker",
                Element.FIRE,
                new Statistics(80, 20, 10, 12, 8, 20),
                "attacker.png",
                false
        );

        Bugemon defender = new Bugemon(
                "defender",
                "Defender",
                Element.ICE,
                new Statistics(80, 10, 18, 10, 6, 10),
                "defender.png",
                false
        );

        DamageDetails weakDamage = DamageCalculator.calculateDamage(
                new Skill("weak", "Weak", Element.FIRE, "desc", 20, 1, 0, false),
                attacker,
                defender,
                null,
                false
        );

        defender.setHp(80);

        DamageDetails strongDamage = DamageCalculator.calculateDamage(
                new Skill("strong", "Strong", Element.FIRE, "desc", 60, 1, 0, false),
                attacker,
                defender,
                null,
                false
        );

        assertTrue(strongDamage.getDamage() > weakDamage.getDamage(), "A stronger skill should inflict more damage");
        assertEquals(80 - strongDamage.getDamage(), defender.getHp());
    }
}
