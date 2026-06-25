package ulb.model.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ulb.controller.battle.BattleController;
import ulb.event.BattleMessage;
import ulb.model.domain.effect.HealingEffect;
import ulb.model.domain.effect.Target;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.repository.IGameRepository;
import ulb.service.battle.BattleSystem;
import ulb.model.inventory.Item;
import ulb.repository.GameRepository;
import ulb.model.team.Team;
import ulb.service.team.EnemyTeamFactory;
import ulb.view.battle.state.ColouredMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TestBattleSystem {
    private InputStream originalIn;
    private IGameRepository repo;

    @BeforeEach
    public void saveInput() {
        originalIn = System.in;
        repo = mock(IGameRepository.class);
    }

    @AfterEach
    public void restoreInput() {
        System.setIn(originalIn);
    }

    @Test
    public void testRunMoveDealsDamage() {
        BattleSystem battleSystem = new BattleSystem(repo);

        Bugemon attacker = new Bugemon(
                "id-attacker",
                "Attacker",
                Element.ICE,
                new Statistics(100, 10, 10, 10, 10, 10),
                "sprite.png",
                false
        );

        Bugemon target = new Bugemon(
                "id-target",
                "Target",
                Element.MECHA,
                new Statistics(100, 10, 10, 10, 10, 10),
                "sprite.png",
                false
        );

        Skill skill = new Skill("s1", "Hit", Element.ICE, "Simple hit", 40, 1, 0, true);
        int hpBefore = target.getStats().getHp();

        battleSystem.runMove(attacker, target, skill);

        assertTrue(target.getStats().getHp() < hpBefore);
        assertTrue(target.isHealthy());
    }

    @Test
    public void testHigherPrioritySkillActsFirst() {
        BattleSystem battleSystem = new BattleSystem(repo);

        Bugemon player = new Bugemon(
                "player",
                "Player",
                Element.PLANT,
                new Statistics(20, 20, 10, 50, 10, 20),
                "player.png",
                false
        );
        Bugemon enemy = new Bugemon(
                "enemy",
                "Enemy",
                Element.FIRE,
                new Statistics(20, 20, 10, 10, 10, 20),
                "enemy.png",
                false
        );

        player.learnSkill(new Skill("slow", "Slow", Element.PLANT, "slow hit", 500, 1, 0, false));
        enemy.learnSkill(new Skill("fast", "Fast", Element.FIRE, "fast hit", 500, 1, 1, false));

        Team playerTeam = new Team("Player");
        playerTeam.add(player);
        Team enemyTeam = new Team("Enemy");
        enemyTeam.add(enemy);

        battleSystem.initializeBattleState(playerTeam, enemyTeam);
        battleSystem.executePlayerSkill(0);

        assertEquals(BattleState.END, battleSystem.getBattleState());
        assertFalse(battleSystem.getHasWon(), "The enemy should win by acting first thanks to higher skill priority");
        assertTrue(enemy.isHealthy(), "The player should not act after being knocked out");
    }

    @Test
    public void testHigherInitiativeActsFirstWhenPriorityIsEqual() {
        BattleSystem battleSystem = new BattleSystem(repo);

        Bugemon player = new Bugemon(
                "player",
                "Player",
                Element.PLANT,
                new Statistics(20, 20, 10, 20, 10, 20),
                "player.png",
                false
        );
        Bugemon enemy = new Bugemon(
                "enemy",
                "Enemy",
                Element.FIRE,
                new Statistics(20, 20, 10, 40, 10, 20),
                "enemy.png",
                false
        );

        player.learnSkill(new Skill("p1", "Player Hit", Element.PLANT, "player hit", 500, 1, 0, false));
        enemy.learnSkill(new Skill("e1", "Enemy Hit", Element.FIRE, "enemy hit", 500, 1, 0, false));

        Team playerTeam = new Team("Player");
        playerTeam.add(player);
        Team enemyTeam = new Team("Enemy");
        enemyTeam.add(enemy);

        battleSystem.initializeBattleState(playerTeam, enemyTeam);
        battleSystem.executePlayerSkill(0);

        assertEquals(BattleState.END, battleSystem.getBattleState());
        assertFalse(battleSystem.getHasWon(), "The enemy should win by acting first thanks to higher initiative");
        assertTrue(enemy.isHealthy(), "The player should not act after being knocked out");
    }

    @Test
    public void testBattleOverSetsEndState() {
        BattleSystem battleSystem = new BattleSystem(repo);

        Bugemon attacker = new Bugemon(
                "id-attacker",
                "Attacker",
                Element.ICE,
                new Statistics(100, 10, 10, 10, 10, 10),
                "sprite.png",
                false
        );

        Team playerTeam = new Team("");
        playerTeam.add(attacker);
        battleSystem.setPlayerTeam(playerTeam);
        battleSystem.executePlayerSkill(0);
        battleSystem.endBattleAndProcess(false);

        assertEquals(BattleState.END, battleSystem.getBattleState());
        assertFalse(battleSystem.getHasWon());
    }

    @Test
    public void testEnemyAndAllyWithSameBugemonAreDifferentReferences() throws IOException {
        DatabaseBugemon db = new DatabaseBugemon();
        Assumptions.assumeTrue(isDatabaseAvailable(db), "Skipping: database not reachable.");

        GameRepository repository = new GameRepository();
        repository.initialize();

        Bugemon catalogBugemon = repository.getBugemonById("florachu").orElseThrow();
        EnemyTeamFactory factory = new EnemyTeamFactory(repository);
        Team enemyTeam = factory.create(1, 42L);

        enemyTeam.getMembers().forEach(member ->
                assertNotSame(catalogBugemon, member,
                        "Enemy team members must not be direct references to catalog bugemons"));
        enemyTeam.getMembers().forEach(member ->
                assertNotSame(catalogBugemon.getStats(), member.getStats(),
                        "Enemy team member stats must not share catalog stat references"));
    }

    private static boolean isDatabaseAvailable(DatabaseBugemon db) {
        if (!db.isConfigured()) return false;
        try (Connection ignored = db.connect()) {
            db.executeSeedFiles();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Test
    public void testRestoreHp() {
        BattleSystem battleSystem = new BattleSystem(repo);

        Bugemon attacker = new Bugemon(
                "id-attacker",
                "Attacker",
                Element.ICE,
                new Statistics(100, 10, 10, 10, 10, 10),
                "sprite.png",
                false
        );

        Team playerTeam = new Team("");
        playerTeam.add(attacker);
        battleSystem.setPlayerTeam(playerTeam);

        Bugemon bugemon = playerTeam.getMembers().getFirst();
        Statistics stats = bugemon.getStats();
        stats.setHp(0);
        playerTeam.restoreAllHp();
        Team team = battleSystem.getPlayerTeam();
        Bugemon bugemon2 = team.getMembers().getFirst();
        assertEquals(bugemon.getStats().getMaxHp(), bugemon2.getStats().getMaxHp());
    }

    @Test
    public void testDamageSnapshotMatchesTargetBeforeReplacement() {
        GameRepository repository = new GameRepository();
        BattleController battleController = new BattleController(null, repository);
        BattleSystem battleSystem = battleController.getBattleSystem();

        Bugemon player = new Bugemon(
                "player",
                "Player",
                Element.ICE,
                new Statistics(100, 30, 10, 50, 10, 30),
                "player.png",
                false
        );
        player.learnSkill(new Skill("kill", "Finisher", Element.ICE, "finishing hit", 500, 1, 1, false));

        Bugemon enemyOne = new Bugemon(
                "enemy-1",
                "Enemy One",
                Element.FIRE,
                new Statistics(10, 10, 10, 10, 10, 10),
                "enemy1.png",
                false
        );
        enemyOne.learnSkill(new Skill("poke", "Poke", Element.FIRE, "small hit", 10, 1, 0, false));

        Bugemon enemyTwo = new Bugemon(
                "enemy-2",
                "Enemy Two",
                Element.PLANT,
                new Statistics(40, 10, 10, 10, 10, 10),
                "enemy2.png",
                false
        );
        enemyTwo.learnSkill(new Skill("poke2", "Poke 2", Element.PLANT, "small hit", 10, 1, 0, false));

        Team playerTeam = new Team("Player");
        playerTeam.add(player);
        Team enemyTeam = new Team("Enemy");
        enemyTeam.add(enemyOne);
        enemyTeam.add(enemyTwo);

        List<ColouredMessage> messages = new ArrayList<>();
        battleSystem.setOnMessageUpdate(message -> messages.add(battleController.toViewMessage(message)));

        battleSystem.initializeBattleState(playerTeam, enemyTeam);
        battleSystem.executePlayerSkill(0);

        ColouredMessage damageMessage = messages.stream()
                .filter(message -> message.text().contains("Enemy One took"))
                .findFirst()
                .orElse(null);
        assertNotNull(damageMessage);
        assertNotNull(damageMessage.getBattleUiState());
        assertNotNull(damageMessage.getBattleUiState().enemy());
        assertEquals("Enemy One", damageMessage.getBattleUiState().enemy().name());
        assertEquals(0, damageMessage.getBattleUiState().enemy().currentHp());

        ColouredMessage replacementMessage = messages.stream()
                .filter(message -> message.text().contains("The opponent sends"))
                .findFirst()
                .orElse(null);
        assertNotNull(replacementMessage);
        assertNotNull(replacementMessage.getBattleUiState());
        assertNotNull(replacementMessage.getBattleUiState().enemy());
        assertEquals("Enemy Two", replacementMessage.getBattleUiState().enemy().name());
        assertEquals("The opponent sends Enemy Two !", replacementMessage.text());
    }

    @Test
    public void testPlayerBugemonLosesAndSwitchesToNext() {
        BattleSystem battleSystem = new BattleSystem(repo);

        Bugemon playerOne = new Bugemon("p1", "PlayerOne", Element.ICE,
                new Statistics(5, 5, 5, 5, 5, 5), "p1.png", false);
        playerOne.learnSkill(new Skill("weak", "Weak", Element.ICE, "weak hit", 1, 1, 0, false));

        Bugemon playerTwo = new Bugemon("p2", "PlayerTwo", Element.ICE,
                new Statistics(100, 10, 10, 10, 10, 10), "p2.png", false);
        playerTwo.learnSkill(new Skill("weak2", "Weak2", Element.ICE, "weak hit", 1, 1, 0, false));

        Bugemon enemy = new Bugemon("e1", "Enemy", Element.FIRE,
                new Statistics(100, 100, 10, 100, 10, 10), "e1.png", false);
        enemy.learnSkill(new Skill("kill", "Finisher", Element.FIRE, "one shot", 9999, 1, 1, false));

        Team playerTeam = new Team("Player");
        playerTeam.add(playerOne);
        playerTeam.add(playerTwo);
        Team enemyTeam = new Team("Enemy");
        enemyTeam.add(enemy);

        List<String> messages = new ArrayList<>();
        battleSystem.setOnMessageUpdate(message -> messages.add(message.text()));

        battleSystem.initializeBattleState(playerTeam, enemyTeam);
        battleSystem.executePlayerSkill(0);

        assertEquals(BattleState.ACTION_SELECTION, battleSystem.getBattleState());
        assertEquals(playerTwo, battleSystem.getActiveBugemonOfPlayer());
        assertFalse(playerOne.isHealthy());
        assertTrue(messages.contains("PlayerOne has been defeated !"));
        assertTrue(messages.contains("Your turn PlayerTwo !"));
        assertFalse(messages.stream().anyMatch(message -> message.contains("PlayerOne is down ! Your turn")));
    }

    @Test
    public void testItemUsagePublishesEffectMessageAfterUseMessage() {
        BattleSystem battleSystem = new BattleSystem(repo);

        Bugemon player = new Bugemon("p1", "PlayerOne", Element.ICE,
                new Statistics(100, 10, 10, 10, 10, 10), "p1.png", false);
        player.setHp(40);

        Bugemon enemy = new Bugemon("e1", "Enemy", Element.FIRE,
                new Statistics(100, 10, 10, 10, 10, 10), "e1.png", false);

        Team playerTeam = new Team("Player");
        playerTeam.add(player);
        Team enemyTeam = new Team("Enemy");
        enemyTeam.add(enemy);

        Item potion = new Item(
                "potion",
                "Potion",
                "Restores HP",
                "heal",
                new HealingEffect(Target.CASTER, 25)
        );
        playerTeam.fillInventory(Map.of(potion, 1));

        List<String> messages = new ArrayList<>();
        battleSystem.setOnMessageUpdate(message -> messages.add(message.text()));

        battleSystem.initializeBattleState(playerTeam, enemyTeam);
        battleSystem.executePlayerItem(potion);

        assertTrue(messages.size() >= 2);
        assertEquals("PlayerOne uses Potion!", messages.get(0));
        assertEquals("PlayerOne heals for 25 HP !", messages.get(1));
        assertEquals(65, player.getHp());
    }

    @Test
    public void testInitializeBattleStateRejectsNullTeams() {
        BattleSystem battleSystem = new BattleSystem(repo);
        Team team = new Team("Player");

        assertThrows(IllegalArgumentException.class, () -> battleSystem.initializeBattleState(null, team));
        assertThrows(IllegalArgumentException.class, () -> battleSystem.initializeBattleState(team, null));
    }

    @Test
    public void testExecutePlayerSkillWithInvalidIndexPostsErrorMessage() {
        BattleSystem battleSystem = new BattleSystem(repo);

        Bugemon player = new Bugemon(
                "player",
                "Player",
                Element.PLANT,
                new Statistics(20, 20, 10, 20, 10, 20),
                "player.png",
                false
        );
        player.learnSkill(new Skill("p1", "Player Hit", Element.PLANT, "player hit", 20, 1, 0, false));

        Bugemon enemy = new Bugemon(
                "enemy",
                "Enemy",
                Element.FIRE,
                new Statistics(20, 20, 10, 20, 10, 20),
                "enemy.png",
                false
        );
        enemy.learnSkill(new Skill("e1", "Enemy Hit", Element.FIRE, "enemy hit", 20, 1, 0, false));

        Team playerTeam = new Team("Player");
        playerTeam.add(player);
        Team enemyTeam = new Team("Enemy");
        enemyTeam.add(enemy);

        List<BattleMessage> messages = new ArrayList<>();
        battleSystem.setOnMessageUpdate(messages::add);

        battleSystem.initializeBattleState(playerTeam, enemyTeam);
        battleSystem.executePlayerSkill(99);

        assertEquals(BattleState.ACTION_SELECTION, battleSystem.getBattleState());
        assertEquals("This skill isn't available.", messages.getLast().text());
    }

    @Test
    public void testSelectBugemonRejectsFaintedMember() {
        BattleSystem battleSystem = new BattleSystem(repo);

        Bugemon healthyPlayer = new Bugemon(
                "player-1",
                "Healthy",
                Element.PLANT,
                new Statistics(30, 20, 10, 20, 10, 20),
                "player.png",
                false
        );
        healthyPlayer.learnSkill(new Skill("p1", "Player Hit", Element.PLANT, "player hit", 20, 1, 0, false));

        Bugemon faintedPlayer = new Bugemon(
                "player-2",
                "Fainted",
                Element.ICE,
                new Statistics(30, 20, 10, 20, 10, 20),
                "player2.png",
                false
        );
        faintedPlayer.learnSkill(new Skill("p2", "Player Hit 2", Element.ICE, "player hit", 20, 1, 0, false));
        faintedPlayer.getStats().setHp(0);

        Bugemon enemy = new Bugemon(
                "enemy",
                "Enemy",
                Element.FIRE,
                new Statistics(20, 20, 10, 20, 10, 20),
                "enemy.png",
                false
        );
        enemy.learnSkill(new Skill("e1", "Enemy Hit", Element.FIRE, "enemy hit", 20, 1, 0, false));

        Team playerTeam = new Team("Player");
        playerTeam.add(healthyPlayer);
        playerTeam.add(faintedPlayer);
        Team enemyTeam = new Team("Enemy");
        enemyTeam.add(enemy);

        List<BattleMessage> messages = new ArrayList<>();
        battleSystem.setOnMessageUpdate(messages::add);

        battleSystem.initializeBattleState(playerTeam, enemyTeam);
        battleSystem.selectBugemon(faintedPlayer);

        assertEquals(healthyPlayer, battleSystem.getActiveBugemonOfPlayer());
        assertEquals("Fainted is down and can't fight.", messages.getLast().text());
    }

    @Test
    public void testBlankPersistenceTypeFallsBackToDefaultAndEnablesFreeBattleMode() {
        BattleSystem battleSystem = new BattleSystem(repo);

        battleSystem.setTeamPersistenceType("   ");

        assertTrue(battleSystem.isFreeBattleMode());
    }

    @Test
    public void testFreeBattlePersistenceTypeEnablesFreeBattleMode() {
        BattleSystem battleSystem = new BattleSystem(repo);

        battleSystem.setTeamPersistenceType(GameMode.FREE_BATTLE.name());

        assertTrue(battleSystem.isFreeBattleMode());
    }

    @Test
    public void testNoTowerPersistenceTypeDisablesFreeBattleMode() {
        BattleSystem battleSystem = new BattleSystem(repo);

        battleSystem.setTeamPersistenceType(GameMode.NO_TOWER.name());

        assertFalse(battleSystem.isFreeBattleMode());
    }
}