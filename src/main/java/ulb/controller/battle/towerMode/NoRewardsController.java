package ulb.controller.battle.towerMode;

import ulb.controller.ApplicationController;
import ulb.repository.IGameRepository;
import ulb.view.battle.towerMode.LevelRewardDisplay;
import ulb.view.battle.towerMode.RewardsView;

import java.util.Comparator;
import java.util.List;

/**
 * Controls the rewards screen by loading level rewards and handling user actions.
 *
 * <p>It prepares reward data for display order and routes the back action to the main menu.</p>
 */
public class NoRewardsController implements RewardsView.Listener {
    private final ApplicationController applicationController;

    public NoRewardsController(RewardsView view, ApplicationController applicationController, IGameRepository gameRepository) {
        this.applicationController = applicationController;
        view.setListener(this);
        view.displayRewards(buildDisplayList(gameRepository));
    }

    /** Handle the back action */
    @Override
    public void onBackClicked() {
        applicationController.returnToMainMenu();
    }

    /**
     * Builds de rewards list for the UI, ordered bys ascending level
     *
     * <p>This guarantees a stable progression-oriented display</p>
     */
    private List<LevelRewardDisplay> buildDisplayList(IGameRepository gameRepository) {
        return gameRepository.getAllLevelRewards().entrySet().stream()
                .sorted(Comparator.comparingInt(java.util.Map.Entry::getKey))
                .map(e -> LevelRewardDisplay.from(e.getKey(), e.getValue()))
                .toList();
    }
}
