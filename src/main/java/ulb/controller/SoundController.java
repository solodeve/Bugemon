package ulb.controller;

import ulb.configuration.Configuration;
import ulb.exception.AudioException;
import ulb.service.audio.JLayerSoundPlayer;
import ulb.service.audio.LoopingSoundPlayer;
import ulb.service.audio.SoundSupport;

/**
 * Singleton that manages playback of all UI and battle sounds.
 */
public final class SoundController {
    private static SoundController INSTANCE;

    private final JLayerSoundPlayer buttonSound;
    private final JLayerSoundPlayer magicSound;
    private final JLayerSoundPlayer healingItemSound;
    private final JLayerSoundPlayer physiqueSound;
    private final JLayerSoundPlayer deathSound;
    private final JLayerSoundPlayer victorySound;
    private final JLayerSoundPlayer defeatSound;
    private final JLayerSoundPlayer impactSound;
    private final JLayerSoundPlayer switchSound;
    private final JLayerSoundPlayer evolutionSound;
    private final JLayerSoundPlayer boostSound;
    private final LoopingSoundPlayer menuSound;

    private SoundController() {
        try {
            this.buttonSound = SoundSupport.loadSound(Configuration.BUTTON_SOUND_PATH);
            this.magicSound = SoundSupport.loadSound(Configuration.MAGIC_SOUND_PATH);
            this.healingItemSound = SoundSupport.loadSound(Configuration.HEALING_ITEM_SOUND_PATH);
            this.physiqueSound = SoundSupport.loadSound(Configuration.PHYSIQUE_SOUND_PATH);
            this.deathSound = SoundSupport.loadSound(Configuration.DEATH_SOUND_PATH);
            this.victorySound = SoundSupport.loadSound(Configuration.VICTORY_SOUND_PATH);
            this.defeatSound = SoundSupport.loadSound(Configuration.DEFEAT_SOUND_PATH);
            this.impactSound = SoundSupport.loadFirstAvailableSound(Configuration.IMPACT_SOUND_PATH, Configuration.PHYSIQUE_SOUND_PATH);
            this.switchSound = SoundSupport.loadFirstAvailableSound(Configuration.SWITCH_SOUND_PATH, Configuration.MAGIC_SOUND_PATH);
            this.evolutionSound = SoundSupport.loadFirstAvailableSound(Configuration.EVOLUTION_SOUND_PATH, Configuration.MAGIC_SOUND_PATH);
            this.boostSound = SoundSupport.loadFirstAvailableSound(Configuration.BOOST_SOUND_PATH, Configuration.MAGIC_SOUND_PATH);
            this.menuSound = SoundSupport.loadLoopingSound(Configuration.MENU_SOUND_PATH);
        } catch (AudioException e) {
            throw new IllegalStateException("Failed to initialize sound system.", e);
        }
    }

    public static SoundController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SoundController();
        }
        return INSTANCE;
    }

    public void playDeath() {
        play(deathSound);
    }

    public void playVictory() {
        play(victorySound);
    }

    public void playDefeat() {
        play(defeatSound);
    }

    public void playImpact() {
        play(impactSound);
    }

    public void playSwitch() {
        play(switchSound);
    }

    public void playEvolution() {
        play(evolutionSound);
    }

    public void playBoost() {
        play(boostSound);
    }

    public void stopMenuMusic() {
        menuSound.stop();
    }

    public void playPhysicalAttack() {
        play(physiqueSound);
    }

    public void playButtonClick() {
        play(buttonSound);
    }

    public void playMagicAttack() {
        play(magicSound);
    }

    public void playHealingItem() {
        play(healingItemSound);
    }

    public void playMenuMusic() { menuSound.playLoop(); }

    private void play(JLayerSoundPlayer soundPlayer) {
        soundPlayer.play();
    }
}
