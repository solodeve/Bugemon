package ulb.model.leaderboard;

/**
 * Represents statistics collected during a single NO tower run.
 *
 * @param floorReached the highest floor reached during the run
 * @param combatsWon the total number of combats won
 * @param bugemonsDefeated the total number of enemy bugemons defeated
 * @param bugemonsLost the number of the player's bugemons that were lost
 * @param flawlessFloors the number of floors completed without losing any bugemon
 */
public record TowerRunStat(
        int floorReached,
        int combatsWon,
        int bugemonsDefeated,
        int bugemonsLost,
        int flawlessFloors
) {}
