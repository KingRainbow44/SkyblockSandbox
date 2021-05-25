package tk.skyblocksandbox.skyblocksandbox.menu.providers;

import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.menu.MenuFactory;
import tk.skyblocksandbox.skyblocksandbox.menu.SkyblockMenu;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayerData;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
import java.util.List;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class MainSkyblockMenu extends SkyblockMenu {
    @Override
    public void init(Player player, InventoryContents contents) {
        ItemMeta glassMeta = toItemMeta(Material.BLACK_STAINED_GLASS_PANE); glassMeta.setDisplayName(" "); contents.fill(makeUnclickable(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), glassMeta));

        SkullMeta profileMeta = (SkullMeta) toItemMeta(Material.PLAYER_HEAD);
            profileMeta.setDisplayName(colorize("&aYour Skyblock Profile"));
            profileMeta.setLore(getProfileLore(player));
            profileMeta.setOwningPlayer(player);

        ItemMeta skillsMeta = toItemMeta(Material.DIAMOND_SWORD);
            skillsMeta.setDisplayName(colorize("&aYour Skills"));
            skillsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            skillsMeta.setLore(toLore(
                    colorize("&7View your Skill progression and"),
                    colorize("&7rewards."),
                    colorize(" "),
                    colorize("&eClick to hide rankings!")
            ));
        ItemMeta collectionsMeta = toItemMeta(Material.PAINTING);
            collectionsMeta.setDisplayName(colorize("&aCollection"));
            collectionsMeta.setLore(toLore(
                    colorize("&7View all of the items available"),
                    colorize("&7in Skyblock. Collect more of an item"),
                    colorize("&7to unlock rewards on your"),
                    colorize("&7way to becoming the master of"),
                    colorize("&7Skyblock!"),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta recipeMeta = toItemMeta(Material.BOOK);
            recipeMeta.setDisplayName(colorize("&aRecipe Book"));
            recipeMeta.setLore(toLore(
                    colorize("&7Through your adventure, you will"),
                    colorize("&7unlock recipes for all kinds of"),
                    colorize("&7special items! You can view how"),
                    colorize("&7to craft these items here."),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta tradesMeta = toItemMeta(Material.EMERALD);
            tradesMeta.setDisplayName(colorize("&aTrades"));
            tradesMeta.setLore(toLore(
                    colorize("&7View your available trades."),
                    colorize("&7These trades are always"),
                    colorize("&7available and accessible through"),
                    colorize("&7the Skyblock Menu."),
                    colorize(" "),
                    colorize("&7Trades Unlocked: &a100&6%"),
                    colorize("&2-------------------- &e26&6/&e26"),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta questMeta = toItemMeta(Material.WRITABLE_BOOK);
            questMeta.setDisplayName(colorize("&aQuest Log"));
            questMeta.setLore(toLore(
                    colorize("&7View your active quests."),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta calendarMeta = toItemMeta(Material.CLOCK);
            calendarMeta.setDisplayName(colorize("&aCalendar & Events"));
            calendarMeta.setLore(toLore(
                    colorize("&7View the Skyblock Calendar."),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta storageMeta = toItemMeta(Material.CHEST);
            storageMeta.setDisplayName(colorize("&aStorage"));
            storageMeta.setLore(toLore(
                    colorize("&7Store global items that you"),
                    colorize("&7want to access any time"),
                    colorize("&7from anywhere here."),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));

        ItemMeta potionsMeta = toItemMeta(Material.POTION);
            potionsMeta.setDisplayName(colorize("&aActive Effects"));
            potionsMeta.setLore(toLore(
                    colorize("&7View and manage all your"),
                    colorize("&7active potion effects."),
                    colorize(" "),
                    colorize("&7Drink Potions or splash them"),
                    colorize("&7on the ground to buff yourself!"),
                    colorize(" "),
                    colorize("&7Currently Active: &e0"),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta petsMeta = toItemMeta(Material.BONE);
            petsMeta.setDisplayName(colorize("&aPets"));
            petsMeta.setLore(toLore(
                    colorize("&7View and manage all your"),
                    colorize("&7Pets."),
                    colorize(" "),
                    colorize("&7Level up your pets faster by"),
                    colorize("&7gaining xp in their favorite"),
                    colorize("&7Skill!"),
                    colorize(" "),
                    colorize("&7Selected Pet: &cNone"),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta craftingMeta = toItemMeta(Material.CRAFTING_TABLE);
            craftingMeta.setDisplayName(colorize("&aCrafting Table"));
            craftingMeta.setLore(toLore(
                    colorize("&7Opens the crafting grid."),
                    colorize(" "),
                    colorize("&eClick to open!")
            ));
        LeatherArmorMeta wardrobeMeta = (LeatherArmorMeta) toItemMeta(Material.LEATHER_CHESTPLATE);
            wardrobeMeta.setColor(Color.fromRGB(127, 63, 178));
            wardrobeMeta.setDisplayName(colorize("&aWardrobe"));
            wardrobeMeta.setLore(toLore(
                    colorize("&7Store armor sets and quickly"),
                    colorize("&7swap between them!"),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        SkullMeta pBankMeta = (SkullMeta) toItemMeta(Material.PLAYER_HEAD);
            pBankMeta.setDisplayName(colorize("&aPersonal Bank"));
            pBankMeta.setLore(toLore(
                    colorize("&7Contact your Banker from"),
                    colorize("&7anywhere."),
                    colorize("&7Cooldown: &eUnlimited"),
                    colorize(" "),
                    colorize("&7Banker Status:"),
                    colorize("&aAvailable"),
                    colorize(" "),
                    colorize("&eClick to open!")
            ));

        ItemMeta closeMeta = toItemMeta(Material.BARRIER);
            closeMeta.setDisplayName(colorize("&cClose"));
        ItemMeta settingsMeta = toItemMeta(Material.REDSTONE_TORCH);
            settingsMeta.setDisplayName(colorize("&aSettings"));
            settingsMeta.setLore(toLore(
                    colorize("&7View and edit your Skyblock"),
                    colorize("&7settings."),
                    colorize(" "),
                    colorize("&eClick to open!")
            ));

        // Add Menu Items \\
        contents.set(1, 4, makeClickable(Material.PLAYER_HEAD, profileMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));

        contents.set(2, 1, makeClickable(Material.DIAMOND_SWORD, skillsMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(2, 2, makeClickable(Material.PAINTING, collectionsMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(2, 3, makeClickable(Material.BOOK, recipeMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(2, 4, makeClickable(Material.EMERALD, tradesMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(2, 5, makeClickable(Material.WRITABLE_BOOK, questMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(2, 6, makeClickable(Material.CLOCK, calendarMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(2, 7, makeClickable(Material.CHEST, storageMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));

        contents.set(3, 2, makeClickable(Material.POTION, potionsMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(3, 3, makeClickable(Material.BONE, petsMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(3, 4, makeClickable(Material.CRAFTING_TABLE, craftingMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(3, 5, makeClickable(Material.LEATHER_CHESTPLATE, wardrobeMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));
        contents.set(3, 6, makeClickable(Material.PLAYER_HEAD, pBankMeta, k -> {
            player.sendMessage(ChatColor.RED + "This feature hasn't been implemented yet!");
        }));

        contents.set(5, 4, makeClickable(Material.BARRIER, closeMeta, k -> {
            player.closeInventory();
        }));
        contents.set(5, 5, makeClickable(Material.REDSTONE_TORCH, settingsMeta, k -> {
            player.closeInventory();
            SkyblockSandbox.getMenuFactory().serveMenu(SkyblockPlayer.getSkyblockPlayer(player), MenuFactory.MenuList.SKYBLOCK_MENU_SETTINGS);
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private List<String> getProfileLore(Player player) {
        List<String> lore = new ArrayList<>();
        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(player);
        SkyblockPlayerData plrData = sbPlayer.getPlayerData();

        lore.add( // Health
                colorize("&c ❤ Health &f" + Utility.commafy(plrData.getFinalMaxHealth()) + " HP")
        );

        lore.add( // Defense
                colorize("&a ❈ Defense &f" + Utility.commafy(plrData.getFinalDefense()))
        );

        lore.add( // Strength
                colorize("&c ❁ Strength &f" + Utility.commafy(plrData.getFinalStrength()))
        );

        lore.add( // Speed
                colorize("&f ✦ Speed &f" + Utility.commafy(plrData.getFinalSpeed()))
        );

        lore.add( // Crit Chance
                colorize("&9 ☣ Crit Chance &f" + Utility.commafy(plrData.getFinalCritChance()) + "%")
        );

        lore.add( // Crit Damage
                colorize("&9 ☠ Crit Damage &f" + Utility.commafy(plrData.getFinalCritDamage()) + "%")
        );

        lore.add( // Intelligence
                colorize("&b ✎ Intelligence &f" + Utility.commafy(plrData.getFinalIntelligence()))
        );

        lore.add( // Ferocity
                colorize("&c ⫽ Ferocity &f" + Utility.commafy(plrData.getFinalFerocity()))
        );

        return lore;
    }
}