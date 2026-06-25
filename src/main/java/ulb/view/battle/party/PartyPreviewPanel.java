package ulb.view.battle.party;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.model.special.DomainExpansion;
import ulb.view.common.SpriteLoader;

/**
 * Manages the selected-Bugemon preview panel for {@link PartySelectionView}.
 *
 * <p>Renders sprite, name, HP bar, status badge, stats and the three-tab panel
 * (Stats / Attacks / Special). Tab switching is exposed via {@link #showStats()},
 * {@link #showAttacks()}, and {@link #showSpecial()}.</p>
 */
class PartyPreviewPanel {

    private static final float  HP_RATIO_THRESHOLD_YELLOW = 0.5f;
    private static final float  HP_RATIO_THRESHOLD_RED    = 0.2f;
    private static final String TAB_ACTIVE_CLASS          = "tab-active";
    private static final String TAB_INACTIVE_CLASS        = "tab-inactive";
    private static final String TAB_HOVER_CLASS           = "tab-hover";

    private Bugemon previewedBugemon;
    private Function<Bugemon, Optional<DomainExpansion>> domainExpansionResolver =
            b -> Optional.empty();

    private final ImageView   bugemonSprite;
    private final Label       stateBadge;
    private final Label       name;
    private final Label       element;
    private final ProgressBar hpBar;
    private final Label       hp;
    private final Label       status;
    private final Label       attack;
    private final Label       defense;
    private final Label       magicAttack;
    private final Label       magicDefense;
    private final Label       initiative;
    private final VBox        statsPanel;
    private final ScrollPane  attacksPane;
    private final VBox        attacksList;
    private final ScrollPane  specialPane;
    private final VBox        specialList;
    private final Button      statsTabButton;
    private final Button      attacksTabButton;
    private final Button      specialTabButton;
    private final SpriteLoader spriteLoader;

    PartyPreviewPanel(
            ImageView bugemonSprite, Label stateBadge, Label name, Label element,
            ProgressBar hpBar, Label hp, Label status,
            Label attack, Label defense, Label magicAttack, Label magicDefense, Label initiative,
            VBox statsPanel, ScrollPane attacksPane, VBox attacksList,
            ScrollPane specialPane, VBox specialList,
            Button statsTabButton, Button attacksTabButton, Button specialTabButton,
            SpriteLoader spriteLoader) {
        this.bugemonSprite    = bugemonSprite;
        this.stateBadge       = stateBadge;
        this.name             = name;
        this.element          = element;
        this.hpBar            = hpBar;
        this.hp               = hp;
        this.status           = status;
        this.attack           = attack;
        this.defense          = defense;
        this.magicAttack      = magicAttack;
        this.magicDefense     = magicDefense;
        this.initiative       = initiative;
        this.statsPanel       = statsPanel;
        this.attacksPane      = attacksPane;
        this.attacksList      = attacksList;
        this.specialPane      = specialPane;
        this.specialList      = specialList;
        this.statsTabButton   = statsTabButton;
        this.attacksTabButton = attacksTabButton;
        this.specialTabButton = specialTabButton;
        this.spriteLoader     = spriteLoader;
        setupTabHovers();
    }

    void setDomainExpansionResolver(Function<Bugemon, Optional<DomainExpansion>> resolver) {
        this.domainExpansionResolver = resolver == null ? b -> Optional.empty() : resolver;
    }

    /** Sets up the initial tab state and clears the detail panels. */
    void initialRender() {
        showStats();
        renderAttacks(null);
        renderSpecial(null);
    }

    void update(Bugemon bugemon, Bugemon activeBugemon) {
        previewedBugemon = bugemon;
        if (bugemon == null) { clear(); return; }

        int    currentHp = bugemon.getHp();
        int    maxHp     = bugemon.getMaxHp();
        double ratio     = maxHp <= 0 ? 0 : (double) currentHp / maxHp;

        bugemonSprite.setImage(
                spriteLoader.loadSpriteForBugemon(bugemon.getSprite(), bugemon.getElement(), true, false).orElse(null));
        name.setText(bugemon.getName());
        element.setText("Type: " + bugemon.getElementDisplayName());
        hp.setText(currentHp + " / " + maxHp);
        hpBar.setProgress(ratio);
        hpBar.setStyle("-fx-accent: " + resolveHealthColor(ratio) + ";");

        applyBadge(bugemon, activeBugemon);
        attack.setText(String.valueOf(bugemon.getAttack()));
        defense.setText(String.valueOf(bugemon.getDefense()));
        magicAttack.setText(String.valueOf(bugemon.getAttackMagic()));
        magicDefense.setText(String.valueOf(bugemon.getDefenseMagic()));
        initiative.setText(String.valueOf(bugemon.getInitiative()));
        renderAttacks(bugemon);
        renderSpecial(bugemon);
    }

    void clear() {
        previewedBugemon = null;
        bugemonSprite.setImage(null);
        stateBadge.setText("READY");
        stateBadge.getStyleClass().setAll("badge-ready");
        name.setText("No Bugemon selected");
        element.setText("Type: -");
        hpBar.setProgress(0);
        hpBar.setStyle("-fx-accent: " + resolveHealthColor(1.0) + ";");
        hp.setText("0 / 0");
        status.setText("-");
        attack.setText("-");
        defense.setText("-");
        magicAttack.setText("-");
        magicDefense.setText("-");
        initiative.setText("-");
        renderAttacks(null);
        renderSpecial(null);
    }

    void showStats() {
        statsPanel.setVisible(true);   statsPanel.setManaged(true);
        attacksPane.setVisible(false); attacksPane.setManaged(false);
        specialPane.setVisible(false); specialPane.setManaged(false);
        statsTabButton.getStyleClass().setAll(TAB_ACTIVE_CLASS);
        attacksTabButton.getStyleClass().setAll(TAB_INACTIVE_CLASS);
        specialTabButton.getStyleClass().setAll(TAB_INACTIVE_CLASS);
    }

    void showAttacks() {
        statsPanel.setVisible(false);  statsPanel.setManaged(false);
        attacksPane.setVisible(true);  attacksPane.setManaged(true);
        specialPane.setVisible(false); specialPane.setManaged(false);
        statsTabButton.getStyleClass().setAll(TAB_INACTIVE_CLASS);
        attacksTabButton.getStyleClass().setAll(TAB_ACTIVE_CLASS);
        specialTabButton.getStyleClass().setAll(TAB_INACTIVE_CLASS);
        renderAttacks(previewedBugemon);
    }

    void showSpecial() {
        statsPanel.setVisible(false);  statsPanel.setManaged(false);
        attacksPane.setVisible(false); attacksPane.setManaged(false);
        specialPane.setVisible(true);  specialPane.setManaged(true);
        statsTabButton.getStyleClass().setAll(TAB_INACTIVE_CLASS);
        attacksTabButton.getStyleClass().setAll(TAB_INACTIVE_CLASS);
        specialTabButton.getStyleClass().setAll(TAB_ACTIVE_CLASS);
        renderSpecial(previewedBugemon);
    }

    private void renderAttacks(Bugemon bugemon) {
        attacksList.getChildren().clear();
        if (bugemon == null) {
            attacksList.getChildren().add(PartyCardBuilder.buildPlaceholderLabel("No Bugemon selected."));
            return;
        }
        List<Skill> skills = bugemon.getSkills();
        if (skills.isEmpty()) {
            attacksList.getChildren().add(PartyCardBuilder.buildPlaceholderLabel("This Bugemon has no attacks."));
            return;
        }
        for (Skill skill : skills) attacksList.getChildren().add(PartyCardBuilder.buildAttackCard(skill));
    }

    private void renderSpecial(Bugemon bugemon) {
        if (specialList == null) return;
        specialList.getChildren().clear();
        if (bugemon == null) {
            specialList.getChildren().add(PartyCardBuilder.buildPlaceholderLabel("No Bugemon selected."));
            return;
        }
        DomainExpansion de = domainExpansionResolver.apply(bugemon).orElse(null);
        boolean hasTf      = PartyCardBuilder.hasTransformation(bugemon);
        if (de == null && !hasTf) {
            specialList.getChildren().add(
                    PartyCardBuilder.buildPlaceholderLabel("This Bugemon doesn't have any special moves"));
            return;
        }
        if (de != null) specialList.getChildren().add(PartyCardBuilder.buildSpecialCard(de));
        if (hasTf)      specialList.getChildren().add(PartyCardBuilder.buildTransformationCard());
    }

    private void applyBadge(Bugemon bugemon, Bugemon activeBugemon) {
        String badgeText;
        String badgeClass;
        String statusText;
        if (bugemon == activeBugemon) {
            badgeText = "ACTIVE"; badgeClass = "badge-active"; statusText = "Currently in battle";
        } else if (bugemon.isHealthy()) {
            badgeText = "READY";  badgeClass = "badge-ready";  statusText = "Ready to battle";
        } else {
            badgeText = "KO";     badgeClass = "badge-ko";     statusText = "Knocked out";
        }
        stateBadge.setText(badgeText);
        stateBadge.getStyleClass().setAll(badgeClass);
        status.setText(statusText);
    }

    private void setupTabHovers() {
        setupTabHover(statsTabButton,   () -> statsPanel.isVisible());
        setupTabHover(attacksTabButton, () -> attacksPane.isVisible());
        setupTabHover(specialTabButton, () -> specialPane.isVisible());
    }

    private void setupTabHover(Button button, Supplier<Boolean> isActive) {
        button.setOnMouseEntered(e -> {
            if (!isActive.get()) button.getStyleClass().setAll(TAB_HOVER_CLASS);
        });
        button.setOnMouseExited(e ->
            button.getStyleClass().setAll(isActive.get() ? TAB_ACTIVE_CLASS : TAB_INACTIVE_CLASS));
    }

    private String resolveHealthColor(double ratio) {
        if (ratio > HP_RATIO_THRESHOLD_YELLOW) return "#2f6f4f";
        if (ratio > HP_RATIO_THRESHOLD_RED)    return "#c8891b";
        return "#9b3d3d";
    }
}
