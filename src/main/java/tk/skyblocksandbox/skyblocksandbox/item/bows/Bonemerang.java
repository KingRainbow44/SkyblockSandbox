package tk.skyblocksandbox.skyblocksandbox.item.bows;

import de.tr7zw.nbtapi.NBTItem;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
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

import java.util.ArrayList;
import java.util.Collection;

public final class Bonemerang extends SandboxItem {

    public Bonemerang() {
        super(Material.BONE, "Bonemerang", SkyblockItemIds.BONEMERANG);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore(9,
                Utility.colorize("&7Deals &cdouble damage &7when"),
                Utility.colorize("&7coming back. Pierces up to &e10"),
                Utility.colorize("&7foes."),
                ""
        );

        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isDungeonItem = true;

        itemData.baseDamage = 270;
        itemData.baseStrength = 130;
        itemData.unStackable = true;

        itemData.hasAbility = true;
        itemData.abilityName = "Swing";
        itemData.abilityTrigger = RIGHT_CLICK_TRIGGER;
        itemData.abilityDescription = "&7Throw the bone a short distance\n" +
                "&7dealing the damage an arrow\n" +
                "&7would.";

        itemData.rarity = LEGENDARY;
        itemData.itemType = BOW;

        return itemData;
    }

    @Override
    public void ability(int action, SkyblockPlayer player) {
        if(action != INTERACT_RIGHT_CLICK) return;
        if(!boneCheck(player)) return;

        Player bukkitPlayer = player.getBukkitPlayer();
        ItemStack bone = bukkitPlayer.getInventory().getItemInMainHand();

        ArmorStand stand = (ArmorStand) bukkitPlayer.getWorld().spawnEntity(bukkitPlayer.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND);
        Vector teleportTo = bukkitPlayer.getLocation().getDirection().normalize().multiply(1);

        NBTItem nbtItem = new NBTItem(bone, true);

        stand.setMetadata("isNotSkyblockEntity", new FixedMetadataValue(SkyblockSandbox.getInstance(), true));

        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);

        stand.setArms(false);
        stand.getEquipment().setItemInMainHand(new ItemStack(Material.BONE));
        stand.setRightArmPose(new EulerAngle(Math.toRadians(350), Math.toRadians(120), Math.toRadians(0)));
        stand.setBasePlate(false);

        bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.BLOCK_BONE_BLOCK_BREAK, 2.0f, 2.0f);

        Collection<Entity> goBone = new ArrayList<>();
        Collection<Entity> backBone = new ArrayList<>();

        new BukkitRunnable() {
            public int ran = 0;

            @Override
            public void run() {
                ran++;

                if(ran == 26) {
                    stand.remove();
                    returnBone(bone);

                    cancel();
                    return;
                }

                int i = ran;
                int num = 120;
                int angle;
                boolean back;

                Location loc = null;

                if(i < 13) {
                    angle = (i * 20) + num;
                    back = false;
                } else {
                    angle = (i * 20) - num;
                    back = true;

                    loc = bukkitPlayer.getLocation();
                    loc.setDirection(teleportTo);
                }

                if(!stand.getLocation().getBlock().getType().isTransparent()) {
                    stand.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, stand.getLocation(), 3);

                    stand.remove();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if(nbtItem.getInteger("ejectedBonemerang") == 1) {
                                returnBone(bone);
                                cancel();
                            }
                        }
                    }.runTaskLater(SkyblockSandbox.getInstance(), 20*3L);

                    cancel();
                    return;
                }

                stand.setRightArmPose(new EulerAngle(Math.toRadians(0), Math.toRadians(angle), Math.toRadians(380)));

                if(i % 2 == 0 && i < 13) {
                    stand.teleport(stand.getLocation().add(teleportTo).multiply(1));
                    stand.teleport(stand.getLocation().add(teleportTo).multiply(1));
                } else if(i % 2 == 0) {
                    stand.teleport(stand.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                    stand.teleport(stand.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                }

                for(Entity e : stand.getNearbyEntities(1, 1, 1)) {
                    if(e instanceof Damageable && e != player.getBukkitPlayer()) {
                        Damageable entity = (Damageable) e;

                        if(!back && goBone.contains(entity)) continue;
                        if(back && backBone.contains(entity)) continue;

                        if(!back && !goBone.contains(entity)) {
                            goBone.add(entity);
                        } else if (back && !backBone.contains(entity)) {
                            backBone.add(entity);
                        }

                        if(!entity.hasMetadata("skyblockEntityId")) continue;
                        if(entity instanceof Player && !entity.hasMetadata("NPC")) continue;

                        SandboxEntity sbEntity = SandboxEntity.getSandboxEntity(entity);
                        Calculator.damage(sbEntity, player, true);
                        if(back) {
                            Calculator.damage(sbEntity, player, true);
                        }
                    }
                }

            }

        }.runTaskTimer(SkyblockSandbox.getInstance(), 2L, 0L);
    }

    /*
     * The check for Bonemerang.
     */
    private boolean boneCheck(SkyblockPlayer player) {
        ItemStack item = player.getBukkitPlayer().getInventory().getItemInMainHand();

        NBTItem nbtItem = new NBTItem(item, true);

        if(!nbtItem.hasKey("ejectedBonemerang")) {
            nbtItem.setInteger("ejectedBonemerang", 0);
        }

        int ejected = nbtItem.getInteger("ejectedBonemerang");

        if(ejected == 1) {
            return false;
        } else {
            item.setType(Material.GHAST_TEAR);
            nbtItem.setInteger("ejectedBonemerang", 1);
            return true;
        }
    }

    /*
     * Return Bonemerang function.
     */
    private void returnBone(ItemStack item) {
        if(item.getType() == Material.AIR) return;

        NBTItem nbtItem = new NBTItem(item, true);
        if(!nbtItem.hasKey("ejectedBonemerang")) {
            return;
        }

        nbtItem.setInteger("ejectedBonemerang", 0);
        item.setType(Material.BONE);
    }
}
