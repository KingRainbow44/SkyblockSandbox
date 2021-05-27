package tk.skyblocksandbox.skyblocksandbox.entity.sandbox;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class UltimateMasterNecron extends SandboxEntity {
    public UltimateMasterNecron() {
        super(EntityType.WITHER);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "King Necron";
        entityData.isBoss = true;

        entityData.canTakeKnockback = false;
        entityData.isUndead = true;

        entityData.health = 9223372036854775807L;
        entityData.defense = 1000000;
        entityData.damage = 2000000000;

        entityData.speed = 125;

        return entityData;
    }
}
