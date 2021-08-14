package tk.skyblocksandbox.skyblocksandbox.menu.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.menu.MenuFactory;
import tk.skyblocksandbox.skyblocksandbox.menu.SkyblockMenu;
import tk.skyblocksandbox.skyblocksandbox.pet.SkyblockPetInstance;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;
import tk.skyblocksandbox.skyblocksandbox.util.sign.BasicSign;

import java.io.IOException;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class PetModifierMenu extends SkyblockMenu {
    private SkyblockPlayer sbPlayer;
    private boolean callForUpdate = false;

    private ItemStack pet;

    @Override
    public void init(Player player, InventoryContents contents) {
        sbPlayer = SkyblockPlayer.getSkyblockPlayer(player); pet = player.getInventory().getItemInMainHand();
        ItemMeta glassMeta = toItemMeta(Material.BLACK_STAINED_GLASS_PANE); glassMeta.setDisplayName(" "); contents.fill(makeUnclickable(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), glassMeta));

        contents.set(1, 4, createItem(0));

        contents.set(2, 5, createItem(3));
        contents.set(2, 6, createItem(2));

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
            case 2:
                ItemMeta petLevelMeta = toItemMeta(Material.EXPERIENCE_BOTTLE);
                petLevelMeta.setDisplayName(colorize("&aChange Pet Level")); petLevelMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                petLevelMeta.setLore(toLore(
                        colorize("&7Change your pet's level"),
                        colorize("&7and its skills too!"),
                        " ",
                        colorize("&eClick to modify!")
                ));

                return makeClickable(Material.EXPERIENCE_BOTTLE, petLevelMeta, k -> {
                    ItemStack pet = this.pet;
                    new BasicSign(sbPlayer, null, "^^^", "Enter the level", "the pet should be.") {
                        @Override
                        public void run(String... lines) {
                            try {
                                int level = Integer.parseInt(lines[0]);
                                if(level > 100) level = 100;

                                SkyblockPetInstance petInstance = new SkyblockPetInstance(pet);
                                petInstance.setPetLevel(level);

                                sbPlayer.sendMessages("&aSet your pet's level to &e" + level + "&a!");
                            } catch (NumberFormatException e) {
                                sbPlayer.sendMessages("&cThis isn't a valid number!");
                            }

                            sbPlayer.openMenu(MenuFactory.MenuList.SKYBLOCK_MENU_PET_CREATOR);
                        }
                    }.build();
                });
            case 3:
                ItemMeta skinChangeMeta = toItemMeta(Material.ZOMBIE_HEAD);
                skinChangeMeta.setDisplayName(colorize("&aChange Pet Skin")); skinChangeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                skinChangeMeta.setLore(toLore(
                        colorize("&7Change the pet's head"),
                        colorize("&7texture to whatever you fancy!"),
                        " ",
                        colorize("&eClick to modify!")
                ));

                return makeClickable(Material.ZOMBIE_HEAD, skinChangeMeta, k -> {
                    ItemStack pet = this.pet;
                    new BasicSign(sbPlayer, null, "^^^", "Enter the name", "of the skin to use.") {
                        @Override
                        public void run(String... lines) {
                            String username = lines[0];

                            try {
                                if(!Utility.isValidUsername(username)) {
                                    sbPlayer.sendMessages("&cUnable to retrieve the skin from the username!");
                                    return;
                                }

                                SkyblockPetInstance petInstance = new SkyblockPetInstance(pet);
                                petInstance.setSkin(username);

                                sbPlayer.sendMessages("&aSuccessfully set skin!");
                            } catch (IOException e) {
                                sbPlayer.sendMessages("&cUnable to retrieve the skin from the username!");
                            }

                            sbPlayer.openMenu(MenuFactory.MenuList.SKYBLOCK_MENU_PET_CREATOR);
                        }
                    }.build();
                });
        }
    }
}
