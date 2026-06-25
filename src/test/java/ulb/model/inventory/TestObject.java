package ulb.model.inventory;

import org.junit.jupiter.api.Test;
import ulb.model.domain.effect.StatModifierEffect;
import ulb.model.domain.effect.Target;
import ulb.model.domain.status.Stat;

import static org.junit.jupiter.api.Assertions.*;

public class TestObject {

    @Test
    public void createObjectTest() {
        StatModifierEffect effect = new StatModifierEffect(Target.CASTER, Stat.DEFENSE, 10);
        Item object = new Item(
                "gel_defensif",
                "Gel Défensif",
                "Augmente la défense du Bugémon actif de 10 jusqu'à la fin du combat.",
                "boost",
                effect/*,
                "gel_defensif.png"*/
        );

        assertNotNull(object);
        assertEquals("gel_defensif", object.id());
        assertEquals("Gel Défensif", object.name());
        assertEquals("Augmente la défense du Bugémon actif de 10 jusqu'à la fin du combat.", object.description());
        assertEquals("boost", object.type());
        assertEquals(effect, object.effect());
    }
}