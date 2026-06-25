package ulb.view.battle.helper;

import javafx.scene.Node;
import javafx.scene.control.Button;
import ulb.controller.SoundController;
import ulb.model.domain.Element;

import java.util.function.IntConsumer;

import ulb.view.battle.BattleView;
import ulb.view.battle.state.BattleSkillsState;
import ulb.view.common.FocusHighlight;

/**
 * Manages the two mutually exclusive button panels on the battle action bar:
 * the main menu (Attack / Team / Items / Give up) and the attack sub-menu (Skill 1–3 / Return).
 * Showing one panel automatically hides the other. The special button is independent and toggled
 * separately via {@link #setSpecialVisible}.
 */
public class BattleButtonManager {

    private final Button attackButton;
    private final Button teamButton;
    private final Button itemsButton;
    private final Button specialButton;
    private final Button giveUpButton;
    private final Button returnButton;
    private final Button skill1Button;
    private final Button skill2Button;
    private final Button skill3Button;

    public BattleButtonManager(
            Button attackButton, Button teamButton,  Button itemsButton,
            Button specialButton, Button giveUpButton, Button returnButton,
            Button skill1Button, Button skill2Button, Button skill3Button) {
        this.attackButton = attackButton;
        this.teamButton = teamButton;
        this.itemsButton = itemsButton;
        this.specialButton = specialButton;
        this.giveUpButton = giveUpButton;
        this.returnButton = returnButton;
        this.skill1Button = skill1Button;
        this.skill2Button = skill2Button;
        this.skill3Button = skill3Button;
    }

    /** Wires all button actions to the listener, playing a click sound before each event. */
    public void bindActions(BattleView.Listener listener, IntConsumer skillSelector) {
        bindAction(attackButton,  listener::onAttackClicked);
        bindAction(teamButton,    listener::onTeamClicked);
        bindAction(itemsButton,   listener::onItemsClicked);
        bindAction(specialButton, listener::onSpecialClicked);
        bindAction(giveUpButton,  listener::onGiveUpClicked);
        bindAction(returnButton,  listener::onReturnClicked);
        bindAction(skill1Button,  () -> skillSelector.accept(0));
        bindAction(skill2Button,  () -> skillSelector.accept(1));
        bindAction(skill3Button,  () -> skillSelector.accept(2));
    }

    public void applyColors() {
        applyButtonClass(attackButton, "btn-battle-attack");
        applyButtonClass(teamButton, "btn-battle-team");
        applyButtonClass(itemsButton, "btn-battle-items");
        applyButtonClass(giveUpButton, "btn-battle-giveup");
        applyButtonClass(returnButton, "btn-battle-return");
    }

    public void setDisabled(boolean disabled) {
        setDisable(attackButton, disabled);
        setDisable(teamButton, disabled);
        setDisable(itemsButton, disabled);
        setDisable(specialButton, disabled);
        setDisable(giveUpButton, disabled);
        setDisable(skill1Button, disabled);
        setDisable(skill2Button, disabled);
        setDisable(skill3Button, disabled);
        setDisable(returnButton, disabled);
    }

    public void showMainButtons(boolean show) {
        setButtonDisplayed(attackButton, show);
        setButtonDisplayed(teamButton, show);
        setButtonDisplayed(itemsButton, show);
        setButtonDisplayed(giveUpButton, show);

        if (show) {
            setButtonDisplayed(skill1Button, false);
            setButtonDisplayed(skill2Button, false);
            setButtonDisplayed(skill3Button, false);
            setButtonDisplayed(returnButton, false);
        }
    }

    public void setSpecialVisible(boolean visible) {
        setButtonDisplayed(specialButton, visible);
    }

    public void showAttackButtons(boolean show) {
        setButtonDisplayed(skill1Button, show && hasValidText(skill1Button));
        setButtonDisplayed(skill2Button, show && hasValidText(skill2Button));
        setButtonDisplayed(skill3Button, show && hasValidText(skill3Button));
        setButtonDisplayed(returnButton, show);

        if (show) {
            setButtonDisplayed(attackButton, false);
            setButtonDisplayed(teamButton, false);
            setButtonDisplayed(itemsButton, false);
            setButtonDisplayed(specialButton, false);
            setButtonDisplayed(giveUpButton, false);
        }
    }

    public void updateSkills(BattleSkillsState skillsState) {
        BattleSkillsState safe = skillsState == null ? BattleSkillsState.empty() : skillsState;
        configureSkill(skill1Button, safe.skill1Name());
        configureSkill(skill2Button, safe.skill2Name());
        configureSkill(skill3Button, safe.skill3Name());
    }

    public void updateSkillColors(Element e1, Element e2, Element e3) {
        applySkillColor(skill1Button, e1);
        applySkillColor(skill2Button, e2);
        applySkillColor(skill3Button, e3);
    }

    public boolean isMainMenuVisible() {
        return attackButton != null && attackButton.isVisible();
    }

    public boolean isAttackMenuVisible() {
        return returnButton != null && returnButton.isVisible();
    }

    public boolean isDisabled() {
        return attackButton != null && attackButton.isDisable();
    }

    /**
     * Sets both {@code visible} and {@code managed} so that hidden nodes do not consume
     * layout space. Also used by {@link BattleView} for non-button nodes (XP bar, special bar).
     */
    public static void setNodeDisplayed(Node node, boolean displayed) {
        if (node == null) return;
        node.setVisible(displayed);
        node.setManaged(displayed);
    }

    private void configureSkill(Button button, String name) {
        if (button == null) return;
        boolean usable = name != null && !name.isBlank() && !name.equals("-");
        button.setText(usable ? name : "-");
        button.setDisable(!usable);
    }

    private void applySkillColor(Button button, Element element) {
        if (button == null) return;
        button.getStyleClass().removeIf(c -> c.startsWith("skill-"));
        if (element == null) return;
        String skillClass = switch (element) {
            case FIRE   -> "skill-fire";
            case ICE    -> "skill-ice";
            case PLANT  -> "skill-plant";
            case MECHA  -> "skill-mecha";
            case LIGHT  -> "skill-light";
            case SHADOW -> "skill-shadow";
            case ALL    -> "skill-all";
        };
        button.getStyleClass().add(skillClass);
        FocusHighlight.apply(button);
    }

    private void bindAction(Button button, Runnable action) {
        if (button != null) {
            button.setOnAction(event -> {
                SoundController.getInstance().playButtonClick();
                action.run();
            });
        }
    }

    private void applyButtonClass(Button button, String styleClass) {
        if (button != null) button.getStyleClass().add(styleClass);
    }

    private void setButtonDisplayed(Button button, boolean displayed) {
        if (button == null) return;
        button.setVisible(displayed);
        button.setManaged(displayed);
    }

    private void setDisable(Button button, boolean disabled) {
        if (button != null) button.setDisable(disabled);
    }

    private boolean hasValidText(Button button) {
        if (button == null) return false;
        String text = button.getText();
        return text != null && !text.isBlank() && !text.equals("-");
    }
}
