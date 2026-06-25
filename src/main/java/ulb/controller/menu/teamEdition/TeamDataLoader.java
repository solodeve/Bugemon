package ulb.controller.menu.teamEdition;

import java.util.ArrayList;
import java.util.List;

import ulb.exception.TeamPersistenceException;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Element;
import ulb.repository.GameRepository;
import ulb.repository.ITeamRepository;
import ulb.service.team.TeamManagement;
import ulb.view.menu.teamEdition.TeamEditionView;

/**
 * Handles all persistence operations for the team edition workflow.
 *
 * <p><b>Role in the architecture:</b> this class is a delegation target of
 * {@link TeamEditionController}. It owns exactly one responsibility — loading
 * and saving data — which maps directly to the Repository layer described in
 * the course. The controller calls its methods and uses the return values to
 * update its own state; this class never reads or writes controller state
 * directly.</p>
 *
 * <p><b>Error reporting:</b> when a persistence operation fails, this class
 * reports the error through {@link TeamEditionView#showStatus} instead of
 * throwing, so the controller does not need to handle checked exceptions
 * for display concerns.</p>
 *
 * <p>Package-private: only {@link TeamEditionController} may instantiate this class.</p>
 */

public class TeamDataLoader {

    private final GameRepository gameRepository;
    private final ITeamRepository teamRepository;
    private final TeamManagement teamManagement;
    private final BugemonPreviewBuilder previewBuilder;
    private final TeamEditionView view;

    TeamDataLoader(GameRepository gameRepository,
                   ITeamRepository teamRepository,
                   TeamManagement teamManagement,
                   BugemonPreviewBuilder previewBuilder,
                   TeamEditionView view) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.teamManagement = teamManagement;
        this.previewBuilder = previewBuilder;
        this.view = view;
    }

    /**
     * Loads all available (starter) Bugemon from the game repository.
     *
     * <p>Filters out Bugemon of element {@link Element#ALL}, which are not valid
     * for team composition. After filtering, checks whether at least one Bugemon
     * is accessible to the current player:</p>
     * <ul>
     *   <li>If the repository is empty → {@link TeamEditionView.Status#GAME_DATA_EMPTY}</li>
     *   <li>If no Bugemon is unlocked → {@link TeamEditionView.Status#NO_UNLOCKED_BUGEMONS}</li>
     * </ul>
     *
     * <p>Package-private: called only by {@link TeamEditionController#initialize()}.</p>
     *
     * @return the list of available Bugemon at level 1; never {@code null},
     *         may be empty if the repository contains nothing
     */

    List<Bugemon> loadGameData() {
        final int BASE_LEVEL = 1;
        List<Bugemon> all = new ArrayList<>(gameRepository.getAllBugemonsAtLevel(BASE_LEVEL).stream()
                .filter(b -> b.getElement() != Element.ALL).toList());
        boolean hasAccessible = all.stream().anyMatch(previewBuilder::isBugemonAccessible);
        if (all.isEmpty()) { view.showStatus(TeamEditionView.Status.GAME_DATA_EMPTY); }
        else if (!hasAccessible) { view.showStatus(TeamEditionView.Status.NO_UNLOCKED_BUGEMONS); }
        return all;
    }

    /**
     * Loads previously saved teams from the repository into the in-memory
     * {@link TeamManagement} instance.
     *
     * <p>Package-private: called only by {@link TeamEditionController#initialize()}.</p>
     */

    void loadSavedTeams() {
        try {
            teamManagement.setTeams(teamRepository.loadTeams(gameRepository));
        } catch (TeamPersistenceException e) {
            view.showStatus(TeamEditionView.Status.LOAD_TEAMS_FAILED, e.getMessage());
        }
    }

    /**
     * Persists all current teams to the repository.
     *
     * <p>Displays an error status message if a {@link TeamPersistenceException} occurs.</p>
     *
     * @return {@code true} if saving succeeded; {@code false} if an error occurred
     */
    boolean saveTeams() {
        try {
            teamRepository.saveTeams(new ArrayList<>(teamManagement.getTeams()));
            return true;
        } catch (TeamPersistenceException e) {
            view.showStatus(TeamEditionView.Status.SAVE_TEAMS_FAILED, e.getMessage());
            return false;
        }
    }
}