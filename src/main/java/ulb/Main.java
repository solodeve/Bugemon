package ulb;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ulb.controller.ApplicationController;
import ulb.exception.DbNotConfiguredException;
import ulb.exception.GameDataLoadingException;
import ulb.exception.ScreenLoadingException;

/**
 * JavaFX entry point of the application.
 *
 * <p>Its role is limited to bootstrapping the root controller and reporting
 * startup failures to the player.</p>
 */
public class Main extends Application {

    private static final String GAME_TITLE = "BUGEMON";

    /**
     * Starts the UI and delegates the application setup to
     * {@link ApplicationController}.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(GAME_TITLE);
        try {
            ApplicationController applicationController = new ApplicationController(primaryStage);
            applicationController.start();
            primaryStage.show();
        } catch (DbNotConfiguredException | GameDataLoadingException | ScreenLoadingException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Startup error");
            alert.setHeaderText("Application startup failed.");
            alert.setContentText(e.getMessage() == null || e.getMessage().isBlank() ? "Unable to initialize the application." : e.getMessage());
            alert.showAndWait();
            primaryStage.close();
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

