package ulb.service.team;

import ulb.model.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * In-memory container and manager for a collection of {@link ulb.model.team.Team} objects.
 *
 * <p>Provides CRUD-like operations on teams and
 * on their Bugemon members. It enforces a maximum number of teams
 * ({@link #MAX_TEAMS}).</p>
 */
public final class TeamManagement {

    private final List<Team> teams = new ArrayList<>();
    private static final int MAX_TEAMS = 5;

    public TeamManagement() {
    }

    public List<Team> getTeams() {
        return List.copyOf(teams);
    }

    public int teamSize() {
        return teams.size();
    }

    public Team getTeamByIndex(int index) {
        return teams.get(index);
    }

    public boolean isTeamEmpty(int index) {
        return isEmptyTeam(getTeamByIndex(index));
    }

    public Optional<Team> getTeamByName(String teamName) {
        if (teamName == null) {
            return Optional.empty();
        }

        String name = teamName.trim();
        for (Team team : teams) {
            if (hasName(team, name)) {
                return Optional.of(team);
            }
        }
        return Optional.empty();
    }

    public void renameTeamByIndex(int index, String newName) {
        Team team = getTeamByIndex(index);
        team.setName(newName);
    }

    public String getTeamNameByIndex(int index) {
        Team team = getTeamByIndex(index);
        return team == null ? null : team.getName();
    }

    public boolean teamHasName(int index, String name) {
        Team team = getTeamByIndex(index);
        return hasName(team, name);
    }

    public void createTeamByName(String name) {
        addTeam(new Team(name));
    }

    public boolean addTeam(Team team) {
        if (!canAddTeam(team)) {
            return false;
        }
        teams.add(team);
        return true;
    }

    public boolean removeTeamByName(String name) {
        return getTeamByName(name).map(teams::remove).orElse(false);
    }

    public void removeTeamByIndex(int index) {
        teams.remove(index);
    }

    public void setTeams(List<Team> newTeams) {
        teams.clear();
        if (newTeams != null) {
            newTeams.forEach(this::addTeam);
        }
    }

    private boolean canAddTeam(Team team) {
        return team != null && teams.size() < MAX_TEAMS && !teams.contains(team);
    }

    private boolean isEmptyTeam(Team team) {
        return team == null || team.isEmpty();
    }

    private boolean hasName(Team team, String name) {
        return team != null && Objects.equals(team.getName(), name);
    }
}
