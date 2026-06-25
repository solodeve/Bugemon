package ulb.service.audio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import ulb.exception.AudioException;
import ulb.exception.AudioPlaybackException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Loops an MP3 resource in a background thread using JLayer.
 */
public final class LoopingSoundPlayer {
    private final String resourcePath;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private volatile Player currentPlayer;

    public LoopingSoundPlayer(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public synchronized void playLoop() {
        if (running.get()) {
            return;
        }
        running.set(true);
        Thread.ofVirtual().name("loop-sound-" + resourcePath).start(() -> {
            try {
                loopPlayback();
            } catch (AudioException e) {
                running.set(false);
                throw new IllegalStateException(e);
            }
        });
    }

    public synchronized void stop() {
        running.set(false);
        Player player = currentPlayer;
        if (player != null) {
            player.close();
        }
    }

    private void loopPlayback() throws AudioException {
        while (running.get()) {
            playOnce();
        }
    }

    private void playOnce() throws AudioException {
        try (BufferedInputStream bufferedInputStream = AudioResource.openStream(resourcePath)) {
            Player player = new Player(bufferedInputStream);
            currentPlayer = player;
            player.play();
        } catch (JavaLayerException | IOException e) {
            throw new AudioPlaybackException("Failed to play looped sound: " + resourcePath, e);
        } finally {
            currentPlayer = null;
        }
    }
}
