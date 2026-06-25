package ulb.repository;

import ulb.model.leaderboard.LeaderboardEntry;

import java.util.List;

/** Contract for leaderboard persistence. */
public interface ILeaderboardRepository {

    void saveEntry(int playerId, LeaderboardEntry entry);
    List<LeaderboardEntry> loadTopEntries();
}
