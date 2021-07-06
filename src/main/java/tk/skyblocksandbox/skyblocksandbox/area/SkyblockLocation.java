package tk.skyblocksandbox.skyblocksandbox.area;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.util.Cuboid;

public enum SkyblockLocation {

    NONE(),
    VILLAGE(false,
            new Location(Bukkit.getWorld(SkyblockSandbox.getConfiguration().hubWorld), -3, 70, -70, 180, 0),
            "&bVillage"
    ),
    DUNGEON_HUB(false,
            new Location(Bukkit.getWorld(SkyblockSandbox.getConfiguration().dungeonHubWorld), -31, 121, 0, 90, 0),
            "&cDungeon Hub"
    );

    private boolean subLocation = false;
    public Location teleportTo;
    public String displayName = "&7None";

    SkyblockLocation(boolean subLocation, Location teleportTo, String displayName) {
        this.subLocation = subLocation; this.teleportTo = teleportTo; this.displayName = displayName;
    }

    SkyblockLocation() {
        teleportTo = null;
    }

    public boolean isSubLocation() {
        return subLocation;
    }

    public Location getLocation() {
        return teleportTo;
    }

    public Cuboid getBoundingBox() {
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }

}
