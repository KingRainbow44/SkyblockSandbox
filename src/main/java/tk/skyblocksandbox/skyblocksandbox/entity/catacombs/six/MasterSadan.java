package tk.skyblocksandbox.skyblocksandbox.entity.catacombs.six;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class MasterSadan extends SandboxEntity {

    public MasterSadan() {
        super(EntityType.GIANT);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Sadan";
        entityData.isBoss = true;

        entityData.canTakeKnockback = true;
        entityData.isUndead = true;

        entityData.health = 800000000;
        entityData.damage = 1000000;
        entityData.defense = 10000;

        return entityData;
    }
}
