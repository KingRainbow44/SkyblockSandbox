package tk.skyblocksandbox.skyblocksandbox.item.weapons;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.npc.SkyblockNPC;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.Collection;
import java.util.Objects;

public final class GiantsSword extends SandboxItem {

    public GiantsSword() {
        super(Material.IRON_SWORD, "Giant's Sword", SkyblockItemIds.GIANTS_SWORD);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore();

        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isDungeonItem = true;
        itemData.rarity = LEGENDARY;
        itemData.itemType = SWORD;

        itemData.baseDamage = 500;

        itemData.hasAbility = true;
        itemData.abilityName = "Giant's Slam";
        itemData.abilityTrigger = RIGHT_CLICK_TRIGGER;
        itemData.abilityCooldown = 30;
        itemData.abilityCost = 100;
        itemData.abilityDescription = "&7Slam your sword into the ground\n" +
                "&7dealing &c100,000 &7damage to\n" +
                "&7nearby enemies.";

        return itemData;
    }

    @Override
    public void ability(int action, SkyblockPlayer sbPlayer) {
       if(action != INTERACT_RIGHT_CLICK) return;
        if(!sbPlayer.manaCheck(100, "Giant's Slam")) return;

        Player player = sbPlayer.getBukkitPlayer();

        Block block = player.getTargetBlock(null, 6);
        World world = block.getWorld();

        Location loc = block.getLocation().subtract(0, 1, 0);
        LivingEntity giant = (LivingEntity) world.spawnEntity(Utility.floor(loc), EntityType.GIANT);
        LivingEntity ride = (LivingEntity) world.spawnEntity(Utility.floor(loc), EntityType.ARMOR_STAND);

        ride.setMetadata("isNotSkyblockEntity", new FixedMetadataValue(SkyblockSandbox.getInstance(), true));
        ride.setInvisible(true);
        ride.setGravity(false);
        ride.setInvulnerable(true);
        ride.addPassenger(giant);

        giant.setMetadata("isNotSkyblockEntity", new FixedMetadataValue(SkyblockSandbox.getInstance(), true));
        giant.setCustomName("Dinnerbone");
        giant.setCustomNameVisible(false);
        giant.setGravity(false);
        giant.setInvulnerable(true);
        giant.setInvisible(true);

        Objects.requireNonNull(giant.getEquipment()).setItemInMainHand(create());
        new BukkitRunnable() {
            @Override
            public void run() {
                ride.setHealth(0);
                ride.remove();

                giant.remove();
                giant.setHealth(0);
            }
        }.runTaskLater(SkyblockSandbox.getInstance(), 20*5L);

        long damage = Calculator.damage(sbPlayer, 100000, 0.05);

        int entityCount = 0;
        for(Entity e : player.getNearbyEntities(4, 4, 4)) {
            if(e instanceof Damageable && e != sbPlayer.getBukkitPlayer()) {
                Damageable entity = (Damageable) e;
                if(!entity.hasMetadata("skyblockEntityId")) continue;
                if(entity instanceof Player && !entity.hasMetadata("NPC")) continue;

                SandboxEntity sbEntity = SandboxEntity.getSandboxEntity(entity);
                Calculator.damage(sbEntity, sbPlayer, true);

                entityCount++;
            }
        }

        if(entityCount > 0) {
            player.sendMessage(Utility.colorize("&7Your Giant's Slam did &c" + (Utility.commafy(damage * entityCount)) + "&7 damage to " + entityCount + " enemies."));
        }
    }
}
