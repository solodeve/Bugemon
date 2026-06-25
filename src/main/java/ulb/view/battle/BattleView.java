package ulb.view.battle;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ulb.model.domain.Element;
import ulb.view.battle.animation.ElementAnimationPlayer;
import ulb.view.battle.state.BattleUiState;
import ulb.view.battle.state.BattleIntroUiState;
import ulb.view.battle.state.BattleSkillsState;
import ulb.view.battle.state.ColouredMessage;
import ulb.view.battle.helper.BackgroundAnimator;
import ulb.view.battle.helper.BattleButtonManager;
import ulb.view.battle.helper.BattleLayoutManager;
import ulb.view.battle.helper.DialogueManager;
import ulb.view.battle.helper.EvolutionHandler;
import ulb.view.battle.helper.EvolutionPlayer;
import ulb.view.battle.helper.BattleBackgroundManager;
import ulb.view.battle.helper.BattleCardRenderer;
import ulb.view.battle.helper.BattleMessageEffects;
import ulb.view.battle.helper.BattleUiSupport;
import ulb.view.battle.helper.SpriteAnimator;
import ulb.view.common.SpriteLoader;

/**
 * View responsible only for displaying the battle screen and forwarding user inputs.
 */
public class BattleView {
    private static final int PLAYER_SPRITE_WIDTH = 300;
    private static final int PLAYER_SPRITE_HEIGHT = 300;
    private static final int ENEMY_SPRITE_WIDTH = 250;
    private static final int ENEMY_SPRITE_HEIGHT = 250;

    public BattleView() {
        // default constructor for FXML
    }


    public interface Listener {
        void onAttackClicked();
        void onTeamClicked();
        void onItemsClicked();
        void onSpecialClicked();
        void onGiveUpClicked();
        void onReturnClicked();
        void onSpacePressed();
        void onSkillSelected(int skillIndex);
        void onEvolutionCompleted();
    }

    @FXML private AnchorPane battleRootPane;
    @FXML private VBox enemyInfoPanel;
    @FXML private VBox playerInfoPanel;
    @FXML private StackPane dialoguePanel;
    @FXML private StackPane actionPanel;
    @FXML private Button teamButton;
    @FXML private Button giveUpButton;
    @FXML private Button itemsButton;
    @FXML private Button attackButton;
    @FXML private Button specialButton;
    @FXML private Button skill1Button;
    @FXML private Button skill2Button;
    @FXML private Button skill3Button;
    @FXML private Button returnButton;
    @FXML private Label messageLabel;
    @FXML private Label playerNameLabel;
    @FXML private Label enemyNameLabel;
    @FXML private Label playerElementLabel;
    @FXML private Label enemyElementLabel;
    @FXML private Label playerHpLabel;
    @FXML private Label enemyHpLabel;
    @FXML private ProgressBar playerHpBar;
    @FXML private ProgressBar enemyHpBar;
    @FXML private ImageView playerSprite;
    @FXML private ImageView enemySprite;
    @FXML private ProgressBar playerXpBar;
    @FXML private ProgressBar playerSpecialBar;
    @FXML private Label playerXpLabel;
    @FXML private Label playerSpecialLabel;
    @FXML private AnchorPane animationOverlay;

    private Listener listener;
    private DialogueManager dialogueManager;
    private SpriteAnimator spriteAnimator;
    private SpriteLoader spriteLoader;
    private BackgroundAnimator backgroundAnimator;
    private EvolutionHandler evolutionHandler;
    private BattleButtonManager buttonManager;
    private BattleLayoutManager layoutManager;
    private ElementAnimationPlayer elementAnimationPlayer;
    private BattleBackgroundManager backgroundManager;
    private BattleMessageEffects messageEffects;
    private BattleCardRenderer cardRenderer;
    private BattleUiSupport uiSupport;
    private boolean xpUiVisible = true;
    private boolean battleControlsDisabled;
    private BattleUiState lastBattleUiState;

    @FXML
    public void initialize() {
        dialogueManager = new DialogueManager(messageLabel);
        spriteLoader = new SpriteLoader();
        backgroundAnimator = new BackgroundAnimator();
        evolutionHandler = new EvolutionHandler(playerSprite, spriteLoader);
        buttonManager = new BattleButtonManager(
                attackButton, teamButton, itemsButton, specialButton, giveUpButton,
                returnButton, skill1Button, skill2Button, skill3Button);

        initEvolution();
        initAnimationLayer();
        initSprites();
        initBars();
        initButtons();
        backgroundManager = new BattleBackgroundManager(
                battleRootPane, playerInfoPanel, enemyInfoPanel, dialoguePanel, actionPanel,
                playerSprite, enemySprite, specialButton, backgroundAnimator);
        messageEffects = new BattleMessageEffects(elementAnimationPlayer, spriteAnimator);
        cardRenderer = new BattleCardRenderer(
                playerNameLabel, enemyNameLabel, playerElementLabel, enemyElementLabel,
                playerHpLabel, enemyHpLabel, playerHpBar, enemyHpBar, playerSprite, enemySprite,
                playerXpBar, playerXpLabel, spriteAnimator, evolutionHandler, spriteLoader);
        uiSupport = new BattleUiSupport(buttonManager, playerSpecialBar, playerSpecialLabel, specialButton);
    }

    private void initEvolution() {
        if (battleRootPane == null || playerSprite == null) return;

        EvolutionPlayer evoPlayer = new EvolutionPlayer(battleRootPane, playerSprite);
        evolutionHandler.setEvolutionPlayer(evoPlayer);
        evolutionHandler.setCallbacks(
            () -> setBattleControlsDisabled(true),
            () -> setBattleControlsDisabled(false),
            () -> { if (listener != null) listener.onEvolutionCompleted(); }
        );

        battleRootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.E)
                        evolutionHandler.triggerShortcut(evolutionHandler.getDisplayedId());
                });
            }
        });
    }

    private void initAnimationLayer() {
        if (animationOverlay == null || playerSprite == null || enemySprite == null) return;

        backgroundAnimator.initialize(animationOverlay);
        elementAnimationPlayer = new ElementAnimationPlayer(animationOverlay);
        elementAnimationPlayer.setSprites(playerSprite, enemySprite);
        spriteAnimator = new SpriteAnimator(playerSprite, enemySprite);
        spriteAnimator.startIdle();
    }

    private void initSprites() {
        if (playerSprite != null) {
            playerSprite.setPreserveRatio(true);
            playerSprite.setFitWidth(PLAYER_SPRITE_WIDTH);
            playerSprite.setFitHeight(PLAYER_SPRITE_HEIGHT);
        }
        if (enemySprite != null) {
            enemySprite.setPreserveRatio(true);
            enemySprite.setFitWidth(ENEMY_SPRITE_WIDTH);
            enemySprite.setFitHeight(ENEMY_SPRITE_HEIGHT);
        }
        if (battleRootPane != null && playerSprite != null && enemySprite != null) {
            layoutManager = new BattleLayoutManager(battleRootPane, playerSprite, enemySprite, playerInfoPanel, enemyInfoPanel);
            layoutManager.configure();
        }
    }

    private void initBars() {
        if (playerHpBar != null) playerHpBar.setProgress(1.0);
        if (enemyHpBar != null) enemyHpBar.setProgress(1.0);
    }

    private void initButtons() {
        buttonManager.showAttackButtons(false);
        buttonManager.showMainButtons(true);
        buttonManager.applyColors();
    }


    public void askingNextBugemonAction(String bugemonName) {
        dialogueManager.addMessage("What should " + bugemonName + " do ?",
                "black", null, null, true);
    }

    public boolean isMessageQueueEmpty() {
        if (dialogueManager != null) return dialogueManager.isMessageQueueEmpty();
        return false;
    }

    public void updateMessageDisplay() {
        if  (dialogueManager != null) {
            dialogueManager.showNextMessage();
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
        buttonManager.bindActions(listener, listener::onSkillSelected);
    }

    public void restoreEvolvedState(java.util.Set<String> ids) {
        evolutionHandler.restoreEvolvedState(ids);
    }

    public void setOnDialogQueueEmpty(Runnable action) {
        if (dialogueManager != null) {
            dialogueManager.setOnQueueEmpty(action);
        }
    }

    /**
     * Enqueues a message with its visual and audio side-effects bound to two moments:
     * animations and sounds fire when the text starts typing ({@code onStart}), while the
     * battle card is refreshed once the text finishes ({@code onDisplayed}) — except for
     * healing messages, where the card must update immediately so the HP bar animates
     * alongside the dialogue.
     */
    public void updateMessage(ColouredMessage message, boolean autoDisplay) {
        if (dialogueManager == null) return;

        BattleUiState battleUiState = message.getBattleUiState();
        boolean refreshOnStart = message.healingSound();

        Runnable onStart = () -> {
            if (messageEffects != null) {
                messageEffects.playAttackEffects(message);
                messageEffects.playPassiveSounds(message);
            }
            if (refreshOnStart && battleUiState != null) refreshBattle(battleUiState);
        };

        Runnable onDisplayed = (battleUiState != null && !refreshOnStart)
                ? () -> refreshBattle(battleUiState)
                : null;

        String color = messageEffects == null ? "black" : messageEffects.colorForType(message.type());
        dialogueManager.addMessage(message.text(), color, onStart, onDisplayed, autoDisplay, message.mustAppearAlone());
    }

    public void displayBattleIntro(BattleIntroUiState introState, boolean autoDisplay) {
        String intro = uiSupport == null ? "Welcome to the arena! Let the fight begin." : uiSupport.formatBattleIntro(introState);
        updateMessage(new ColouredMessage(intro, Optional.empty()), autoDisplay);
    }

    public void displayNoSkillsAvailable(boolean autoDisplay) {
        updateMessage(new ColouredMessage("No skills available.", Optional.empty()), autoDisplay);
    }

    public void displayNoItemsAvailable(boolean autoDisplay) {
        updateMessage(new ColouredMessage("No items available.", Optional.empty()), autoDisplay);
    }

    public void showMainButtons(boolean show) {
        buttonManager.showMainButtons(show);
        if (show && uiSupport != null) {
            uiSupport.updateSpecialUi(lastBattleUiState == null ? null : lastBattleUiState.special(), isMainMenuVisible(), battleControlsDisabled);
        }
    }

    public void showAttackButtons(boolean show) {
        buttonManager.showAttackButtons(show);
    }

    public void setBattleControlsDisabled(boolean disabled) {
        battleControlsDisabled = disabled;
        buttonManager.setDisabled(disabled);
    }

    public void updateSkills(BattleSkillsState skillsState) {
        buttonManager.updateSkills(skillsState);
    }

    public void updateSkillColors(Element e1, Element e2, Element e3) {
        buttonManager.updateSkillColors(e1, e2, e3);
    }

    /**
     * Redraws the battle screen from the given snapshot.
     * HP bar animation is suppressed on a Bugemon swap to avoid a visual slide
     * from the previous Bugemon's HP to the new one.
     */
    public void refreshBattle(BattleUiState battleUiState) {
        lastBattleUiState = battleUiState;
        boolean isFreeBattle = battleUiState != null && battleUiState.isFreeBattle();
        boolean playerSwapped = cardRenderer != null && cardRenderer.hasPlayerSwap(battleUiState);

        if (uiSupport != null) {
            uiSupport.updateSpecialUi(battleUiState == null ? null : battleUiState.special(), isMainMenuVisible(), battleControlsDisabled);
        }
        if (cardRenderer != null) {
            cardRenderer.updateBugemonCard(battleUiState == null ? null : battleUiState.player(), true, isFreeBattle, playerSwapped);
            cardRenderer.updateBugemonCard(battleUiState == null ? null : battleUiState.enemy(), false, isFreeBattle, playerSwapped);
        }
        if (battleUiState != null) {
            evolutionHandler.checkConditions(battleUiState.player(), battleUiState.enemy());
        }
    }

    /** Applies the background immediately; no-op if it is already displayed. */
    public void syncBackground(String backgroundPath) {
        if (backgroundManager != null) backgroundManager.syncBackground(backgroundPath);
    }

    /**
     * Swaps the background with a dark-flash-restore transition, hiding all panels except
     * the casting Bugemon's sprite. No-op if the background is already set to {@code backgroundPath}.
     *
     * @param playerCaster {@code true} keeps the player sprite visible; {@code false} keeps the enemy's
     */
    public void playDomainBackgroundTransition(String backgroundPath, boolean playerCaster) {
        if (backgroundManager != null) {
            backgroundManager.playDomainBackgroundTransition(backgroundPath, playerCaster);
        }
    }

    public void updateXpBar(double progress, long currentXp, long xpForNextLevel) {
        if (!xpUiVisible || cardRenderer == null) {
            return;
        }
        cardRenderer.updateXpBar(progress, currentXp, xpForNextLevel);
    }

    public void setXpUiVisible(boolean visible) {
        xpUiVisible = visible;
        BattleButtonManager.setNodeDisplayed(playerXpBar,   visible);
        BattleButtonManager.setNodeDisplayed(playerXpLabel, visible);
    }

    public boolean isMainMenuVisible() {
        return buttonManager.isMainMenuVisible();
    }

    public boolean isAttackMenuVisible() {
        return buttonManager.isAttackMenuVisible();
    }

    public boolean areBattleControlsDisabled() {
        return buttonManager.isDisabled();
    }
}
