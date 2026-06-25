package ulb.view.battle.helper;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Keeps sprites and info panels positioned at their intended ratios on any window size.
 * Listens to pane dimension changes and recomputes {@link AnchorPane} anchors accordingly.
 */
public class BattleLayoutManager {
    private static final double REFERENCE_BATTLE_WIDTH = 1000.0;
    private static final double REFERENCE_BATTLE_HEIGHT = 650.0;
    private static final double PLAYER_CENTER_X_RATIO = 270.0 / REFERENCE_BATTLE_WIDTH;
    private static final double PLAYER_CENTER_Y_RATIO = 400.0 / REFERENCE_BATTLE_HEIGHT;
    private static final double ENEMY_CENTER_X_RATIO = 700.0 / REFERENCE_BATTLE_WIDTH;
    private static final double ENEMY_CENTER_Y_RATIO = 280.0 / REFERENCE_BATTLE_HEIGHT;
    private static final double INFO_PANEL_GAP_Y_REFERENCE = 20.0;

    private final AnchorPane battleRootPane;
    private final ImageView playerSprite;
    private final ImageView enemySprite;
    private final VBox playerInfoPanel;
    private final VBox enemyInfoPanel;

    public BattleLayoutManager(AnchorPane battleRootPane, ImageView playerSprite, ImageView enemySprite,
                                VBox playerInfoPanel, VBox enemyInfoPanel) {
        this.battleRootPane = battleRootPane;
        this.playerSprite = playerSprite;
        this.enemySprite = enemySprite;
        this.playerInfoPanel = playerInfoPanel;
        this.enemyInfoPanel = enemyInfoPanel;
    }

    /**
     * Attaches resize listeners and triggers an initial layout pass.
     * Must be called after the root pane has been added to the scene graph;
     * the first update is deferred via {@code Platform.runLater} to ensure
     * the pane's dimensions are available.
     */
    public void configure() {
        AnchorPane.setRightAnchor(enemySprite, null);
        if (enemyInfoPanel != null) AnchorPane.setRightAnchor(enemyInfoPanel, null);
        battleRootPane.widthProperty().addListener((obs, oldWidth, newWidth) -> update());
        battleRootPane.heightProperty().addListener((obs, oldHeight, newHeight) -> update());
        javafx.application.Platform.runLater(this::update);
    }

    private void update() {
        double width = positiveOrReference(battleRootPane.getWidth(), REFERENCE_BATTLE_WIDTH);
        double height = positiveOrReference(battleRootPane.getHeight(), REFERENCE_BATTLE_HEIGHT);
        placeSpriteAtRatio(playerSprite, width, height, PLAYER_CENTER_X_RATIO, PLAYER_CENTER_Y_RATIO);
        placeSpriteAtRatio(enemySprite, width, height, ENEMY_CENTER_X_RATIO, ENEMY_CENTER_Y_RATIO);
        if (playerInfoPanel != null) placeInfoPanelAboveSprite(playerInfoPanel, playerSprite, width, height);
        if (enemyInfoPanel != null) placeInfoPanelAboveSprite(enemyInfoPanel, enemySprite, width, height);
    }

    private void placeSpriteAtRatio(ImageView sprite, double paneWidth, double paneHeight,
                                     double centerXRatio, double centerYRatio) {
        double spriteWidth  = positiveOrReference(sprite.getFitWidth(),  sprite.getBoundsInLocal().getWidth());
        double spriteHeight = positiveOrReference(sprite.getFitHeight(), sprite.getBoundsInLocal().getHeight());
        double left = clamp((paneWidth  * centerXRatio) - spriteWidth  / 2.0, 0.0, Math.max(0.0, paneWidth  - spriteWidth));
        double top  = clamp((paneHeight * centerYRatio) - spriteHeight / 2.0, 0.0, Math.max(0.0, paneHeight - spriteHeight));
        AnchorPane.setLeftAnchor(sprite, left);
        AnchorPane.setTopAnchor(sprite, top);
    }

    private void placeInfoPanelAboveSprite(VBox infoPanel, ImageView sprite,
                                            double paneWidth, double paneHeight) {
        double panelWidth  = positiveOrReference(infoPanel.getWidth(),  positiveOrReference(infoPanel.prefWidth(-1),  infoPanel.getBoundsInLocal().getWidth()));
        double panelHeight = positiveOrReference(infoPanel.getHeight(), positiveOrReference(infoPanel.prefHeight(-1), infoPanel.getBoundsInLocal().getHeight()));
        double spriteWidth = positiveOrReference(sprite.getFitWidth(), sprite.getBoundsInLocal().getWidth());
        double spriteLeft  = positiveOrReference(AnchorPane.getLeftAnchor(sprite), 0.0);
        double spriteTop   = positiveOrReference(AnchorPane.getTopAnchor(sprite),  0.0);
        double gapY = INFO_PANEL_GAP_Y_REFERENCE * (paneHeight / REFERENCE_BATTLE_HEIGHT);
        double left = clamp(spriteLeft + spriteWidth / 2.0 - panelWidth / 2.0, 0.0, Math.max(0.0, paneWidth  - panelWidth));
        double top  = clamp(spriteTop - panelHeight - gapY,                       0.0, Math.max(0.0, paneHeight - panelHeight));
        AnchorPane.setLeftAnchor(infoPanel, left);
        AnchorPane.setTopAnchor(infoPanel, top);
    }

    private double positiveOrReference(double value, double reference) {
        return value > 0 ? value : reference;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
