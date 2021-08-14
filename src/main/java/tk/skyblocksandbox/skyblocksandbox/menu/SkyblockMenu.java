package tk.skyblocksandbox.skyblocksandbox.menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class SkyblockMenu implements InventoryProvider {

    public final ClickableItem makeClickable(Material material, Consumer<InventoryClickEvent> callable) {
        return ClickableItem.of(new ItemStack(material), callable);
    }

    public final ClickableItem makeClickable(Material material, int amount, Consumer<InventoryClickEvent> callable) {
        return ClickableItem.of(new ItemStack(material, amount), callable);
    }

    public final ClickableItem makeClickable(Material material, ItemMeta itemMeta, Consumer<InventoryClickEvent> callable) {
        ItemStack item = new ItemStack(material);
        item.setItemMeta(itemMeta);

        return ClickableItem.of(item, callable);
    }

    public final ClickableItem makeClickable(Material material, ItemMeta itemMeta, int amount, Consumer<InventoryClickEvent> callable) {
        ItemStack item = new ItemStack(material, amount);
        item.setItemMeta(itemMeta);

        return ClickableItem.of(item, callable);
    }

    public ItemMeta toItemMeta(Material item) {
        return new ItemStack(item).getItemMeta();
    }

    /*
     * Un-Clickable
     */
    public final ClickableItem makeUnclickable(ItemStack item) {
        return ClickableItem.of(item, e -> e.setCancelled(true));
    }

    public final ClickableItem makeUnclickable(ItemStack item, ItemMeta itemMeta) {
        item.setItemMeta(itemMeta);

        return ClickableItem.of(item, e -> e.setCancelled(true));
    }

    /*
     * Lore Utilities
     */
    public final List<String> toLore(String... loreLines) {
        return new ArrayList<>(Arrays.asList(loreLines));
    }

}
