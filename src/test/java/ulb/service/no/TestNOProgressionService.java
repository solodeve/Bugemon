package ulb.service.no;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Element;
import ulb.model.domain.Level;
import ulb.model.domain.Statistics;
import ulb.model.no.FloorDefinition;
import ulb.model.no.NO;
import ulb.model.team.Team;
import ulb.repository.IGameRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestNOProgressionService {

    private IGameRepository repository;
    private Team playerTeam;
    private NOProgressionService service;

    @BeforeEach
    void setup() {
        repository = mock(IGameRepository.class);
        playerTeam = new Team("Player");
        playerTeam.add(bugemon("p1", Element.PLANT));
        service = new NOProgressionService();
    }

    // helpers

    private static Bugemon bugemon(String id, Element element) {
        Bugemon b = new Bugemon(id, "B-" + id, element,
                new Statistics(50, 10, 10, 10, 10, 10), "sprite.png", false);
        b.setLevel(new Level(10, 0));
        return b;
    }

    private static Team team(String name, String id, Element element) {
        Team t = new Team(name);
        t.add(bugemon(id, element));
        return t;
    }

    private NO noWithOneFloor(Team enemyTeam, Team bossTeam) {
        FloorDefinition floor = new FloorDefinition(
                1, List.of(enemyTeam), bossTeam, "Boss", "boss.png");
        when(repository.getAllFloors()).thenReturn(List.of(floor));
        when(repository.getFloorByNumber(1)).thenReturn(Optional.of(floor));
        return new NO(repository);
    }

    private NO noWithTwoFloors(Team enemy1, Team boss1, Team enemy2, Team boss2) {
        FloorDefinition f1 = new FloorDefinition(1, List.of(enemy1), boss1, "Boss1", "b1.png");
        FloorDefinition f2 = new FloorDefinition(2, List.of(enemy2), boss2, "Boss2", "b2.png");
        when(repository.getAllFloors()).thenReturn(List.of(f1, f2));
        when(repository.getFloorByNumber(1)).thenReturn(Optional.of(f1));
        when(repository.getFloorByNumber(2)).thenReturn(Optional.of(f2));
        return new NO(repository);
    }

    // loss

    @Test
    void loss_returnsEndRunWithFloorNumber() {
        NO no = noWithOneFloor(
                team("Enemy", "e1", Element.FIRE),
                team("Boss",  "b1", Element.PLANT));

        NOProgressionResult result = service.processBattleResult(false, no, repository, playerTeam);

        assertEquals(NOProgressionResult.NextState.END_RUN, result.nextState());
        assertEquals(NOBattleOutcome.RUN_LOST, result.outcome());
        assertEquals(1, result.floorNumber());
    }

    // AFO boss (Element.ALL)

    @Test
    void win_afoRoom_returnsEndRun() {
        Team afoTeam = team("AFO", "afo", Element.ALL);
        NO no = noWithOneFloor(team("Enemy", "e1", Element.FIRE), afoTeam);
        // advance to boss room
        no.getCurrentFloor().goToNextRoom();
        clearInvocations(repository);

        NOProgressionResult result = service.processBattleResult(true, no, repository, playerTeam);

        assertEquals(NOProgressionResult.NextState.END_RUN, result.nextState());
        assertEquals(NOBattleOutcome.AFO_BLOCKED, result.outcome());
        verifyNoInteractions(repository);
    }

    // enemy room win

    @Test
    void win_enemyRoom_unlocksBugemonsAndAdvancesRoom() {
        Team enemyTeam = team("Enemy", "e1", Element.FIRE);
        NO no = noWithOneFloor(enemyTeam, team("Boss", "b1", Element.PLANT));

        int roomBefore = no.getCurrentFloor().getCurrentRoomNumber();
        NOProgressionResult result = service.processBattleResult(true, no, repository, playerTeam);

        assertEquals(NOProgressionResult.NextState.CONTINUE_TOWER, result.nextState());
        assertEquals(roomBefore + 1, no.getCurrentFloor().getCurrentRoomNumber());
        verify(repository).unlockBugemonsFromTeam(enemyTeam);
    }

    // boss room win — last floor (tower conquered)

    @Test
    void win_bossRoom_lastFloor_returnsEndRun() {
        Team bossTeam = team("Boss", "b1", Element.PLANT);
        NO no = noWithOneFloor(team("Enemy", "e1", Element.FIRE), bossTeam);
        no.getCurrentFloor().goToNextRoom(); // move to boss room

        NOProgressionResult result = service.processBattleResult(true, no, repository, playerTeam);

        assertEquals(NOProgressionResult.NextState.END_RUN, result.nextState());
        assertEquals(NOBattleOutcome.TOWER_CONQUERED, result.outcome());
        verify(repository).unlockBugemonsFromTeam(bossTeam);
        verify(repository, never()).unlockBugemonsOnFloorClear(anyInt());
    }

    // boss room win — not last floor (floor cleared)

    @Test
    void win_bossRoom_notLastFloor_advancesFloorAndRestoresHp() {
        Team boss1 = team("Boss1", "b1", Element.PLANT);
        NO no = noWithTwoFloors(
                team("E1", "e1", Element.FIRE), boss1,
                team("E2", "e2", Element.ICE),  team("Boss2", "b2", Element.PLANT));
        no.getCurrentFloor().goToNextRoom(); // move to boss room of floor 1

        int hpBefore = playerTeam.getMembers().getFirst().getHp();
        // damage the player bugemon to verify restore
        Bugemon member = playerTeam.getMembers().getFirst();
        member.setHp(member.getHp() - 10);

        NOProgressionResult result = service.processBattleResult(true, no, repository, playerTeam);

        assertEquals(NOProgressionResult.NextState.CONTINUE_TOWER, result.nextState());
        assertEquals(2, no.getCurrentFloorNumber());
        verify(repository).unlockBugemonsFromTeam(boss1);
        verify(repository).unlockBugemonsOnFloorClear(1);
        assertEquals(hpBefore, playerTeam.getMembers().getFirst().getHp());
    }
}
