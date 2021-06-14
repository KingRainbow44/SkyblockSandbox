package tk.skyblocksandbox.dungeonsandbox.catacombs;

import tk.skyblocksandbox.dungeonsandbox.dungeon.Dungeon;
import tk.skyblocksandbox.dungeonsandbox.util.Generation;

public final class FloorSix extends Dungeon {

    public FloorSix() {
        super("The Catacombs", "THE_CATACOMBS", 6, false);
        floorGeneration = Generation.FloorGenerationTypes.FLOOR_6;

        roomsTotal = 16;
    }

    @Override
    public void initializeBossRoom() {

    }

    @Override
    public void destroy(boolean forcibly) {

    }
}
