package ulb.repository;

import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.model.inventory.Item;
import ulb.model.special.DomainExpansion;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Read-only contract for the static game catalog: Bugemons, skills, items, domain expansions. */
public interface IBugemonCatalogRepository {

    List<Bugemon> getAllBugemons();
    List<Bugemon> getAllBugemonsAtLevel(int level);
    Optional<Bugemon> getBugemonById(String id);
    boolean containsBugemon(String bugemonId);

    Optional<Skill> getSkillById(String id);
    Optional<Skill> getLevelRewardByLevel(int level);
    Map<Integer, Skill> getAllLevelRewards();

    Optional<Item> getItemById(String id);
    Optional<DomainExpansion> getDomainExpansionById(String id);
    Map<Item, Integer> getStartingInventory();
}
