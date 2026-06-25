package ulb.controller.battle;

import java.util.List;
import java.util.Optional;

import ulb.event.BattleMessage;
import ulb.exception.MissingSkillException;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.service.battle.BattleSystem;
import ulb.view.battle.state.BattleSkillsState;
import ulb.view.battle.state.BattleUiState;
import ulb.view.battle.state.ColouredMessage;
import ulb.view.battle.BattleView;
import ulb.view.battle.state.SpecialUiState;

/**
 * Builds all UI state objects needed to render the battle screen:
 * Bugemon stats, skill buttons, special gauge, XP bar, and coloured messages.
 */
class BattleUiBuilder {

    private final BattleSystem battleSystem;

    BattleUiBuilder(BattleSystem battleSystem) {
        this.battleSystem = battleSystem;
    }

    BattleUiState buildBattleUiState() {
        return BattleUiState.of(
                battleSystem.getActiveBugemonOfPlayer(),
                battleSystem.getActiveBugemonOfEnemy(),
                battleSystem.isFreeBattleMode(),
                battleSystem.getActiveBackgroundPath(),
                buildSpecialUiState()
        );
    }

    SpecialUiState buildSpecialUiState() {
        String domainName = battleSystem.getPlayerDomainName();
        boolean hasDomain = domainName != null && !domainName.isBlank();
        if (!hasDomain) {
            return SpecialUiState.hidden();
        }
        int charge = Math.max(0, battleSystem.getPlayerSpecialCharge());
        int cost = Math.max(0, battleSystem.getPlayerDomainCost());
        double progress = cost <= 0 ? 0.0 : Math.min(1.0, (double) charge / cost);
        return new SpecialUiState(true, true, battleSystem.canPlayerUseDomain(),
                progress, charge, cost);
    }

    BattleSkillsState buildBattleSkillsState(Bugemon player) {
        if (player == null) {
            return BattleSkillsState.empty();
        }
        List<Skill> skills = player.getSkills();
        return new BattleSkillsState(
                getRequiredSkill(skills, 0).getName(),
                getRequiredSkill(skills, 1).getName(),
                getRequiredSkill(skills, 2).getName()
        );
    }

    ulb.model.domain.Element getSkillElementOrNull(List<Skill> skills, int index) {
        return getRequiredSkill(skills, index).getElement();
    }

    private Skill getRequiredSkill(List<Skill> skills, int index) {
        if (skills == null || index < 0 || index >= skills.size()) {
            throw new MissingSkillException("Missing skill at index " + index + ".");
        }
        return skills.get(index);
    }

    ColouredMessage toViewMessage(BattleMessage message) {
        return new ColouredMessage(
                message.text(), message.colour(),
                Optional.of(buildBattleUiState()),
                false,
                message.attackElement(), message.playerAttacking(),
                message.attackMagic(), message.healingSound(),
                message.type(), message.deathSound(), message.impactSound(),
                message.switchSound(), message.boostSound()
        );
    }

    void updateXpBarForBugemon(BattleView view, Bugemon bugemon) {
        if (bugemon == null) {
            view.updateXpBar(0.0, 0, 0);
            return;
        }
        view.updateXpBar(bugemon.getXpProgress(), bugemon.getCurrentXp(), bugemon.getXpForNextLevel());
    }
}
