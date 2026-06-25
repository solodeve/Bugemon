package ulb.view.menu.teamEdition;

import java.util.List;
import ulb.model.domain.effect.Effect;
import ulb.model.domain.effect.HealingEffect;
import ulb.model.domain.effect.RemoveStatusEffect;
import ulb.model.domain.effect.ResetMalusEffect;
import ulb.model.domain.status.Stat;
import ulb.model.domain.effect.StatModifierEffect;
import ulb.model.domain.effect.Target;

/**
 * Formats a {@link TeamEditionView.SkillDescriptionData} into a multi-line string
 * suitable for display in the skill detail pane of {@link TeamEditionView}.
 *
 * <p>The output order is: description, element, power, precision, priority, category,
 * then the effect list (comma-separated). Unknown {@link ulb.model.domain.effect.Effect}
 * subtypes fall back to {@code Effect.toString()}.
 */
final class SkillDescriptionFormatter {

    private SkillDescriptionFormatter() {}

    /**
     * Converts {@code description} into the full display string.
     * A {@code null} or blank {@link TeamEditionView.SkillDescriptionData#description()}
     * is replaced with {@code "No description."}.
     */
    static String format(TeamEditionView.SkillDescriptionData description) {
        StringBuilder builder = new StringBuilder();
        builder.append(description.description() == null || description.description().isBlank()
                ? "No description."
                : description.description());
        builder.append(System.lineSeparator()).append(System.lineSeparator());
        builder.append("Element: ").append(description.element() == null ? "-" : description.element());
        builder.append(System.lineSeparator());
        builder.append("Power: ").append(description.power());
        builder.append(System.lineSeparator());
        builder.append("Precision: ").append(Math.round(description.precision() * 100)).append('%');
        builder.append(System.lineSeparator());
        builder.append("Priority: ").append(description.priority());
        builder.append(System.lineSeparator());
        builder.append("Category: ").append(description.magical() ? "Magic" : "Physical");
        builder.append(System.lineSeparator());
        builder.append(effectsToString(description.effects()));
        return builder.toString();
    }

    private static String effectsToString(List<Effect> effects) {
        if (effects == null || effects.isEmpty()) {
            return "";
        }

        return effects.stream()
                .filter(java.util.Objects::nonNull)
                .map(SkillDescriptionFormatter::formatEffect)
                .filter(effect -> effect != null && !effect.isBlank())
                .reduce((left, right) -> left + ", " + right)
                .orElse("");
    }

    private static String formatEffect(Effect effect) {
        if (effect instanceof StatModifierEffect(Target target, Stat stat, int modifier)) {
            String sign = modifier > 0 ? "+" : "";
            return formatEnum(target) + " " + formatEnum(stat) + " " + sign + modifier;
        }
        if (effect instanceof HealingEffect(Target target, int value)) {
            return "Heal " + formatEnum(target) + " by " + value;
        }
        if (effect instanceof RemoveStatusEffect(Target target)) {
            return "Remove status from " + formatEnum(target);
        }
        if (effect instanceof ResetMalusEffect(Target target)) {
            return "Reset malus for " + formatEnum(target);
        }
        return effect.toString();
    }

    private static String formatEnum(Enum<?> value) {
        if (value == null) {
            return "";
        }

        String[] words = value.name().toLowerCase().split("_");
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            if (word.isBlank()) {
                continue;
            }
            if (!builder.isEmpty()) {
                builder.append(' ');
            }
            builder.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return builder.toString();
    }
}

