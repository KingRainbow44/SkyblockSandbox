package tk.skyblocksandbox.skyblocksandbox.listener;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.event.ArmorEquipEvent;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItemStack;
import tk.skyblocksandbox.skyblocksandbox.menu.MenuFactory;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class ItemListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(player);

        int ability;
        switch(event.getAction()) {
            default:
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                ability = SandboxItem.INTERACT_RIGHT_CLICK;
                break;
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                ability = SandboxItem.INTERACT_LEFT_CLICK;
                break;
        }

        SandboxItem sbItem = sbPlayer.getItemInHand(true);
        if(sbItem == null) return;

        sbItem.ability(ability, sbPlayer);
        if(sbItem.shouldCancel()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorStandInteract(PlayerInteractAtEntityEvent event) {
        if(event.getRightClicked() instanceof ArmorStand) {
            Event interactEvent = new PlayerInteractEvent(event.getPlayer(), Action.RIGHT_CLICK_AIR, null, null, BlockFace.SELF);
            Bukkit.getPluginManager().callEvent(interactEvent);

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            NBTItem nbt = new NBTItem(event.getCurrentItem(), true);
            if(!nbt.hasKey("itemData")) return;

            NBTCompound nbtItem = nbt.getCompound("itemData");
            if(nbtItem.hasKey("isSkyblockMenu") && nbtItem.getBoolean("isSkyblockMenu")) {
                if(event.getWhoClicked() instanceof Player) {
                    SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) event.getWhoClicked());
                    SkyblockSandbox.getMenuFactory().serveMenu(sbPlayer, MenuFactory.MenuList.SKYBLOCK_MENU_MAIN);

                    event.setCancelled(true);
                }
            }
        }

        if(event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
            NBTItem nbt = new NBTItem(event.getCursor(), true);
            if(!nbt.hasKey("itemData")) return;

            NBTCompound nbtItem = nbt.getCompound("itemData");
            if(nbtItem.hasKey("isSkyblockMenu") && nbtItem.getBoolean("isSkyblockMenu")) {
                if(event.getWhoClicked() instanceof Player) {
                    SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) event.getWhoClicked());
                    SkyblockSandbox.getMenuFactory().serveMenu(sbPlayer, MenuFactory.MenuList.SKYBLOCK_MENU_MAIN);

                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        NBTItem nbt = new NBTItem(event.getItemDrop().getItemStack(), true);
        if(!nbt.hasKey("itemData")) return;

        NBTCompound nbtItem = nbt.getCompound("itemData");
        if(nbtItem.hasKey("isSkyblockMenu") && nbtItem.getBoolean("isSkyblockMenu")) {
            SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(event.getPlayer());
            SkyblockSandbox.getMenuFactory().serveMenu(sbPlayer, MenuFactory.MenuList.SKYBLOCK_MENU_MAIN);

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if(event.getCursor() == null) return;
        NBTItem nbt = new NBTItem(event.getCursor(), true);
        if(!nbt.hasKey("itemData")) return;

        NBTCompound nbtItem = nbt.getCompound("itemData");
        if(nbtItem.hasKey("isSkyblockMenu") && nbtItem.getBoolean("isSkyblockMenu")) {
            if(event.getWhoClicked() instanceof Player) {
                SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) event.getWhoClicked());
                SkyblockSandbox.getMenuFactory().serveMenu(sbPlayer, MenuFactory.MenuList.SKYBLOCK_MENU_MAIN);

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEquip(ArmorEquipEvent event) {
        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(event.getPlayer());

        if(event.getOldArmorPiece() != null) {
            ItemStack old = event.getOldArmorPiece();
            if(SandboxItemStack.isSandboxItem(old)) {
                SandboxItem sbOld = SandboxItemStack.toSandboxItem(old);
                sbOld.onRemove(sbPlayer);
            }
        }

        if(event.getNewArmorPiece() != null) {
            ItemStack newArmor = event.getNewArmorPiece();
            if(SandboxItemStack.isSandboxItem(newArmor)) {
                SandboxItem sbNew = SandboxItemStack.toSandboxItem(newArmor);
                sbNew.onWear(sbPlayer);
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if(!(event.getEntity() instanceof Arrow)) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                event.getEntity().remove();
            }
        }.runTaskLater(SkyblockSandbox.getInstance(), 20L);
    }

}
