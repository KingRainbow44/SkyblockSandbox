package tk.skyblocksandbox.skyblocksandbox.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.item.armor.necron.NecronBoots;
import tk.skyblocksandbox.skyblocksandbox.item.armor.necron.NecronChestplate;
import tk.skyblocksandbox.skyblocksandbox.item.armor.necron.NecronHelmet;
import tk.skyblocksandbox.skyblocksandbox.item.armor.necron.NecronLeggings;
import tk.skyblocksandbox.skyblocksandbox.item.bows.Bonemerang;
import tk.skyblocksandbox.skyblocksandbox.item.weapons.AxeOfTheShredded;
import tk.skyblocksandbox.skyblocksandbox.item.weapons.Hyperion;
import tk.skyblocksandbox.skyblocksandbox.item.weapons.MidasStaff;
import tk.skyblocksandbox.skyblocksandbox.item.materials.NecronsHandle;

import java.util.HashMap;
import java.util.Map;

public final class SkyblockItemManager {

    private final Map<String, SandboxItem> items = new HashMap<>();

    public SkyblockItemManager() {
        registerItem(new Hyperion());
        registerItem(new MidasStaff());
        registerItem(new Bonemerang());
        registerItem(new AxeOfTheShredded());

        registerItem(new NecronsHandle());

        registerItem(new NecronHelmet());
        registerItem(new NecronChestplate());
        registerItem(new NecronLeggings());
        registerItem(new NecronBoots());
    }

    public void registerItem(SandboxItem item) {
        items.put(item.getItemId(), item);
    }

    public Object isSkyblockItem(ItemStack item) {
        if(!isSBItemInstance(item)) return null;

        SandboxItemStack itemStack = new SandboxItemStack(item);

        return items.getOrDefault(itemStack.getInternalId(), null);
    }

    public Object isSkyblockItem(String itemId) {
        return items.getOrDefault(itemId, null);
    }

    public boolean isSBItemInstance(ItemStack itemStack) {
        if(itemStack == null || itemStack.getType() == Material.AIR) return false;

        NBTItem nbtItem = new NBTItem(itemStack);
        if(!nbtItem.hasKey("isSkyblockItem")) return false;
        return nbtItem.getBoolean("isSkyblockItem");
    }

    public Map<String, SandboxItem> getRegisteredItems() {
        return items;
    }
}
