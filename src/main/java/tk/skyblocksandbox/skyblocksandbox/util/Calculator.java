package tk.skyblocksandbox.skyblocksandbox.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class Calculator {

    /*
     * Calculator
     * Used for doing calculations during, for example, damage.
     */

    public static long damage(SkyblockPlayer sbPlayer, int baseAbilityDamage, double abilityScaling) {
        int intelligence = sbPlayer.getPlayerData().getFinalIntelligence();

        return Math.round(baseAbilityDamage * (1 + (intelligence / 100f) * abilityScaling));
    }

    public static void damage(SkyblockPlayer sbPlayer, float damage, boolean doKnockback) {
        Player player = sbPlayer.getBukkitPlayer();

        double finalDamage = damage;
        float damageReduction = sbPlayer.getPlayerData().defense / (sbPlayer.getPlayerData().defense + 100f);

        if(damageReduction > 0) {
            finalDamage = damage - (damage * damageReduction);
        }

        sbPlayer.getPlayerData().damage((int) Math.round(finalDamage));

        if(doKnockback && sbPlayer.getPlayerData().canTakeKnockback) {
            Bukkit.getScheduler().runTaskLater(SkyblockSandbox.getInstance(), () -> sbPlayer.getBukkitPlayer().setVelocity( sbPlayer.getBukkitPlayer().getLocation().getDirection().multiply(-0.2) ), 1L);
        }

        sbPlayer.updateHud();

        sbPlayer.hurt();
        player.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.CUSTOM, 0));

        if(sbPlayer.getPlayerData().currentHealth <= 0) {
            sbPlayer.kill();
        }
    }

    public static long damage(SkyblockPlayer sbTarget, SkyblockPlayer sbPlayer, boolean doKnockback) {
        /*
         * Variables
         */
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
        float damageReduction = (float) sbTarget.getPlayerData().getFinalDefense() / (sbTarget.getPlayerData().getFinalDefense() + 100);

        if(damageReduction > 0) {
            damage = damage - (damage * damageReduction);
        }

        sbTarget.getPlayerData().damage((int) Math.round(damage));

        if(doKnockback && sbTarget.getPlayerData().canTakeKnockback) {
            Bukkit.getScheduler().runTaskLater(SkyblockSandbox.getInstance(), () -> sbTarget.getBukkitPlayer().setVelocity( sbPlayer.getBukkitPlayer().getLocation().getDirection().multiply(0.2) ), 1L);
        }

        sbTarget.hurt();
        sbTarget.getBukkitPlayer().setLastDamageCause(new EntityDamageByEntityEvent(sbPlayer.getBukkitPlayer(), sbTarget.getBukkitPlayer(), EntityDamageEvent.DamageCause.CUSTOM, 0));

        if(sbTarget.getPlayerData().currentHealth <= 0) {
            sbTarget.kill();
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
                    "&9&m--------------------"
            );
        }

        return Math.round(damage);
    }

    public static long damage(SandboxEntity entity, float damage, boolean doKnockback) {
        double finalDamage = damage;
        float damageReduction = entity.getEntityData().defense / (entity.getEntityData().defense + 100);

        if(damageReduction > 0) {
            damage = damage - (damage * damageReduction);
        }

        entity.damage(Math.round(damage));

        if(doKnockback && entity.getEntityData().canTakeKnockback) {
            Bukkit.getScheduler().runTaskLater(SkyblockSandbox.getInstance(), () -> entity.getBukkitEntity().setVelocity( entity.getBukkitEntity().getLocation().getDirection().multiply(-0.2) ), 1L);
        }

        if(entity.getEntityData().isBoss) {
            entity.updateBossBar();
        } else {
            entity.getBukkitEntity().setCustomName(colorize("&8[&7Lvl " + entity.getEntityData().level +"&8] &c" + entity.getEntityData().entityName + " &a" + Math.round(entity.getEntityData().health) + "/" + Math.round(entity.getEntityData().health) + "&c❤"));
        }

        entity.hurt();
        entity.getBukkitEntity().setLastDamageCause(new EntityDamageEvent(entity.getBukkitEntity(), EntityDamageEvent.DamageCause.CUSTOM, 0));

        if(entity.getHealth() <= 0) {
            entity.kill(true);
        }

        return Math.round(finalDamage);
    }

    public static long damage(SandboxEntity entity, SkyblockPlayer sbPlayer, boolean doKnockback) {
        /*
         * Variables
         */
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
        float damageReduction = entity.getEntityData().defense / (entity.getEntityData().defense + 100);

        if(damageReduction > 0) {
            damage = damage - (damage * damageReduction);
        }

        entity.damage(Math.round(damage));

        if(doKnockback && entity.getEntityData().canTakeKnockback) {
            Bukkit.getScheduler().runTaskLater(SkyblockSandbox.getInstance(), () -> entity.getBukkitEntity().setVelocity( sbPlayer.getBukkitPlayer().getLocation().getDirection().multiply(0.2) ), 1L);
        }

        if(entity.getEntityData().isBoss) {
            entity.updateBossBar();
        } else {
            entity.getBukkitEntity().setCustomName(colorize("&8[&7Lvl " + entity.getEntityData().level +"&8] &c" + entity.getEntityData().entityName + " &a" + Math.round(entity.getEntityData().health) + "/" + Math.round(entity.getEntityData().health) + "&c❤"));
        }

        entity.hurt();
        entity.getBukkitEntity().setLastDamageCause(new EntityDamageByEntityEvent(sbPlayer.getBukkitPlayer(), entity.getBukkitEntity(), EntityDamageEvent.DamageCause.CUSTOM, 0));

        if(entity.getHealth() <= 0) {
            entity.kill(true);
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
                    "entity name: " + entity.getEntityData().entityName,
                    "entity id: " + entity.getEntityId(),
                    "entity health: " + entity.getHealth(),
                    "&9&m--------------------"
            );
        }

        return Math.round(damage);
    }

}
