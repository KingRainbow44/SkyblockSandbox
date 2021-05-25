package tk.skyblocksandbox.skyblocksandbox.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.npc.traits.SkyblockEntityTrait;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class SkyblockNPC {

    private final NPC npc;

    public SkyblockNPC(String npcName) {
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        npc.addTrait(SkyblockEntityTrait.class);
    }

    public void setEntityData(SkyblockEntityData entityData) {
        SkyblockEntityTrait trait = npc.getOrAddTrait(SkyblockEntityTrait.class);
        trait.setEntityData(entityData);
    }

    public void setEntityId(int entityId) {
        SkyblockEntityTrait trait = npc.getOrAddTrait(SkyblockEntityTrait.class);
        trait.setEntityId(entityId);
    }

    public NPC getNpc() {
        return npc;
    }

    /*
     * Static Methods
     */

    public static boolean isNpc(Entity entity) {
        return entity.hasMetadata("NPC");
    }

    public static double damage(NPC npc, SkyblockPlayer sbPlayer, boolean doKnockback) {
        /*
         * Variables
         */
        SkyblockEntity sbEntity = SkyblockEntity.getSkyblockEntityFromNPC(npc);
        SkyblockEntityData entityData = npc.getOrAddTrait(SkyblockEntityTrait.class).getEntityData();
        SandboxItem sbItem = sbPlayer.getItemInHand(true);

        int weaponDamage = sbItem.getItemData().finalDamage();
        int combatSkillLevel = 60/* sbPlayer().getPlayerData().skillCombatLevel */; // TODO: Skills
        int criticalDamage = 1;
        if(Math.random() < (sbPlayer.getPlayerData().critChance / 100.0f)) {
            criticalDamage = sbPlayer.getPlayerData().critDamage / 100;
        }

        // double giantKiller = ((float) entity.getEntityHealth() - sbPlayer.getPlayerData().currentHealth) / sbPlayer.getPlayerData().currentHealth;
        double enchantmentBonus = 0; // TODO: Enchantments
        int weaponBonus = 0;
        int armorBonus = 1;

        int finalStrength = sbPlayer.getPlayerData().getFinalStrength();

        /*
         * Base Damage = (5 + WeaponDamage + [Strength / 5]) * (1 + [Strength / 100])
         */
        int strengthPart1 = finalStrength / 5;
        int strengthPart2 = finalStrength / 100;
        int baseDamage = (5 + weaponDamage + strengthPart1) * (1 + strengthPart2);

        /*
         * Damage Multiplier = 1 + (Combat Level * 0.04) + Enchantment Bonus + Weapon Bonus
         */
        double damageMultiplier = 1 + (combatSkillLevel * 0.04) + enchantmentBonus + weaponBonus;

        /*
         * Final Damage = Base Damage * Damage Multiplier * Armor Bonus * (1 + CritDamage)
         */
        double finalDamage = baseDamage * damageMultiplier * armorBonus * criticalDamage;

        /*
         * Deal Damage
         */
        double damage = finalDamage;
        float damageReduction = entityData.defense / (entityData.defense + 100);

        if(damageReduction > 0) {
            damage = damage - (damage * damageReduction);
        }

        sbEntity.damage(Math.round(damage));

        if(doKnockback && entityData.canTakeKnockback) {
            Bukkit.getScheduler().runTaskLater(SkyblockSandbox.getInstance(), () -> npc.getEntity().setVelocity( sbPlayer.getBukkitPlayer().getLocation().getDirection().multiply(0.2) ), 1L);
        }

        if(entityData.isBoss) {
            sbEntity.updateBossBar();
        } else {
            sbEntity.getBukkitEntity().setCustomName(colorize("&8[&7Lvl " + entityData.level +"&8] &c" + entityData.entityName + " &a" + Math.round(entityData.health) + "/" + Math.round(entityData.health) + "&c❤"));
        }

        sbEntity.hurt();
        npc.getEntity().setLastDamageCause(new EntityDamageByEntityEvent(sbPlayer.getBukkitPlayer(), npc.getEntity(), EntityDamageEvent.DamageCause.CUSTOM, 0));

        if(sbEntity.getEntityHealth() <= 0) {
            sbEntity.kill(true);
        }

        if(sbPlayer.getPlayerData().debugStateDamage) {
            sbPlayer.sendMessages(
                    "&9&m--------------------",
                    "final damage: " + damage,
                    "damage indicator: " + Math.round(damage),
                    "&9&m--------------------",
                    "weapon damage: " + weaponDamage,
                    "weapon bonus: " + weaponBonus,
                    "combat skill level: " + combatSkillLevel,
                    "critical damage: (0 = not a crit): " + criticalDamage,
                    "enchantment bonus: " + enchantmentBonus,
                    "weapon bonus: " + weaponBonus,
                    "armor bonus: " + armorBonus,
                    "&9&m--------------------",
                    "total strength (across inventory): " + finalStrength,
                    "strength p1: " + strengthPart1,
                    "strength p2: " + strengthPart2,
                    "&9&m--------------------",
                    "base damage: " + baseDamage,
                    "damage multiplier: " + damageMultiplier,
                    "damage before armor: " + finalDamage,
                    "&9&m--------------------",
                    "entity name: " + sbEntity.getEntityData().entityName,
                    "entity id: " + sbEntity.getEntityId(),
                    "entity health: " + sbEntity.getEntityHealth(),
                    "&9&m--------------------"
            );
        }

        return Math.round(damage);
    }

    public static double damage(NPC npc, double damage, boolean doKnockback) {
        SkyblockEntityData entityData = npc.getOrAddTrait(SkyblockEntityTrait.class).getEntityData();
        SkyblockEntity sbEntity = SkyblockEntity.getSkyblockEntityFromNPC(npc);

        double finalDamage = damage;

        float damageReduction = entityData.defense / (entityData.defense + 100);

        if(damageReduction > 0) {
            damage = damage - (damage * damageReduction);
        }

        sbEntity.damage(Math.round(damage));

        if(doKnockback && entityData.canTakeKnockback) {
            Bukkit.getScheduler().runTaskLater(SkyblockSandbox.getInstance(), () -> npc.getEntity().setVelocity( npc.getEntity().getLocation().getDirection().multiply(-0.2) ), 1L);
        }

        if(entityData.isBoss) {
            sbEntity.updateBossBar();
        } else {
            sbEntity.getBukkitEntity().setCustomName(colorize("&8[&7Lvl " + entityData.level +"&8] &c" + entityData.entityName + " &a" + Math.round(entityData.health) + "/" + Math.round(entityData.health) + "&c❤"));
        }

        sbEntity.hurt();
        npc.getEntity().setLastDamageCause(new EntityDamageEvent(npc.getEntity(), EntityDamageEvent.DamageCause.CUSTOM, 0));

        if(sbEntity.getEntityHealth() <= 0) {
            sbEntity.kill(true);
        }

        return Math.round(finalDamage);
    }

}
