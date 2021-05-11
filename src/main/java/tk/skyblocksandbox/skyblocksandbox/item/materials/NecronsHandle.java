package tk.skyblocksandbox.skyblocksandbox.item.materials;

import org.bukkit.Material;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;

import java.util.Collection;

public final class NecronsHandle extends SandboxItem {

    public NecronsHandle() {
        super(Material.STICK, "Necron's Handle", SkyblockItemIds.NECRONS_HANDLE);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore();

        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isMaterial = true;
        itemData.isDungeonItem = true;
        itemData.canHaveStars = false;
        itemData.canReforge = false;

        itemData.rarity = EPIC;
        itemData.itemType = ITEM;

        return itemData;
    }
}
