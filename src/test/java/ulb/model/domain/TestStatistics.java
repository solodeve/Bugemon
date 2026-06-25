package ulb.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestStatistics {
    @Test
    public void shouldCreateValidStatistics() {
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);
        assertEquals(100, stats.getHp());
        assertEquals(50, stats.getAttack());
        assertEquals(40, stats.getDefense());
        assertEquals(30, stats.getInitiative());
    }

    @Test
    public void shouldThrowExceptionForNegativeValues() {
        assertThrows(IllegalArgumentException.class, () -> new Statistics(-1, 50, 40, 30, 10, 50));
        assertThrows(IllegalArgumentException.class, () -> new Statistics(100, -5, 40, 30, 10, 50));
        assertThrows(IllegalArgumentException.class, () -> new Statistics(100, 5, -40, 30, 10, 50));
        assertThrows(IllegalArgumentException.class, () -> new Statistics(100, 5, 40, -30, 10, 50));

    }

    @Test
    public void shouldUpdateValuesCorrectly() {
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);
        stats.setHp(120);
        assertEquals(120, stats.getHp());
    }
}
