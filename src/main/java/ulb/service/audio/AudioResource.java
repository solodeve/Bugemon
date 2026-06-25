package ulb.service.audio;

import ulb.exception.AudioResourceNotFoundException;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Centralizes classpath lookup for audio files used by the sound players.
 */
final class AudioResource {
    private AudioResource() {
    }

    static boolean exists(String resourcePath) {
        return AudioResource.class.getResource(resourcePath) != null;
    }

    static BufferedInputStream openStream(String resourcePath)
            throws AudioResourceNotFoundException {
        InputStream rawInputStream = AudioResource.class.getResourceAsStream(resourcePath);
        if (rawInputStream == null) {
            throw new AudioResourceNotFoundException("Audio resource not found: " + resourcePath);
        }
        return new BufferedInputStream(rawInputStream);
    }
}
