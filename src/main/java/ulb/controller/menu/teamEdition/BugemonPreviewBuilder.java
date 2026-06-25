package ulb.controller.menu.teamEdition;

import java.util.*;
import ulb.configuration.Configuration;
import ulb.model.domain.*;
import ulb.repository.GameRepository;
import ulb.view.menu.teamEdition.TeamEditionView;

/**
 * Builds all UI data objects needed to display Bugemon information in the team editor:
 * available-card lists, detail previews, skill descriptions, and effect formatting.
 */
public class BugemonPreviewBuilder {

    private final GameRepository gameRepository;
    private final GameMode gameMode;

    public BugemonPreviewBuilder(GameRepository gameRepository, GameMode gameMode) {
        this.gameRepository = gameRepository;
        this.gameMode = gameMode;
    }

    /**
     * Central access rule for the "available Bugemons" grid.
     *
     * <p>Today only starters are selectable. A future reward/unlock system should
     * update this method instead of touching the view logic.</p>
     */
    public boolean isBugemonAccessible(Bugemon bugemon) {
        return bugemon != null && (bugemon.isStarter() || gameRepository.isBugemonUnlocked(bugemon.getId()));
    }

    public List<TeamEditionView.BugemonCardData> buildAvailableBugemonCards(List<Bugemon> bugemons) {
        return bugemons.stream()
                .map(b -> new TeamEditionView.BugemonCardData(b.getId(), b.getName(), isBugemonAccessible(b)))
                .toList();
    }

    public Optional<TeamEditionView.BugemonPreviewData> buildPreviewData(Bugemon bugemon) {
        if (bugemon == null) {
            return Optional.empty();
        }
        return Optional.of(new TeamEditionView.BugemonPreviewData(
                bugemon.getId(),
                bugemon.getName(),
                bugemon.getLevel(),
                gameMode != GameMode.FREE_BATTLE,
                bugemon.getElementDisplayName(),
                gameRepository.getDomainExpansionById(bugemon.getDomainExpansionId()).isPresent(), hasTransformation(bugemon), bugemon.getHp(), bugemon.getAttack(), bugemon.getDefense(), bugemon.getAttackMagic(), bugemon.getDefenseMagic(), bugemon.getInitiative(), buildSkillData(bugemon)
        ));
    }

    private boolean hasTransformation(Bugemon bugemon) {
        if (bugemon == null || bugemon.getId() == null || bugemon.getId().isBlank()) {
            return false;
        }
        String path = Configuration.bugemonEvolutionVideoResourcePathForId(bugemon.getId());
        return getClass().getResource(path) != null;
    }

    private List<TeamEditionView.SkillData> buildSkillData(Bugemon bugemon) {
        List<Skill> knownSkills = bugemon.getKnownSkills();
        if (knownSkills.isEmpty()) {
            return List.of();
        }
        List<Skill> equipped = bugemon.getSkills();
        return knownSkills.stream()
                .filter(Objects::nonNull)
                .map(skill -> {boolean isEquipped = equipped.stream().anyMatch(s -> s.getId().equals(skill.getId()));
                    return new TeamEditionView.SkillData(skill.getId(), skill.getName(), buildSkillDescription(skill), isEquipped);
                }).toList();
    }

    public TeamEditionView.SkillDescriptionData buildSkillDescription(Skill skill) {
        return new TeamEditionView.SkillDescriptionData(
                skill.getDescription(),
                skill.getElementDisplayName(),
                skill.getPower(),
                skill.getPrecision(),
                skill.getPriority(),
                skill.getAttackMagic(),
                skill.getEffects()
        );
    }
}

