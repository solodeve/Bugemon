package ulb.model.no;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Element;
import ulb.model.domain.Level;
import ulb.model.domain.Statistics;
import ulb.model.team.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFloor {
    private Team playerTeam;

    @BeforeEach
    public void setup() {
        playerTeam = new Team("Player");
        playerTeam.add(bugemon("p1"));
    }

    private static Bugemon bugemon(String id) {
        Bugemon b = new Bugemon(
                id, "B-" + id, Element.PLANT,
                new Statistics(50, 10, 10, 10, 10, 10),
                "sprite.png", false
        );
        b.setLevel(new Level(10, 0));
        return b;
    }

    private static Team team(String name, String... members) {
        Team team = new Team(name);
        for (String member : members) {
            team.add(bugemon(member));
        }
        return team;
    }

    @Test
    public void testFloorSeparatesEnemyRoomsAndBossRoom() {
        List<Team> enemyTeams = List.of(
                team("enemy1", "a"),
                team("enemy2", "b"),
                team("enemy3", "c")
        );
        Team bossTeam = team("boss", "z");

        Floor floor = new Floor(1, enemyTeams, bossTeam, null, null);

        assertEquals(1, floor.getFloorNumber());
        assertEquals(3, floor.getRegularEnemiesRooms().size());
        assertEquals(4, floor.getNumberOfRooms());
        assertEquals(Room.RoomType.ENEMY, floor.getCurrentRoom().getRoomType());
        assertEquals(Room.RoomType.BOSS, floor.getBossRoom().getRoomType());
        assertTrue(floor.getBossRoom().isBossRoom());

        floor.goToNextRoom();
        assertEquals(2, floor.getCurrentRoom().getRoomNumber());

        floor.goToNextRoom();
        assertEquals(3, floor.getCurrentRoom().getRoomNumber());

        floor.goToNextRoom();
        assertEquals(4, floor.getCurrentRoom().getRoomNumber());
        assertEquals(Room.RoomType.BOSS, floor.getCurrentRoom().getRoomType());
    }

    @Test
    public void testFloorExposesAllRoomsInOrderAndUnlocksNextRoom() {
        List<Team> enemyTeams = List.of(
                team("enemy1", "a"),
                team("enemy2", "b")
        );
        Team bossTeam = team("boss", "z");

        Floor floor = new Floor(2, enemyTeams, bossTeam, "Boss Alpha", "boss.png");

        List<Room> rooms = floor.getAllRooms();
        assertEquals(3, rooms.size());
        assertEquals(Room.RoomType.ENEMY, rooms.get(0).getRoomType());
        assertEquals(Room.RoomType.ENEMY, rooms.get(1).getRoomType());
        assertEquals(Room.RoomType.BOSS, rooms.get(2).getRoomType());
        assertTrue(rooms.get(0).isUnlocked());
        assertEquals("Boss Alpha", rooms.get(2).getDisplayName());
        assertEquals("boss.png", rooms.get(2).getDisplaySpritePath());

        floor.goToNextRoom();
        assertTrue(floor.getCurrentRoom().isUnlocked());
        assertEquals(2, floor.getCurrentRoomNumber());
    }
}
