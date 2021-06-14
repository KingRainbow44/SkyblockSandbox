package tk.skyblocksandbox.dungeonsandbox.dungeon;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import tk.skyblocksandbox.dungeonsandbox.catacombs.FloorOne;
import tk.skyblocksandbox.dungeonsandbox.catacombs.FloorSix;
import tk.skyblocksandbox.dungeonsandbox.generator.VoidGenerator;
import tk.skyblocksandbox.dungeonsandbox.util.Generation;
import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Schematic;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.Map;

public abstract class Dungeon {

    private final String friendlyName;
    private final String dungeonName;
    private final int dungeonFloor;
    private final boolean isMasterMode;

    public static final String CATACOMBS = "THE_CATACOMBS";

    protected String dungeonToken = "";
    protected Generation.AvailableRooms lastRoomGenerated = null;
    protected Generation.FloorGenerationTypes floorGeneration = null;
    protected boolean dungeonGenerationFinished = false;

    protected int roomsBeforeBlood = 5;
    protected int roomsBeforeFairy = 2;
    protected int roomsTotal = 2; // In 1x1s

    public boolean bloodRoomGenerated = false;
    public boolean fairyRoomGenerated = false;
    public boolean entranceGenerated = false;

    public Dungeon(String friendlyName, String internalName, int dungeonFloor, boolean masterMode) {
        this.friendlyName = friendlyName;
        this.dungeonName = internalName;
        this.dungeonFloor = dungeonFloor;
        isMasterMode = masterMode;
    }

    public String getDungeonName() {
        return friendlyName;
    }

    public String getInternalName() {
        return dungeonName;
    }

    public int getDungeonFloor() {
        return dungeonFloor;
    }

    public boolean isMasterModeFloor() {
        return isMasterMode;
    }

    public Generation.AvailableRooms getLastRoomGenerated() {
        return lastRoomGenerated;
    }

    /*
     * Methods
     */
    public void warpParty(PartyInstance party) {
        if(!dungeonGenerationFinished) return;
        World world = Bukkit.getWorld(dungeonToken);

        for(SkyblockPlayer member : party.getMembers()) {
            member.getBukkitPlayer().teleport(new Location(world, 0.5, 81, 0.5));
        }
    }

    /*
     * Static Methods
     */

    public static Dungeon getDungeon(String dungeonName, int dungeonFloor) {
        switch(dungeonName) {
            default:
                return null;
            case CATACOMBS:
                switch(dungeonFloor) {
                    case 1:
                        return new FloorOne();
                    case 6:
                        return new FloorSix();
                }
                return null;
        }
    }

    /*
     * Abstract Methods
     */

    /**
     * Called when the party first requests a dungeon entrance.
     */
    public void initializeDungeon() {
        dungeonToken = "dungeon_" + Utility.generateRandomString();
        WorldCreator worldCreator = new WorldCreator(dungeonToken);
        worldCreator.generator(new VoidGenerator());

        org.bukkit.World bukkitWorld = worldCreator.createWorld();
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(bukkitWorld);

        Generation generation = new Generation();

        assert bukkitWorld != null;
        Utility.applyGamerules(bukkitWorld);

        boolean pasted;

        // Entrance
        pasted = Schematic.pasteSchematic(
                new Location(bukkitWorld, 0, 108, 0),
                "entrance", false
        ); if(!pasted) {
            Bukkit.getLogger().warning("Unable to paste entrance.schem, check CONSOLE for more details.");
            return;
        } entranceGenerated = true;

        // Other Rooms
        for(int i = 1; i <= roomsTotal; i++) {
            int z = i * 32;
            Generation.AvailableRooms room = generation.generateRandomRoom(this);
            pasted = Schematic.pasteSchematic(
                    new Location(bukkitWorld, 0, 109, z),
                    generation.enumToSchematic(room), false
            ); if(!pasted) {
                Bukkit.getLogger().warning("Unable to paste " + Utility.changeCase(room.name(), false) + ".schem, check CONSOLE for more details.");
                return;
            }
        }

        // Finish Generation
        dungeonGenerationFinished = true;
    }

    /**
     * After beating the room at the end of the dungeon, this is called.
     */
    public abstract void initializeBossRoom();

    /**
     * Called when a dungeon instance breaks.
     * This is a "force-close," to destroy the instance correctly, use DungeonInstance#remove()
     */
    public abstract void destroy(boolean forcibly);

}
