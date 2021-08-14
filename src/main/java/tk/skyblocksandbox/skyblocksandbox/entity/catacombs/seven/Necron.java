package tk.skyblocksandbox.skyblocksandbox.entity.catacombs.seven;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class Necron extends SandboxEntity {

    public Necron() {
        super(EntityType.WITHER);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Necron";
        entityData.isBoss = true;

        entityData.canTakeKnockback = false;
        entityData.isUndead = true;

        entityData.health = 1000000000;
        entityData.defense = 300;
        entityData.damage = 5000;

        return entityData;
    }
}
