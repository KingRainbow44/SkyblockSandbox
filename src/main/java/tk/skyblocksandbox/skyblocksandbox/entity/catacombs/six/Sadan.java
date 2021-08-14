package tk.skyblocksandbox.skyblocksandbox.entity.catacombs.six;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class Sadan extends SandboxEntity {

    public Sadan() {
        super(EntityType.GIANT);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Sadan";
        entityData.isBoss = true;

        entityData.canTakeKnockback = true;
        entityData.isUndead = true;

        entityData.health = 40000000;
        entityData.damage = 100000;

        return entityData;
    }
}
