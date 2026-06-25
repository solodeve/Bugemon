package ulb.model.leaderboard;

import ulb.model.team.Team;
import ulb.model.user.User;

/**
 * Represents an entry in the leaderboard.
 *
 * @param user the player associated with the entry
 * @param team the team used by the player during the run
 * @param score the total score achieved
 * @param runStat the detailed statistics of the associated run
 */
public record LeaderboardEntry(
        User user,
        Team team,
        int score,
        TowerRunStat runStat
) {
    public String getUsername() {
        return user != null ? user.getUsername() : "Unknown";
    }
}