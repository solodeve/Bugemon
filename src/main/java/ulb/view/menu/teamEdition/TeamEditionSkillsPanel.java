package ulb.view.menu.teamEdition;

import java.util.List;
import java.util.function.Supplier;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ulb.controller.SoundController;

/**
 * Manages the skill list and skill-description sub-panel for {@link TeamEditionView}.
 *
 * <p>The panel has two states: the skill list (checkboxes + name buttons) and the
 * description detail view. {@link #backToList()} switches back from description to list;
 * it is also called as part of {@link #render} to display freshly loaded skills.</p>
 */
class TeamEditionSkillsPanel {

    private List<TeamEditionView.SkillData> currentSkills = List.of();

    private final VBox skillsContainer;
    private final ScrollPane attacksScrollPane;
    private final VBox attacksDescriptionPane;
    private final Label attacksDescriptionTitle;
    private final Label attacksDescriptionText;
    private final Supplier<TeamEditionView.Listener> listenerProvider;

    TeamEditionSkillsPanel(
            VBox skillsContainer,
            ScrollPane attacksScrollPane,
            VBox attacksDescriptionPane,
            Label attacksDescriptionTitle,
            Label attacksDescriptionText,
            Supplier<TeamEditionView.Listener> listenerProvider) {
        this.skillsContainer = skillsContainer;
        this.attacksScrollPane = attacksScrollPane;
        this.attacksDescriptionPane = attacksDescriptionPane;
        this.attacksDescriptionTitle = attacksDescriptionTitle;
        this.attacksDescriptionText = attacksDescriptionText;
        this.listenerProvider = listenerProvider;
    }

    void render(List<TeamEditionView.SkillData> skills) {
        currentSkills = skills == null ? List.of() : skills;
        backToList();
    }

    /** Switches from the description sub-panel back to the skill list. */
    void backToList() {
        attacksDescriptionPane.setVisible(false);
        attacksDescriptionPane.setManaged(false);
        attacksScrollPane.setVisible(true);
        attacksScrollPane.setManaged(true);

        skillsContainer.getChildren().clear();

        if (currentSkills.isEmpty()) {
            skillsContainer.getChildren().add(createNoSkillLabel());
            return;
        }

        for (TeamEditionView.SkillData skill : currentSkills) {
            if (skill == null) continue;
            skillsContainer.getChildren().add(buildSkillRow(skill));
        }
    }

    private HBox buildSkillRow(TeamEditionView.SkillData skill) {
        CheckBox equipCheck = new CheckBox();
        equipCheck.setSelected(skill.isEquipped());
        equipCheck.setOnAction(e -> {
            TeamEditionView.Listener l = listenerProvider.get();
            if (l != null) l.onSkillToggled(skill.id(), equipCheck.isSelected());
        });

        Button skillButton = new Button(skill.name());
        skillButton.setMaxWidth(Double.MAX_VALUE);
        skillButton.setWrapText(true);
        skillButton.setAlignment(Pos.CENTER_LEFT);
        skillButton.getStyleClass().add("skill-button");
        skillButton.setOnAction(e -> {
            SoundController.getInstance().playButtonClick();
            showDescription(skill);
        });

        HBox row = new HBox(8, equipCheck, skillButton);
        row.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(skillButton, Priority.ALWAYS);
        return row;
    }

    private void showDescription(TeamEditionView.SkillData skill) {
        attacksDescriptionTitle.setText(skill.name());
        attacksDescriptionText.setText(SkillDescriptionFormatter.format(skill.description()));
        attacksScrollPane.setVisible(false);
        attacksScrollPane.setManaged(false);
        attacksDescriptionPane.setVisible(true);
        attacksDescriptionPane.setManaged(true);
    }

    private Label createNoSkillLabel() {
        Label label = new Label("No attack.");
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add("no-skill-label");
        return label;
    }
}

