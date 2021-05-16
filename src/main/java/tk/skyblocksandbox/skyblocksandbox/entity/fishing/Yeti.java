package tk.skyblocksandbox.skyblocksandbox.entity.fishing;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class Yeti extends SkyblockEntity {

    public Yeti() {
        super(EntityType.PLAYER);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Yeti";
        entityData.health = 2000000;
        entityData.vanillaHealth = 20;

        return entityData;
    }
}
