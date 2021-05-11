package tk.skyblocksandbox.skyblocksandbox.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

public final class InventoryListener implements Listener {

    /*
     * Inventory related events can be found at https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/inventory/package-summary.html
     */

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        if(!(entity instanceof Player)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null) return;

        if(!SkyblockSandbox.getManagement().getItemManager().isSBItemInstance(item)) {

        }
    }

    @EventHandler
    public void onInventoryClose(InventoryClickEvent event) {

    }

    @EventHandler
    public void onCreativeInteraction(InventoryClickEvent event) {

    }

    @EventHandler
    public void onInventoryDrag(InventoryClickEvent event) {

    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {

    }

    @EventHandler
    public void onInventoryMoveItem(InventoryClickEvent event) {

    }

    @EventHandler
    public void onInventoryOpen(InventoryClickEvent event) {

    }

    @EventHandler
    public void onInventoryPickup(EntityPickupItemEvent event) {

    }

}
