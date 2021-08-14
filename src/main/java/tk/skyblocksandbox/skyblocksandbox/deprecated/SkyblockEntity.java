package tk.skyblocksandbox.skyblocksandbox.deprecated;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.decimal4j.util.DoubleRounder;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityManager;
import tk.skyblocksandbox.skyblocksandbox.npc.traits.SkyblockEntityTrait;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

@Deprecated
public abstract class SkyblockEntity {

    private final EntityType entityType;

    private LivingEntity entity;

    protected BossBar entityBossBar = null;
    private int entityId = -1;

    private long currentHealth = 0;

    public SkyblockEntity(EntityType entityType) {
        this.entityType = entityType;
    }

//    public void create(Location location) {
//        SkyblockEntityManager entityManager = SkyblockSandbox.getManagement().getEntityManager();
//        entityId = entityManager.registerEntity(this);
//
//        NPC npc = CitizensAPI.getNPCRegistry().createNPC(entityType, getEntityData().entityName);
//        npc.spawn(location);
//
//        if(npc.getEntity() instanceof LivingEntity) {
//            entity = (LivingEntity) npc.getEntity();
//        } else {
//            throw new NullPointerException("EntityType is not a living entity");
//        }
//
//        if(entityType == EntityType.PLAYER) {
//            SkinTrait skin = npc.getOrAddTrait(SkinTrait.class);
//            skin.setSkinPersistent(getEntityData().skinName, getEntityData().skinSignature, getEntityData().skinData);
//
//            Equipment equipment = npc.getOrAddTrait(Equipment.class);
//            if(getEntityData().helmet != null) equipment.set(Equipment.EquipmentSlot.HELMET, getEntityData().helmet);
//            if(getEntityData().chestplate != null) equipment.set(Equipment.EquipmentSlot.CHESTPLATE, getEntityData().chestplate);
//            if(getEntityData().leggings != null) equipment.set(Equipment.EquipmentSlot.LEGGINGS, getEntityData().leggings);
//            if(getEntityData().boots != null) equipment.set(Equipment.EquipmentSlot.BOOTS, getEntityData().boots);
//
//            if(getEntityData().mainHand != null) equipment.set(Equipment.EquipmentSlot.HAND, getEntityData().mainHand);
//            if(getEntityData().offHand != null) equipment.set(Equipment.EquipmentSlot.OFF_HAND, getEntityData().offHand);
//        } else if (entityType == EntityType.WITHER || entityType == EntityType.ENDER_DRAGON) {
//            BossBar bossBar = ((Boss) entity).getBossBar();
//            bossBar.setProgress(0.0f);
//            bossBar.setVisible(false);
//        }
//
//        if(getEntityData().isBoss) {
//            npc.setName(colorize("&e&l﴾ &c&l" + getEntityData().entityName + " &e&l﴿"));
//        } else {
//            npc.setName(colorize(colorize("&8[&7Lvl " + getEntityData().level +"&8] &c" + getEntityData().entityName + " &a" + Math.round(getEntityData().health) + "/" + Math.round(getEntityData().health) + "&c❤")));
//        }
//
//        entity.setMetadata("skyblockEntityData", new FixedMetadataValue(SkyblockSandbox.getInstance(), getEntityData().toJson()));
//        entity.setMetadata("skyblockEntityId", new FixedMetadataValue(SkyblockSandbox.getInstance(), entityId));
//
//        if(entityManager.getEntity(entityId) == null) throw new NullPointerException("Value of returning Skyblock Entity from Entity was null.");
//    }

//    @Deprecated
//    public void createEntity(Location location) {
//        create(location);
//    }

    public void setHealth(long health) {
        currentHealth = health;
    }

    public void kill(boolean death) {
        if(entityId == -1) return;

        SkyblockSandbox.getManagement().getEntityManager().unregisterEntity(entityId);
        if(getEntityData().isBoss) {
            getEntityBossBar().setVisible(false);
            getEntityBossBar().removeAll();
        }

        if(entity.hasMetadata("NPC")) {
            NPC entityNPC = CitizensAPI.getNPCRegistry().getNPC(entity);
            entityNPC.destroy();
            return;
        }

        if(death) remove();
    }

    public void remove() {
        if(entityId == -1) return;
        entity.setHealth(0);
    }

    /**
     * Fakes the red effect on an entity using ProtocolLib.
     */
    public void hurt() {
        entity.getWorld().playSound(
                entity.getLocation(),
                Sound.ENTITY_PLAYER_ATTACK_CRIT,
                1, 1
        );

        PacketContainer entityStatus = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);

        entityStatus.getIntegers().write(0, entity.getEntityId());
        entityStatus.getBytes().write(0, (byte) 2);

        for(Player player : Bukkit.getOnlinePlayers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, entityStatus);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void damage(long damage) {
        currentHealth -= damage;
        updateHealth();
    }

    public long getEntityHealth() {
        return currentHealth;
    }

    public BossBar getEntityBossBar() {
        return entityBossBar;
    }

    public int getEntityId() {
        return entityId;
    }

    public LivingEntity getBukkitEntity() {
        return entity;
    }

    public abstract SkyblockEntityData getEntityData();

    public void updateHealth() {
        if(getEntityData().isBoss) {
            updateBossBar();
        } else {
            if(getEntityData().isNpc) {
                NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);
                npc.setName(colorize("&8[&7Lvl " + getEntityData().level +"&8] &c" + getEntityData().entityName + " &a" + Math.round(getEntityData().health) + "/" + Math.round(getEntityData().health) + "&c❤"));
            } else {
                entity.setCustomName(colorize("&8[&7Lvl " + getEntityData().level +"&8] &c" + getEntityData().entityName + " &a" + Math.round(getEntityData().health) + "/" + Math.round(getEntityData().health) + "&c❤"));
            }
        }
    }

    public void updateBossBar() {
        if(getEntityBossBar() == null) return;

        double progress = DoubleRounder.round((float) getEntityHealth() / (float) getEntityData().health, 2, RoundingMode.DOWN);
        if(progress < 0) progress = 0.0f;
        if(progress > 1) progress = 1.0f;

        getEntityBossBar().setProgress(progress);
    }

    /*
     * Static Methods
     */

    public static Object getSkyblockEntity(Entity entity) {
        if(entity.hasMetadata("NPC")) return getSkyblockEntityFromNPC(
                CitizensAPI.getNPCRegistry().getNPC(entity)
        );

        if(entity instanceof Player) return SkyblockPlayer.getSkyblockPlayer(
                (Player) entity
        );

        return SkyblockSandbox.getManagement().getEntityManager().getEntity(entity);
    }

    public static SandboxEntity getSkyblockEntityFromNPC(NPC npc) {
        SkyblockEntityTrait entityTrait = npc.getOrAddTrait(SkyblockEntityTrait.class);
        return SkyblockSandbox.getManagement().getEntityManager().getEntity(entityTrait.getEntityId());
    }
}
