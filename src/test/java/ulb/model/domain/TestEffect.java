package ulb.model.domain;

import org.junit.jupiter.api.Test;
import ulb.model.domain.effect.HealingEffect;
import ulb.model.domain.effect.StatModifierEffect;
import ulb.model.domain.effect.Target;
import ulb.model.domain.status.Stat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEffect {
    @Test
    public void healingEffectShouldNotAcceptNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> new HealingEffect(Target.CASTER, -10));
    }

    @Test
    public void healingEffectShouldNotHealOpponent() {
        assertThrows(IllegalArgumentException.class, () -> new HealingEffect(Target.OPPONENT, 10));
    }

    @Test
    public void statModifierEffectShouldStoreValuesCorrectly() {
        StatModifierEffect effect = new StatModifierEffect(Target.OPPONENT, Stat.DEFENSE, -5);
        assertEquals(Target.OPPONENT, effect.target());
        assertEquals(-5, effect.modifier());
    }
}
