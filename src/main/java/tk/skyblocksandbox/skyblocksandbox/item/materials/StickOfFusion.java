package tk.skyblocksandbox.skyblocksandbox.item.materials;

import org.bukkit.Material;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;

import java.util.Collection;

public final class StickOfFusion extends SandboxItem {

    public NecronsHandle() {
        super(Material.STICK, "Stick Of Fusion", SkyblockItemIds.STICK_OF_FUSION);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore(,
                " ",
                Utility.colorize("&7Special stick of admin fusion's"),
                Utility.colorize("&7Edition #1"),
                Utility.colorize("&7Granted to fusiongamerMC"),
                " ",)
        );
        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isMaterial = true;
        itemData.isDungeonItem = false;
        itemData.canHaveStars = false;
        itemData.canReforge = false;

        itemData.rarity = MYTHIC;
        itemData.itemType = ITEM;

        return itemData;
    }
}
