package tk.skyblocksandbox.skyblocksandbox.npc.traits;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;

public final class SkyblockEntityTrait extends Trait {

    public SkyblockEntityTrait() {
        super("SkyblockEntity");
    }

    public void setEntityData(SkyblockEntityData entityData) {
        this.entityData = entityData.toJson();
        isHostile = entityData.isHostile;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    /*
     * Contains the SkyblockEntityData for the NPC.
     * Formatting in a string, to be JSON decoded later.
     */
    @Persist("entityData")
    String entityData = "";

    @Persist("entityId")
    int entityId =-1;

    @Persist
    boolean isHostile = false;

    public void onAttach() {
        npc.getNavigator().getLocalParameters().attackRange(5.0).attackDelayTicks(10);
        npc.getNavigator().getLocalParameters().baseSpeed(getEntityData().speed / 100f);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                SkyblockSandbox.getInstance(),
                this,
                5L,
                5L
        );
    }

    public void onSpawn() {
        LookClose lookTrait = getNPC().getOrAddTrait(LookClose.class);
        lookTrait.setRealisticLooking(true);
    }

    private int ticks = -1;

    @Override
    public void run() {
        if(!getNPC().isSpawned()) return;
        ticks++;

        if(isHostile) {
            if(ticks % 10 == 0) {
                for(Entity entity : getNPC().getEntity().getNearbyEntities(5, 5, 5)) {
                    if(entity.hasMetadata("isNotSkyblockEntity") || entity instanceof ArmorStand) return;
                    if(entity == getNPC().getEntity()) return;

                    Object sbEntity = SkyblockEntity.getSkyblockEntity(entity);
                    if(sbEntity instanceof SkyblockPlayer) {
                        Calculator.damage((SkyblockPlayer) sbEntity, getEntityData().damage, true);
                    } else {
                        Calculator.damage((SkyblockEntity) sbEntity, getEntityData().damage, true);
                    }
                }
            }
        }

        if(isHostile) {
            Entity target = getNextEntity(5);

            if(target == null) {
                target = getNextEntity(32); if(target == null) return;
                getNPC().getNavigator().setTarget(target.getLocation());
            } else {
                getNPC().getNavigator().setTarget(target, true);
            }
        }
    }

    private Entity getNextEntity(int maxDistance) {
        Entity closest = null;
        double lastDistance = Double.MAX_VALUE;
        for(Entity entity : getNPC().getEntity().getNearbyEntities(maxDistance, maxDistance, maxDistance)) {
            if(entity == getNPC().getEntity() || !(entity instanceof Player)) continue;

            double distanceFromNPC = getNPC().getEntity().getLocation().distance(entity.getLocation());
            if(distanceFromNPC < lastDistance) {
                lastDistance = distanceFromNPC;
                closest = entity;
            }
        }

        return closest;
    }

    /*
     * Trait Methods
     */

    public SkyblockEntityData getEntityData() {
        return SkyblockEntityData.parse(entityData);
    }

    public int getEntityId() {
        return entityId;
    }

}
