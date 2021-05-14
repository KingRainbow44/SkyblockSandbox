package tk.skyblocksandbox.dungeonsandbox.catacombs;

import tk.skyblocksandbox.dungeonsandbox.dungeon.Dungeon;

public final class FloorOne extends Dungeon {

    public FloorOne() {
        super("The Catacombs", "THE_CATACOMBS", 1, false);
    }

    /**
     * Called when the party first requests a dungeon entrance.
     */
    @Override
    public void initializeDungeon() {

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
