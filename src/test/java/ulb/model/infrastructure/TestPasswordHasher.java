package ulb.model.infrastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestPasswordHasher {
    @Test
    void hashesPasswordWithSaltAndVerifiesIt() {
        PasswordHasher passwordHasher = new PasswordHasher();

        String firstHash = passwordHasher.hash("secret");
        String secondHash = passwordHasher.hash("secret");

        assertNotEquals("secret", firstHash);
        assertNotEquals(firstHash, secondHash);
        assertTrue(passwordHasher.matches("secret", firstHash));
        assertFalse(passwordHasher.matches("wrong", firstHash));
    }
}
