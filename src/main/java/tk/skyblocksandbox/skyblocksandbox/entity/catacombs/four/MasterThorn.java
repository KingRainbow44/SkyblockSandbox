package tk.skyblocksandbox.skyblocksandbox.entity.catacombs.four;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class MasterThorn extends SandboxEntity {
    public MasterThorn() {
        super(EntityType.GHAST);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.isBoss = true;
        entityData.isHostile = true;
        entityData.isUndead = true;

        entityData.entityName = "Thorn";
        entityData.health = 100000000;
        entityData.damage = 100000;
        entityData.defense = 1000;

        return entityData;
    }
}
