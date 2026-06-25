package ulb.view.menu.teamEdition;

import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ulb.model.domain.effect.Effect;
import ulb.view.common.SelectionStyle;
import ulb.view.common.SelectionStyleSupport;
import ulb.view.common.SpriteLoader;

/**
 * View dedicated to editing the Bugemons inside one selected team.
 *
 * <p>Row/card construction is delegated to {@link TeamEditionBugemonGrid}.
 * The selected-Bugemon preview is delegated to {@link TeamEditionBugemonPreview}.
 * Skill rendering is delegated to {@link TeamEditionSkillsPanel}.
 * Keyboard navigation and slot wiring are delegated to {@link TeamEditionKeyNav}.</p>
 */
public class TeamEditionView {

    /**
     * @param accessible {@code false} means the Bugemon is still locked â€” its card is
     *                   rendered in greyscale, is not focusable, and click/Enter are not wired.
     */
    public record BugemonCardData(String bugemonId, String displayName, boolean accessible) {}

    public record SkillData(String id, String name, SkillDescriptionData description, boolean isEquipped) {}

    public record SkillDescriptionData(
            String description,
            String element,
            int power,
            float precision,
            int priority,
            boolean magical,
            List<Effect> effects) {}

    public record BugemonPreviewData(
            String bugemonId,
            String name,
            int level,
            boolean showLevel,
            String type,
            boolean domainExpansion,
            boolean transformation,
            int hp, int attack, int defense,
            int magicAttack, int magicDefense, int initiative,
            List<SkillData> skills) {}

    public interface Listener {
        void onAvailableBugemonSelected(int index);
        void onTeamSlotSelected(int index);
        void onAddBugemonRequested();
        void onRemoveBugemonRequested();
        void onBackRequested();
        void onSkillToggled(String skillId, boolean isEquipped);
        boolean isTeamFull();
        boolean isBugemonInTeam(String bugemonId);
    }

    public enum Status {
        CLEAR, GAME_DATA_EMPTY, NO_UNLOCKED_BUGEMONS, LOAD_TEAMS_FAILED,
        SAVE_TEAMS_FAILED, SELECT_TEAM_FIRST, SELECT_AVAILABLE_BUGEMON_FIRST,
        TEAM_FULL, BUGEMON_ALREADY_IN_TEAM, ADD_FAILED, BUGEMON_ADDED,
        SELECT_TEAM_BUGEMON_FIRST, REMOVE_FAILED, BUGEMON_REMOVED,
        SELECT_TEAM_BUGEMON_FOR_SKILLS, ADD_TO_TEAM_TO_EDIT_ATTACKS,
        TOO_MANY_ATTACKS, NO_TEAM_SELECTED, SELECTED_TEAM_NOT_FOUND
    }

    private static final SelectionStyle TEAM_SLOT_STYLE =
            new SelectionStyle("team-slot-base", "team-slot-selected", "team-slot-focused");

    @FXML private Label teamNameLabel;
    @FXML private Label statusLabel;
    @FXML private Label teamSlotLabel;
    @FXML private ImageView selectedBugemonImage;
    @FXML private Label selectedBugemonName;
    @FXML private Label selectedBugemonType;
    @FXML private Label selectedBugemonDomainExpansionTitle;
    @FXML private Label selectedBugemonDomainExpansion;
    @FXML private Label selectedBugemonTransformationTitle;
    @FXML private Label selectedBugemonTransformation;
    @FXML private Label selectedBugemonHp;
    @FXML private Label selectedBugemonAttack;
    @FXML private Label selectedBugemonDefense;
    @FXML private Label selectedBugemonMagicAttack;
    @FXML private Label selectedBugemonMagicDefense;
    @FXML private Label selectedBugemonInitiative;
    @FXML private VBox selectedBugemonSkillsContainer;

    @FXML private Button attacksDescriptionBackButton;
    @FXML private ScrollPane attacksScrollPane;
    @FXML private VBox attacksDescriptionPane;
    @FXML private Label attacksDescriptionTitle;
    @FXML private Label attacksDescriptionText;

    @FXML private ScrollPane availableBugemonsScrollPane;
    @FXML private FlowPane availableBugemonsContainer;
    @FXML private Button backButton;

    @FXML private StackPane slotPane1, slotPane2, slotPane3, slotPane4, slotPane5, slotPane6;
    @FXML private ImageView slotImage1, slotImage2, slotImage3, slotImage4, slotImage5, slotImage6;

    private Listener listener;
    private int selectedAvailableCardIndex = -1;
    private int selectedTeamSlotIndex      = -1;

    private final SpriteLoader spriteLoader = new SpriteLoader();

    private TeamEditionSkillsPanel  skillsPanel;
    private TeamEditionBugemonPreview bugemonPreview;
    private TeamEditionBugemonGrid  bugemonGrid;
    private TeamEditionKeyNav       keyNav;

    @FXML
        void initialize() {
        skillsPanel = new TeamEditionSkillsPanel(
                selectedBugemonSkillsContainer, attacksScrollPane, attacksDescriptionPane,
                attacksDescriptionTitle, attacksDescriptionText, () -> listener);

        bugemonPreview = new TeamEditionBugemonPreview(
                selectedBugemonImage, selectedBugemonName, selectedBugemonType,
                selectedBugemonDomainExpansionTitle, selectedBugemonDomainExpansion,
                selectedBugemonTransformationTitle, selectedBugemonTransformation,
                selectedBugemonHp, selectedBugemonAttack, selectedBugemonDefense,
                selectedBugemonMagicAttack, selectedBugemonMagicDefense, selectedBugemonInitiative,
                spriteLoader, skillsPanel::render);

        keyNav = new TeamEditionKeyNav(
                getSlotPanes(),
                () -> bugemonGrid != null ? bugemonGrid.getCards()           : List.of(),
                () -> bugemonGrid != null ? bugemonGrid.getCenterSlot()       : null,
                () -> bugemonGrid != null ? bugemonGrid.getCurrentBugemons()  : List.of(),
                backButton, availableBugemonsScrollPane, availableBugemonsContainer,
                () -> listener, this::updateTeamSlotStyle);

        bugemonGrid = new TeamEditionBugemonGrid(
                availableBugemonsContainer, availableBugemonsScrollPane,
                () -> listener, spriteLoader,
                keyNav::handleAvailableCardNavigation,
                keyNav::focusAvailableCard,
                () -> keyNav.focusSlot(getSlotPanes().size() - 1),
                () -> selectedAvailableCardIndex);

        keyNav.configureTeamSlots();
        keyNav.configureActionButtons();
        Platform.runLater(keyNav::focusInitialControl);
    }

    public void setListener(Listener listener) { this.listener = listener; }

    public void setTeamName(String name) {
        String display = name == null || name.isBlank() ? "No team selected" : name;
        teamNameLabel.setText(name == null || name.isBlank() ? "EDIT TEAM" : "EDIT TEAM : " + name);
        teamSlotLabel.setText("Bugemons of " + display);
    }

    public void refreshStatus(String msg) { statusLabel.setText(msg == null ? "" : msg); }

    public void showStatus(Status status) { showStatus(status, null); }

    public void showStatus(Status status, String detail) {
        Status s = status == null ? Status.CLEAR : status;
        refreshStatus(switch (s) {
            case CLEAR                          -> "";
            case GAME_DATA_EMPTY                -> "Cannot load game data: repository is empty.";
            case NO_UNLOCKED_BUGEMONS           -> "Cannot load playable bugemons: none are unlocked yet.";
            case LOAD_TEAMS_FAILED              -> "Cannot load saved teams: "   + safe(detail);
            case SAVE_TEAMS_FAILED              -> "Cannot save teams: "         + safe(detail);
            case SELECT_TEAM_FIRST              -> "Select a team first.";
            case SELECT_AVAILABLE_BUGEMON_FIRST -> "Click on a bugemon image first.";
            case TEAM_FULL                      -> "Team is already full.";
            case BUGEMON_ALREADY_IN_TEAM        -> "This bugemon is already in the team.";
            case ADD_FAILED                     -> "Cannot add bugemon.";
            case BUGEMON_ADDED                  -> "Bugemon added.";
            case SELECT_TEAM_BUGEMON_FIRST      -> "Click on a bugemon already in the team first.";
            case REMOVE_FAILED                  -> "Cannot remove bugemon.";
            case BUGEMON_REMOVED                -> "Bugemon removed.";
            case SELECT_TEAM_BUGEMON_FOR_SKILLS -> "Select a bugemon from your team first.";
            case ADD_TO_TEAM_TO_EDIT_ATTACKS    -> "Add this bugemon to your team to edit its attacks.";
            case TOO_MANY_ATTACKS               -> "Cannot equip more than 3 attacks.";
            case NO_TEAM_SELECTED               -> "No team selected.";
            case SELECTED_TEAM_NOT_FOUND        -> "Selected team not found.";
        });
    }

    public void showAvailableBugemons(List<BugemonCardData> bugemons)  { bugemonGrid.show(bugemons); }
    public void showSelectedBugemon(BugemonPreviewData bugemon) { bugemonPreview.show(bugemon); }
    public void setPreviewGreyscale(boolean greyscale) { bugemonPreview.setGreyscale(greyscale); }
    public void clearSelectedBugemonPreview() { bugemonPreview.clear(); }

    public void clearTeamMembersSelection() {
        resetTeamSlotHighlight();
        clearSelectedBugemonPreview();
    }

    public void highlightSelectedAvailableCard(int index) {
        selectedAvailableCardIndex = index;
        bugemonGrid.highlight(index);
    }

    public void resetAvailableCardHighlight() { highlightSelectedAvailableCard(-1); }

    public void highlightSelectedTeamSlot(int index) {
        selectedTeamSlotIndex = index;
        List<StackPane> slots = getSlotPanes();
        for (int i = 0; i < slots.size(); i++) updateTeamSlotStyle(i);
    }

    public void resetTeamSlotHighlight() { highlightSelectedTeamSlot(-1); }

    public void refreshSlots(List<String> members) {
        List<ImageView> images = getSlotImages();
        images.forEach(img -> img.setImage(null));
        for (int i = 0; i < members.size() && i < images.size(); i++) {
            String id = members.get(i);
            if (id != null) images.get(i).setImage(spriteLoader.tryLoadSprite(id.toLowerCase() + ".png").orElse(null));
        }
    }

    @FXML void onBack() { if (listener != null) listener.onBackRequested(); }
    @FXML void onSkillDescriptionBack() { skillsPanel.backToList(); }

    private void updateTeamSlotStyle(int index) {
        List<StackPane> slots = getSlotPanes();
        if (index < 0 || index >= slots.size()) return;
        SelectionStyleSupport.applyStyle(slots.get(index), index, selectedTeamSlotIndex, TEAM_SLOT_STYLE);
    }

    private List<StackPane> getSlotPanes() {
        return Arrays.asList(slotPane1, slotPane2, slotPane3, slotPane4, slotPane5, slotPane6);
    }

    private List<ImageView> getSlotImages() {
        return Arrays.asList(slotImage1, slotImage2, slotImage3, slotImage4, slotImage5, slotImage6);
    }

    private String safe(String s) { return s == null ? "" : s; }
}
