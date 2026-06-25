package ulb.model.infrastructure;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Provides password hashing and verification utilities based on PBKDF2 (HMAC-SHA256).
 */
public class PasswordHasher {
    private static final int ITERATIONS = 65_536;
    private static final SecureRandom RANDOM = new SecureRandom();

    public PasswordHasher() {}

    /**
     * Generates a PBKDF2 hash with a random salt.
     *
     * @param password plain-text password
     * @return formatted hash "pbkdf2:iterations:base64(salt):base64(hash)"
     */
    public String hash(String password) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return "pbkdf2:" + ITERATIONS + ":" + encode(salt) + ":" + encode(pbkdf2(password.toCharArray(), salt, ITERATIONS));
    }

    /**
     * Checks whether a password matches the stored hash.
     *
     * @param password plain-text password
     * @param storedHash stored hash in the format "pbkdf2:iterations:base64(salt):base64(hash)"
     * @return true if the password matches, otherwise false
     */
    public boolean matches(String password, String storedHash) {
        try {
            if (password == null || storedHash == null) {
                return false;
            }

            String[] parts = storedHash.split(":");
            if (parts.length != 4 || !"pbkdf2".equals(parts[0])) {
                return false;
            }

            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = decode(parts[2]);
            byte[] expectedHash = decode(parts[3]);
            byte[] actualHash = pbkdf2(password.toCharArray(), salt, iterations);
            return MessageDigest.isEqual(expectedHash, actualHash);
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * Derives a key using PBKDF2 (HMAC-SHA256).
     *
     * @param password password characters
     * @param salt random salt
     * @param iterations iteration count
     * @return derived key bytes
     */
    private byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        try {
            KeySpec spec = new PBEKeySpec(password, salt, iterations, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to hash password.", e);
        }
    }

    private String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private byte[] decode(String value) {
        return Base64.getDecoder().decode(value);
    }
}
