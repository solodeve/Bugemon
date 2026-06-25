package ulb.repository;

import ulb.exception.TeamPersistenceException;
import ulb.model.team.Team;

import java.util.List;

/** Contract for team persistence operations. */
public interface ITeamRepository {

    /** Loads all teams of the configured type, resolving Bugemon references via the given catalog. */
    List<Team> loadTeams(IBugemonCatalogRepository catalog) throws TeamPersistenceException;

    /** Persists the given teams, replacing any previously stored teams of the same type. */
    void saveTeams(List<Team> teams) throws TeamPersistenceException;

    /** Deletes the team with the given name for this repository's type. */
    void deleteTeamByName(String name) throws TeamPersistenceException;
}
