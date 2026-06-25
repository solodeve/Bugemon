package ulb.model.repository;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import ulb.model.domain.*;
import ulb.model.inventory.Item;
import ulb.model.no.FloorDefinition;
import ulb.model.special.DomainExpansion;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.repository.GameRepository;

/**
 * Integration tests for the GameRepository against the local PostgreSQL database.
 * All tests are skipped automatically when the database is not reachable.
 */
class TestGameRepository {

    private static GameRepository repository;

    @BeforeAll
    static void setUpAll() {
        DatabaseBugemon db = new DatabaseBugemon();
        if (!db.isConfigured()) return;
        try (Connection ignored = db.connect()) {
            db.executeSeedFiles();
        } catch (SQLException e) {
            return;
        }
        try {
            repository = new GameRepository();
            repository.initialize();
        } catch (Exception e) {
            repository = null;
        }
    }

    @BeforeEach
    void assumeDbAvailable() {
        Assumptions.assumeTrue(repository != null, "Skipping: database not reachable.");
    }

    private static boolean isDatabaseAvailable(DatabaseBugemon db) {
        if (!db.isConfigured()) return false;
        try (Connection ignored = db.connect()) {
            db.executeSeedFiles();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Test
    @DisplayName("Should load all bugemons from DB")
    void shouldLoadAllBugemons() {
        List<Bugemon> all = repository.getAllBugemons();
        assertFalse(all.isEmpty(), "Bugemon catalog should not be empty after DB init");
    }

    @Test
    @DisplayName("Should correctly link skills to bugemons")
    void shouldLinkSkillsToBugemons() {
        // florachu has 3 skills seeded: fouet_liane, pollen_sournois, racines_vives
        Bugemon florachu = repository.getBugemonById("florachu").orElseThrow();
        assertFalse(florachu.getSkills().isEmpty(), "florachu should have skills loaded from DB");
        florachu.getSkills().forEach(s -> assertNotNull(s.getName()));
    }

    @Test
    @DisplayName("No bugemon should exceed the 3-skill limit")
    void noBugemonExceedsSkillLimit() {
        for (Bugemon b : repository.getAllBugemons()) {
            assertTrue(b.getSkills().size() <= 3,
                    b.getId() + " has " + b.getSkills().size() + " skills (max 3)");
        }
    }

    @Test
    @DisplayName("Should assign domain expansion ID to bugemons")
    void shouldAssignDomainExpansionIdToBugemon() {
        // exceflam has domainExpansionId = volcanic_caldera in seed
        Bugemon exceflam = repository.getBugemonById("exceflam").orElseThrow();
        assertEquals("volcanic_caldera", exceflam.getDomainExpansionId());
    }

    @Test
    @DisplayName("Should load domain expansion with correct boosted element")
    void shouldLoadDomainExpansionWithCorrectElement() {
        DomainExpansion de = repository.getDomainExpansionById("volcanic_caldera").orElseThrow();
        assertEquals(Element.FIRE, de.boostedElement());
        assertFalse(de.bonuses().isEmpty());
    }

    @Test
    @DisplayName("getAllBugemonsAtLevel should return independent copies, not mutate catalog")
    void shouldReturnLeveledCopiesWithoutMutatingCatalog() {
        Bugemon catalogBugemon = repository.getBugemonById("florachu").orElseThrow();
        Bugemon leveledBugemon = repository.getAllBugemonsAtLevel(5).stream()
                .filter(b -> b.getId().equals("florachu")).findFirst().orElseThrow();

        assertEquals(5, leveledBugemon.getLevel());
        assertEquals(1, catalogBugemon.getLevel(), "Catalog bugemon must not be mutated");
        assertNotSame(catalogBugemon, leveledBugemon);
        assertNotSame(catalogBugemon.getStats(), leveledBugemon.getStats());
    }

    @Test
    @DisplayName("Should load items with their effects from DB")
    void shouldLoadItemsWithEffects() {
        // invigorating_berry is a heal item in seed
        Item item = repository.getItemById("invigorating_berry").orElseThrow();
        assertEquals("heal", item.type());
        assertNotNull(item.effect(), "invigorating_berry must have a heal effect");
    }

    @Test
    @DisplayName("Should load floor definitions from DB")
    void shouldLoadFloorsFromDb() {
        List<FloorDefinition> floors = repository.getAllFloors();
        assertFalse(floors.isEmpty(), "Floor catalog should not be empty after DB init");

        FloorDefinition floor1 = repository.getFloorByNumber(1).orElseThrow();
        assertEquals(1, floor1.floorNumber());
        assertFalse(floor1.enemyTeams().isEmpty(), "Floor 1 should have at least one enemy team");
        assertNotNull(floor1.bossTeam(), "Floor 1 should have a boss team");
    }

    @Test
    @DisplayName("Floor enemy teams should have at least one member")
    void floorEnemyTeamsShouldHaveMembers() {
        for (FloorDefinition floor : repository.getAllFloors()) {
            for (var team : floor.enemyTeams()) {
                assertFalse(team.getMembers().isEmpty(),
                        "Floor " + floor.floorNumber() + " enemy team '" + team.getName() + "' has no members");
            }
        }
    }
}
