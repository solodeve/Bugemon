package ulb.view.battle.towerMode;

import java.util.List;

import ulb.model.domain.Skill;
import ulb.model.domain.effect.Effect;
import ulb.model.domain.effect.HealingEffect;
import ulb.model.domain.effect.RemoveStatusEffect;
import ulb.model.domain.effect.ResetMalusEffect;
import ulb.model.domain.effect.StatModifierEffect;

/**
 * View-layer projection of a skill that becomes available as a level-up reward.
 *
 * <p>Used by {@link RewardsView} to display each unlocked skill as a card.
 * All fields are pre-formatted strings or primitives; no domain objects cross
 * into the view layer through this record.
 */
public record LevelRewardDisplay(
        int level,
        String name,
        String type,
        String description,
        int power,
        double accuracy,
        int priority,
        boolean isMagic,
        String effectText
) {
    public static LevelRewardDisplay from(int level, Skill skill) {
        List<Effect> effects = skill.getEffects();
        String effectText = effects.isEmpty() ? "Aucun effet" : formatFirstEffect(skill);
        String elementText = skill.getElementDisplayName() != null ? skill.getElementDisplayName() : "?";
        return new LevelRewardDisplay(
                level,
                skill.getName(),
                elementText,
                skill.getDescription(),
                skill.getPower(),
                skill.getPrecision(),
                skill.getPriority(),
                skill.getAttackMagic(),
                effectText
        );
    }

    private static String formatFirstEffect(Skill skill) {
        Effect effect = skill.getEffects().getFirst();
        return switch (effect) {
            case StatModifierEffect e -> {
                String sign = e.modifier() > 0 ? "+" : "";
                yield e.stat().name().charAt(0) + e.stat().name().substring(1).toLowerCase()
                        + " " + sign + e.modifier();
            }
            case HealingEffect e      -> "Soin +" + e.value() + " HP";
            case RemoveStatusEffect e -> "Retire les statuts négatifs";
            case ResetMalusEffect e   -> "Réinitialise les malus";
        };
    }
}
