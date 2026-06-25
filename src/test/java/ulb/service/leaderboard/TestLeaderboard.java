package ulb.service.leaderboard;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ulb.model.leaderboard.LeaderboardEntry;
import ulb.model.leaderboard.TowerRunStat;
import ulb.model.team.Team;
import ulb.model.user.User;
import ulb.repository.IGameRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestLeaderboard {

    @Test
    void scoreDoesNotRewardStartingFloorAndAppliesAllRunFactors() {
        Leaderboard leaderboard = leaderboardWithLoadedEntries(List.of());

        TowerRunStat runStat = new TowerRunStat(
                4,
                7,
                13,
                2,
                3
        );

        int score = leaderboard.calculatePoint(runStat);

        assertEquals(44, score);
    }

    @Test
    void constructorLoadsExistingEntriesWithoutSavingThemAgain() {
        IGameRepository repository = mock(IGameRepository.class);
        LeaderboardEntry existingEntry = new LeaderboardEntry(
                new User(10, "existing"),
                new Team("Loaded team"),
                25,
                new TowerRunStat(2, 3, 4, 0, 1)
        );
        when(repository.loadLeaderboard()).thenReturn(new ArrayList<>(List.of(existingEntry)));

        Leaderboard leaderboard = new Leaderboard(repository);

        assertEquals(List.of(existingEntry), leaderboard.getEntries());
        verify(repository, never()).saveLeaderboardEntry(existingEntry);
    }

    @Test
    void addEntryBuildsSavedEntryFromProvidedUserTeamAndRunStat() {
        IGameRepository repository = mock(IGameRepository.class);
        when(repository.loadLeaderboard()).thenReturn(new ArrayList<>());
        Leaderboard leaderboard = new Leaderboard(repository);
        User player = new User(42, "player");
        Team team = new Team("NO team");
        TowerRunStat runStat = new TowerRunStat(3, 5, 8, 1, 2);

        leaderboard.addEntry(player, team, runStat);

        ArgumentCaptor<LeaderboardEntry> entryCaptor = ArgumentCaptor.forClass(LeaderboardEntry.class);
        verify(repository).saveLeaderboardEntry(entryCaptor.capture());
        LeaderboardEntry savedEntry = entryCaptor.getValue();

        assertEquals(player.getId(), savedEntry.user().getId());
        assertEquals(player.getUsername(), savedEntry.user().getUsername());
        assertSame(team, savedEntry.team());
        assertSame(runStat, savedEntry.runStat());
        assertEquals(30, savedEntry.score());
        assertEquals(List.of(savedEntry), leaderboard.getEntries());
    }

    @Test
    void addEntrySkipsLowerScoreForSameUser() {
        IGameRepository repository = mock(IGameRepository.class);
        User player = new User(42, "player");
        LeaderboardEntry existingEntry = new LeaderboardEntry(
                player,
                new Team("Loaded team"),
                20,
                new TowerRunStat(2, 3, 4, 0, 1)
        );
        when(repository.loadLeaderboard()).thenReturn(new ArrayList<>(List.of(existingEntry)));
        Leaderboard leaderboard = new Leaderboard(repository);

        leaderboard.addEntry(player, new Team("Other team"), new TowerRunStat(1, 0, 0, 0, 0));

        assertEquals(List.of(existingEntry), leaderboard.getEntries());
        verify(repository, never()).saveLeaderboardEntry(any());
    }

    @Test
    void addEntryReplacesEntryForSameUserWithHigherScore() {
        IGameRepository repository = mock(IGameRepository.class);
        User player = new User(42, "player");
        LeaderboardEntry existingEntry = new LeaderboardEntry(
                player,
                new Team("Loaded team"),
                10,
                new TowerRunStat(2, 1, 1, 0, 0)
        );
        when(repository.loadLeaderboard()).thenReturn(new ArrayList<>(List.of(existingEntry)));
        Leaderboard leaderboard = new Leaderboard(repository);
        Team team = new Team("NO team");
        TowerRunStat runStat = new TowerRunStat(3, 5, 8, 1, 2);

        leaderboard.addEntry(player, team, runStat);

        ArgumentCaptor<LeaderboardEntry> entryCaptor = ArgumentCaptor.forClass(LeaderboardEntry.class);
        verify(repository).saveLeaderboardEntry(entryCaptor.capture());
        LeaderboardEntry savedEntry = entryCaptor.getValue();

        assertEquals(player.getId(), savedEntry.user().getId());
        assertEquals(player.getUsername(), savedEntry.user().getUsername());
        assertSame(team, savedEntry.team());
        assertSame(runStat, savedEntry.runStat());
        assertEquals(30, savedEntry.score());
        assertEquals(List.of(savedEntry), leaderboard.getEntries());
    }

    @Test
    void exposedEntriesCannotBeMutatedByCallers() {
        LeaderboardEntry existingEntry = new LeaderboardEntry(
                new User(7, "player"),
                new Team("Team"),
                12,
                new TowerRunStat(1, 2, 3, 0, 1)
        );
        Leaderboard leaderboard = leaderboardWithLoadedEntries(new ArrayList<>(List.of(existingEntry)));

        List<LeaderboardEntry> entries = leaderboard.getEntries();

        assertThrows(UnsupportedOperationException.class, () -> entries.add(existingEntry));
        assertEquals(List.of(existingEntry), leaderboard.getEntries());
    }

    private static Leaderboard leaderboardWithLoadedEntries(List<LeaderboardEntry> entries) {
        IGameRepository repository = mock(IGameRepository.class);
        when(repository.loadLeaderboard()).thenReturn(new ArrayList<>(entries));
        return new Leaderboard(repository);
    }
}
