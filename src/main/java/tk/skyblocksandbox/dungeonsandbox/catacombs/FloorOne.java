package tk.skyblocksandbox.dungeonsandbox.catacombs;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import tk.skyblocksandbox.dungeonsandbox.dungeon.Dungeon;
import tk.skyblocksandbox.dungeonsandbox.generator.VoidGenerator;
import tk.skyblocksandbox.dungeonsandbox.util.Generation;
import tk.skyblocksandbox.skyblocksandbox.util.Schematic;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

public final class FloorOne extends Dungeon {

    public FloorOne() {
        super("The Catacombs", "THE_CATACOMBS", 1, false);
        floorGeneration = Generation.FloorGenerationTypes.FLOOR_1;
    }

    /**
     * Called when the party first requests a dungeon entrance.
     */
    @Override
    public void initializeDungeon() {
        dungeonToken = Utility.generateRandomString();
        WorldCreator worldCreator = new WorldCreator(dungeonToken);
        worldCreator.generator(new VoidGenerator());

        org.bukkit.World bukkitWorld = worldCreator.createWorld();
        World world = BukkitAdapter.adapt(bukkitWorld);

        Utility.applyGamerules(bukkitWorld);

        // Entrance
        boolean pasted = Schematic.pasteSchematic(
                new Location(bukkitWorld, 0, 80, 0),
                "entrance", false
        ); if(!pasted) {
            Bukkit.getLogger().warning("Unable to paste entrance.schem, check CONSOLE for more details.");
            return;
        }

        // First Room

        // Finish Generation
        dungeonGenerationFinished = true;
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
