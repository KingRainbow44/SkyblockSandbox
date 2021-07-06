package tk.skyblocksandbox.dungeonsandbox.generator.dungeon.roomData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.dungeonsandbox.generator.dungeon.IRoomData;

public final class RoomSecret implements IRoomData {

    private final int xOffset, yOffset, zOffset;
    private final Object secretType;

    public RoomSecret(int xOffset, int yOffset, int zOffset, Material secretType) {
        this.xOffset = xOffset; this.yOffset = yOffset; this.zOffset = zOffset;
        this.secretType = secretType;
    }

    public RoomSecret(int xOffset, int yOffset, int zOffset, EntityType secretType) {
        this.xOffset = xOffset; this.yOffset = yOffset; this.zOffset = zOffset;
        this.secretType = secretType;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public int getZOffset() {
        return zOffset;
    }

    public Object getSecretType() {
        return secretType;
    }
}
