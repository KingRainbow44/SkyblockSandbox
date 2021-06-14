package tk.skyblocksandbox.skyblocksandbox.listener;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.npc.SkyblockNPC;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;

public final class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            event.setCancelled(true);
            return;
        }

        if(event instanceof EntityDamageByEntityEvent) {
            // Entity was Damaged by another Entity. (EvE)
            EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) event;
            Entity damager = entityEvent.getDamager();
            Entity damagee = entityEvent.getEntity();

            /*
             * Scenario Breakdown:
             * - SBEntity damaged by SBEntity (EvE)
             * - SBPlayer damaged by SBEntity (PvE)
             * - SBEntity damaged by SBPlayer (PvE)
             * - SBPlayer damaged by SBPlayer (PvP)
             */

            if(damager.hasMetadata("skyblockEntityId") && damagee.hasMetadata("skyblockEntityId")) { // (EvE)
                SandboxEntity sbDamager = SandboxEntity.getSandboxEntity(damager);
                SandboxEntity sbDamagee = SandboxEntity.getSandboxEntity(damagee);

                event.setCancelled(true);
                Calculator.damage(sbDamagee, sbDamager.getEntityData().damage, true);
            } else if (damager.hasMetadata("skyblockEntityId") && damagee instanceof Player) { // (PvE) - Scenario 1
//                SandboxEntity sbDamager = SandboxEntity.getSandboxEntity(damager);
//                SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) damagee);
//
//                event.setCancelled(true);
//                Calculator.damage(sbPlayer, sbDamager.getEntityData().damage, true);
                event.setCancelled(true);
                return; // Custom Damage System is built in.
            } else if (damager instanceof Player && damagee.hasMetadata("skyblockEntityId")) { // (PvE) - Scenario 2
                SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) damager);
                SandboxEntity sbDamagee = SandboxEntity.getSandboxEntity(damagee);

                event.setCancelled(true);
                Calculator.damage(sbDamagee, sbPlayer, true);
            } else if (damager instanceof Player && damagee instanceof Player) { // (PvP)
                SkyblockPlayer sbDamager = SkyblockPlayer.getSkyblockPlayer((Player) damager);
                SkyblockPlayer sbDamagee = SkyblockPlayer.getSkyblockPlayer((Player) damagee);

                event.setCancelled(true);
                Calculator.damage(sbDamagee, sbDamager, true);
            } else if (damager instanceof Projectile && damagee instanceof Player && !CitizensAPI.getNPCRegistry().isNPC(damagee)) {
                SkyblockPlayer sbDamagee = SkyblockPlayer.getSkyblockPlayer((Player) damagee);
                ProjectileSource source = ((Projectile) damager).getShooter();

                event.setCancelled(true);
                if(source instanceof Player) {
                    Player pSource = (Player) source;
                    if(pSource.equals(damagee)) return;

                    SkyblockPlayer sbSource = SkyblockPlayer.getSkyblockPlayer(pSource);

                    Calculator.damage(sbDamagee, sbSource, true);
                } else if (source instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) source;
                    if(entity.hasMetadata("skyblockEntityId")) {
                        SandboxEntity sbDamager = SandboxEntity.getSandboxEntity(entity);
                        Calculator.damage(sbDamagee, sbDamager.getEntityData().damage, true);
                    }
                }
            } else if (damager instanceof Projectile && damagee.hasMetadata("skyblockEntityId")) {
                ProjectileSource source = ((Projectile) damager).getShooter();
                if(!(source instanceof Player)) return;

                Player pSource = (Player) source;
                SkyblockPlayer sbSource = SkyblockPlayer.getSkyblockPlayer(pSource);
                SandboxEntity sbDamagee = SandboxEntity.getSandboxEntity(damagee);

                event.setCancelled(true);
                Calculator.damage(sbDamagee, sbSource, true);
            } else {
                if(damagee instanceof Player) {
                    SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) damagee);
                    Calculator.damage(sbPlayer, (int) Math.round(event.getDamage()), true);
                }

                if(damagee.hasMetadata("skyblockEntityId")) {
                    SandboxEntity sbEntity = SandboxEntity.getSandboxEntity(damagee);
                    Calculator.damage(sbEntity, (int) Math.round(event.getDamage()), true);
                }
                event.setCancelled(true);
            }
        } else {
            // Entity was Void damaged.
            Entity damagee = event.getEntity();

            /*
             * Scenario Breakdown:
             * - SBPlayer was void damaged.
             * - SBEntity was void damaged.
             */

            if(damagee instanceof Player && !damagee.hasMetadata("NPC")) {
                SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) damagee);
                sbPlayer.getPlayerData().damage(Math.round(event.getDamage()));
            }

            if(damagee.hasMetadata("skyblockEntityId")) {
                SandboxEntity sbEntity = SandboxEntity.getSandboxEntity(damagee);
                sbEntity.damage(Math.round(event.getDamage()));
            }

            event.setCancelled(true);
        }
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

        if(!(damager instanceof Player) || damager.hasMetadata("NPC")) return;
        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) damager);

        SkyblockNPC.damage(entity, sbPlayer, true);

        event.setDamage(0);
        event.setCancelled(true);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile entity = event.getEntity();
        if(entity instanceof Arrow) {
            entity.setBounce(false);

            new BukkitRunnable() {
                @Override
                public void run() {
                    entity.remove();
                }
            }.runTaskLater(SkyblockSandbox.getInstance(), 3*20L);
        }
    }

}
