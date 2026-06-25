package ulb.service.leaderboard;

import ulb.model.leaderboard.LeaderboardEntry;
import ulb.model.leaderboard.TowerRunStat;
import ulb.model.team.Team;
import ulb.model.user.User;
import ulb.repository.IGameRepository;

import java.util.Comparator;
import java.util.List;

/**
 * Service for managing the game leaderboard.
 * 
 * Handles leaderboard entry creation, scoring calculations, and persistence.
 * Maintains an in-memory cache of leaderboard entries loaded from the repository.
 */
public class Leaderboard {
    private final IGameRepository gameRepository;
    private List<LeaderboardEntry> leaderboardEntries;

    public Leaderboard(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
        load();
    }

    public int calculatePoint(TowerRunStat runStat) {
        return 2*(runStat.floorReached()-1) + 2*runStat.combatsWon() +
                runStat.bugemonsDefeated() - 2*runStat.bugemonsLost() + 5*runStat.flawlessFloors();
    }

    /**
     * Adds a run to the leaderboard, keeping only the best score per player.
     * Persists the entry only when it improves the stored score.
     */
    public void addEntry(User user, Team team, TowerRunStat runStat) {
        int score = calculatePoint(runStat);
        User entryUser = snapshotUser(user);
        LeaderboardEntry leaderboardEntry = new LeaderboardEntry(entryUser, team, score, runStat);

        if (upsertEntry(leaderboardEntry)) {
            save(leaderboardEntry);
        }
    }

    public List<LeaderboardEntry> getEntries() {
        return List.copyOf(leaderboardEntries);
    }

    private void save(LeaderboardEntry leaderboardEntry) {
        gameRepository.saveLeaderboardEntry(leaderboardEntry);
    }

    private void load() {
        this.leaderboardEntries = gameRepository.loadLeaderboard();
    }

    private boolean upsertEntry(LeaderboardEntry newEntry) {
        LeaderboardEntry bestExisting = findBestEntryForUser(newEntry.user());
        if (bestExisting == null) {
            addAndSort(newEntry);
            return true;
        }
        if (!isBetterScore(newEntry, bestExisting)) {
            keepBestEntry(bestExisting);
            return false;
        }
        replaceEntry(newEntry);
        return true;
    }

    private LeaderboardEntry findBestEntryForUser(User user) {
        LeaderboardEntry bestExisting = null;
        for (LeaderboardEntry entry : leaderboardEntries) {
            if (isSameUser(entry.user(), user) && (bestExisting == null || entry.score() > bestExisting.score())) {
                bestExisting = entry;
            }
        }
        return bestExisting;
    }

    private boolean isBetterScore(LeaderboardEntry candidate, LeaderboardEntry bestExisting) {
        return candidate.score() > bestExisting.score();
    }

    private void keepBestEntry(LeaderboardEntry bestExisting) {
        removeEntriesForUser(bestExisting.user());
        addAndSort(bestExisting);
    }

    private void replaceEntry(LeaderboardEntry entry) {
        removeEntriesForUser(entry.user());
        addAndSort(entry);
    }

    private void removeEntriesForUser(User user) {
        leaderboardEntries.removeIf(entry -> isSameUser(entry.user(), user));
    }

    private void addAndSort(LeaderboardEntry entry) {
        leaderboardEntries.add(entry);
        sortEntries();
    }

    private void sortEntries() {
        leaderboardEntries.sort(Comparator.comparingInt(LeaderboardEntry::score).reversed());
    }

    private User snapshotUser(User user) {
        if (user == null) {
            return null;
        }
        return new User(user.getId(), user.getUsername());
    }

    private boolean isSameUser(User left, User right) {
        if (left == null || right == null) {
            return false;
        }
        Integer leftId = left.getId();
        Integer rightId = right.getId();
        if (leftId != null && rightId != null) {
            return leftId.equals(rightId);
        }
        String leftName = left.getUsername();
        String rightName = right.getUsername();
        return leftName != null && leftName.equals(rightName);
    }
}
