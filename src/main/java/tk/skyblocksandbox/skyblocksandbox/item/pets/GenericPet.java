package tk.skyblocksandbox.skyblocksandbox.item.pets;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.pet.SkyblockPetData;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
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
        SkyblockPetData itemData = new SkyblockPetData();

        itemData.itemType = PET;
        itemData.rarity = MYTHIC;

        return itemData;
    }

    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(baseItem);

        // Item Meta - START \\
        ItemMeta meta = item.getItemMeta();
        if(meta != null) {
            meta.setDisplayName(Utility.rarityToColor(getItemData().rarity) + itemName);
            meta.setLore(new ArrayList<>(getLore()));

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_DYE);
        }
        item.setItemMeta(meta);
        // Item Meta - END \\

        NBTItem nbtItem = new NBTItem(item, true);

        NBTCompound petData = nbtItem.addCompound("petData");
        petData.setString("petName", "Skyblock");
        petData.setInteger("petLevel", 1);
        petData.setLong("petExperience", 0L);

        addNbt(item);
        return item;
    }
}
