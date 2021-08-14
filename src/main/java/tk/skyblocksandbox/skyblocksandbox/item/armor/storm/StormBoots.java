package tk.skyblocksandbox.skyblocksandbox.item.armor.storm;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
import java.util.Collection;

public final class StormBoots extends SandboxItem {

    public StormBoots() {
        super(Material.LEATHER_BOOTS, "Storm's Boots", SkyblockItemIds.STORM_BOOTS);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.itemType = BOOTS;
        itemData.rarity = LEGENDARY;
        itemData.isDungeonItem = true;
        itemData.hasAbility = true;
        itemData.abilityTrigger = FULL_SET_BONUS;

        itemData.abilityName = "Witherborn";
        itemData.abilityDescription = "&7Spawns a wither minion every\n" +
                "&e30 &7seconds up to a\n" +
                "&7maximum &a1 &7wither. Your withers will\n" +
                "&7travel to and explode on nearby\n" +
                "&7enemies.";

        itemData.baseHealth = 145;
        itemData.baseDefense = 65;
        itemData.baseIntelligence = 250;

        return itemData;
    }

    @Override
    public Collection<String> getLore() {
        Lore lore = new Lore(10,
                "",
                Utility.colorize("&7Reduces the damage you take"),
                Utility.colorize("&7from withers by &c10%&7.")
        );

        return lore.genericLore(this);
    }

    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(baseItem);

        // Item Meta - START \\
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        if(meta != null) {
            meta.setUnbreakable(true);
            meta.setDisplayName(Utility.rarityToColor(getItemData().rarity) + itemName);
            meta.setLore(new ArrayList<>(getLore()));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_DYE);

            meta.setColor(Color.fromRGB(28, 212, 228));
        }
        item.setItemMeta(meta);
        // Item Meta - END \\

        addNbt(item);

        return item;
    }
}
