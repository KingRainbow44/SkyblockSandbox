package tk.skyblocksandbox.skyblocksandbox.item.pets;

import org.bukkit.Material;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import java.util.Collection;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class GenericPet extends SandboxItem {

    public GenericPet() {
        super(Material.PLAYER_HEAD, "Generic Pet", SkyblockItemIds.PET);
    }

    @Override
    public Collection<String> getLore() {
        return new Lore("start",
                colorize("&eYour very own pet!"),
                colorize("&eUse the Custom Item Creator"),
                colorize("&eor '/pet' command to customize"),
                colorize("&eyour pet!"),
                colorize(" ")
        ).genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.itemType = PET;
        itemData.rarity = MYTHIC;

        itemData.isPet = true;

        return itemData;
    }
}
