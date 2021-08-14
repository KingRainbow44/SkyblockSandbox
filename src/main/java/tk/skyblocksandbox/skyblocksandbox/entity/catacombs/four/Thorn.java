package tk.skyblocksandbox.skyblocksandbox.entity.catacombs.four;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class Thorn extends SandboxEntity {
    public Thorn() {
        super(EntityType.GHAST);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.isBoss = true;
        entityData.isHostile = true;
        entityData.isUndead = true;

        entityData.entityName = "Thorn";
        entityData.health = 10000000;
        entityData.damage = 10000;
        entityData.defense = 100;

        return entityData;
    }
}
