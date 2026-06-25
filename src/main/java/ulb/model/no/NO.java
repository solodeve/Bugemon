package ulb.model.no;

import ulb.model.team.Team;
import ulb.repository.IGameRepository;

import java.util.List;

/**
 * Represents one complete NO tower run.
 */
public class NO {
    private static final int STARTING_FLOOR = 1;

    private final int numberOfFloors;
    private int currentNumberFloor;
    private Floor currentFloor;
    private final IGameRepository gameRepository;

    /**
     * Starts a new NO tower run
     */
    public NO(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
        int floors = gameRepository.getNumbersOfFloors();
        if (floors <= 0) {
            List<FloorDefinition> all = gameRepository.getAllFloors();
            floors = all == null ? 0 : all.size();
        }
        this.numberOfFloors = Math.max(1, floors);
        this.currentNumberFloor = STARTING_FLOOR;
        setupFloor();
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public int getCurrentFloorNumber() {
        return currentNumberFloor;
    }

    public Room getCurrentRoom() {
        return currentFloor == null ? null : currentFloor.getCurrentRoom();
    }

    public int getCurrentRoomNumber() {
        return currentFloor == null ? 0 : currentFloor.getCurrentRoomNumber();
    }

    public int getTotalRoomCount() {
        return currentFloor == null ? 0 : currentFloor.getNumberOfRooms();
    }

    public int getEnemyRoomCount() {
        return currentFloor == null ? 0 : currentFloor.getEnemyRoomCount();
    }

    public List<Room> getAllRooms() {
        return currentFloor == null ? List.of() : currentFloor.getAllRooms();
    }

    public void goToNextRoom() {
        if (currentFloor != null) {
            currentFloor.goToNextRoom();
        }
    }

    public Team getCurrentEnemyTeam() {
        Room currentRoom = getCurrentRoom();
        return currentRoom == null ? null : currentRoom.getEnemyTeam();
    }

    public boolean isCurrentRoomBossRoom() {
        Room currentRoom = getCurrentRoom();
        return currentRoom != null && currentRoom.isBossRoom();
    }

    public boolean hasCurrentRoomAfoBugemon() {
        Room currentRoom = getCurrentRoom();
        return currentRoom != null && currentRoom.hasAfoBugemon();
    }

    /**
     * Advances to the next floor when the current one is cleared.
     */
    public void goToNextFloor() {
        if (currentNumberFloor < numberOfFloors) {
            currentNumberFloor += 1;
            setupFloor();
        } else {
            throw new IllegalStateException("Cannot advance past the last floor (current: " + currentNumberFloor + ", total: " + numberOfFloors + ").");
        }
    }

    /**
     * Rebuilds the current floor from the repository-backed definition.
     */
    private void setupFloor() {
        FloorDefinition floorDefinition = gameRepository.getFloorByNumber(currentNumberFloor).orElse(null);
        if (floorDefinition == null) {
            currentFloor = null;
            return;
        }
        List<Team> enemyTeams = floorDefinition.enemyTeams();
        Team bossTeam = floorDefinition.bossTeam();
        currentFloor = new Floor(currentNumberFloor, enemyTeams, bossTeam, floorDefinition.bossName(), floorDefinition.bossSpritePath());
    }
}
