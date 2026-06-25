package ulb.view.menu.teamEdition;

import java.util.List;
import java.util.function.Consumer;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.Node;
import ulb.view.common.SpriteLoader;

/**
 * Manages the selected-Bugemon preview panel for {@link TeamEditionView}.
 *
 * <p>Displays name, sprite, stats and forwards skill data to the skills panel via
 * a {@code Consumer<List<SkillData>>} callback, keeping this class independent of
 * {@link TeamEditionSkillsPanel}.</p>
 */
class TeamEditionBugemonPreview {

    private static final int LOCKED_SATURATION  = -1;
    private static final double LOCKED_OPACITY  = 0.55;

    private final ImageView bugemonImage;
    private final Label bugemonName;
    private final Label bugemonType;
    private final Label domainExpansionTitle;
    private final Label domainExpansion;
    private final Label transformationTitle;
    private final Label transformation;
    private final Label hp;
    private final Label attack;
    private final Label defense;
    private final Label magicAttack;
    private final Label magicDefense;
    private final Label initiative;
    private final SpriteLoader spriteLoader;
    private final Consumer<List<TeamEditionView.SkillData>> onSkillsChanged;

    TeamEditionBugemonPreview(
            ImageView bugemonImage,
            Label bugemonName,
            Label bugemonType,
            Label domainExpansionTitle,
            Label domainExpansion,
            Label transformationTitle,
            Label transformation,
            Label hp, Label attack, Label defense,
            Label magicAttack, Label magicDefense, Label initiative,
            SpriteLoader spriteLoader,
            Consumer<List<TeamEditionView.SkillData>> onSkillsChanged) {
        this.bugemonImage         = bugemonImage;
        this.bugemonName          = bugemonName;
        this.bugemonType          = bugemonType;
        this.domainExpansionTitle = domainExpansionTitle;
        this.domainExpansion      = domainExpansion;
        this.transformationTitle  = transformationTitle;
        this.transformation       = transformation;
        this.hp                   = hp;
        this.attack               = attack;
        this.defense              = defense;
        this.magicAttack          = magicAttack;
        this.magicDefense         = magicDefense;
        this.initiative           = initiative;
        this.spriteLoader         = spriteLoader;
        this.onSkillsChanged      = onSkillsChanged;
    }

    void show(TeamEditionView.BugemonPreviewData bugemon) {
        if (bugemon == null) { clear(); return; }
        bugemonImage.setEffect(null);
        bugemonImage.setOpacity(1.0);
        bugemonName.setText(formatName(bugemon));
        bugemonImage.setImage(spriteLoader.tryLoadSprite(bugemon.bugemonId().toLowerCase() + ".png").orElse(null));
        updateStats(bugemon);
    }

    void setGreyscale(boolean greyscale) {
        if (greyscale) {
            ColorAdjust grey = new ColorAdjust();
            grey.setSaturation(LOCKED_SATURATION);
            bugemonImage.setEffect(grey);
            bugemonImage.setOpacity(LOCKED_OPACITY);
        } else {
            bugemonImage.setEffect(null);
            bugemonImage.setOpacity(1.0);
        }
    }

    void clear() {
        bugemonName.setText("No bugemon selected");
        bugemonImage.setImage(null);
        bugemonImage.setEffect(null);
        bugemonImage.setOpacity(1.0);
        setText(bugemonType, "-");
        updateDomainExpansionLabels("");
        updateTransformationLabels("");
        setText(hp, "-");
        setText(attack, "-");
        setText(defense, "-");
        setText(magicAttack, "-");
        setText(magicDefense, "-");
        setText(initiative, "-");
        onSkillsChanged.accept(List.of());
    }

    private void updateStats(TeamEditionView.BugemonPreviewData b) {
        setText(bugemonType, b.type());
        updateDomainExpansionLabels(b.domainExpansion() ? "Yes" : "No");
        updateTransformationLabels(b.transformation() ? "Yes" : "No");
        setText(hp,           Integer.toString(b.hp()));
        setText(attack,       Integer.toString(b.attack()));
        setText(defense,      Integer.toString(b.defense()));
        setText(magicAttack,  Integer.toString(b.magicAttack()));
        setText(magicDefense, Integer.toString(b.magicDefense()));
        setText(initiative,   Integer.toString(b.initiative()));
        onSkillsChanged.accept(b.skills());
    }

    private String formatName(TeamEditionView.BugemonPreviewData b) {
        return b.showLevel() ? b.name() + " Lvl: " + b.level() : b.name();
    }

    private void updateDomainExpansionLabels(String value) {
        setVisible(domainExpansionTitle);
        setVisible(domainExpansion);
        domainExpansion.setText(value == null || value.isBlank() ? "No" : value);
    }

    private void updateTransformationLabels(String value) {
        setVisible(transformationTitle);
        setVisible(transformation);
        transformation.setText(value == null || value.isBlank() ? "No" : value);
    }

    private void setText(Label label, String value) {
        label.setText(value == null ? "-" : value);
    }

    private void setVisible(Node node) {
        if (node == null) return;
        node.setVisible(true);
        node.setManaged(true);
    }
}

