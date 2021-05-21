package tk.skyblocksandbox.skyblocksandbox.listener;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.*;

import java.util.Objects;

public final class InventoryListener implements Listener {

    /*
     * Inventory related events can be found at https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/inventory/package-summary.html
     */

    @EventHandler
    public void onInventoryPickup(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof Player)) return;

        Player player = (Player) entity;

        ItemStack item = event.getItem().getItemStack();
        event.getItem().remove();

        if(!SandboxItemStack.isSandboxItem(item)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getInventory().remove(item);
                }
            }.runTaskLater(SkyblockSandbox.getInstance(), 5L);
            player.getInventory().addItem(new BukkitSandboxItem(item).create());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Entity entity = event.getPlayer();
        if(!(entity instanceof Player)) return;

        Player player = (Player) event.getPlayer();
        if(player.getInventory().getItem(8) == null || Objects.requireNonNull(player.getInventory().getItem(8)).getType() == Material.AIR) {
            Object sbMenu = SkyblockSandbox.getManagement().getItemManager().isSkyblockItem(SkyblockItemIds.SKYBLOCK_MENU);
            player.getInventory().setItem(8, ((SandboxItem) sbMenu).create());
            return;
        }

        NBTItem nbtItem = new NBTItem(Objects.requireNonNull(player.getInventory().getItem(8)), true);

        if(!nbtItem.hasKey("isSkyblockMenu") || !nbtItem.getBoolean("isSkyblockMenu")) {
            Object sbMenu = SkyblockSandbox.getManagement().getItemManager().isSkyblockItem(SkyblockItemIds.SKYBLOCK_MENU);
            player.getInventory().setItem(8, ((SandboxItem) sbMenu).create());
        }
    }

}
