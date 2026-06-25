package ulb.controller.menu.teamEdition;

import java.util.List;

import ulb.model.domain.Bugemon;
import ulb.model.team.Team;
import ulb.view.menu.teamEdition.TeamEditionView;


/**
 * Handles all view-refresh and slot-management operations for the team edition screen.
 *
 * <p><b>Role in the architecture:</b> this class is a delegation target of
 * {@link TeamEditionController}. It owns exactly one responsibility — translating
 * the controller's current selection state into concrete view calls. Every method
 * receives the relevant state as parameters; this class holds no mutable state of
 * its own.</p>
 *
 * <p><b>Keeping view-refresh methods here prevents
 * the controller from becoming a mix of orchestration logic and low-level UI calls,
 * which is the "low cohesion" anti-pattern.</p>
 *
 * <p>Package-private: only {@link TeamEditionController} may instantiate this class.</p>
 */

class TeamSlotRefresher {

    private final TeamEditionView view;
    private final BugemonPreviewBuilder previewBuilder;

    TeamSlotRefresher(TeamEditionView view, BugemonPreviewBuilder previewBuilder) {
        this.view = view;
        this.previewBuilder = previewBuilder;
    }

    List<Bugemon> getMembers(Team team) {
        return team == null ? List.of() : team.getMembers();
    }

    /**
     * Returns the names of all members in the currently selected team.
     *
     * @return a list of Bugemon names in order; empty if no team is selected
    */
    List<String> getSelectedTeamMemberNames(Team team) {
        return getMembers(team).stream().map(Bugemon::getName).toList();
    }
    List<String> getSelectedTeamMemberIds(Team team) {
        return getMembers(team).stream().map(Bugemon::getId).toList();
    }

    void clearSelectionState() {
        view.clearSelectedBugemonPreview();
        view.resetAvailableCardHighlight();
        view.resetTeamSlotHighlight();
    }

    /**
     * Shows the before/after stat preview for the given Bugemon,
     * or clears the panel if {@code selectedTeamBugemon} is {@code null}.
     */
    void updatePreview(Bugemon selectedTeamBugemon) {
        if (selectedTeamBugemon == null) {
            view.clearSelectedBugemonPreview();
            return;
        }
        previewBuilder.buildPreviewData(selectedTeamBugemon)
                .ifPresentOrElse(
                        view::showSelectedBugemon,
                        view::clearSelectedBugemonPreview
                );
        view.highlightSelectedTeamSlot(0);
    }

    /**
     * Refreshes the team slot grid and restores the slot highlight for
     * {@code selectedTeamBugemon} if it is set.
     */
    void refreshSlots(Team selectedTeam, Bugemon selectedTeamBugemon) {
        view.refreshSlots(getSelectedTeamMemberIds(selectedTeam));
        if (selectedTeamBugemon != null) {
            highlightSlotByName(selectedTeam, selectedTeamBugemon.getName());
        } else {
            view.resetTeamSlotHighlight();
        }
    }

    /**
     * Highlights the slot corresponding to the team member with the given name.
     *
     * <p>Resets the highlight if the name is {@code null} or not found.</p>
     *
     * @param bugemonName the name of the Bugemon whose slot should be highlighted;
     *                    {@code null} resets the highlight
     */
    void highlightSlotByName(Team selectedTeam, String bugemonName) {
        if (bugemonName == null) {
            view.resetTeamSlotHighlight();
            return;
        }
        int index = getSelectedTeamMemberNames(selectedTeam).indexOf(bugemonName);
        if (index >= 0) view.highlightSelectedTeamSlot(index);
        else view.resetTeamSlotHighlight();
    }
}