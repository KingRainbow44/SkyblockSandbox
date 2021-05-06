package tk.skyblocksandbox.skyblocksandbox.item;

import com.kingrainbow44.persistentdatacontainers.DataContainerAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.bows.Bonemerang;
import tk.skyblocksandbox.skyblocksandbox.item.weapons.Hyperion;
import tk.skyblocksandbox.skyblocksandbox.item.weapons.MidasStaff;
import tk.skyblocksandbox.skyblocksandbox.item.materials.NecronsHandle;

import java.util.HashMap;
import java.util.Map;

public final class SkyblockItemManager {

    private final Map<String, SkyblockItem> items = new HashMap<>();

    public SkyblockItemManager() {
        registerItem(new Hyperion());
        registerItem(new MidasStaff());
        registerItem(new Bonemerang());

        registerItem(new NecronsHandle());
    }

    public void registerItem(SkyblockItem item) {
        items.put(item.getItemId(), item);
    }

    public Object isSkyblockItem(ItemStack item) {
        if(item == null) return null;
        if(!DataContainerAPI.validityCheck(item, SkyblockSandbox.getInstance(), "id", PersistentDataType.STRING)) return false;

        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        Object itemId = DataContainerAPI.get(container, SkyblockSandbox.getInstance(), "id", PersistentDataType.STRING);
        if(!(itemId instanceof String)) return null;

        return items.getOrDefault(itemId, null);
    }

    public Object isSkyblockItem(String itemId) {
        return items.getOrDefault(itemId, null);
    }

    public Map<String, SkyblockItem> getRegisteredItems() {
        return items;
    }

}
