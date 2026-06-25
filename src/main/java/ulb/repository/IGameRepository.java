package ulb.repository;

import ulb.exception.TeamPersistenceException;
import ulb.model.domain.Bugemon;
import ulb.model.leaderboard.LeaderboardEntry;
import ulb.model.team.Team;
import ulb.model.user.User;

import java.util.List;

/**
 * Composite repository for game session data: catalog, floors, unlock state, teams, and leaderboard.
 */
public interface IGameRepository extends IBugemonCatalogRepository, IFloorRepository, IUnlockRepository {

    User getUser();

    String getCurrentUsername();

    /** Unlocks all non-special Bugemons present in the given team and returns the newly unlocked ones. */
    List<Bugemon> unlockBugemonsFromTeam(Team team);

    /**
     * Unlocks special Bugemons tied to clearing a specific NO tower floor.
     *
     * @param floorNumber the floor that was just cleared
     * @return the list of newly unlocked Bugemons; empty if none apply
     */
    List<Bugemon> unlockBugemonsOnFloorClear(int floorNumber);

    void saveTeams(Team team, String teamType) throws TeamPersistenceException;

    void saveLeaderboardEntry(LeaderboardEntry leaderboardEntry);

    List<LeaderboardEntry> loadLeaderboard();
}
