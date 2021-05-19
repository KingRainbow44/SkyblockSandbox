package tk.skyblocksandbox.skyblocksandbox.item.misc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.menu.MenuFactory;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
import java.util.List;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class SkyblockMenu extends SandboxItem {

    public SkyblockMenu() {
        super(Material.NETHER_STAR, colorize("&aSkyblock Menu &7(Right Click)"), SkyblockItemIds.SKYBLOCK_MENU);
    }

    @Override
    public List<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();

        lore.add(colorize("&7View all of your Skyblock"));
        lore.add(colorize("&7progress, including your Skills,"));
        lore.add(colorize("&7Collections, Recipes, and more!"));
        lore.add(colorize(""));
        lore.add(colorize("&eClick to open!"));

        return lore;
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isSkyblockMenu = true;
        itemData.canReforge = false;
        itemData.rarity = SPECIAL;

        itemData.hasAbility = true;

        return itemData;
    }

    @Override
    public void ability(int action, SkyblockPlayer player) {
        SkyblockSandbox.getMenuFactory().serveMenu(player, MenuFactory.MenuList.SKYBLOCK_MENU_MAIN);
    }

    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(baseItem);

        // Item Meta - START \\
        ItemMeta meta = item.getItemMeta();
        if(meta != null) {
            meta.setUnbreakable(true);
            meta.setDisplayName(Utility.rarityToColor(getItemData().rarity) + itemName);
            meta.setLore(new ArrayList<>(getLore()));

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_DYE);
        }
        item.setItemMeta(meta);
        // Item Meta - END \\

        addNbt(item);

        return item;
    }
}
