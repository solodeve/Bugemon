package ulb.view.battle.party;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ulb.configuration.Configuration;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.model.special.DomainBonus;
import ulb.model.special.DomainExpansion;
import ulb.view.common.SpriteLoader;

/**
 * Static factory methods for building Bugemon card UI nodes in {@link PartySelectionView}.
 *
 * <p>Covers party-button content/style, attack cards, special cards,
 * transformation cards, placeholder labels, and all text-formatting helpers.
 * No state is held — every method is stateless.</p>
 */
final class PartyCardBuilder {

    private static final int PARTY_SPRITE_WIDTH          = 70;
    private static final int PARTY_SPRITE_HEIGHT         = 70;
    private static final int PARTY_NAME_LABEL_MAX_WIDTH  = 94;
    private static final int PARTY_BUTTON_SPACING        = 6;

    private PartyCardBuilder() {}

    static VBox buildButtonContent(Bugemon bugemon, boolean active, SpriteLoader loader) {
        ImageView sprite = new ImageView(
                loader.loadSpriteForBugemon(bugemon.getSprite(), bugemon.getElement(), true, false).orElse(null));
        sprite.setFitWidth(PARTY_SPRITE_WIDTH);
        sprite.setFitHeight(PARTY_SPRITE_HEIGHT);
        sprite.setPreserveRatio(true);

        Label nameLabel = new Label(bugemon.getName());
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setMaxWidth(PARTY_NAME_LABEL_MAX_WIDTH);
        nameLabel.getStyleClass().add("party-btn-name");

        Label hpLabel = new Label("HP " + bugemon.getHp() + "/" + bugemon.getMaxHp());
        hpLabel.getStyleClass().add("party-btn-hp");

        String slotStatus = active ? "ACTIVE" : bugemon.isHealthy() ? "READY" : "KO";
        Label stateLabel = new Label(slotStatus);
        stateLabel.getStyleClass().add(bugemon.isHealthy() ? "party-btn-state-healthy" : "party-btn-state-ko");

        VBox content = new VBox(PARTY_BUTTON_SPACING, nameLabel, sprite, hpLabel, stateLabel);
        content.setAlignment(Pos.CENTER);
        return content;
    }

    static void applyButtonStyle(Button button, Bugemon bugemon, boolean active) {
        button.getStyleClass().add("party-btn-base");
        if (active) button.getStyleClass().add("party-btn-active");
        else if (bugemon.isHealthy()) button.getStyleClass().add("party-btn-healthy");
        else button.getStyleClass().add("party-btn-ko");
    }

    static VBox buildAttackCard(Skill skill) {
        Label name = styledLabel(skill.getName(), true, "card-name-label");
        Label meta = styledLabel(buildSkillMetadata(skill), true, "card-meta-label");
        Label desc = styledLabel(resolveSkillDescription(skill), true, "card-desc-label");
        desc.setMaxWidth(Double.MAX_VALUE);
        VBox card = new VBox(6, name, meta, desc);
        card.getStyleClass().add("attack-card");
        return card;
    }

    static VBox buildSpecialCard(DomainExpansion de) {
        Label name = styledLabel(de.name(), true, "card-name-label");
        Label meta = styledLabel(
                "Boosted type: " + de.getBoostedElementDisplayName() + " | Cost: " + de.cost(),
                true, "card-meta-label");
        Label desc = styledLabel(resolveDomainDescription(de), true, "card-desc-label");
        desc.setMaxWidth(Double.MAX_VALUE);
        VBox card = new VBox(6, name, meta, desc);
        card.getStyleClass().add("special-card");
        return card;
    }

    static VBox buildTransformationCard() {
        Label name = styledLabel("Transformation", true, "card-name-label");
        Label meta = styledLabel("Battle special", true, "card-meta-label");
        Label desc = styledLabel(
                "This Bugemon can transform during battle when its health are below 20.",
                true, "card-desc-label");
        desc.setMaxWidth(Double.MAX_VALUE);
        VBox card = new VBox(6, name, meta, desc);
        card.getStyleClass().add("transformation-card");
        return card;
    }

    static Label buildPlaceholderLabel(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.getStyleClass().add("placeholder-label");
        return label;
    }

    static boolean hasTransformation(Bugemon bugemon) {
        if (bugemon == null || bugemon.getId() == null || bugemon.getId().isBlank()) return false;
        String path = Configuration.bugemonEvolutionVideoResourcePathForId(bugemon.getId());
        return PartyCardBuilder.class.getResource(path) != null;
    }

    private static String buildSkillMetadata(Skill skill) {
        String category = skill.getAttackMagic() ? "Magic" : "Physical";
        int precision   = Math.round(skill.getPrecision() * 100);
        return skill.getElementDisplayName()
                + " | " + category
                + " | Power " + skill.getPower()
                + " | Precision " + precision + "%";
    }

    private static String resolveSkillDescription(Skill skill) {
        String d = skill.getDescription();
        return d == null || d.isBlank() ? "No description available." : d;
    }

    private static String resolveDomainDescription(DomainExpansion de) {
        String d = de.description();
        if (d == null || d.isBlank()) d = "No description available.";
        String bonuses = formatDomainBonuses(de.bonuses());
        return bonuses.isBlank() ? d : d + System.lineSeparator() + System.lineSeparator() + "Bonuses: " + bonuses;
    }

    private static String formatDomainBonuses(List<DomainBonus> bonuses) {
        if (bonuses == null || bonuses.isEmpty()) return "";
        return bonuses.stream()
                .filter(b -> b != null && b.stat() != null && b.modifier() != 0)
                .map(PartyCardBuilder::formatDomainBonus)
                .reduce((l, r) -> l + ", " + r)
                .orElse("");
    }

    private static String formatDomainBonus(DomainBonus bonus) {
        String stat = bonus.stat().name().toLowerCase().replace('_', ' ');
        String sign = bonus.modifier() > 0 ? "+" : "";
        return stat + " " + sign + bonus.modifier();
    }

    private static Label styledLabel(String text, boolean wrap, String styleClass) {
        Label label = new Label(text);
        label.setWrapText(wrap);
        label.getStyleClass().add(styleClass);
        return label;
    }
}
