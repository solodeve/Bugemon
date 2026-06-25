package ulb.controller.menu.teamSelection;

import java.util.List;
import java.util.Optional;
import ulb.controller.ApplicationController;
import ulb.model.domain.GameMode;
import ulb.exception.TeamPersistenceException;
import ulb.repository.GameRepository;
import ulb.repository.TeamRepository;
import ulb.repository.ITeamRepository;
import ulb.model.team.Team;
import ulb.service.team.TeamManagement;
import ulb.view.menu.teamSelection.TeamSelectionView;

/**
 * Controller for the team-selection screen shown before starting a battle.
 *
 * <p>Loads all saved teams from the database on construction and
 * displays them in the view. When the player selects a team and clicks
 * "Start", delegates to {@link ApplicationController#startGameModeWithTeam(Team)}.</p>
 *
 * <p><b>MVC role:</b> Controller. Receives events from {@link TeamSelectionView},
 * reads data from the configured team repository, and navigates via
 * {@link ApplicationController}.</p>
 */
public final class TeamSelectionController implements TeamSelectionView.Listener {
    private final TeamSelectionView view;
    private final ApplicationController applicationController;
    private final GameRepository gameRepository;
    private final TeamManagement teamManagement = new TeamManagement();
    private final ITeamRepository teamDatabaseRepository;
    private final TeamSelectionDataManager dataManager;

    /**
     * The index of the team currently highlighted in the view.
     * {@code -1} means no team is selected.
     */
    private int selectedTeamIndex = -1;

    /**
     * Constructs the controller, wires it as the view's listener, and loads
     * the saved teams.
     *
     * @param view the team-selection view
     * @param applicationController the root controller for navigation
     * @param gameRepository the data source for Bugemon resolution
     * @param gameMode the active game mode used to scope persisted teams
     */
    public TeamSelectionController(TeamSelectionView view, ApplicationController applicationController, GameRepository gameRepository, GameMode gameMode) throws TeamPersistenceException {
        this.view = view;
        this.applicationController = applicationController;
        this.gameRepository = gameRepository;
        
        GameMode effectiveGameMode = gameMode == null ? GameMode.FREE_BATTLE : gameMode;
        this.teamDatabaseRepository = new TeamRepository(effectiveGameMode.name(), gameRepository.getCurrentUserId());
        this.dataManager = new TeamSelectionDataManager(teamDatabaseRepository, teamManagement);
        view.setListener(this);
        loadTeams();
    }

    /**
     * Loads all saved teams from JSON, populates the view, and disables
     * the "Start" button until the player selects a team.
     */
    private void loadTeams() throws TeamPersistenceException {
        List<Team> savedTeams = teamDatabaseRepository.loadTeams(gameRepository);
        teamManagement.setTeams(savedTeams);
        view.showStatus(TeamSelectionView.Status.CLEAR);
        refreshTeamsView();
        clearSelection();
    }

    /** Highlights the team at {@code index} and enables the edit/start buttons accordingly. */
    @Override
    public void onTeamSelected(int index) {
        if (index < 0 || index >= teamManagement.teamSize()) {
            clearSelection();
            return;
        }

        selectedTeamIndex = index;
        view.highlightSelectedTeam(index);
        view.setEditButtonDisabled(false);
        view.showStatus(TeamSelectionView.Status.CLEAR);

        if (!teamManagement.isTeamEmpty(index)) {
            view.setStartButtonDisabled(false);
        }
    }

    /** Normalizes, validates, creates, persists and selects the new team. */
    @Override
    public void onCreateTeamClicked(String name) {
        Optional<String> normalized = normalizeTeamName(name);
        if (normalized.isEmpty()) {
            view.showStatus(TeamSelectionView.Status.TEAM_NAME_REQUIRED);
            return;
        }
        if (!createAndPersistTeam(normalized.get())) {return;}

        view.showStatus(TeamSelectionView.Status.TEAM_CREATED);
        refreshTeamsView();
        selectTeamByName(normalized.get());
    }

    /** Validates, renames, persists the team at {@code index} and refreshes the view. */
    @Override
    public void onRenameTeamClicked(int index, String newName) {
        if (!isValidIndex(index)) {
            showErrorAndRefresh(TeamSelectionView.Status.SELECT_TEAM_FIRST);
            return;
        }
        Optional<String> normalized = normalizeTeamName(newName);
        if (normalized.isEmpty()) {
            showErrorAndRefresh(TeamSelectionView.Status.NEW_NAME_REQUIRED);
            return;
        }
        if (!renameAndPersistTeam(index, normalized.get())) {
            refreshTeamsViewAndRestore();
            return;
        }
        applyRenameSuccess(normalized.get());
    }

    private void applyRenameSuccess(String name) {
        view.showStatus(TeamSelectionView.Status.TEAM_RENAMED);
        refreshTeamsView();
        selectTeamByName(name);
    }

    private boolean createAndPersistTeam(String teamName) {
        Optional<Team> teamByName = teamManagement.getTeamByName(teamName);
        if (teamByName.isPresent()) {
            view.showStatus(TeamSelectionView.Status.TEAM_NAME_ALREADY_EXISTS);
            return false;
        }
        Team team = new Team(teamName);
        if (!teamManagement.addTeam(team)) {
            view.showStatus(TeamSelectionView.Status.MAX_TEAMS_REACHED);
            return false;
        }
        try {return dataManager.persistCreation(teamName);}
        catch (TeamPersistenceException e) {
            view.showStatus(TeamSelectionView.Status.SAVE_FAILED, e.getMessage());
            return false;
        }
    }

    private boolean renameAndPersistTeam(int index, String newName) {
        String oldName = teamManagement.getTeamNameByIndex(index);
        if (!validateTeamRename(oldName, newName)) return false;

        teamManagement.renameTeamByIndex(index, newName);
        try {return dataManager.persistRename(index, oldName);}
        catch (TeamPersistenceException e) {
            view.showStatus(TeamSelectionView.Status.SAVE_FAILED, e.getMessage());
            return false;
        }
    }

    /**
     * Validates that {@code newName} is a legal rename target for a team
     * currently named {@code oldName}.
     * Shows the appropriate status in the view if validation fails.
     *
     * @return {@code true} if the rename is allowed
    */
    private boolean validateTeamRename(String oldName, String newName) {
        if (oldName.equals(newName)) {
            view.showStatus(TeamSelectionView.Status.CLEAR);
            return false;
        }
        if (teamManagement.getTeamByName(newName).isPresent()) {
            view.showStatus(TeamSelectionView.Status.TEAM_NAME_ALREADY_EXISTS);
            return false;
        }
        return true;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < teamManagement.teamSize();
    }

    private void showErrorAndRefresh(TeamSelectionView.Status status) {
        view.showStatus(status);
        refreshTeamsView();
        restoreSelection();
    }

    private void refreshTeamsViewAndRestore() {
        refreshTeamsView();
        restoreSelection();
    }

    /** Removes the team at {@code index} from memory and persists the change. */
    @Override
    public void onDeleteTeamClicked(int index) {
        if (!isValidIndex(index)) {
            view.showStatus(TeamSelectionView.Status.SELECT_TEAM_FIRST);
            return;
        }
        Team backup = teamManagement.getTeamByIndex(index);
        if (!removeTeamFromMemory(index)) return;
        try {
            if (!dataManager.persistDeletion(backup)) {return;}
        } catch (TeamPersistenceException e) {
            view.showStatus(TeamSelectionView.Status.SAVE_FAILED,e.getMessage());
            return;
        }
        refreshTeamsView();
        clearSelection();
        view.showStatus(TeamSelectionView.Status.TEAM_DELETED);
    }

    private boolean removeTeamFromMemory(int index) {
        String deletedName = teamManagement.getTeamNameByIndex(index);
        if (!teamManagement.removeTeamByName(deletedName)) {
            view.showStatus(TeamSelectionView.Status.DELETE_FAILED);
            return false;
        }
        return true;
    }

    @Override
    public void onEditTeamClicked() {
        Optional<Team> selectedTeam = getSelectedTeam();
        if (selectedTeam.isEmpty()) {
            view.showStatus(TeamSelectionView.Status.SELECT_TEAM_FIRST);
            return;
        }

        if (applicationController != null) {
            applicationController.openTeamEditor(selectedTeam.get());
        }
    }

    /**
     * Called when the player clicks "Start Game".
     * Passes the selected team to {@link ApplicationController#startGameModeWithTeam(Team)}.
     */
    @Override
    public void onStartGameClicked() {
        Optional<Team> selectedTeam = getSelectedTeam();
        if (selectedTeam.isEmpty() || applicationController == null) {
            return;
        }
        if (selectedTeam.get().isEmpty()) {
            view.showStatus(TeamSelectionView.Status.EMPTY_TEAM);
            return;
        }
        applicationController.startGameModeWithTeam(selectedTeam.get());
    }

    @Override
    public void onBackClicked() {
        if (applicationController != null) {
            applicationController.returnToMainMenu();
        }
    }

    private void refreshTeamsView() {
        view.displayTeams(teamManagement.getTeams());
    }

    private void clearSelection() {
        selectedTeamIndex = -1;
        view.clearSelectedTeamHighlight();
        view.setStartButtonDisabled(true);
        view.setEditButtonDisabled(true);
    }

    private void restoreSelection() {
        if (selectedTeamIndex >= 0 && selectedTeamIndex < teamManagement.teamSize()) {
            view.highlightSelectedTeam(selectedTeamIndex);
            view.setStartButtonDisabled(teamManagement.isTeamEmpty(selectedTeamIndex));
            view.setEditButtonDisabled(false);
            return;
        }

        clearSelection();
    }

    private Optional<Team> getSelectedTeam() {
        if (selectedTeamIndex < 0 || selectedTeamIndex >= teamManagement.teamSize()) {
            return Optional.empty();
        }

        return Optional.ofNullable(teamManagement.getTeamByIndex(selectedTeamIndex));
    }

    private Optional<String> normalizeTeamName(String rawName) {
        if (rawName == null) {
            return Optional.empty();
        }
        String normalized = rawName.trim();
        return normalized.isEmpty() ? Optional.empty() : Optional.of(normalized);
    }

    private void selectTeamByName(String name) {
        for (int i = 0; i < teamManagement.teamSize(); i++) {
            if (teamManagement.teamHasName(i, name)) {
                onTeamSelected(i);
                return;
            }
        }

        clearSelection();
    }
}

