package ulb.model.domain;

import org.junit.jupiter.api.Test;
import ulb.model.domain.effect.HealingEffect;
import ulb.model.domain.effect.Effect;
import ulb.model.domain.effect.StatModifierEffect;
import ulb.model.domain.effect.Target;
import ulb.model.domain.status.Stat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSkill {
    @Test
    public void shouldAddEffectsDynamically() {
        Skill skill = new Skill("fouet_liane", "Fouet-Liane", Element.PLANT, "Description", 40, 10, 0, true);
        StatModifierEffect effect = new StatModifierEffect(Target.OPPONENT, Stat.DEFENSE, -5);

        skill.addEffect(effect);
        assertEquals(1, skill.getEffects().size());
    }

    @Test
    public void effectsListShouldBeImmutableFromOutside() {
        Skill skill = new Skill("id", "name", Element.FIRE, "desc", 50, 10, 0, true);
        List<Effect> effects = skill.getEffects();

        assertThrows(UnsupportedOperationException.class, () -> effects.add(new HealingEffect(Target.CASTER, 10)));
    }
}
