package tk.skyblocksandbox.skyblocksandbox.entity.sandbox;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityManager;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

public final class UltimateMasterSadan extends SandboxEntity {
    public UltimateMasterSadan() {
        super(EntityType.GIANT);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Ultimate Sadan";
        entityData.isBoss = true;

        entityData.canTakeKnockback = false;
        entityData.isUndead = true;

        entityData.health = 10000000000L;
        entityData.defense = 1000000;
        entityData.damage = 2000000000;

        entityData.speed = 180;

        return entityData;
    }

    private long ticks = -1;

    @Override
    public void ability() {
        ticks++;

        if(ticks % 200 == 0) {
            if(Utility.generateRandomNumber(1, 30) == 30) {
                setHealth(Math.round(getHealth() + (getHealth() * 0.5)));
            }
        }
    }
}
