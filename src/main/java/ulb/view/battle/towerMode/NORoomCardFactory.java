package ulb.view.battle.towerMode;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import ulb.configuration.Configuration;
import ulb.model.no.Room;
import ulb.view.common.SpriteLoader;

/**
 * Encapsulates room-card composition details so the main view stays focused on flow orchestration.
 */
final class NORoomCardFactory {

    private static final int ROOM_CARD_WIDTH = 145;
    private static final int ROOM_CARD_HEIGHT = 190;
    private static final int BOSS_CARD_WIDTH = 240;
    private static final int BOSS_CARD_HEIGHT = 250;
    private static final int ROOM_SPRITE_SIZE = 72;
    private static final int BOSS_SPRITE_WIDTH = 150;
    private static final int BOSS_SPRITE_HEIGHT = 120;
    private static final int ROOM_FRAME_WIDTH = 95;
    private static final int ROOM_FRAME_HEIGHT = 85;
    private static final int BOSS_FRAME_WIDTH = 180;
    private static final int BOSS_FRAME_HEIGHT = 140;
    private static final int FRAME_ARC = 18;

    private final SpriteLoader spriteLoader = new SpriteLoader();

    /**
     * Creates one room card reflecting room type (boss/normal), lock state, and current-selection state.
     */
    VBox createRoomCard(Room room, boolean isCurrentRoom) {
        boolean bossRoom = room != null && room.isBossRoom();
        boolean unlocked = room != null && room.isUnlocked();
        RoomCardMetrics metrics = metricsFor(bossRoom);
        VBox card = newCard(metrics, bossRoom, unlocked, isCurrentRoom);
        card.getChildren().addAll(
                buildTypeLabel(room, bossRoom),
                buildSpriteFrame(room, unlocked, metrics),
                buildNameLabel(room, bossRoom, unlocked),
                buildStateLabel(unlocked, isCurrentRoom));
        return card;
    }

    private RoomCardMetrics metricsFor(boolean bossRoom) {
        return bossRoom
                ? new RoomCardMetrics(BOSS_CARD_WIDTH, BOSS_CARD_HEIGHT, BOSS_FRAME_WIDTH, BOSS_FRAME_HEIGHT, BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT)
                : new RoomCardMetrics(ROOM_CARD_WIDTH, ROOM_CARD_HEIGHT, ROOM_FRAME_WIDTH, ROOM_FRAME_HEIGHT, ROOM_SPRITE_SIZE, ROOM_SPRITE_SIZE);
    }

    private VBox newCard(RoomCardMetrics metrics, boolean bossRoom, boolean unlocked, boolean isCurrentRoom) {
        VBox card = new VBox(10);
        card.setPrefWidth(metrics.cardWidth());
        card.setPrefHeight(metrics.cardHeight());
        card.setMinWidth(metrics.cardWidth());
        card.setStyle(buildRoomCardStyle(bossRoom, unlocked, isCurrentRoom));
        card.setOpacity(unlocked ? 1.0 : 0.55);
        card.setFillWidth(true);
        return card;
    }

    private StackPane buildSpriteFrame(Room room, boolean unlocked, RoomCardMetrics metrics) {
        Rectangle frame = new Rectangle(metrics.frameWidth(), metrics.frameHeight());
        frame.setArcWidth(FRAME_ARC);
        frame.setArcHeight(FRAME_ARC);
        frame.setStyle("-fx-fill: rgba(255,255,255,0.92);");
        return new StackPane(frame, buildSprite(room, unlocked, metrics));
    }

    private ImageView buildSprite(Room room, boolean unlocked, RoomCardMetrics metrics) {
        Image spriteImage = resolveSpriteImage(room);
        ImageView sprite = new ImageView(spriteImage);
        sprite.setFitWidth(metrics.spriteWidth());
        sprite.setFitHeight(metrics.spriteHeight());
        sprite.setPreserveRatio(true);
        sprite.setOpacity(unlocked ? 1.0 : 0.35);
        return sprite;
    }

    private Image resolveSpriteImage(Room room) {
        return spriteLoader.tryLoadSprite(room != null ? room.getDisplaySpritePath() : null)
                .or(() -> spriteLoader.tryLoadSprite(room != null ? room.getPreviewSprite() : null))
                .or(() -> spriteLoader.tryLoadSprite(Configuration.DEFAULT_BUGEMON_SPRITE_PATH))
                .orElse(null);
    }

    private Label buildTypeLabel(Room room, boolean bossRoom) {
        Label typeLabel = new Label(bossRoom ? "BOSS" : "ROOM " + room.getRoomNumber());
        typeLabel.getStyleClass().add(bossRoom ? "room-card-type-label-boss" : "room-card-type-label-room");
        return typeLabel;
    }

    private Label buildNameLabel(Room room, boolean bossRoom, boolean unlocked) {
        Label nameLabel = new Label(room.getDisplayName());
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(bossRoom ? 190 : 120);
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.getStyleClass().add(unlocked
                ? (bossRoom ? "room-card-name-unlocked" : "room-card-name-unlocked-sm")
                : "room-card-name-locked");
        return nameLabel;
    }

    private Label buildStateLabel(boolean unlocked, boolean isCurrentRoom) {
        Label stateLabel = new Label(
                isCurrentRoom ? "Current battle" : unlocked ? "Unlocked" : "Locked"
        );
        stateLabel.getStyleClass().add(
                isCurrentRoom ? "room-card-state-current" : unlocked ? "room-card-state-unlocked" : "room-card-state-locked"
        );
        return stateLabel;
    }

    private String buildRoomCardStyle(boolean bossRoom, boolean unlocked, boolean isCurrentRoom) {
        String borderColor = isCurrentRoom ? "#8ef0a7" : bossRoom ? "#ffd166" : "#d9e6ff";
        String backgroundColor = unlocked
                ? (bossRoom ? "rgba(64,38,12,0.88)" : "rgba(18,26,42,0.86)")
                : "rgba(70,78,92,0.78)";
        String borderWidth = isCurrentRoom ? "3" : bossRoom ? "2.5" : "2";
        String padding = bossRoom ? "18" : "14";

        return "-fx-background-color: " + backgroundColor + ";" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: " + borderWidth + ";" +
                "-fx-border-radius: 20;" +
                "-fx-padding: " + padding + ";" +
                "-fx-alignment: center;";
    }

    private record RoomCardMetrics(
            int cardWidth,
            int cardHeight,
            int frameWidth,
            int frameHeight,
            int spriteWidth,
            int spriteHeight
    ) {}
}
