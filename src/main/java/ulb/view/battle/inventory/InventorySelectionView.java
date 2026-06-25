package ulb.view.battle.inventory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ulb.configuration.Configuration;
import ulb.controller.SoundController;
import ulb.model.inventory.Item;
import ulb.model.team.Team;

/**
 * Pure view used to display the player's inventory during battle.
 *
 * <p>Dynamically builds a scrollable list of item rows. Each row contains
 * a sprite, a type badge, the item name, its description, and its quantity.</p>
 *
 * <p><b>MVC role:</b> View. Displays data and forwards events via {@link Listener}.
 * Contains no battle logic.</p>
 */
public class InventorySelectionView {

    public interface Listener {
        void onItemSelected(int index);
        void onBackClicked();
    }

    private static final int ROW_SPACING         = 14;
    private static final int SPRITE_WIDTH        = 44;
    private static final int SPRITE_HEIGHT       = 44;
    private static final int DETAILS_SPACING     = 4;
    private static final String HEAL_COLOUR      = "#2f855a";
    private static final String BOOST_COLOUR     = "#3b82f6";
    private static final String DEFAULT_COLOUR   = "#3f3f46";
    private static final String BORDER_DEFAULT   = "#2d2d2d";
    private static final String BORDER_SELECTED  = "#3b82f6";
    private static final String BG_DEFAULT       = "white";
    private static final String BG_SELECTED      = "#eff6ff";

    @FXML private VBox   inventoryContainer;
    @FXML private Button btnBack;

    private Listener listener;
    private final List<HBox> itemRows = new ArrayList<>();
    private int selectedItemIndex = -1;

    private javafx.event.EventHandler<KeyEvent> keyNavHandler;

    @FXML
    public void initialize() {
        keyNavHandler = event -> {
            switch (event.getCode()) {
                case UP     -> { moveSelection(-1); event.consume(); }
                case DOWN   -> { moveSelection(1);  event.consume(); }
                case ENTER  -> {
                    if (selectedItemIndex >= 0 && listener != null) {
                        listener.onItemSelected(selectedItemIndex);
                        event.consume();
                    }
                }
                case ESCAPE -> { handleBack(); event.consume(); }
                default -> {}
            }
        };
        // Register key nav and clean it up when the scene changes
        inventoryContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (oldScene != null) oldScene.removeEventFilter(KeyEvent.KEY_PRESSED, keyNavHandler);
            if (newScene != null) newScene.addEventFilter(KeyEvent.KEY_PRESSED, keyNavHandler);
        });
    }

    public void setListener(Listener listener) { this.listener = listener; }
    public void setBackButtonDisabled(boolean disabled) { btnBack.setDisable(disabled); }

    public void displayInventory(Team playerTeam) {
        inventoryContainer.getChildren().clear();
        itemRows.clear();
        selectedItemIndex = -1;

        if (playerTeam == null) {
            inventoryContainer.getChildren().add(new Label("Inventory unavailable."));
            return;
        }
        List<Item> items = playerTeam.getInventoryItems();
        if (items.isEmpty()) {
            inventoryContainer.getChildren().add(new Label("Your bag is empty."));
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            HBox row = buildItemRow(items.get(i), playerTeam.getInventoryQuantity(items.get(i)), i);
            inventoryContainer.getChildren().add(row);
            itemRows.add(row);
        }
        if (!itemRows.isEmpty()) { selectedItemIndex = 0; setRowSelected(0, true); }
    }

    @FXML
    private void handleBack() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) listener.onBackClicked();
    }

    private HBox buildItemRow(Item item, int qty, int index) {
        HBox row = new HBox(ROW_SPACING);
        row.setMaxWidth(Double.MAX_VALUE);
        applyRowStyle(row, BORDER_DEFAULT, BG_DEFAULT);

        row.setOnMouseEntered(e -> { setRowSelected(selectedItemIndex, false); selectedItemIndex = index; setRowSelected(index, true); });
        row.setOnMouseExited(e  -> { if (index != selectedItemIndex) applyRowStyle(row, BORDER_DEFAULT, BG_DEFAULT); });
        row.setOnMouseClicked(e -> { if (listener != null) listener.onItemSelected(index); });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label quantityLabel = new Label("x" + qty);
        quantityLabel.getStyleClass().add("inventory-item-qty");

        row.getChildren().addAll(buildSpriteBox(item), buildDetailsBox(item), spacer, quantityLabel);
        return row;
    }

    private StackPane buildSpriteBox(Item item) {
        ImageView spriteView = new ImageView();
        loadObjectSprite(item.sprite()).ifPresent(spriteView::setImage);
        spriteView.setFitWidth(SPRITE_WIDTH);
        spriteView.setFitHeight(SPRITE_HEIGHT);
        spriteView.setPreserveRatio(true);

        StackPane box = new StackPane(spriteView);
        box.setMinSize(60, 60);
        box.setPrefSize(60, 60);
        box.setMaxSize(60, 60);
        box.getStyleClass().add("inventory-sprite-box");
        return box;
    }

    private VBox buildDetailsBox(Item item) {
        Label typeLabel = new Label(formatType(item.type()));
        typeLabel.setStyle(
                "-fx-background-color: " + getAccentColour(item.type()) + ";"
                + "-fx-background-radius: 999;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 11px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 3 10;");

        Label titleLabel = new Label(item.name());
        titleLabel.getStyleClass().add("inventory-item-title");

        Label descLabel = new Label(item.description());
        descLabel.getStyleClass().add("inventory-item-desc");
        descLabel.setWrapText(true);

        VBox details = new VBox(DETAILS_SPACING, typeLabel, titleLabel, descLabel);
        HBox.setHgrow(details, Priority.ALWAYS);
        return details;
    }

    private void moveSelection(int delta) {
        if (itemRows.isEmpty()) return;
        int newIndex = Math.max(0, Math.min(itemRows.size() - 1, selectedItemIndex + delta));
        if (newIndex == selectedItemIndex) return;
        setRowSelected(selectedItemIndex, false);
        selectedItemIndex = newIndex;
        setRowSelected(selectedItemIndex, true);
    }

    private void setRowSelected(int index, boolean selected) {
        if (index < 0 || index >= itemRows.size()) return;
        applyRowStyle(itemRows.get(index),
                selected ? BORDER_SELECTED : BORDER_DEFAULT,
                selected ? BG_SELECTED     : BG_DEFAULT);
    }

    private void applyRowStyle(HBox row, String borderColour, String bgColour) {
        row.setStyle(
                "-fx-alignment: center-left;"
                + "-fx-padding: 12 18;"
                + "-fx-background-color: " + bgColour + ";"
                + "-fx-background-radius: 16;"
                + "-fx-border-radius: 16;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: " + borderColour + ";"
                + "-fx-cursor: hand;"
                + "-fx-effect: dropshadow(gaussian, rgba(15, 23, 42, 0.10), 8, 0.2, 0, 2);");
    }

    private String getAccentColour(String type) {
        if (type == null) return DEFAULT_COLOUR;
        return switch (type.toLowerCase()) {
            case "heal"  -> HEAL_COLOUR;
            case "boost" -> BOOST_COLOUR;
            default      -> DEFAULT_COLOUR;
        };
    }

    private String formatType(String type) {
        return (type == null || type.isBlank()) ? "ITEM" : type.toUpperCase();
    }

    private Optional<Image> loadObjectSprite(String spriteName) {
        if (spriteName == null || spriteName.isBlank()) return Optional.empty();
        Optional<Image> primary = loadImage(Configuration.objectSpriteResourcePath(spriteName));
        return primary.isPresent() ? primary : loadImage(Configuration.pngResourcePath(spriteName));
    }

    private Optional<Image> loadImage(String path) {
        InputStream stream = getClass().getResourceAsStream(path);
        return stream == null ? Optional.empty() : Optional.of(new Image(stream));
    }
}
