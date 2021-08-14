package tk.skyblocksandbox.skyblocksandbox.entity.vanilla;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class Zombie extends SandboxEntity {

    public Zombie() {
        super(EntityType.ZOMBIE);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.health = 100;
        entityData.damage = 10;
        entityData.level = 1;

        entityData.entityName = "Zombie";

        entityData.isUndead = true;

        return entityData;
    }
}
