package tk.skyblocksandbox.skyblocksandbox.entity;

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
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.decimal4j.util.DoubleRounder;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.npc.traits.SkyblockEntityTrait;

import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public abstract class SandboxEntity {

    private final EntityType entityType;

    private LivingEntity entity;

    protected BossBar entityBossBar = null;
    private int entityId = -1;

    private long currentHealth = 0;

    public SandboxEntity(EntityType entityType) {
        this.entityType = entityType;
    }

    public void create(Location location) {
        SkyblockEntityManager entityManager = SkyblockSandbox.getManagement().getEntityManager();

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(entityType, getEntityData().entityName);
        npc.spawn(location);

        if(npc.getEntity() instanceof LivingEntity) {
            entity = (LivingEntity) npc.getEntity();
        } else {
            throw new NullPointerException("EntityType is not a living entity.");
        }

        entityId = entityManager.registerEntity(this); // Register entity from the Bukkit entity id system.
        if(entityManager.getEntity(entityId) == null) throw new NullPointerException("Value of returning Skyblock Entity from Entity was null.");

        if(entityType == EntityType.PLAYER) {
            SkinTrait skin = npc.getOrAddTrait(SkinTrait.class);
            skin.setSkinPersistent(getEntityData().skinName, getEntityData().skinSignature, getEntityData().skinData);

            Equipment equipment = npc.getOrAddTrait(Equipment.class);
            if(getEntityData().helmet != null) equipment.set(Equipment.EquipmentSlot.HELMET, getEntityData().helmet);
            if(getEntityData().chestplate != null) equipment.set(Equipment.EquipmentSlot.CHESTPLATE, getEntityData().chestplate);
            if(getEntityData().leggings != null) equipment.set(Equipment.EquipmentSlot.LEGGINGS, getEntityData().leggings);
            if(getEntityData().boots != null) equipment.set(Equipment.EquipmentSlot.BOOTS, getEntityData().boots);

            if(getEntityData().mainHand != null) equipment.set(Equipment.EquipmentSlot.HAND, getEntityData().mainHand);
            if(getEntityData().offHand != null) equipment.set(Equipment.EquipmentSlot.OFF_HAND, getEntityData().offHand);
        } else if (entityType == EntityType.WITHER || entityType == EntityType.ENDER_DRAGON) {
            BossBar bossBar = ((Boss) entity).getBossBar();
            bossBar.setProgress(0.0f);
            bossBar.setVisible(false);
        }

        if(getEntityData().isBoss) {
            npc.setName(colorize("&e&l﴾ &c&l" + getEntityData().entityName + " &e&l﴿"));
            entityBossBar = Bukkit.createBossBar(colorize("&c&l" + getEntityData().entityName), BarColor.RED, BarStyle.SOLID);
            for(Entity entity : entity.getNearbyEntities(20, 20, 20)) {
                if(entity instanceof Player) {
                    entityBossBar.addPlayer((Player) entity);
                }
            }
        } else {
            npc.setName(colorize(colorize("&8[&7Lvl " + getEntityData().level +"&8] &c" + getEntityData().entityName + " &a" + Math.round(getEntityData().health) + "/" + Math.round(getEntityData().health) + "&c❤")));
        }

        SkyblockEntityTrait entityTrait = npc.getOrAddTrait(SkyblockEntityTrait.class);
        entityTrait.setEntityId(entityId);
        entityTrait.setEntityData(getEntityData());

        currentHealth = getEntityData().health;

        entity.setMetadata("skyblockEntityData", new FixedMetadataValue(SkyblockSandbox.getInstance(), getEntityData().toJson()));
        entity.setMetadata("skyblockEntityId", new FixedMetadataValue(SkyblockSandbox.getInstance(), entityId));
    }

    public final LivingEntity getBukkitEntity() {
        return entity;
    }

    public final int getEntityId() {
        return entityId;
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
        hurt();

        if(currentHealth <= 0) {
            kill(true);
        }
    }

    public void kill(boolean death) {
        if(entityId == -1) return;

        SkyblockSandbox.getManagement().getEntityManager().unregisterEntity(entityId);
        if(getEntityData().isBoss) {
            entityBossBar.setVisible(false);
            entityBossBar.removeAll();
        }

        if(entity.hasMetadata("NPC")) {
            NPC entityNPC = CitizensAPI.getNPCRegistry().getNPC(entity);
            entityNPC.destroy();
            return;
        }

        if(death) {
            entity.setHealth(0);
        }
    }

    public long getHealth() {
        return currentHealth;
    }

    public void setHealth(long health) {
        currentHealth = health;
    }

    public void updateBossBar() {
        if(entityBossBar == null) throw new NullPointerException("No entity boss bar to update.");

        double progress = DoubleRounder.round((float) getHealth() / (float) getEntityData().health, 2, RoundingMode.DOWN);
        if(progress < 0) progress = 0.0f;
        if(progress > 1) progress = 1.0f;

        entityBossBar.setProgress(progress);
    }

    /**
     * @return The SkyblockEntityData class which compactly holds all the data about a Skyblock Entity.
     */
    public abstract SkyblockEntityData getEntityData();

    public void ability() {} // A 'null' method. Not all entities have an ability. Runs every tick.

    /*
     * Static Get Methods
     */
    public static SandboxEntity getSandboxEntity(Object entity) {
        if(entity instanceof NPC) {
            return SkyblockSandbox.getManagement().getEntityManager().getEntity(((NPC) entity).getEntity());
        } else if (entity instanceof Entity) {
            return SkyblockSandbox.getManagement().getEntityManager().getEntity((Entity) entity);
        } else {
            throw new NullPointerException("Not a Sandbox Entity.");
        }
    }
}
