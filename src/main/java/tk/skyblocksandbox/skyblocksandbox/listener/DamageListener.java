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
import org.bukkit.util.Vector;
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
        if(event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            event.setCancelled(true);
            return; // This means that the damage was from our custom damage system. Don't double damage.
        }

        event.setCancelled(true);

        Entity entity = event.getEntity();
        double damage = event.getDamage();

        CustomPlayerManager playerManager = SkyblockSandbox.getApi().getPlayerManager();

        // SkyblockEntity was damaged by a SkyblockPlayer.
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;

            Entity damager = damageEvent.getDamager();

            // Damager is NOT a Player in Any Way - START \\
            if(!(damager instanceof Player)) {
                if(!(entity instanceof Player)) {
                    event.setCancelled(true);
                    return;
                }

                if(damager.hasMetadata("NPC")) {
//                    NPC npc = CitizensAPI.getNPCRegistry().getNPC(damager);
                    event.setCancelled(true);
                    return;
                } else if (DataContainerAPI.has(damager, SkyblockSandbox.getInstance(), "entityUUID", PersistentDataType.INTEGER)) {
                    SkyblockEntity sbDamager = (SkyblockEntity) SkyblockEntity.getSkyblockEntity(entity);
                    SkyblockPlayer sbTarget = SkyblockPlayer.getSkyblockPlayer((Player) entity);

                    Calculator.damage(sbTarget, sbDamager.getEntityData().damage, true);

                    event.setCancelled(true);
                    return;
                } else {
                    SkyblockPlayer sbTarget = SkyblockPlayer.getSkyblockPlayer((Player) entity);
                    Calculator.damage(sbTarget, (int) Math.round(damage), true);

                    event.setCancelled(true);
                    return;
                }
            }
            // Damager is NOT a Player in Any Way - END \\

            // Damager is NOT an NPC & the entity IS a player. \\
            if(entity instanceof Player && !damager.hasMetadata("NPC")) {

                if(!entity.hasMetadata("NPC")) {
                    SkyblockPlayer sbTarget = SkyblockPlayer.getSkyblockPlayer((Player) entity);
                    SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) damager);

                    if(!sbPlayer.getPlayerData().pvpEnabled || !sbTarget.getPlayerData().pvpEnabled) {
                        event.setCancelled(true);
                        return;
                    }

                    Calculator.damage(sbTarget, sbPlayer, true);
                }

                event.setCancelled(true);
                return;
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

            sbPlayer.getPlayerData().damage((int) Math.round(damage));
            event.setCancelled(true);
            return;
        }

        // Entity damaged was a SkyblockEntity.
        if(entity.hasMetadata("NPC")) return;

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
