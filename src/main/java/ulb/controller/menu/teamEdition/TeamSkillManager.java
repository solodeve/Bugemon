package ulb.controller.menu.teamEdition;

import java.util.Optional;

import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.view.menu.teamEdition.TeamEditionView;

 
/**
 * Handles skill equip and unequip actions triggered by the team edition view.
 *
 * <p><b>Role in the architecture:</b> this class is a delegation target of
 * {@link TeamEditionController}. It owns exactly one responsibility — applying
 * a skill toggle to the correct Bugemon and keeping the view consistent
 * with the result.</p>
 *
 * <p><b>Decoupling pattern:</b> instead of receiving a reference to
 * {@link TeamDataLoader}, this class accepts a {@link Runnable} {@code saveCallback}.
 * This follows the decoupling principle from the course: the handler does not
 * need to know <em>how</em> saving works, only <em>that</em> it should happen
 * after a successful toggle. Swapping the persistence strategy requires no change
 * here.</p>
 *
 * <p>Package-private: only {@link TeamEditionController} may instantiate this class.</p>
 */

public class TeamSkillManager {

    private final TeamEditionView view;
    private final BugemonPreviewBuilder previewBuilder;
    private final Runnable saveCallback;

    /**
     * @param saveCallback called after any successful equip/unequip to persist the change
     */
    TeamSkillManager(TeamEditionView view, BugemonPreviewBuilder previewBuilder, Runnable saveCallback) {
        this.view = view;
        this.previewBuilder = previewBuilder;
        this.saveCallback = saveCallback;
    }

    /**
     * Entry point called by the controller's {@code onSkillToggled} handler.
     *
     * @param skillId                 ID of the skill that was toggled
     * @param isEquipped              {@code true} if the skill should be equipped
     * @param selectedTeamBugemon     the Bugemon currently selected in the team slot (may be null)
     * @param selectedAvailableBugemon the Bugemon highlighted in the available grid (may be null)
     */
    void handle(String skillId, boolean isEquipped,
                Bugemon selectedTeamBugemon, Bugemon selectedAvailableBugemon) {
        if (selectedTeamBugemon == null) {
            handleSkillToggleWithoutTeam(selectedAvailableBugemon);
            return;
        }
        Optional<Skill> target = selectedTeamBugemon.getKnownSkills().stream()
                .filter(s -> s.getId().equals(skillId)).findFirst();
        if (target.isEmpty()) return;
        applySkillToggle(target.get(), isEquipped, selectedTeamBugemon);
        previewBuilder.buildPreviewData(selectedTeamBugemon)
                .ifPresentOrElse(view::showSelectedBugemon, view::clearSelectedBugemonPreview);
    }

    private void handleSkillToggleWithoutTeam(Bugemon selectedAvailableBugemon) {
        if (selectedAvailableBugemon != null) {
            previewBuilder.buildPreviewData(selectedAvailableBugemon)
                    .ifPresentOrElse(view::showSelectedBugemon, view::clearSelectedBugemonPreview);
            view.showStatus(TeamEditionView.Status.ADD_TO_TEAM_TO_EDIT_ATTACKS);
            return;
        }
        view.showStatus(TeamEditionView.Status.SELECT_TEAM_BUGEMON_FOR_SKILLS);
    }

    private void applySkillToggle(Skill target, boolean isEquipped, Bugemon selectedTeamBugemon) {
        if (isEquipped) {
            if (!selectedTeamBugemon.equipSkill(target)) {
                view.showStatus(TeamEditionView.Status.TOO_MANY_ATTACKS);
            } else {
                saveCallback.run();
            }
        } else {
            selectedTeamBugemon.unequipSkill(target);
            saveCallback.run();
        }
    }
}