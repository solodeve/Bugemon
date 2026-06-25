package ulb.controller.menu.teamEdition;

import java.util.*;
import ulb.controller.ApplicationController;
import ulb.model.domain.*;
import ulb.repository.GameRepository;
import ulb.repository.TeamRepository;
import ulb.repository.ITeamRepository;
import ulb.model.team.Team;
import ulb.service.team.TeamManagement;
import ulb.view.menu.teamEdition.TeamEditionView;

/**
 * Controller responsible for the team creation and management workflow.
 *
 * <p>This controller drives the team management screen, allowing the player to:</p>
 * <ul>
 *   <li>Create, rename, and delete teams.</li>
 *   <li>Add or remove Bugemon from a selected team.</li>
 *   <li>Browse the list of available (starter) Bugemon.</li>
 *   <li>Persist all changes to persistent storage via the configured team repository.</li>
 * </ul>
 *
 * <p>Persistence is delegated to {@link TeamDataLoader}, view updates to
 * {@link TeamSlotRefresher}, and skill-toggle logic to {@link TeamSkillManager},
 * following the cohesion principle described in the course.</p>
 *
 * <p><b>State managed by this controller:</b></p>
 * <ul>
 *   <li>{@link #selectedTeam} – the team currently shown in the detail pane.</li>
 *   <li>{@link #selectedAvailableBugemon} – the Bugemon highlighted in the
 *       "available" card grid (to be added to a team).</li>
 *   <li>{@link #selectedTeamBugemon} – the Bugemon highlighted in the
 *       current team's slot grid (to be removed or inspected).</li>
 * </ul>
 */
public final class TeamEditionController implements TeamEditionView.Listener {

    private final TeamEditionView view;
    private final TeamManagement teamManagement;
    private final ApplicationController applicationController;
    private final String initialTeamName;
    private final GameMode gameMode;
    private final BugemonPreviewBuilder previewBuilder;

    private final TeamDataLoader dataLoader;
    private final TeamSlotRefresher viewRefresher;
    private final TeamSkillManager skillHandler;

    private List<Bugemon> availableBugemons;
    private Team selectedTeam;
    private Bugemon selectedAvailableBugemon;
    private Bugemon selectedTeamBugemon;

    public TeamEditionController(TeamEditionView view, GameRepository gameRepository,
                                 ApplicationController applicationController,
                                 String initialTeamName, GameMode gameMode) {
        this.view = view;
        this.applicationController = applicationController;
        this.initialTeamName = initialTeamName;
        this.gameMode = gameMode == null ? GameMode.FREE_BATTLE : gameMode;
        this.view.setListener(this);
        this.teamManagement = new TeamManagement();
        this.previewBuilder = new BugemonPreviewBuilder(gameRepository, this.gameMode);
        ITeamRepository teamRepository = new TeamRepository(this.gameMode.name(), gameRepository.getCurrentUserId());
        this.dataLoader = new TeamDataLoader(gameRepository, teamRepository, teamManagement, previewBuilder, view);
        this.viewRefresher = new TeamSlotRefresher(view, previewBuilder);
        this.skillHandler = new TeamSkillManager(view, previewBuilder, dataLoader::saveTeams);
        this.availableBugemons = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        availableBugemons = dataLoader.loadGameData();
        dataLoader.loadSavedTeams();
        view.showAvailableBugemons(previewBuilder.buildAvailableBugemonCards(availableBugemons));
        view.clearSelectedBugemonPreview();
        showTeam(resolveInitialTeam().orElse(null));
        if (selectedTeam != null) {
            view.showStatus(TeamEditionView.Status.CLEAR);
        }
    }

    /**
     * Updates the view to display the given team's details, resetting all selections.
     *
     * @param team the team to show; {@code null} to clear the detail pane
     */
    private void showTeam(Team team) {
        selectedTeam = team;
        selectedAvailableBugemon = null;
        if (team == null) {
            view.setTeamName("");
            selectedTeamBugemon = null;
            viewRefresher.refreshSlots(null, null);
            viewRefresher.clearSelectionState();
            return;
        }
        view.setTeamName(team.getName());
        selectedTeamBugemon = team.getFirstMember().orElse(null);
        viewRefresher.refreshSlots(selectedTeam, selectedTeamBugemon);
        viewRefresher.updatePreview(selectedTeamBugemon);
        view.resetAvailableCardHighlight();
    }

    /**
     * Handles the "Add Bugemon" action triggered by the view.
     *
     * <p>Validates that a team is selected, that an available Bugemon is highlighted,
     * that the team is not already full, and that the Bugemon is not already a member
     * (checked by ID via {@link #isBugemonInList(List, String)}). On success, adds the
     * Bugemon to the team, persists the change, and updates the view.</p>
     */
    public void handleAddBugemon() {
        if (selectedTeam == null) { view.showStatus(TeamEditionView.Status.SELECT_TEAM_FIRST); return; }
        if (selectedAvailableBugemon == null) { view.showStatus(TeamEditionView.Status.SELECT_AVAILABLE_BUGEMON_FIRST); return; }
        if (selectedTeam.isFull()) { view.showStatus(TeamEditionView.Status.TEAM_FULL); return; }
        if (isBugemonInList(viewRefresher.getMembers(selectedTeam), selectedAvailableBugemon.getId())) {
            view.showStatus(TeamEditionView.Status.BUGEMON_ALREADY_IN_TEAM); return;
        }
        if (!tryAddSelectedBugemonToTeam()) { return; }
        finalizeBugemonAddition();
    }

    private boolean tryAddSelectedBugemonToTeam() {
        if (!selectedTeam.add(selectedAvailableBugemon)) {
            view.showStatus(TeamEditionView.Status.ADD_FAILED);
            return false;
        }
        if (!dataLoader.saveTeams()) {
            selectedTeam.remove(selectedAvailableBugemon);
            return false;
        }
        return true;
    }

    private void finalizeBugemonAddition() {
        selectedTeamBugemon = selectedAvailableBugemon;
        selectedAvailableBugemon = null;
        viewRefresher.refreshSlots(selectedTeam, selectedTeamBugemon);
        view.highlightSelectedTeamSlot(viewRefresher.getSelectedTeamMemberNames(selectedTeam).indexOf(selectedTeamBugemon.getName()));
        view.resetAvailableCardHighlight();
        view.showStatus(TeamEditionView.Status.BUGEMON_ADDED);
    }

    /**
     * Handles the "Remove Bugemon" action triggered by the view.
     */
    public void handleRemoveBugemon() {
        if (selectedTeam == null) { view.showStatus(TeamEditionView.Status.SELECT_TEAM_FIRST); return; }
        if (selectedTeamBugemon == null) { view.showStatus(TeamEditionView.Status.SELECT_TEAM_BUGEMON_FIRST); return; }
        if (!selectedTeam.remove(selectedTeamBugemon)) { view.showStatus(TeamEditionView.Status.REMOVE_FAILED); return; }
        if (!dataLoader.saveTeams()) { selectedTeam.add(selectedTeamBugemon); return; }
        finalizeBugemonRemoval();
    }


    private void finalizeBugemonRemoval() {
        selectedTeamBugemon = null;
        view.clearSelectedBugemonPreview();
        view.clearTeamMembersSelection();
        viewRefresher.refreshSlots(selectedTeam, null);
        view.resetTeamSlotHighlight();
        view.showStatus(TeamEditionView.Status.BUGEMON_REMOVED);
    }
    /**
     * Called by the view when the player clicks on a card in the "available" Bugemon grid.
     *
     * <p>Validates the index, sets {@link #selectedAvailableBugemon}, shows its preview
     * in the view, highlights its card, and resets any team slot highlight.</p>
     *
     * @param index zero-based index of the selected card in {@link #availableBugemons}
    */
    @Override
    public void onAvailableBugemonSelected(int index) {
        if (index < 0 || index >= availableBugemons.size()) return;
        Bugemon bugemon = availableBugemons.get(index);
        selectedTeamBugemon = null;
        view.highlightSelectedAvailableCard(index);
        view.resetTeamSlotHighlight();
        view.clearTeamMembersSelection();
        if (!previewBuilder.isBugemonAccessible(bugemon)) {
            selectedAvailableBugemon = null;
            previewBuilder.buildPreviewData(bugemon).ifPresentOrElse(
                    data -> { view.showSelectedBugemon(data); view.setPreviewGreyscale(true); },
                    view::clearSelectedBugemonPreview); return;}
        selectedAvailableBugemon = bugemon;
        previewBuilder.buildPreviewData(selectedAvailableBugemon)
                .ifPresentOrElse(view::showSelectedBugemon, view::clearSelectedBugemonPreview);
    }

    /**
     * Called by the view when the player clicks on a slot in the selected team's slot grid.
     *
     * <p>Validates the index against the selected team's member list. Sets
     * {@link #selectedTeamBugemon}, shows its preview in the view, highlights its slot,
     * resets the available card highlight, and selects the member by name in the list.</p>
     *
     * @param index zero-based index of the clicked slot in the selected team's member list
     */
    @Override
    public void onTeamSlotSelected(int index) {
        List<Bugemon> members = viewRefresher.getMembers(selectedTeam);
        if (index < 0 || index >= members.size()) return;
        selectedTeamBugemon = members.get(index);
        selectedAvailableBugemon = null;
        previewBuilder.buildPreviewData(selectedTeamBugemon)
                .ifPresentOrElse(view::showSelectedBugemon, view::clearSelectedBugemonPreview);
        view.highlightSelectedTeamSlot(index);
        view.resetAvailableCardHighlight();
    }

    @Override
    public void onSkillToggled(String skillId, boolean isEquipped) {
        skillHandler.handle(skillId, isEquipped, selectedTeamBugemon, selectedAvailableBugemon);
    }

    @Override public void onAddBugemonRequested()    { handleAddBugemon(); }
    @Override public void onRemoveBugemonRequested() { handleRemoveBugemon(); }
    @Override public void onBackRequested()          { handleBack(); }

    public void handleBack() {
        if (applicationController != null) applicationController.openTeamSelection();
    }

    @Override
    public boolean isTeamFull() {
        return selectedTeam != null && selectedTeam.isFull();
    }

    @Override
    public boolean isBugemonInTeam(String bugemonId) {
        return selectedTeam != null && isBugemonInList(viewRefresher.getMembers(selectedTeam), bugemonId);
    }

    /**
     * Checks whether a Bugemon with the given ID is present in the provided list.
     *
     * @param bugemonList the list to search; must not be {@code null}
     * @param bugemonId   the ID to look up
     * @return {@code true} if a Bugemon with the given ID is found; {@code false} otherwise
     */
    public boolean isBugemonInList(List<Bugemon> bugemonList, String bugemonId) {
        return bugemonList.stream().anyMatch(b -> Objects.equals(b.getId(), bugemonId));
    }

    private Optional<Team> resolveInitialTeam() {
        if (initialTeamName == null || initialTeamName.isBlank()) {
            view.showStatus(TeamEditionView.Status.NO_TEAM_SELECTED);
            return Optional.empty();
        }
        Optional<Team> team = teamManagement.getTeamByName(initialTeamName);
        if (team.isEmpty()) view.showStatus(TeamEditionView.Status.SELECTED_TEAM_NOT_FOUND);
        return team;
    }
}