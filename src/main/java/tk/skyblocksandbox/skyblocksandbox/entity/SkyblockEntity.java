package tk.skyblocksandbox.skyblocksandbox.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.decimal4j.util.DoubleRounder;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.npc.SkyblockNPC;
import tk.skyblocksandbox.skyblocksandbox.npc.traits.SkyblockEntityTrait;

import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;
import static tk.skyblocksandbox.skyblocksandbox.util.Utility.key;

public abstract class SkyblockEntity {

    private final EntityType entityType;

    private LivingEntity entity;

    protected BossBar entityBossBar = null;
    private int entityId = -1;

    private long currentHealth = 0;

    public SkyblockEntity(EntityType entityType) {
        this.entityType = entityType;
    }

    public void createEntity(Location location) {
        SkyblockEntityManager entityManager = SkyblockSandbox.getManagement().getEntityManager();
        entityId = entityManager.registerEntity(this);

        this.currentHealth = getEntityData().health;

        if(entityType == EntityType.PLAYER) {
            SkyblockNPC npc;

            if(getEntityData().isBoss) {
                npc = new SkyblockNPC(colorize("&e&l﴾ &c&l" + getEntityData().entityName + " &e&l﴿"));
            } else {
                npc = new SkyblockNPC(colorize("&8[&7Lvl " + getEntityData().level +"&8] &c" + getEntityData().entityName + " &a" + Math.round(getEntityData().health) + "/" + Math.round(getEntityData().health) + "&c❤"));
            }

            npc.getNpc().spawn(location);

            entity = (LivingEntity) npc.getNpc().getEntity();
            npc.setEntityData(getEntityData());
            npc.setEntityId(entityId);

            SkinTrait skinTrait = npc.getNpc().getOrAddTrait(SkinTrait.class);
            skinTrait.setSkinPersistent(getEntityData().skinName, getEntityData().skinSignature, getEntityData().skinData);

            npc.getNpc().setProtected(false);

            PersistentDataContainer data = entity.getPersistentDataContainer();
            data.set(key("skyblockEntity"), PersistentDataType.BYTE, (byte) 1);
            data.set(key("entityUUID"), PersistentDataType.INTEGER, entityId); // Redundancy (for other methods that use legacy "entityUUID".)
            data.set(key("damage"), PersistentDataType.INTEGER, getEntityData().damage);

            if(entityManager.getEntity(entityId) == null) throw new NullPointerException("Value of returning Skyblock Entity from Entity was null.");

            if(getEntityData().isBoss) {
                entityBossBar = Bukkit.createBossBar(colorize("&c&l" + getEntityData().entityName), BarColor.RED, BarStyle.SOLID);
                for(Player nearbyPlayer : Bukkit.getOnlinePlayers()) {
                    entityBossBar.addPlayer(nearbyPlayer);
                }
            }
        } else {
            World world = location.getWorld();

            Entity entity = world.spawnEntity(location, entityType);
            entity.setCustomNameVisible(true);

            PersistentDataContainer data = entity.getPersistentDataContainer();

            data.set(key("skyblockEntity"), PersistentDataType.BYTE, (byte) 1);
            data.set(key("entityUUID"), PersistentDataType.INTEGER, entityId);
            data.set(key("damage"), PersistentDataType.INTEGER, getEntityData().damage);

            this.entity = (LivingEntity) entity;

            if(entityManager.getEntity(entityId) == null) throw new NullPointerException("Value of returning Skyblock Entity from Entity was null.");

            if(!getEntityData().isBoss) {
                entity.setCustomName(colorize("&8[&7Lvl " + getEntityData().level +"&8] &c" + getEntityData().entityName + " &a" + Math.round(getEntityData().health) + "/" + Math.round(getEntityData().health) + "&c❤"));
            } else {
                if(entityType == EntityType.WITHER || entityType == EntityType.ENDER_DRAGON) {
                    BossBar bossBar = ((Boss) entity).getBossBar();
                    bossBar.setProgress(0.0f);
                    bossBar.setVisible(false);
                }

                entity.setCustomName(colorize("&e&l﴾ &c&l" + getEntityData().entityName + " &e&l﴿"));
                entityBossBar = Bukkit.createBossBar(colorize("&c&l" + getEntityData().entityName), BarColor.RED, BarStyle.SOLID);
                for(Player nearbyPlayer : Bukkit.getOnlinePlayers()) {
                    entityBossBar.addPlayer(nearbyPlayer);
                }
            }
        }
    }

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
     * @param attacker The player hurting the entity.
     */
    public void hurt(Player attacker) {
        PacketContainer entityStatus = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);

        entityStatus.getIntegers().write(0, entity.getEntityId());
        entityStatus.getBytes().write(0, (byte) 2);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(attacker, entityStatus);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void damage(long damage) {
        currentHealth -= damage;
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

    public static SkyblockEntity getSkyblockEntityFromNPC(NPC npc) {
        SkyblockEntityTrait entityTrait = npc.getOrAddTrait(SkyblockEntityTrait.class);
        return SkyblockSandbox.getManagement().getEntityManager().getEntity(entityTrait.getEntityId());
    }
}
