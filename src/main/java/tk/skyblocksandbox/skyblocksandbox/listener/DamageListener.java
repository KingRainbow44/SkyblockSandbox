package tk.skyblocksandbox.skyblocksandbox.listener;

import com.kingrainbow44.customplayer.player.CustomPlayerManager;
import com.kingrainbow44.persistentdatacontainers.DataContainerAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityManager;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;

public final class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return; // This means that the damage was from our custom damage system. Don't double damage.
        event.setCancelled(true);

        Entity entity = event.getEntity();
        double damage = event.getDamage();

        CustomPlayerManager playerManager = SkyblockSandbox.getApi().getPlayerManager();

        // SkyblockEntity was damaged by a SkyblockPlayer.
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
            if(entity instanceof Player) return;

            Entity damager = damageEvent.getDamager();
            if(!(damager instanceof Player)) return;

            Object rawPlayer = playerManager.isCustomPlayer((Player) damager);
            if(!(rawPlayer instanceof SkyblockPlayer)) return;
            SkyblockPlayer sbPlayer = (SkyblockPlayer) rawPlayer;

            // Entity damaged was a SkyblockEntity.
            SkyblockEntityManager entityManager = SkyblockSandbox.getManagement().getEntityManager();
            if(!DataContainerAPI.has(entity, SkyblockSandbox.getInstance(), "entityUUID", PersistentDataType.INTEGER)) return;
            Object rawUUID = DataContainerAPI.get(entity.getPersistentDataContainer(), SkyblockSandbox.getInstance(), "entityUUID", PersistentDataType.INTEGER);
            if(!(rawUUID instanceof Integer)) return;

            SkyblockEntity sbEntity = entityManager.getEntity((int) rawUUID);
            if(sbEntity == null) return;

            Calculator.damage(sbEntity, sbPlayer, true);
            return;
        }

        // Entity damaged was a SkyblockPlayer.
        if(entity instanceof Player) {
            Player player = (Player) entity;
            Object rawPlayer = playerManager.isCustomPlayer(player);
            if(!(rawPlayer instanceof SkyblockPlayer)) return;
            SkyblockPlayer sbPlayer = (SkyblockPlayer) rawPlayer;

            Calculator.damage(sbPlayer, (float) damage, true, false);
            return;
        }

        // Entity damaged was a SkyblockEntity.
        SkyblockEntityManager entityManager = SkyblockSandbox.getManagement().getEntityManager();
        if(!DataContainerAPI.has(entity, SkyblockSandbox.getInstance(), "entityUUID", PersistentDataType.INTEGER)) return;
        Object rawUUID = DataContainerAPI.get(entity.getPersistentDataContainer(), SkyblockSandbox.getInstance(), "entityUUID", PersistentDataType.INTEGER);
        if(!(rawUUID instanceof Integer)) return;

        SkyblockEntity sbEntity = entityManager.getEntity((int) rawUUID);
        if(sbEntity == null) return;

        Calculator.damage(sbEntity, (float) damage, true);
    }

}
