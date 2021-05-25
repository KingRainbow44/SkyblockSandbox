package tk.skyblocksandbox.skyblocksandbox.npc.traits;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class SkyblockEntityTrait extends Trait {

    public SkyblockEntityTrait() {
        super("SkyblockEntity");
    }

    public void setEntityData(SkyblockEntityData entityData) {
        this.entityData = entityData.toJson();

        health = entityData.health;
        defense = entityData.defense;
        damage = entityData.damage;
        speed = entityData.speed;
        level = entityData.level;

        isHostile = entityData.isHostile;
        isNpc = entityData.isNpc;
        isBoss = entityData.isBoss;
        isUndead = entityData.isUndead;
        isArthropod = entityData.isArthropod;

        canTakeKnockback = entityData.canTakeKnockback;

        helmet = entityData.helmet;
        chestplate = entityData.chestplate;
        leggings = entityData.leggings;
        boots = entityData.boots;

        mainHand = entityData.mainHand;
        offHand = entityData.offHand;

        skinName = entityData.skinName;
        skinSignature = entityData.skinSignature;
        skinData = entityData.skinData;

        entityName = entityData.entityName;

        entityDataAdded = true;
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

    /*
     * Entity Data Raw
     */

    @Persist("entityHealth")
    long health = 100;

    @Persist("defense")
    float defense = 0;

    @Persist("damage")
    int damage = 0;

    @Persist("speed")
    int speed = 100;

    @Persist("level")
    int level = 1;

    @Persist("isHostile")
    boolean isHostile = true;

    @Persist("isNpc")
    boolean isNpc = true;

    @Persist("isBoss")
    boolean isBoss = false;

    @Persist("isUndead")
    boolean isUndead = false;

    @Persist("isArthropod")
    boolean isArthropod = false;

    @Persist
    boolean canTakeKnockback = true;

    @Persist("helmet")
    ItemStack helmet = null;

    @Persist("chestplate")
    ItemStack chestplate = null;

    @Persist("leggings")
    ItemStack leggings = null;

    @Persist("boots")
    ItemStack boots = null;

    @Persist("mainHand")
    ItemStack mainHand = null;

    @Persist("offHand")
    ItemStack offHand = null;

    @Persist("skinName")
    String skinName = "";

    @Persist("skinSignature")
    String skinSignature = "";

    @Persist
    String skinData = "";

    @Persist("entityName")
    String entityName = "";

    @Persist("entityId")
    int entityId = 1;

    @Persist("entityDataAdded")
    boolean entityDataAdded = false;

    @Persist("speedAdded")
    boolean speedAdded = false;

    @Override
    public void load(DataKey key) {
        entityData = key.getString("entityData");
    }

    @Override
    public void save(DataKey key) {
        key.setString("entityData", getEntityData().toJson());
    }

    public void onAttach() {
        npc.getNavigator().getLocalParameters().attackRange(4.0).attackDelayTicks(15);

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

        if(entityDataAdded && !speedAdded) {
            npc.getNavigator().getLocalParameters().baseSpeed(getEntityData().speed / 100f);
            speedAdded = true;
        }

        if(!isBoss) {
            long currentHealth = SkyblockSandbox.getManagement().getEntityManager().getEntity(entityId).getHealth();
            getNPC().setName(colorize("&8[&7Lvl " + level +"&8] &c" + entityName + " &a" + currentHealth + "/" + Math.round(health) + "&c❤"));
        } else {
            getNPC().setName(colorize("&e&l﴾ &c&l" + entityName + " &e&l﴿"));
        }

        if(isHostile) {
            if(ticks % 15 == 0) {
                for(Entity entity : getNPC().getEntity().getNearbyEntities(4, 4, 4)) {
                    if(!(entity instanceof Player)) return;
                    Calculator.damage(SkyblockPlayer.getSkyblockPlayer((Player) entity), getEntityData().damage, true);
                }
            }
        }

        if(isHostile) {
            Entity target = getNextEntity(5);

            if(target == null) {
                target = getNextEntity(32); if(target == null) return;
                getNPC().getNavigator().setTarget(target.getLocation());
            } else {
                npc.getNavigator().setTarget(target, true);
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
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.health = health;
        entityData.defense = defense;
        entityData.damage = damage;
        entityData.speed = speed;

        entityData.level = level;
        entityData.entityName = entityName;

        entityData.isHostile = isHostile;
        entityData.isNpc = isNpc;
        entityData.isBoss = isBoss;
        entityData.isUndead = isUndead;
        entityData.isArthropod = isArthropod;

        entityData.canTakeKnockback = canTakeKnockback;

        entityData.helmet = helmet;
        entityData.chestplate = chestplate;
        entityData.leggings = leggings;
        entityData.boots = boots;

        entityData.mainHand = mainHand;
        entityData.offHand = offHand;

        entityData.skinName = skinName;
        entityData.skinSignature = skinSignature;
        entityData.skinData = skinData;

        return entityData;
    }

    public int getEntityId() {
        return entityId;
    }

}
