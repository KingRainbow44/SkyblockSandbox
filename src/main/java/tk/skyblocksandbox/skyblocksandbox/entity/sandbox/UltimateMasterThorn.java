package tk.skyblocksandbox.skyblocksandbox.entity.sandbox;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class UltimateMasterThorn extends SandboxEntity {
    public UltimateMasterThorn() {
        super(EntityType.GHAST);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.isBoss = true;
        entityData.isHostile = true;
        entityData.isUndead = true;

        entityData.entityName = "Ultimate Thorn";
        entityData.health = 20000000000L;
        entityData.damage = 750000000;
        entityData.defense = 100000;
        entityData.speed = 250;

        return entityData;
    }
}
