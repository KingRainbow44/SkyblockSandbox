package tk.skyblocksandbox.skyblocksandbox.menu.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.menu.MenuFactory;
import tk.skyblocksandbox.skyblocksandbox.menu.SkyblockMenu;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class PetModifierMenu extends SkyblockMenu {
    private SkyblockPlayer sbPlayer;
    private boolean callForUpdate = false;

    private ItemStack pet;

    @Override
    public void init(Player player, InventoryContents contents) {
        sbPlayer = SkyblockPlayer.getSkyblockPlayer(player); pet = player.getInventory().getItemInMainHand();
        ItemMeta glassMeta = toItemMeta(Material.BLACK_STAINED_GLASS_PANE); glassMeta.setDisplayName(" "); contents.fill(makeUnclickable(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), glassMeta));

        contents.set(1, 1, createItem(0));

        contents.set(5, 4, createItem(1));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        if(callForUpdate) {
            callForUpdate = false;
        }
    }

    private ClickableItem createItem(int itemType) {
        switch(itemType) {
            default:
            case 0:
                SkullMeta attributeMeta = (SkullMeta) pet.getItemMeta(); assert attributeMeta != null;
                attributeMeta.setDisplayName(colorize("&aModify Pet Attributes"));
                attributeMeta.setLore(toLore(
                        colorize("&7Modify basic pet attributes such"),
                        colorize("&7as pet experience!"),
                        " ",
                        colorize("&eClick to modify!")
                ));

                return makeClickable(Material.PLAYER_HEAD, attributeMeta, k -> {
                    SkyblockSandbox.getMenuFactory().serveMenu(sbPlayer, MenuFactory.MenuList.SKYBLOCK_MENU_MAIN);
                });
            case 1:
                ItemMeta closeMeta = toItemMeta(Material.BARRIER);
                closeMeta.setDisplayName(colorize("&cSave & Exit"));

                return makeClickable(Material.BARRIER, closeMeta, k -> {
                    sbPlayer.getBukkitPlayer().closeInventory();
                });
        }
    }
}
