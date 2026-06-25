package ulb.model.team;

import org.junit.jupiter.api.Test;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Element;
import ulb.model.domain.Statistics;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTeam {

    @Test
    public void setNameTest() {
        Team t = new Team("A");
        assertEquals("A", t.getName());

        t.setName("B");
        assertEquals("B", t.getName());
    }

    @Test
    public void addBugemonTest() {
        Team t = new Team("A");
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);
        Bugemon b = new Bugemon("id", "Bug", Element.ICE, stats, "sprite.png", false);

        assertTrue(t.add(b));
        assertEquals(1, t.size());
        assertTrue(t.contains(b));
    }

    @Test
    public void preventDuplicateTest() {
        Team t = new Team("A");
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);
        Bugemon b = new Bugemon("id", "Bug", Element.ICE, stats, "sprite.png", false);

        assertTrue(t.add(b));
        assertFalse(t.add(b));

        assertEquals(1, t.size());
    }

    @Test
    public void addBugemonWhenNotPossibleTest() {
        Team t = new Team("A");
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);

        Bugemon b1 = new Bugemon("id1", "Bug", Element.ICE, stats, "sprite.png", false);
        Bugemon b2 = new Bugemon("id2", "Bug", Element.ICE, stats, "sprite.png", false);
        Bugemon b3 = new Bugemon("id3", "Bug", Element.ICE, stats, "sprite.png", false);
        Bugemon b4 = new Bugemon("id4", "Bug", Element.ICE, stats, "sprite.png", false);
        Bugemon b5 = new Bugemon("id5", "Bug", Element.ICE, stats, "sprite.png", false);
        Bugemon b6 = new Bugemon("id6", "Bug", Element.ICE, stats, "sprite.png", false);
        Bugemon b7 = new Bugemon("id7", "Bug", Element.ICE, stats, "sprite.png", false);

        assertTrue(t.add(b1));
        assertTrue(t.add(b2));
        assertTrue(t.add(b3));
        assertTrue(t.add(b4));
        assertTrue(t.add(b5));
        assertTrue(t.add(b6));
        assertTrue(t.isFull());

        assertFalse(t.add(b7));
        assertEquals(6, t.size());
    }

    @Test
    public void removeBugemonTest() {
        Team t = new Team("A");
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);
        Bugemon b = new Bugemon("id", "Bug", Element.ICE, stats, "sprite.png", false);

        t.add(b);
        assertEquals(1, t.size());

        assertTrue(t.remove(b));
        assertEquals(0, t.size());
        assertFalse(t.contains(b));
        assertFalse(t.isFull());
    }

    @Test
    public void removeBugemonWhenNotPossibleTest() {
        Team t = new Team("A");
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);
        Bugemon b = new Bugemon("id2", "Bug", Element.ICE, stats, "sprite.png", false);
        Bugemon other = new Bugemon("id1", "Bug", Element.ICE, stats, "sprite.png", false);

        assertFalse(t.remove(null));

        assertFalse(t.remove(b));

        assertTrue(t.add(b));
        assertFalse(t.remove(other));
        assertEquals(1, t.size());
    }

    @Test
    public void respectsMaxSizeTest() {
        Team t = new Team("A");

        assertEquals(6, t.getMaxSize());
        assertFalse(t.isFull());
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);

        t.add(new Bugemon("id1", "Bug", Element.ICE, stats, "sprite.png", false));
        t.add(new Bugemon("id2", "Bug", Element.ICE, stats, "sprite.png", false));
        t.add(new Bugemon("id3", "Bug", Element.ICE, stats, "sprite.png", false));
        t.add(new Bugemon("id4", "Bug", Element.ICE, stats, "sprite.png", false));
        t.add(new Bugemon("id5", "Bug", Element.ICE, stats, "sprite.png", false));
        t.add(new Bugemon("id6", "Bug", Element.ICE, stats, "sprite.png", false));

        assertTrue(t.isFull());
        assertEquals(6, t.size());
    }

    @Test
    public void clearTeamTest() {
        Team t = new Team("A");
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);
        t.add(new Bugemon("id1", "Bug", Element.ICE, stats, "sprite.png", false));
        t.add(new Bugemon("id2", "Bug", Element.ICE, stats, "sprite.png", false));

        assertEquals(2, t.size());

        t.clear();
        assertEquals(0, t.size());
        assertFalse(t.isFull());
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    public void getMembersIsUnmodifiableTest() {
        Team team = new Team("A");
        Statistics stats = new Statistics(100, 50, 40, 30, 10, 50);
        team.add(new Bugemon("id1", "Bug", Element.ICE, stats, "sprite.png", false));
        
        List<Bugemon> members = team.getMembers();
        assertThrows(UnsupportedOperationException.class, () -> {
            members.add(new Bugemon("id2", "Bug", Element.ICE, stats, "sprite.png", false));
        });
    }

    @Test
    public void checkHealthTest(){
        Team t = new Team("a");
        Statistics stats = new Statistics(80, 40, 40, 40, 10, 40);
        Statistics stats2 = new Statistics(0, 40, 40, 40, 10, 40);
        Bugemon pikachu = new Bugemon("id", "Bug", Element.ICE, stats, "sprite.png", false);
        Bugemon bulbizzare = new Bugemon("id", "Bug", Element.ICE, stats2, "sprite.png", false);
        t.add(bulbizzare);
        t.add(pikachu);
        assertSame(pikachu, t.getHealthyBugemon().orElseThrow());
    }
}

