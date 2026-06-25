package ulb.service.audio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import ulb.exception.AudioException;
import ulb.exception.AudioPlaybackException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Plays one-shot MP3 sounds from classpath resources using JLayer.
 */
public final class JLayerSoundPlayer {
    private final String resourcePath;
    private final AtomicBoolean playing = new AtomicBoolean(false);

    public JLayerSoundPlayer(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public void play() {
        if (!playing.compareAndSet(false, true)) {
            return;
        }
        Thread.ofVirtual().name("sfx-" + resourcePath).start(() -> {
            try {
                playBlocking();
            } catch (AudioException e) {
                throw new IllegalStateException("Failed to play sound: " + resourcePath, e);
            } finally {
                playing.set(false);
            }
        });
    }

    private void playBlocking() throws AudioException {
        try (BufferedInputStream bufferedInputStream = AudioResource.openStream(resourcePath)) {
            Player player = new Player(bufferedInputStream);
            player.play();
        } catch (JavaLayerException | IOException e) {
            throw new AudioPlaybackException("Failed to play sound: " + resourcePath, e);
        }
    }
}
