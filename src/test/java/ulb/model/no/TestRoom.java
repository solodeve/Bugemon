package ulb.model.no;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Element;
import ulb.model.domain.Level;
import ulb.model.domain.Statistics;
import ulb.model.team.Team;

import static org.junit.jupiter.api.Assertions.*;

public class TestRoom {
    private Team playerTeam;
    private Team enemyTeam;

    @BeforeEach
    public void setup() {
        playerTeam = new Team("Player");
        playerTeam.add(bugemon("p1"));
        enemyTeam = new Team("Enemy");
        enemyTeam.add(bugemon("e1"));
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

    @Test
    public void testNewRoomIsLockedByDefault() {
        Room room = new Room(1, enemyTeam, null,null,null);
        assertFalse(room.isUnlocked());
    }

    @Test
    public void testUnlockRoom() {
        Room room = new Room(1, enemyTeam, null,null,null);
        room.unlockRoom();
        assertTrue(room.isUnlocked());
    }

    @Test
    public void testRoomTypeDefaultsToEnemy() {
        Room room = new Room(1, enemyTeam, null,null,null);
        assertEquals(Room.RoomType.ENEMY, room.getRoomType());
        assertFalse(room.isBossRoom());
    }

    @Test
    public void testBossRoomTypeIsRecognised() {
        Room room = new Room(2, enemyTeam,  Room.RoomType.BOSS, "Dark Lord", "boss.png");
        assertEquals(Room.RoomType.BOSS, room.getRoomType());
        assertTrue(room.isBossRoom());
        assertEquals("Dark Lord", room.getDisplayName());
        assertEquals("boss.png", room.getDisplaySpritePath());
    }

    @Test
    public void testDisplayNameFallsBackToTeamName() {
        Room room = new Room(1, enemyTeam,  Room.RoomType.ENEMY, null, null);
        assertEquals("Enemy", room.getDisplayName());
    }

    @Test
    public void testGetEnemyTeam() {
        Room room = new Room(1, enemyTeam, null,null,null);
        assertSame(enemyTeam, room.getEnemyTeam());
    }
}
