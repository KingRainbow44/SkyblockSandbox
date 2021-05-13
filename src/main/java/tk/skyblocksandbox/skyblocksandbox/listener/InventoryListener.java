package tk.skyblocksandbox.skyblocksandbox.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.BukkitSandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItemStack;

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

}
