package tk.skyblocksandbox.skyblocksandbox.menu.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.skyblocksandbox.skyblocksandbox.menu.SkyblockMenu;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.storage.pets.PetStorage;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class PetsSkyblockMenu extends SkyblockMenu {

    private SkyblockPlayer sbPlayer;
    private boolean callForUpdate = false;

    @Override
    public void init(Player player, InventoryContents contents) {
        sbPlayer = SkyblockPlayer.getSkyblockPlayer(player);
        ItemMeta glassMeta = toItemMeta(Material.BLACK_STAINED_GLASS_PANE); glassMeta.setDisplayName(" "); contents.fillBorders(makeUnclickable(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), glassMeta));

        contents.set(1, 1, createPetItem(0));

        contents.set(5, 4, createItem(0));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        if(callForUpdate) {
            callForUpdate = false;

            contents.set(1, 1, createItem(0));
        }
    }

    private ClickableItem createItem(int itemType) {
        switch(itemType) {
            default:
            case 0:
                ItemMeta closeMeta = toItemMeta(Material.BARRIER);
                closeMeta.setDisplayName(colorize("&cClose"));

                return makeClickable(Material.BARRIER, closeMeta, k -> {
                    sbPlayer.getBukkitPlayer().closeInventory();
                });
            case 1:
                String status;
                if(sbPlayer.getPlayerData().pvpEnabled) status = colorize("&aEnabled"); else status = colorize("&cDisabled");
                ItemMeta pvpMeta = toItemMeta(Material.IRON_SWORD);
                pvpMeta.setDisplayName(colorize("&aToggle PvP"));
                pvpMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                pvpMeta.setLore(toLore(
                        colorize("&7Enable Server-wide PvP."),
                        colorize(" "),
                        colorize("&7Current Status: " + status),
                        colorize(" "),
                        colorize("&eClick to toggle!")
                ));

                return makeClickable(Material.IRON_SWORD, pvpMeta, k -> {
                    if(sbPlayer.getPlayerData().pvpEnabled) {
                        sbPlayer.getPlayerData().pvpEnabled = false;
                    } else {
                        sbPlayer.getPlayerData().pvpEnabled = true;
                    }
                    callForUpdate = true;
                });
        }
    }

    private ClickableItem createPetItem(int index) {
        PetStorage pets = (PetStorage) sbPlayer.getPlayerData().getPlayerStorage().getPetStorage();
        Object petItem = pets.getPets().get(index); if(!(petItem instanceof ItemStack)) throw new NullPointerException("Invalid pet in storage.");
        ItemStack petItemStack = (ItemStack) petItem;

        return makeClickable(Material.PLAYER_HEAD, petItemStack.getItemMeta(), k -> {
            sbPlayer.getBukkitPlayer().closeInventory();
        });
    }

}
