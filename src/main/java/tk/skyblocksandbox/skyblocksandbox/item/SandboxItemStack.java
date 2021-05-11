package tk.skyblocksandbox.skyblocksandbox.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

public final class SandboxItemStack {

    private final ItemStack item;
    private final NBTItem nbtItem;

    public SandboxItemStack(ItemStack item) {
        this.item = item;
        this.nbtItem = new NBTItem(item);
    }

    public ItemStack getBukkitItemStack() {
        return item;
    }

    public NBTItem getNbtItem() {
        return nbtItem;
    }

    public String getInternalId() {
        return nbtItem.getString("itemId");
    }

    /*
     * Skyblock Stats Functions
     */

    /*
     * Static Gets
     */

    public static boolean isSandboxItem(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.hasKey("itemId");
    }

    public static SandboxItem toSandboxItem(ItemStack item) {
        if(!isSandboxItem(item)) return null;
        NBTItem nbtItem = new NBTItem(item);

        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        return itemManager.getRegisteredItems().getOrDefault(nbtItem.getString("itemId"), null);
    }
}
