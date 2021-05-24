package tk.skyblocksandbox.skyblocksandbox.item.misc;

import org.bukkit.Material;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;

import java.util.Collection;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class TheCarottesCarrot extends SandboxItem {
    public TheCarottesCarrot() {
        super(Material.CARROT, "TheCarotte's Carrot", SkyblockItemIds.THECAROTTES_CARROT);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore(3,
                colorize("&8Awarded to those who are worthy enough"),
                colorize("&8to take and handle the power of carrots..."),
                " "
        );

        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isDungeonItem = true;
        itemData.canHaveStars = false;

        itemData.baseHealth = 10;

        itemData.rarity = SPECIAL;
        itemData.itemType = ITEM;

        return itemData;
    }
}
