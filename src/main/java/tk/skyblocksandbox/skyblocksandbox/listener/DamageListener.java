package tk.skyblocksandbox.skyblocksandbox.listener;

import com.kingrainbow44.customplayer.player.CustomPlayerManager;
import com.kingrainbow44.persistentdatacontainers.DataContainerAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
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
import tk.skyblocksandbox.skyblocksandbox.npc.SkyblockNPC;
import tk.skyblocksandbox.skyblocksandbox.npc.traits.SkyblockEntityTrait;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;

public final class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return; // This means that the damage was from our custom damage system. Don't double damage.
        if(event.getCause() == EntityDamageEvent.DamageCause.WITHER) { // This is a check for the wither effect. Which can cause double death.
            event.setDamage(0); // Cancel damage.
        }

        Entity entity = event.getEntity();
        double damage = event.getDamage();

        CustomPlayerManager playerManager = SkyblockSandbox.getApi().getPlayerManager();

        // SkyblockEntity was damaged by a SkyblockPlayer.
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;

            Entity damager = damageEvent.getDamager();
            if(!(damager instanceof Player)) return;

            if(entity instanceof Player && !entity.hasMetadata("NPC")) {
//                Calculator.damage((SkyblockPlayer) SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer((Player) entity), (SkyblockPlayer) SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer((Player) damager), true); // TODO: Maybe add setting?
                event.setCancelled(true);
                return;
            }

            if(entity instanceof Player && entity.hasMetadata("NPC")) {
                return; // Let NPCDamageEvent handle damage.
            }

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
            event.setCancelled(true);
            return;
        }

        // Entity damaged was a SkyblockPlayer.
        if(entity instanceof Player) {
            Player player = (Player) entity;
            Object rawPlayer = playerManager.isCustomPlayer(player);
            if(!(rawPlayer instanceof SkyblockPlayer)) return;
            SkyblockPlayer sbPlayer = (SkyblockPlayer) rawPlayer;

            Calculator.damage(sbPlayer, (float) damage, true);
            event.setCancelled(true);
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
        event.setCancelled(true);
    }

    @EventHandler
    public void onVoidNPCDamage(NPCDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return; // If this is not here, it causes a StackOverflow exception.

        NPC entity = event.getNPC();

        SkyblockNPC.damage(entity, (float) event.getDamage(), true);

        event.setDamage(0);
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityNPCDamage(NPCDamageByEntityEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return; // If this is not here, it causes a StackOverflow exception.

        NPC entity = event.getNPC();
        Entity damager = event.getDamager();

        if(!(damager instanceof Player)) return;
        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) damager);

        SkyblockNPC.damage(entity, sbPlayer, true);

        event.setDamage(0);
        event.setCancelled(true);
    }

}
