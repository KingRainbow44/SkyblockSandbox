package tk.skyblocksandbox.skyblocksandbox.item.weapons;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
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

public final class AxeOfTheShredded extends SandboxItem {

    public AxeOfTheShredded() {
        super(Material.DIAMOND_AXE, "Axe of the Shredded", SkyblockItemIds.AXE_OF_THE_SHREDDED);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore(2,
                "",
                Utility.colorize("&7Heal &c50❤ &7per hit."),
                Utility.colorize("&7Deal &a250% &7more damage to Zombies."),
                Utility.colorize("&7Receive &a25% &7less damage"),
                Utility.colorize("&7from Zombies when held")
        );

        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.rarity = LEGENDARY;
        itemData.itemType = SWORD;
        itemData.baseDamage = 140;
        itemData.baseStrength = 115;

        itemData.hasAbility = true;
        itemData.abilityTrigger = RIGHT_CLICK_TRIGGER;
        itemData.abilityName = "Throw";
        itemData.abilityCost = 20;
        itemData.abilityDescription = "&7Throw your axe damaging all\n" +
                "&7enemies in its path dealing\n" +
                "&c10% &7melee damage.\n" +
                "&7Consecutive throws stack &c2x\n" +
                "&7damage but cost &92x &7mana up\n" +
                "&7to 16x";

        return itemData;
    }


    @Override
    public void ability(int action, SkyblockPlayer sbPlayer) {
        if(action != INTERACT_RIGHT_CLICK) return;
        if(!sbPlayer.manaCheck(20, "Throw")) return;

        Player player = sbPlayer.getBukkitPlayer();

        Location throwLoc = player.getLocation().add(0, 1.2, 0);
        Vector throwVec = player.getLocation().add(player.getLocation().getDirection().multiply(10)).toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.2D);

        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(throwLoc, EntityType.ARMOR_STAND);
        armorStand.setMetadata("isNotSkyblockEntity", new FixedMetadataValue(SkyblockSandbox.getInstance(), true));
        armorStand.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.setSmall(true);

        final Vector[] previousVector = {throwVec};
        new BukkitRunnable() {
            private int run = -1;

            @Override
            public void run() {
                if(run < 101) {
                    run++;
                } else {
                    cancel();
                    return;
                }

                if(!armorStand.getLocation().getBlock().getType().isTransparent() || armorStand.isOnGround()) {
                    armorStand.remove();
                    cancel();
                    return;
                }

                double xPos = armorStand.getRightArmPose().getX();
                armorStand.setRightArmPose(new EulerAngle(xPos + 0.6D, 0.0D, 0.0D));

                Vector newVector = new Vector(throwVec.getX(), previousVector[0].getY() - 0.03D, throwVec.getZ());
                previousVector[0] = newVector;
                armorStand.setVelocity(newVector);

                for(Entity e : armorStand.getNearbyEntities(1, 1, 1)) {
                    if(e instanceof Damageable && e != sbPlayer.getBukkitPlayer()) {
                        Damageable entity = (Damageable) e;
                        if(!entity.hasMetadata("skyblockEntityId")) return;
                        if(entity instanceof Player && !entity.hasMetadata("NPC")) return;

                        SandboxEntity sbEntity = SandboxEntity.getSandboxEntity(entity);
                        Calculator.damage(sbEntity, sbPlayer, true);
                    }
                }
            }
        }.runTaskTimer(SkyblockSandbox.getInstance(), 0L, 2L);
    }
}
