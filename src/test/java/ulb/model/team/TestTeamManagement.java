package ulb.model.team;

import org.junit.jupiter.api.Test;
import ulb.service.team.TeamManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTeamManagement {
    private TeamManagement teamManagement;

    @Test
    void checkIfCountIncrease() {
        teamManagement = new TeamManagement();

        teamManagement.createTeamByName("A");
        assertEquals(1, teamManagement.teamSize());
    }

    @Test
    void checkIfCountDecrease() {
        teamManagement = new TeamManagement();

        teamManagement.createTeamByName("A");
        teamManagement.createTeamByName("B");
        teamManagement.removeTeamByIndex(0);
        assertEquals(1, teamManagement.teamSize());
    }

    @Test
    void checkRenameTeam() {
        teamManagement = new TeamManagement();

        teamManagement.createTeamByName("A");
        teamManagement.renameTeamByIndex(0, "B");
        assertEquals("B", teamManagement.getTeamNameByIndex(0));
    }

    @Test
    void checkIfTeamIsRemoved() {
        teamManagement = new TeamManagement();

        teamManagement.createTeamByName("A");
        teamManagement.removeTeamByIndex(0);

        assertEquals(0, teamManagement.teamSize());
    }

    @Test
    void checkIfTeamIsRemovedByName() {
        teamManagement = new TeamManagement();

        teamManagement.createTeamByName("A");
        teamManagement.removeTeamByName("A");

        assertEquals(0, teamManagement.teamSize());
    }

    @Test
    void shouldRefuseMoreThanFiveTeams() {
        teamManagement = new TeamManagement();

        for (int i = 0; i < 5; i++) {
            assertTrue(teamManagement.addTeam(new Team("T" + i)));
        }

        assertFalse(teamManagement.addTeam(new Team("TooMuch")));
        assertEquals(5, teamManagement.teamSize());
    }

}
