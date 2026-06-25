package ulb.model.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ulb.model.domain.Bugemon;
import ulb.model.inventory.Inventory;
import ulb.model.inventory.Item;

import java.util.*;

/**
 * Represents a team of Bugemons assembled by a player.
 *
 * <p>A team groups up to {@value #MAX_TEAM_SIZE_DEFAULT} Bugemons and owns
 * an {@link Inventory} of consumable items used during battle.
 * Teams are created and edited on the team-management screen.</p>
 *
 * <h2>Invariants</h2>
 * <ul>
 *   <li>A team cannot contain more than {@link #getMaxSize()} Bugemons.</li>
 *   <li>The same Bugemon instance cannot appear twice in a team.</li>
 *   <li>The members list is exposed as an unmodifiable view; mutations go through
 *       {@link #add(Bugemon)} and {@link #remove(Bugemon)}.</li>
 * </ul>
 */
public final class Team {
    private static final int MAX_TEAM_SIZE_DEFAULT = 6;

    private String name;
    private final int maxTeamSize = MAX_TEAM_SIZE_DEFAULT;
    private final List<Bugemon> members;
    private final Set<Bugemon> usedBugemons;
    private final Inventory inventory;

    public Team(String name) {
        this.name = name == null ? "" : name;
        this.members = new ArrayList<>();
        this.usedBugemons = new HashSet<>();
        this.inventory = new Inventory();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    @JsonIgnore
    public int getMaxSize() {
        return maxTeamSize;
    }

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * maxs out the bugemons' stats (for Free Battle Mode)
     */
    public void setAllMembersMaxLevel() {
        members.forEach(Bugemon::setLevelMax);
    }

    public Set<String> getEvolvedMemberIds() {
        return members.stream()
                .filter(Bugemon::isEvolved)
                .map(Bugemon::getId)
                .collect(java.util.stream.Collectors.toSet());
    }

    public List<Bugemon> getMembers() {
        return List.copyOf(members);
    }

    @JsonIgnore
    public int size() {
        return members.size();
    }

    @JsonIgnore
    public boolean isFull() {
        return members.size() >= maxTeamSize;
    }

    public boolean contains(Bugemon bugemon) {
        return members.contains(bugemon);
    }

    public boolean add(Bugemon bugemon) {
        if (isFull() || members.contains(bugemon)) {
            return false;
        }

        members.add(bugemon);
        return true;
    }

    public boolean isEmpty(){
        return members.isEmpty();
    }

    public Optional<Bugemon> getFirstMember() {
        if (members.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(members.getFirst());
    }

    public Optional<Bugemon> getTeamMember(int index) {
        if (index < 0 || index >= members.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(members.get(index));
    }

    public boolean remove(Bugemon bugemon) {
        if (bugemon == null) {
            return false;
        }
        return members.remove(bugemon);
    }

    public void clear() {
        members.clear();
    }

    /**
     * This method returns the first Bugemon still able to fight.
     */
    @JsonIgnore
    public Optional<Bugemon> getHealthyBugemon() {
        for (Bugemon bugemon : members) {
            if (bugemon.isHealthy()) {
                return Optional.of(bugemon);
            }
        }
        return Optional.empty();
    }


    public boolean hasAfoBugemon() {
        return members.stream().anyMatch(b -> b.getElement() == ulb.model.domain.Element.ALL);
    }

    /**
     * Two teams are equal when they share the same name and members,
     * regardless of their inventory or battle history.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Team other)) {
            return false;
        }

        return Objects.equals(name, other.name)
                && Objects.equals(members, other.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, members);
    }

    /**
    *Used to keeps track of which bugemons participated in a battle (usefull for battle rewards)
    */
    public void addUsedBugemon(Bugemon bugemon) {
        if (bugemon != null) {
            usedBugemons.add(bugemon);
        }
    }

    /**
     * Returns the set of the bugemons that were used in battle
     */
    public Set<Bugemon> getUsedBugemons() {
        return usedBugemons;
    }

    public boolean hasName(String otherName) {
        return name.equals(otherName);
    }

    /**
     * Clears the list of Bugemons that have been used in battle.
     */
    public void clearUsedBugemons() {
        usedBugemons.clear();
    }

    public void removeObjectFromInventory(Item item) {
        inventory.removeItem(item);
    }

    public boolean isInventoryEmpty() {
        return inventory.isEmpty();
    }

    public void fillInventory(Map<Item, Integer> items) {
        if (items == null) {
            return;
        }

        inventory.addAll(items);
    }

    public void clearInventory() {
        inventory.clear();
    }

    public List<Item> getInventoryItems() {
        return inventory.getItemsName();
    }

    public int getInventoryQuantity(Item item) {
        return inventory.getQuantity(item);
    }

    public String getLeadSprite() {
        if (members.isEmpty() || members.getFirst() == null) {
            return null;
        }
        Bugemon leadBugemon = members.getFirst();
        return leadBugemon.getSprite();
    }

    public void initializeInventoryContent(Map<Item, Integer> startingInventory) {
            clearInventory();
            fillInventory(startingInventory);
    }

    /**
     * Restores all members to full HP. Called at the end of a battle.
     */
    public void restoreAllHp() {
        members.forEach(Bugemon::restoreHp);
    }

    /**
     * Clears all temporary battle-only state from each team member.
     */
    public void resetBattleState() {
        members.forEach(Bugemon::resetBattleState);
    }
}
