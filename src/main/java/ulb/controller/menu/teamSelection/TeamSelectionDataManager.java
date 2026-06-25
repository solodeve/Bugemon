package ulb.controller.menu.teamSelection;

import ulb.exception.TeamPersistenceException;
import ulb.model.team.Team;
import ulb.repository.ITeamRepository;
import ulb.service.team.TeamManagement;

/**
 * Helper responsible for persisting team-selection changes.
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Save team changes.</li>
 *   <li>Execute persistence operations with rollback support.</li>
 * </ul>
 *
 * <p>This class isolates persistence logic from
 * TeamSelectionController.</p>
 */
class TeamSelectionDataManager {

    private final ITeamRepository repository;
    private final TeamManagement teamManagement;

    TeamSelectionDataManager(
            ITeamRepository repository,
            TeamManagement teamManagement) {

        this.repository = repository;
        this.teamManagement = teamManagement;
    }

    /**
     * A checked-exception-friendly functional interface used to wrap
     * persistence calls so they can be passed as lambdas to {@link #persistWithRollback}.
    */
    @FunctionalInterface
    interface PersistAction {
        void execute() throws TeamPersistenceException;
    }

    /**
     * Executes the given persistence action and restores the previous
     * in-memory state if an error occurs.
     *
     * @param action operation to execute
     * @param rollback action restoring the previous state
     *
     * @return {@code true} if persistence succeeded
     *
     * @throws TeamPersistenceException if persistence fails
     */
    boolean persistWithRollback(
            PersistAction action,
            Runnable rollback)
            throws TeamPersistenceException {

        try {
            action.execute();
            return true;
        } catch (TeamPersistenceException e) {
            rollback.run();
            throw e;
        }
    }

    /**
     * Persists the current team collection using the configured repository.
     *
     * @throws TeamPersistenceException if saving fails
     */
    void saveTeams() throws TeamPersistenceException {
        repository.saveTeams(teamManagement.getTeams());
    }
    /**
     * Persists a newly created team.
     *
     * <p>If persistence fails, the created team is removed
     * from memory.</p>
     *
     * @param teamName name of the created team
     * @return {@code true} if persistence succeeded
     * @throws TeamPersistenceException if saving fails
     */
    boolean persistCreation(String teamName)
            throws TeamPersistenceException {

        return persistWithRollback(
                this::saveTeams,
                () -> teamManagement.removeTeamByName(teamName)
        );
    }
    /**
     * Persists a team rename operation.
     *
     * <p>If persistence fails, the previous team name
     * is restored.</p>
     *
     * @param index index of the renamed team
     * @param oldName previous team name
     * @return {@code true} if persistence succeeded
     * @throws TeamPersistenceException if saving fails
     */

    boolean persistRename(
            int index,
            String oldName)
            throws TeamPersistenceException {

        return persistWithRollback(
                this::saveTeams,
                () -> teamManagement.renameTeamByIndex(index, oldName)
        );
    }
    
    /**
     * Persists a team deletion.
     *
     * <p>If persistence fails, the deleted team
     * is restored.</p>
     *
     * @param deletedTeam removed team backup
     * @return {@code true} if persistence succeeded
     * @throws TeamPersistenceException if saving fails
     */
    boolean persistDeletion(
            Team deletedTeam)
            throws TeamPersistenceException {

        return persistWithRollback(
                this::saveTeams,
                () -> teamManagement.addTeam(deletedTeam)
        );
    }
}