package ulb.controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import ulb.configuration.Configuration;
import ulb.view.common.FocusHighlight;
import ulb.view.menu.teamEdition.TeamEditionView;

/**
 * Loads FXML files and manages scene transitions on the JavaFX stage.
 */
class ViewLoader {

    private final Stage stage;
    private final EventHandler<KeyEvent> keyHandler;
    private SceneDimensions standardDimensions;

    /**
     * @param stage      the application stage on which scenes are displayed
     * @param keyHandler the keyboard filter registered on every new scene
     */
    ViewLoader(Stage stage, EventHandler<KeyEvent> keyHandler) {
        this.stage      = stage;
        this.keyHandler = keyHandler;
    }

    /**
     * @param <T>          the controller type declared in the FXML
     * @param resourcePath absolute resource path (e.g. {@code Configuration.LOGIN_VIEW_PATH})
     * @return a {@link LoadedView} holding the root and its controller
     * @throws IOException if the resource cannot be found or parsed
     */
    <T> LoadedView<T> load(String resourcePath) throws IOException {
        URL resource = getClass().getResource(resourcePath);
        if (resource == null) {
            throw new IOException("View resource not found: " + resourcePath);
        }
        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();
        return new LoadedView<>(root, loader.getController());
    }

    /**
     * Displays the given root on the stage at the standard window dimensions.
     *
     * @param root the scene's root node
     * @throws IOException if the standard dimensions cannot be computed
     */
    void setScene(Parent root) throws IOException {
        SceneDimensions dims = getStandardDimensions();
        Scene scene = root.getScene() != null ? root.getScene() : new Scene(root, dims.width(), dims.height());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
        wireHoverFocus(root);
        stage.setScene(scene);
        stage.sizeToScene();
    }

    void close() {
        if (stage != null) {
            stage.close();
        }
    }

    private SceneDimensions getStandardDimensions() throws IOException {
        if (standardDimensions != null) {
            return standardDimensions;
        }
        LoadedView<TeamEditionView> ref = load(Configuration.TEAM_EDITION_VIEW_PATH);
        standardDimensions = measureDimensions(ref.root());
        return standardDimensions;
    }

    private static SceneDimensions measureDimensions(Parent root) {
        Scene scene = new Scene(root);
        root.applyCss();
        root.layout();
        double w = root.prefWidth(Region.USE_COMPUTED_SIZE);
        double h = root.prefHeight(Region.USE_COMPUTED_SIZE);
        if (w <= 0) w = scene.getWidth();
        if (h <= 0) h = scene.getHeight();
        return new SceneDimensions(w, h);
    }

    private static void wireHoverFocus(Parent root) {
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof Button btn) {
                FocusHighlight.apply(btn);
            } else if (node instanceof ScrollPane sp && sp.getContent() instanceof Parent p) {
                wireHoverFocus(p);
            } else if (node instanceof Parent p) {
                wireHoverFocus(p);
            }
        }
    }

    record LoadedView<T>(Parent root, T controller) {
        public Parent getRoot() { return root; }
        public T getController() { return controller; }
    }

    private record SceneDimensions(double width, double height) {}
}

