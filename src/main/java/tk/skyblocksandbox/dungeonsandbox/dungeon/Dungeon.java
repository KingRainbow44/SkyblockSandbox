package tk.skyblocksandbox.dungeonsandbox.dungeon;

import org.bukkit.*;
import tk.skyblocksandbox.dungeonsandbox.generator.VoidGenerator;
import tk.skyblocksandbox.dungeonsandbox.generator.dungeon.Rooms;
import tk.skyblocksandbox.dungeonsandbox.util.GenerationUtil;
import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Schematic;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Dungeon {

    /*
     * Dungeon Settings
     */
    protected boolean isMaster = false;
    protected boolean hasBoss = true;

    /*
     * Dungeon Data
     */
    protected String friendlyName = ChatColor.RED + "The Catacombs";
    protected int dungeonFloor = 0;

    public int maxRooms = 2; // Rooms are counted in 1x1s. For example, a 2x2 or 'L' room are 4 and 3 rooms respectively.

    /*
     * Generation Variables
     */
    public String dungeonToken = "";

    public int roomsGenerated = 0;
    public int roomsBeforeFairy = 1;
    public int roomsBeforeBlood = 2;

    public boolean generationFinished = false;
    public boolean entranceGenerated = false;
    public boolean fairyRoomGenerated = false;
    public boolean bloodRoomGenerated = false;

    public Map<Rooms, Location> roomLayout = new HashMap<>(); // A HashMap of Rooms to the center of the copy location.
    public Collection<Rooms> roomsPasted = new ArrayList<>();

    /**
     * Blank constructor to account for debugging reasons.
     */
    public Dungeon() {

    }

    /**
     * Generic constructor. Should be used for most floors.
     * @param friendlyName The name used on the scoreboard.
     * @param totalRooms The total amount of rooms to generate. Counted in 1x1s. Read note about counting.
     * @param masterFloor Should the floor be marked as a 'master floor'. Enemies are harder, and bosses could be different.
     */
    public Dungeon(String friendlyName, int totalRooms, boolean masterFloor) {
        this.friendlyName = friendlyName;
        this.maxRooms = totalRooms;
        this.isMaster = masterFloor;
    }

    /**
     * Semi-generic constructor. Not used often.
     * @param friendlyName The name used on the scoreboard.
     * @param totalRooms The total amount of rooms to generate. Counted in 1x1s. Read note about counting.
     * @param masterFloor Should the floor be marked as a 'master floor'. Enemies are harder, and bosses could be different.
     * @param hasBoss Should the floor's blood room take you to another location to fight a boss.
     */
    public Dungeon(String friendlyName, int totalRooms, boolean masterFloor, boolean hasBoss) {
        this.friendlyName = friendlyName;
        this.maxRooms = totalRooms;
        this.isMaster = masterFloor;
        this.hasBoss = hasBoss;
    }

    /**
     * Totally useless constructor. Not used ever.
     * @param friendlyName The name used on the scoreboard.
     * @param roomLayout The room layout that should be used. Allows for repeating dungeons to work.
     * @param masterFloor Should the floor be marked as a 'master floor'. Enemies are harder, and bosses could be different.
     * @param hasBoss Should the floor's blood room take you to another location to fight a boss.
     */
    public Dungeon(String friendlyName, Map<Rooms, Location> roomLayout, boolean masterFloor, boolean hasBoss) {
        this.friendlyName = friendlyName;
        this.roomLayout = roomLayout;
        this.isMaster = masterFloor;
        this.hasBoss = hasBoss;
    }

    /**
     * Generate rooms & teleport players.
     */
    public final void initializeDungeon() {
        boolean pasted; // Create variable used for later.

        dungeonToken = "dungeon_" + Utility.generateRandomString(); // Set dungeon token. Used for identifying the world.

        WorldCreator worldCreator = new WorldCreator(dungeonToken); // Create a world creator instance.
        worldCreator.generator(new VoidGenerator()); // Set the generator to VoidGenerator.

        World world = worldCreator.createWorld(); // Create the world.
        assert world != null; // Assert the world as not null. Should not be null.

        Utility.applyGamerules(world); // Apply game-rules to the world.

        pasted = Schematic.pasteSchematic(
                new Location(world, 0, 108, 0),
                "entrance", false
        ); if(!pasted) {
            Bukkit.getLogger().warning("Unable to paste entrance.schem, check CONSOLE for more details.");
            return;
        } entranceGenerated = true;

        for(int i = 1; i <= maxRooms; i++) {
            Rooms room = GenerationUtil.random(this, roomsPasted);
            roomsPasted.add(room);

            int z = i * 32;
            pasted = Schematic.pasteSchematic(
                    new Location(world, 0, 108, z),
                    room.getSchematic(), false
            ); if(!pasted) {
                Bukkit.getLogger().warning("Unable to paste " + Utility.changeCase(room.name(), false) + ".schem, check CONSOLE for more details.");
                return;
            }
        }
    }

    public final void warpParty(PartyInstance party) {
        World world = Bukkit.getWorld(dungeonToken);
        assert world != null;

        for(SkyblockPlayer sbPlayer : party.getMembers()) {
            sbPlayer.getBukkitPlayer().teleport(
                    new Location(world, 0, 110, 0)
            );
        }
    }

    /*
     * Get Methods
     */

    public boolean isMaster() {
        return isMaster;
    }

    public boolean hasBoss() {
        return hasBoss;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public int getDungeonFloor() {
        return dungeonFloor;
    }

    public Map<Rooms, Location> getRoomLayout() {
        return roomLayout;
    }

    /*
     * Enum
     */

    public enum Types {
        THE_CATACOMBS_0,
        THE_CATACOMBS_1,
        THE_CATACOMBS_2,
        THE_CATACOMBS_3,
        THE_CATACOMBS_4,
        THE_CATACOMBS_5,
        THE_CATACOMBS_6,
        THE_CATACOMBS_7,

        THE_CATACOMBS_8,
        THE_CATACOMBS_9,
        THE_CATACOMBS_10
    }
}
