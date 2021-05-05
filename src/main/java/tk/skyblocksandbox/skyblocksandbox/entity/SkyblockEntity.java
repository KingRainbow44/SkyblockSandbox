package tk.skyblocksandbox.skyblocksandbox.entity;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;
import static tk.skyblocksandbox.skyblocksandbox.util.Utility.key;

public abstract class SkyblockEntity {

    private final EntityType entityType;

    private Entity entity;
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

        if(getEntityData().isNpc) {
            // TODO: Handle NPC entities.
            return;
        } else {
            World world = location.getWorld();

            Entity entity = world.spawnEntity(location, entityType);
            entity.setCustomNameVisible(true);

            if(entityType == EntityType.WITHER || entityType == EntityType.ENDER_DRAGON) {
                BossBar bossBar = ((Boss) entity).getBossBar();
                bossBar.setProgress(0.0f);
                bossBar.removeAll();
            }

            PersistentDataContainer data = entity.getPersistentDataContainer();

            data.set(key("skyblockEntity"), PersistentDataType.BYTE, (byte) 1);
            data.set(key("entityUUID"), PersistentDataType.INTEGER, entityId);
            data.set(key("damage"), PersistentDataType.INTEGER, getEntityData().damage);

            this.entity = entity;

            if(entityManager.getEntity(entityId) == null) throw new NullPointerException("Value of returning Skyblock Entity from Entity was null.");

            if(!getEntityData().isBoss) {
                entity.setCustomName(colorize("&8[&7Lvl " + getEntityData().level +"&8] &c" + getEntityData().entityName + " &a" + Math.round(getEntityData().health) + "/" + Math.round(getEntityData().health) + "&c❤"));
            } else {
                entity.setCustomName(colorize("&e&l< &c&l" + getEntityData().entityName + " &e>"));
                entityBossBar = Bukkit.createBossBar(colorize("&c&l" + getEntityData().entityName), BarColor.RED, BarStyle.SOLID);
                for(Entity nearbyPlayer : world.getNearbyEntities(location, 50, 50, 50)) {
                    if(!(nearbyPlayer instanceof Player)) return;
                    entityBossBar.addPlayer((Player) nearbyPlayer);
                }
            }
        }
    }

    public void setHealth(long health) {
        currentHealth = health;
    }

    public void kill(boolean death) {
        if(death) remove();
        if(entityId == -1) return;

        SkyblockSandbox.getManagement().getEntityManager().unregisterEntity(entityId);
    }

    public void remove() {
        if(entityId == -1) return;
        ((Damageable) entity).setHealth(0);
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

    public Entity getBukkitEntity() {
        return entity;
    }

    public abstract SkyblockEntityData getEntityData();

    public void updateBossBar() {}

}
