package ulb.controller.battle.towerMode;

import ulb.controller.ApplicationController;
import ulb.model.no.NO;
import ulb.model.no.Room;
import ulb.service.no.NOProgressionResult;
import ulb.view.battle.towerMode.NOView;

/**
 * Controller dedicated to the NO tower flow and progression screen.
 */
public final class NoController implements NOView.Listener {
    private final ApplicationController applicationController;
    private NOView view;
    private NO noAdventure;

    public NoController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    public void attachView(NOView view, NO noAdventure) {
        this.view = view;
        this.noAdventure = noAdventure;
        view.setListener(this);
        refreshView();
    }


    public void refreshView() {
        if (noAdventure == null) { // No NO run is active -> fallback in case a user is able to access NO from free battle
            view.displayStatus(NOView.Status.NO_ACTIVE_TOWER);
            return;
        }

        displayCurrentTowerState();
        displayLastBattleOutcome();
    }

    private void displayCurrentTowerState() {
        view.displayTowerState(
                noAdventure.getCurrentFloorNumber(),
                noAdventure.getNumberOfFloors(),
                noAdventure.getCurrentRoomNumber(),
                noAdventure.getTotalRoomCount(),
                noAdventure.getEnemyRoomCount(),
                noAdventure.getAllRooms(),
                noAdventure.getCurrentRoom()
        );
    }

    private void displayLastBattleOutcome() {
        NOProgressionResult result = applicationController.consumeNoTowerBattleOutcome();
        view.displayLastBattleResult(result);
        view.displayUnlockedBugemons(result == null ? java.util.List.of() : result.getUnlockedBugemons());
    }

    /**
     * creates and starts a battle instance {@code currentRoom}
     */
    @Override
    public void onStartBattleClicked() {
        Room currentRoom = noAdventure.getCurrentRoom();
        applicationController.startNoTowerBattle(currentRoom);
    }

    @Override
    public void onQuitTowerClicked() {
        applicationController.abandonNoTower();
    }
}
