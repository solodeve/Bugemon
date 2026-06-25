package ulb.model.no;

import ulb.model.team.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one floor of the NO tower.
 */
public class Floor {

    private final int floorNumber;
    private int currentRoom;

    private final ArrayList<Room> regularEnemiesRooms;
    private final Room bossRoom;
    private final int numberOfRooms;

    /**
     * Creates a floor with a sequence of enemy rooms followed by a boss room.
     */
    public Floor(int floorNumber, List<Team> enemyTeams, Team bossTeam, String bossName, String bossSpritePath) {
        this.floorNumber = floorNumber;

        this.currentRoom = 1;
        this.numberOfRooms = enemyTeams == null ? 0 : enemyTeams.size();
        this.regularEnemiesRooms = new ArrayList<>();

        if (enemyTeams != null) {
            for (int i = 0; i < enemyTeams.size(); i++) {
                regularEnemiesRooms.add(new Room(i + 1, enemyTeams.get(i), Room.RoomType.ENEMY, null, null));
            }
        }

        this.bossRoom = new Room(regularEnemiesRooms.size() + 1, bossTeam, Room.RoomType.BOSS, bossName, bossSpritePath);
        unlockFirstRoom();
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Room getCurrentRoom() {
        int ROOM_INDEX_OFFSET = 1;
        return getAllRooms().get(currentRoom - ROOM_INDEX_OFFSET);
    }

    public List<Room> getRegularEnemiesRooms() {
        return List.copyOf(regularEnemiesRooms);
    }

    public Room getBossRoom() {
        return bossRoom;
    }

    public List<Room> getAllRooms() {
        ArrayList<Room> rooms = new ArrayList<>(regularEnemiesRooms);
        if (bossRoom != null) {
            rooms.add(bossRoom);
        }
        return List.copyOf(rooms);
    }
    // retrieves the number of rooms (boss room included)
    public int getNumberOfRooms() {
        return getAllRooms().size();
    }

    // retrieves the number of rooms excluding the boss room
    public int getEnemyRoomCount() {
        return numberOfRooms;
    }

    /**
     * Returns the room position within the current floor
     */
    public int getCurrentRoomNumber() {
        return currentRoom;
    }

    /**
     * Advances to the next room and unlocks it if one exists.
     */
    public void goToNextRoom() {
        if (currentRoom < getAllRooms().size()) {
            currentRoom += 1;
            getCurrentRoom().unlockRoom();
        }
    }

    private void unlockFirstRoom() {
        List<Room> allRooms = getAllRooms();
        if (!allRooms.isEmpty()) {
            allRooms.getFirst().unlockRoom();
        }
    }
}
