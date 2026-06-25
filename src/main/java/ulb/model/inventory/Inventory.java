package ulb.model.inventory;

import ulb.repository.GameRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds the player's current stock of consumable items during a battle.
 *
 * <p>The inventory is modelled as a {@link HashMap} mapping each {@link Item}
 * to its remaining quantity. Quantities are always positive; when a quantity
 * reaches zero the entry is removed entirely.</p>
 *
 * <p>Each {@link ulb.model.team.Team} owns one {@code Inventory} instance.
 * At the start of each battle, {@link ulb.controller.battle.BattleController#setUpBattle()}
 * populates the inventory from the starting configuration stored in
 * {@link GameRepository#getStartingInventory()}.</p>
 */
public class Inventory {

    /**
     * Internal map from item to quantity.
     * Keys are {@link Item} instances from the game catalogue.
     * Values are always {@code >= 1} (entries at zero are removed).
     */
    private final HashMap<Item, Integer> inventory;

    public Inventory() {
        inventory = new HashMap<>();
    }

    /**
     * Returns an unmodifiable snapshot of the current inventory contents.
     * The returned map cannot be modified; use {@link #addItem} and
     * {@link #removeItem} to change the stock.
     *
     * @return An unmodifiable map of items to their quantities.
     */
    public Map<Item, Integer> getItems() {
        return Map.copyOf(inventory);
    }

    /**
     * Adds one unit of the given item to the inventory.
     * If the item is already present its quantity is incremented; otherwise
     * a new entry is created with quantity {@code 1}.
     *
     * @param object The item to add. Must not be {@code null}.
     * @throws IllegalArgumentException if {@code object} is {@code null}.
     */
    public void addItem(Item object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }

        if (inventory.containsKey(object)) {
            int value = inventory.get(object);
            inventory.replace(object, value + 1);
        } else {
            inventory.put(object, 1);
        }
    }

    /**
     * Removes one unit of the given item from the inventory.
     * If the quantity drops to zero the entry is removed entirely.
     * Does nothing if the item is not present.
     *
     * @param object The item to consume. Must not be {@code null}.
     * @throws IllegalArgumentException if {@code object} is {@code null}.
     */
    public void removeItem(Item object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }

        if (inventory.containsKey(object)) {
            int value = inventory.get(object);
            if (value == 1) {
                inventory.remove(object);
            } else {
                inventory.replace(object, value - 1);
            }
        }
    }

    /**
     * Removes all items from the inventory.
     * Called before populating the inventory at the start of each battle.
     */
    public void clear() {
        inventory.clear();
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    public void addAll(Map<Item, Integer> items) {
        if (items == null) {
            return;
        }

        items.forEach((object, quantity) -> {
            for (int i = 0; i < quantity; i++) {
                addItem(object);
            }
        });
    }

    public List<Item> getItemsName() {
        return new ArrayList<>(inventory.keySet());
    }

    public int getQuantity(Item object) {
        if (object == null) {
            return 0;
        }
        return inventory.getOrDefault(object, 0);
    }
}
