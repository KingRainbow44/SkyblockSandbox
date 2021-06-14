package tk.skyblocksandbox.dungeonsandbox.catacombs;

import tk.skyblocksandbox.dungeonsandbox.dungeon.Dungeon;
import tk.skyblocksandbox.dungeonsandbox.util.Generation;

public final class FloorOne extends Dungeon {

    public FloorOne() {
        super("The Catacombs", "THE_CATACOMBS", 1, false);
        floorGeneration = Generation.FloorGenerationTypes.FLOOR_1;

        roomsTotal = 7;
    }

    /**
     * After beating the room at the end of the dungeon, this is called.
     */
    @Override
    public void initializeBossRoom() {

    }

    /**
     * Called when a dungeon instance breaks.
     * This is a "force-close," to destroy the instance correctly, use DungeonInstance#remove()
     */
    @Override
    public void destroy(boolean forcibly) {

    }
}
