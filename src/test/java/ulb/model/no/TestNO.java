package ulb.model.no;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Element;
import ulb.model.domain.Level;
import ulb.model.domain.Statistics;
import ulb.repository.IGameRepository;
import ulb.model.team.Team;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestNO {
    private IGameRepository repo;
    private Team playerTeam;

    @BeforeEach
    public void setup() {
        repo = mock(IGameRepository.class);

        playerTeam = new Team("Player");
        playerTeam.add(bugemon("p1"));
    }

    private static Bugemon bugemon(String id) {
        Bugemon bugemon = new Bugemon(
                id,
                "B-" + id,
                Element.PLANT,
                new Statistics(50, 10, 10, 10, 10, 10),
                "sprite.png",
                false
        );
        bugemon.setLevel(new Level(10, 0));
        return bugemon;
    }

    private static Team team(String name, String... members) {
        Team team = new Team(name);
        for (String member : members) {
            team.add(bugemon(member));
        }
        return team;
    }

    @Test
    public void testConstructorUsesFloorDefinitions() {
        // Some callers only stub getAllFloors(); keep behavior stable by falling back.
        when(repo.getAllFloors()).thenReturn(List.of(
                new FloorDefinition(
                        1,
                        List.of(team("enemy1", "a"), team("enemy2", "b"), team("enemy3", "c")),
                        team("boss1", "z"),
                        "Boss 1",
                        "boss1.png"
                )
        ));
        when(repo.getFloorByNumber(1)).thenReturn(Optional.of(
                new FloorDefinition(
                        1,
                        List.of(team("enemy1", "a"), team("enemy2", "b"), team("enemy3", "c")),
                        team("boss1", "z"),
                        "Boss 1",
                        "boss1.png"
                )
        ));

        NO no = new NO(repo);

        assertEquals(1, no.getNumberOfFloors());
        assertNotNull(no.getCurrentFloor());
        assertEquals(1, no.getCurrentFloor().getFloorNumber());
        assertEquals(3, no.getCurrentFloor().getRegularEnemiesRooms().size());
        assertEquals(Room.RoomType.BOSS, no.getCurrentFloor().getBossRoom().getRoomType());
    }

    @Test
    public void testNextFloorIncrementsAndLoadsNewDefinition() {
        when(repo.getAllFloors()).thenReturn(List.of(
                new FloorDefinition(
                        1,
                        List.of(team("enemy1", "a"), team("enemy2", "b"), team("enemy3", "c")),
                        team("boss1", "z"),
                        "Boss 1",
                        "boss1.png"
                ),
                new FloorDefinition(
                        2,
                        List.of(team("enemy4", "d"), team("enemy5", "e"), team("enemy6", "f")),
                        team("boss2", "y"),
                        "Boss 2",
                        "boss2.png"
                )
        ));
        when(repo.getFloorByNumber(1)).thenReturn(Optional.of(
                new FloorDefinition(
                        1,
                        List.of(team("enemy1", "a"), team("enemy2", "b"), team("enemy3", "c")),
                        team("boss1", "z"),
                        "Boss 1",
                        "boss1.png"
                )
        ));
        when(repo.getFloorByNumber(2)).thenReturn(Optional.of(
                new FloorDefinition(
                        2,
                        List.of(team("enemy4", "d"), team("enemy5", "e"), team("enemy6", "f")),
                        team("boss2", "y"),
                        "Boss 2",
                        "boss2.png"
                )
        ));

        NO no = new NO(repo);
        no.goToNextFloor();

        assertEquals(2, no.getCurrentFloor().getFloorNumber());
        assertEquals("boss2", no.getCurrentFloor().getBossRoom().getEnemyTeam().getName());
    }

    @Test
    public void testConstructorLeavesCurrentFloorNullWhenDefinitionIsMissing() {
        when(repo.getAllFloors()).thenReturn(List.of(
                new FloorDefinition(
                        1,
                        List.of(team("enemy1", "a")),
                        team("boss1", "z"),
                        "Boss 1",
                        "boss1.png"
                )
        ));
        when(repo.getFloorByNumber(1)).thenReturn(Optional.empty());

        NO no = new NO(repo);

        assertEquals(1, no.getCurrentFloorNumber());
        assertNull(no.getCurrentFloor());
    }

    @Test
    public void testGoToNextFloorThrowsOnLastFloor() {
        when(repo.getAllFloors()).thenReturn(List.of(
                new FloorDefinition(
                        1,
                        List.of(team("enemy1", "a")),
                        team("boss1", "z"),
                        "Boss 1",
                        "boss1.png"
                )
        ));
        when(repo.getFloorByNumber(1)).thenReturn(Optional.of(
                new FloorDefinition(
                        1,
                        List.of(team("enemy1", "a")),
                        team("boss1", "z"),
                        "Boss 1",
                        "boss1.png"
                )
        ));

        NO no = new NO(repo);
        assertThrows(IllegalStateException.class, no::goToNextFloor);
    }
}
