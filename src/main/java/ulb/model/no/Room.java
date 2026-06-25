package ulb.model.no;

import ulb.model.team.Team;

/**
 * Represents a room within a certain floor.
 */
public class Room {
    public enum RoomType {
        ENEMY,
        BOSS
    }

    private final int roomNumber;
    private final Team enemyTeam;
    private boolean isUnlock;
    private final RoomType roomType;
    private final String displayName;
    private final String displaySpritePath;

    public Room(int roomNumber, Team enemyTeam, RoomType roomType, String displayName, String displaySpritePath) {
        this.enemyTeam = enemyTeam;
        this.roomNumber = roomNumber;
        this.roomType = roomType == null ? RoomType.ENEMY : roomType;
        this.displayName = displayName == null || displayName.isBlank()
                ? defaultDisplayName(enemyTeam, this.roomType, roomNumber)
                : displayName;
        this.displaySpritePath = displaySpritePath == null ? "" : displaySpritePath;
    }

    public int getRoomNumber() { return roomNumber; }
    public Team getEnemyTeam() { return enemyTeam; }
    public RoomType getRoomType() { return roomType; }
    public boolean isBossRoom() { return roomType == RoomType.BOSS; }
    public boolean hasAfoBugemon() { return enemyTeam != null && enemyTeam.hasAfoBugemon(); }
    public String getDisplayName() { return displayName; }
    public String getDisplaySpritePath() { return displaySpritePath; }
    public boolean isUnlocked() { return isUnlock; }

    public void unlockRoom() {
        isUnlock = true;
    }

    private String defaultDisplayName(Team enemyTeam, RoomType roomType, int roomNumber) {
        if (roomType == RoomType.BOSS) {
            String teamName = enemyTeam == null ? "" : enemyTeam.getName();
            return teamName == null || teamName.isBlank() ? "Boss" : teamName;
        }

        if (roomType == RoomType.ENEMY) {
            String teamName = enemyTeam == null ? "" : enemyTeam.getName();
            return teamName == null || teamName.isBlank() ? "Enemy" : teamName;
        }

        return "Enemy " + roomNumber;
    }

    public String getPreviewSprite() {
        if (displaySpritePath != null && !displaySpritePath.isBlank()) {
            return displaySpritePath;
        }

        return enemyTeam == null ? null : enemyTeam.getLeadSprite();
    }
}
