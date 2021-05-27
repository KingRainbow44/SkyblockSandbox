package tk.skyblocksandbox.skyblocksandbox.entity.catacombs.seven;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class MasterNecron extends SandboxEntity {
    public MasterNecron() {
        super(EntityType.WITHER);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Necron";
        entityData.isBoss = true;

        entityData.canTakeKnockback = false;
        entityData.isUndead = true;

        entityData.health = 2000000000;
        entityData.defense = 1000;
        entityData.damage = 250000;

        return entityData;
    }
}
