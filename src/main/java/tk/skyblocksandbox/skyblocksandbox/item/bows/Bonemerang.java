package tk.skyblocksandbox.skyblocksandbox.item.bows;

import com.kingrainbow44.persistentdatacontainers.DataContainerAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.Collection;

public final class Bonemerang extends SkyblockItem {

    public Bonemerang() {
        super("&6Bonemerang", Material.BONE, SkyblockItemIds.BONEMERANG);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore(0,
                "",
                Utility.colorize("&7Deals &cdouble damage &7when"),
                Utility.colorize("&7coming back. Pierces up to &e10"),
                Utility.colorize("&7foes."),
                " "
        );

        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isDungeonItem = true;

        itemData.baseDamage = 270;
        itemData.baseStrength = 130;

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

        ArmorStand stand = (ArmorStand) bukkitPlayer.getWorld().spawnEntity(bukkitPlayer.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND);
        Vector teleportTo = bukkitPlayer.getLocation().getDirection().normalize().multiply(1);

        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);

        stand.setArms(false);
        stand.getEquipment().setItemInMainHand(createItem());
        stand.setRightArmPose(new EulerAngle(Math.toRadians(350), Math.toRadians(120), Math.toRadians(0)));
        stand.setBasePlate(false);

        bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.BLOCK_BONE_BLOCK_BREAK, 2.0f, 2.0f);

        new BukkitRunnable() {
            public int ran = 0;

            @Override
            public void run() {
                ran++;

                if(ran == 30) {
                    stand.remove();
                    cancel();
                    return;
                }

                int i = ran;
                int num = 120;
                int angle = 0;
                boolean back;

                if(i < 13) {
                    angle = (i * 20) + num;
                    back = false;
                } else {
                    angle = (i * 20) - num;
                    back = true;
                }
                stand.setRightArmPose(new EulerAngle(Math.toRadians(0), Math.toRadians(angle), Math.toRadians(380)));

                if(i % 2 == 0 && i < 13) {
                    stand.teleport(stand.getLocation().add(teleportTo).multiply(1));
                    stand.teleport(stand.getLocation().add(teleportTo).multiply(1));
                } else if(i % 2 == 0) {
                    stand.teleport(stand.getLocation().subtract(player.getBukkitPlayer().getLocation().getDirection().normalize().multiply(1)).multiply(1));
                    stand.teleport(stand.getLocation().subtract(player.getBukkitPlayer().getLocation().getDirection().normalize().multiply(1)).multiply(1));
                }

                for(Entity e : stand.getNearbyEntities(1, 1, 1)) {
                    if(e instanceof Damageable && e != player.getBukkitPlayer()) {
                        Damageable entity = (Damageable)e;
                        if(entity instanceof ArmorStand) return;

                        if(entity instanceof Player) {
                            SkyblockPlayer sbTarget = (SkyblockPlayer) SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer((Player) entity);
                            Calculator.damage(sbTarget, 30f, true, false);
                            if(back) {
                                Calculator.damage(sbTarget, 30f, true, false);
                            }
                        }else{
                            SkyblockEntity sbEntity = SkyblockSandbox.getManagement().getEntityManager().getEntity(entity);
                            if(sbEntity == null) return;

                            entity.setLastDamageCause(new EntityDamageByEntityEvent(player.getBukkitPlayer(), e, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 0));
                            Calculator.damage(sbEntity, player, true);
                            if(back) {
                                Calculator.damage(sbEntity, player, true);
                            }
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
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();

        if(!DataContainerAPI.has(item, SkyblockSandbox.getInstance(), "ejectedBonemerang", PersistentDataType.INTEGER)) {
            DataContainerAPI.set(dataContainer, SkyblockSandbox.getInstance(), "ejectedBonemerang", 0);
        }

        Object ejected = DataContainerAPI.get(dataContainer, SkyblockSandbox.getInstance(), "ejectedBonemerang", PersistentDataType.INTEGER);
        if(!(ejected instanceof Integer)) return false;

        if((int) ejected == 1) {
            return false;
        } else {
            item.setType(Material.GHAST_TEAR);
            DataContainerAPI.set(dataContainer, SkyblockSandbox.getInstance(), "ejectedBonemerang", 1);

            new BukkitRunnable() {
                /**
                 * When an object implementing interface {@code Runnable} is used
                 * to create a thread, starting the thread causes the object's
                 * {@code run} method to be called in that separately executing
                 * thread.
                 * <p>
                 * The general contract of the method {@code run} is that it may
                 * take any action whatsoever.
                 *
                 * @see Thread#run()
                 */
                @Override
                public void run() {
                    Object ejected = DataContainerAPI.get(dataContainer, SkyblockSandbox.getInstance(), "ejectedBonemerang", PersistentDataType.INTEGER);
                    if(!(ejected instanceof Integer)) return;

                    if((int) ejected == 1) {
                        returnBone(item);
                        cancel();
                    }
                }
            }.runTaskLater(SkyblockSandbox.getInstance(), 20*3L);
            return true;
        }
    }

    /*
     * Return Bonemerang function.
     */
    private void returnBone(ItemStack item) {
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();

        if(!DataContainerAPI.has(item, SkyblockSandbox.getInstance(), "ejectedBonemerang", PersistentDataType.INTEGER)) {
            return;
        }

        DataContainerAPI.set(dataContainer, SkyblockSandbox.getInstance(), "ejectedBonemerang", 0);
        item.setType(Material.BONE);
    }
}
