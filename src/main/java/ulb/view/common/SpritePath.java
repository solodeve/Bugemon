package ulb.view.common;

import java.util.List;
import java.util.Optional;

/**
 * Thin wrapper around a raw sprite path string, responsible for normalising it before
 * candidate paths are generated.
 *
 * <p>Some paths are stored in the database or configuration with a leading {@code @}
 * convention (e.g. {@code @sprites/bugemon/fire.png}). {@link #from} strips that prefix
 * so the rest of the loading pipeline never needs to handle it.
 *
 * <p>The record holds the already-normalized string in {@link #raw}; callers should
 * always construct instances through {@link #from}, not the compact constructor.
 */
public record SpritePath(String raw) {
    /**
     * Parses {@code spritePath} into a {@code SpritePath}, stripping a leading {@code @}
     * if present.
     *
     * @param spritePath the raw path value; may include a leading {@code @}
     * @return {@code empty} if {@code spritePath} is {@code null} or blank
     */
    public static Optional<SpritePath> from(String spritePath) {
        if (spritePath == null || spritePath.isBlank()) {
            return Optional.empty();
        }

        String normalized = spritePath.trim();
        if (normalized.startsWith("@")) {
            normalized = normalized.substring(1);
        }

        return Optional.of(new SpritePath(normalized));
    }

    /**
     * Returns the ordered list of classpath paths that {@link SpriteLoader} should try when
     * looking up this sprite.
     *
     * <p>Four variants are generated (raw, stripped, prefixed-stripped, prefixed) to handle
     * the different storage conventions found across Bugemon data entries — some paths are
     * absolute classpath references, others are bare filenames that must be resolved under
     * {@code spriteFolder}.
     *
     * @param spriteFolder the classpath directory prefix (e.g. {@code "/sprites/front"})
     * @return an immutable, ordered list of candidate paths; never {@code null}
     */
    public List<String> defaultCandidates(String spriteFolder) {
        String cleaned = raw.replaceFirst("^/+", "");
        String prefixed = spriteFolder + "/" + cleaned;
        return List.of(raw, "/" + cleaned, "/" + prefixed, prefixed);
    }
}
