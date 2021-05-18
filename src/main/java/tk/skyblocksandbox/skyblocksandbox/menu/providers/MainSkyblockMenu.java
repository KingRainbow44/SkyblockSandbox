package tk.skyblocksandbox.skyblocksandbox.menu.providers;

import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tk.skyblocksandbox.skyblocksandbox.menu.SkyblockMenu;

import java.util.ArrayList;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class MainSkyblockMenu extends SkyblockMenu {
    @Override
    public void init(Player player, InventoryContents contents) {
        ItemMeta glassMeta = toItemMeta(Material.BLACK_STAINED_GLASS_PANE); glassMeta.setDisplayName(" "); contents.fill(makeUnclickable(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), glassMeta));

        // First Row - START \\
        SkullMeta profileMeta = (SkullMeta) toItemMeta(Material.PLAYER_HEAD);
            profileMeta.setDisplayName(colorize("&aYour Skyblock Profile"));
            profileMeta.setOwningPlayer(player);
        // First Row - END \\

        // Second Row - START \\
        ItemMeta skillsMeta = toItemMeta(Material.DIAMOND_SWORD);
            skillsMeta.setDisplayName(colorize("Your Skills"));
            skillsMeta.setLore(toLore(
                    colorize("&7View your Skill progression and"),
                    colorize("&7rewards."),
                    colorize(" "),
                    colorize("&eClick to hide rankings!")
            ));
        // Second Row - END \\
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}