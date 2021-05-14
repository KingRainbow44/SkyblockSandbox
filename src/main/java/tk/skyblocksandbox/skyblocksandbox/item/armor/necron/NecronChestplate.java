package tk.skyblocksandbox.skyblocksandbox.item.armor.necron;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class NecronChestplate extends SandboxItem {

    public NecronChestplate() {
        super(Material.LEATHER_CHESTPLATE, "Necron's Chestplate", SkyblockItemIds.NECRON_CHESTPLATE);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.itemType = CHESTPLATE;
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

        itemData.baseHealth = 260;
        itemData.baseDefense = 140;
        itemData.baseIntelligence = 10;
        itemData.baseCriticalDamage = 30;
        itemData.baseStrength = 40;

        return itemData;
    }

    @Override
    public Collection<String> getLore() {
        Lore lore = new Lore(14,
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

            meta.setColor(Color.fromRGB(231, 65, 60));
        }
        item.setItemMeta(meta);
        // Item Meta - END \\

        addNbt(item);

        return item;
    }
}
