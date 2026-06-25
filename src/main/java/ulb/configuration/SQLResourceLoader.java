package ulb.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.regex.Pattern;

/**
 * Loads SQL queries from classpath resources and renders the small set of
 * validated dynamic fragments required by the repositories.
 */
public final class SQLResourceLoader {
    private static final Pattern SQL_IDENTIFIER = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*");

    private SQLResourceLoader() {
    }

    public static String loadQuery(String relativePath) {
        String resourcePath = normalize(relativePath);
        try (InputStream input = SQLResourceLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("SQL resource not found: " + resourcePath);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8).strip();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read SQL resource: " + resourcePath, e);
        }
    }

    public static String renderIdentifier(String template, String tokenName, String identifier) {
        return replaceToken(template, tokenName, validateIdentifier(identifier));
    }

    public static String renderValue(String template, String tokenName, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Replacement value cannot be blank for token " + tokenName + ".");
        }
        return replaceToken(template, tokenName, value);
    }

    public static String buildPlaceholders(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Placeholder count must be positive.");
        }
        return IntStream.range(0, count)
                .mapToObj(index -> "?")
                .collect(Collectors.joining(", "));
    }

    private static String validateIdentifier(String identifier) {
        if (identifier == null || !SQL_IDENTIFIER.matcher(identifier).matches()) {
            throw new IllegalArgumentException("Invalid SQL identifier: " + identifier);
        }
        return identifier;
    }

    private static String replaceToken(String template, String tokenName, String replacement) {
        return template.replace("${" + tokenName + "}", replacement);
    }

    private static String normalize(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            throw new IllegalArgumentException("SQL resource path cannot be blank.");
        }
        String trimmedPath = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
        return Configuration.SQL_QUERY_RESOURCE_PATH + "/" + trimmedPath;
    }
}
