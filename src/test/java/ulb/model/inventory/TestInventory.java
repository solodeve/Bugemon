package ulb.model.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.model.domain.effect.StatModifierEffect;
import ulb.model.domain.effect.Target;
import ulb.model.domain.status.Stat;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestInventory {
    private Inventory inventory;
    private Item object;

    @BeforeEach
    public void setup() {
        inventory = new Inventory();

        StatModifierEffect effect = new StatModifierEffect(Target.CASTER, Stat.DEFENSE, 10);
        object = new Item(
                "gel_defensif",
                "Gel Défensif",
                "Augmente la défense du Bugémon actif de 10 jusqu'à la fin du combat.",
                "boost",
                effect
        );
    }

    @Test
    public void createInventoryTest() {
        inventory.clear();
        Map<Item, Integer> objects = inventory.getItems();

        assertNotNull(inventory);
        assertNotNull(objects);
    }

    @Test
    public void addObjectInInventoryTest() {
        inventory.clear();
        inventory.addItem(object);
        Map<Item, Integer> objects = inventory.getItems();

        assertTrue(objects.containsKey(object));
        assertEquals(1, objects.get(object));
    }

    @Test
    public void addObjectAlreadyPresentInInventoryTest() {
        inventory.clear();
        inventory.addItem(object);
        inventory.addItem(object);
        Map<Item, Integer> objects = inventory.getItems();

        assertTrue(objects.containsKey(object));
        assertEquals(2, objects.get(object));
    }

    @Test
    public void removeObjectFromInventoryTest() {
        inventory.clear();
        inventory.addItem(object);
        inventory.removeItem(object);

        Map<Item, Integer> objects = inventory.getItems();

        assertFalse(objects.containsKey(object));
    }

    @Test
    public void removeObjectAlreadyPresentInInventoryTest() {
        inventory.clear();
        inventory.addItem(object);
        inventory.addItem(object);
        inventory.removeItem(object);

        Map<Item, Integer> objects = inventory.getItems();

        assertTrue(objects.containsKey(object));
        assertEquals(1, objects.get(object));
    }
}