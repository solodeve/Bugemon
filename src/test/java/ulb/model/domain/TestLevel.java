package ulb.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLevel {
    @Test
    public void shouldAddXpAndLevelUp() {
        Level level = new Level();
        long initialXp = level.getCurrentXp();

        level.addXpAndApplyLevelUps();

        assert (level.getCurrentXp() >= initialXp) : "XP should have increased";
    }

    @Test
    public void shouldCalculateXpForNextLevel() {
        Level level = new Level();
        assertEquals(0, level.calculateXpForNextLevel(1, 2000, 1.5));
        assertEquals(11, level.calculateXpForNextLevel(2, 2000, 1.5));
        assertEquals(24, level.calculateXpForNextLevel(3, 2000, 1.5));
        assertEquals(40, level.calculateXpForNextLevel(4, 2000, 1.5));
        assertEquals(58, level.calculateXpForNextLevel(5, 2000, 1.5));
    }

    @Test
    public void shouldCalculateXp() {
        Level level = new Level();
        assertEquals(90, level.calculateXp(1, 1, 3));
    }

    @Test
    public void shouldCalculateXpProgress() {
        Level level = new Level(3, 12);

        assertEquals(0.5, level.getXpProgress(), 0.0001);
    }

    @Test
    public void shouldLevelUp() {
        Level level = new Level();
        assertEquals(1, level.getCurrentLevel());
        level.levelUp();
        assertEquals(2, level.getCurrentLevel());
    }
}
