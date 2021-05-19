package tk.skyblocksandbox.skyblocksandbox.menu.providers;

import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tk.skyblocksandbox.skyblocksandbox.menu.SkyblockMenu;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayerData;

import java.util.ArrayList;
import java.util.List;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class MainSkyblockMenu extends SkyblockMenu {
    @Override
    public void init(Player player, InventoryContents contents) {
        ItemMeta glassMeta = toItemMeta(Material.BLACK_STAINED_GLASS_PANE); glassMeta.setDisplayName(" "); contents.fill(makeUnclickable(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), glassMeta));

        // First Row - START \\
        SkullMeta profileMeta = (SkullMeta) toItemMeta(Material.PLAYER_HEAD);
            profileMeta.setDisplayName(colorize("&aYour Skyblock Profile"));
            profileMeta.setLore(getProfileLore(player));
            profileMeta.setOwningPlayer(player);
        // First Row - END \\

        // Second Row - START \\
        ItemMeta skillsMeta = toItemMeta(Material.DIAMOND_SWORD);
            skillsMeta.setDisplayName(colorize("&aYour Skills"));
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
        ItemMeta tradesMenu = toItemMeta(Material.EMERALD);
            tradesMenu.setDisplayName(colorize("&aTrades"));
            tradesMenu.setLore(toLore(
                    colorize("&7View your available"),
                    colorize("&7in Skyblock. Collect more of an item"),
                    colorize("&7to unlock rewards on your"),
                    colorize("&7way to becoming the master of"),
                    colorize("&7Skyblock!"),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta questMenu = toItemMeta(Material.WRITABLE_BOOK);
            questMenu.setDisplayName(colorize("&aQuest Log"));
            questMenu.setLore(toLore(
                    colorize("&7View all of the items available"),
                    colorize("&7in Skyblock. Collect more of an item"),
                    colorize("&7to unlock rewards on your"),
                    colorize("&7way to becoming the master of"),
                    colorize("&7Skyblock!"),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta calendarMenu = toItemMeta(Material.CLOCK);
            calendarMenu.setDisplayName(colorize("&aCalendar & Events"));
            calendarMenu.setLore(toLore(
                    colorize("&7View all of the items available"),
                    colorize("&7in Skyblock. Collect more of an item"),
                    colorize("&7to unlock rewards on your"),
                    colorize("&7way to becoming the master of"),
                    colorize("&7Skyblock!"),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        ItemMeta storageMenu = toItemMeta(Material.CHEST);
            storageMenu.setDisplayName(colorize("&aStorage"));
            storageMenu.setLore(toLore(
                    colorize("&7View all of the items available"),
                    colorize("&7in Skyblock. Collect more of an item"),
                    colorize("&7to unlock rewards on your"),
                    colorize("&7way to becoming the master of"),
                    colorize("&7Skyblock!"),
                    colorize(" "),
                    colorize("&eClick to view!")
            ));
        // Second Row - END \\

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
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private List<String> getProfileLore(Player player) {
        List<String> lore = new ArrayList<>();
        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(player);
        SkyblockPlayerData plrData = sbPlayer.getPlayerData();

        lore.add( // Health
                colorize("&c❤ Health &f" + plrData.getFinalMaxHealth() + " HP")
        );

        lore.add( // Defense
                colorize("&a❈ Defense &f" + plrData.getFinalDefense())
        );

        lore.add( // Strength
                colorize("&c❁ Strength &f" + plrData.getFinalStrength())
        );

        lore.add( // Speed
                colorize("&f✦ Speed &f" + plrData.getFinalSpeed())
        );

        lore.add( // Crit Chance
                colorize("&c☣ Crit Chance &f" + plrData.getFinalCritChance() + "%")
        );

        lore.add( // Crit Damage
                colorize("&c☠ Crit Damage &f" + plrData.getFinalCritDamage() + "%")
        );

        lore.add( // Intelligence
                colorize("&c✎ Intelligence &f" + plrData.getFinalStrength())
        );

        lore.add( // Ferocity
                colorize("&c⫽ Ferocity &f" + plrData.getFinalFerocity())
        );

        return lore;
    }
}