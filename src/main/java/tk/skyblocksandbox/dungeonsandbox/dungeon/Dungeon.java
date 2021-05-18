package tk.skyblocksandbox.dungeonsandbox.dungeon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import tk.skyblocksandbox.dungeonsandbox.catacombs.FloorOne;
import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public abstract class Dungeon {

    private final String friendlyName;
    private final String dungeonName;
    private final int dungeonFloor;
    private final boolean isMasterMode;

    public static final String CATACOMBS = "THE_CATACOMBS";

    protected String dungeonToken = "";
    protected boolean dungeonGenerationFinished = false;

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

    public void warpParty(PartyInstance party) {
        if(!dungeonGenerationFinished) return;
        World world = Bukkit.getWorld(dungeonToken);

        for(SkyblockPlayer member : party.getMembers()) {
            member.getBukkitPlayer().teleport(new Location(world, 0, 81, 0));
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
    public abstract void initializeDungeon();

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
