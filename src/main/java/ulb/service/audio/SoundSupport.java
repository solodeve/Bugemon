package ulb.service.audio;

import ulb.exception.AudioResourceNotFoundException;

/**
 * Small factory helpers around JLayer-based audio players.
 */
public final class SoundSupport {
    private SoundSupport() {
    }

    public static JLayerSoundPlayer loadSound(String resourcePath) throws AudioResourceNotFoundException {
        ensureResourceExists(resourcePath);
        return new JLayerSoundPlayer(resourcePath);
    }

    public static JLayerSoundPlayer loadFirstAvailableSound(String... resourcePaths) throws AudioResourceNotFoundException {
        for (String resourcePath : resourcePaths) {
            if (AudioResource.exists(resourcePath)) {
                return new JLayerSoundPlayer(resourcePath);
            }
        }
        throw new AudioResourceNotFoundException("No audio resource found among: " + String.join(", ", resourcePaths));
    }

    public static LoopingSoundPlayer loadLoopingSound(String resourcePath) throws AudioResourceNotFoundException {
        ensureResourceExists(resourcePath);
        return new LoopingSoundPlayer(resourcePath);
    }

    private static void ensureResourceExists(String resourcePath) throws AudioResourceNotFoundException {
        if (!AudioResource.exists(resourcePath)) {
            throw new AudioResourceNotFoundException("Audio resource not found: " + resourcePath);
        }
    }
}
