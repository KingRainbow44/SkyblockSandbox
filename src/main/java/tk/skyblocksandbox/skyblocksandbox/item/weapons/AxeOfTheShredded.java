package tk.skyblocksandbox.skyblocksandbox.item.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
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
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class AxeOfTheShredded extends SandboxItem {

    private static final Map<Player, Integer> axeThrows = new HashMap<>();

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

        itemData.canDungeonize = true;

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

        Player player = sbPlayer.getBukkitPlayer();

        // Incorporate Stacking
        int manaCost, damageBoost, currentAxeThrows;

        if(!axeThrows.containsKey(player)) {
            axeThrows.put(player, 0);
        }

        axeThrows.put(player, axeThrows.get(player) + 1);
        currentAxeThrows = axeThrows.get(player);
        switch(currentAxeThrows - 1) {
            case 0:
                manaCost = 20; damageBoost = 1;
                break;
            case 1:
                manaCost = 40; damageBoost = 2;
                break;
            case 2:
                manaCost = 60; damageBoost = 4;
                break;
            case 3:
                manaCost = 80; damageBoost = 8;
                break;
            default:
            case 4:
                manaCost = 160; damageBoost = 16;
                break;
        }

        if(sbPlayer.getPlayerData().debugStateMessages) {
            sbPlayer.sendMessages(
                    "mana cost: " + manaCost,
                    "damage boost: " + damageBoost,
                    "------------",
                    "in axe throws: " + axeThrows.containsKey(player)
            );
        }

        if(!sbPlayer.manaCheck(manaCost, "Throw")) return;

        int finalCurrentAxeThrows = currentAxeThrows;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(finalCurrentAxeThrows == axeThrows.get(player)) {
                    axeThrows.replace(player, 0);
                }
            }
        }.runTaskLater(SkyblockSandbox.getInstance(), 10L);

        Location throwLoc = player.getLocation().add(0, 1.2, 0);
        Vector throwVec = player.getLocation().add(player.getLocation().getDirection().multiply(10)).toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.2D);

        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(throwLoc, EntityType.ARMOR_STAND);
        armorStand.setMetadata("isNotSkyblockEntity", new FixedMetadataValue(SkyblockSandbox.getInstance(), true));
        armorStand.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);

        final Vector[] previousVector = {throwVec};
        Collection<Entity> damaged = new ArrayList<>();

        new BukkitRunnable() {
            private int run = -1;

            @Override
            public void run() {
                run++;
                if(run > 100) {
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
                        if(damaged.contains(e)) continue;
                        if(!damaged.contains(e)) {
                            damaged.add(e);
                        }

                        if(!entity.hasMetadata("skyblockEntityId")) continue;
                        if(entity instanceof Player && !entity.hasMetadata("NPC")) continue;

                        SandboxEntity sbEntity = SandboxEntity.getSandboxEntity(entity);
                        Calculator.damage(sbEntity, sbPlayer, true, damageBoost, 0.1f);
                    }
                }
            }
        }.runTaskTimer(SkyblockSandbox.getInstance(), 0L, 2L);
    }
}
