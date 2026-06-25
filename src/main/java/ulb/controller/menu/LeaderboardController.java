package ulb.controller.menu;

import ulb.controller.ApplicationController;
import ulb.model.leaderboard.LeaderboardEntry;
import ulb.model.leaderboard.TowerRunStat;
import ulb.model.team.Team;
import ulb.model.user.User;
import ulb.service.leaderboard.Leaderboard;
import ulb.view.menu.LeaderboardView;

import java.util.List;

/**
 * Manages leaderboard data and coordinates between the leaderboard service and its view.
 */
public final class LeaderboardController implements LeaderboardView.LeaderboardListener {
    private final Leaderboard leaderboard;
    private final ApplicationController applicationController;
    private LeaderboardView leaderboardView;

    public LeaderboardController(Leaderboard leaderboard, ApplicationController applicationController) {
        this.applicationController = applicationController;
        this.leaderboard = leaderboard;
    }

    /**
     * Records a completed NO tower run. Does nothing if {@code user} or {@code runStat} is {@code null}.
     */
    public void recordNoTowerRun(User user, Team playerTeam, TowerRunStat runStat) {
        if (user == null || runStat == null) {
            return;
        }
        leaderboard.addEntry(user, playerTeam, runStat);
    }

    public void setLeaderboardView(LeaderboardView leaderboardView) {
        this.leaderboardView = leaderboardView;
    }

    public List<LeaderboardEntry> getEntries() {
        return leaderboard.getEntries();
    }

    /**
     * Pushes current entries to the leaderboard view. Does nothing if no view is attached.
     */
    public void populateLeaderboard() {
        if (leaderboardView == null) return;
        leaderboardView.displayEntries(getEntries());
    }

    @Override
    public void onBackClicked() {
        applicationController.returnToMainMenu();
    }
}
