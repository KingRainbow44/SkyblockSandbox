package tk.skyblocksandbox.skyblocksandbox.item.armor.storm;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
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

public final class StormHelmet extends SandboxItem {

    public StormHelmet() {
        super(Material.PLAYER_HEAD, "Storm's Helmet", SkyblockItemIds.STORM_HELMET);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.itemType = HELMET;
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

        itemData.baseHealth = 180;
        itemData.baseDefense = 80;
        itemData.baseIntelligence = 400;

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
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if(meta != null) {
            meta.setDisplayName(Utility.rarityToColor(getItemData().rarity) + itemName);
            meta.setLore(new ArrayList<>(getLore()));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_DYE);

            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/7c33b1e96b8ba078a91f0002d4919c615543cd093c2d709d9aee9f6268134c2c").getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField = null;

            try {
                profileField = meta.getClass().getDeclaredField("profile");
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }

            profileField.setAccessible(true);

            try {
                profileField.set(meta, profile);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        item.setItemMeta(meta);
        // Item Meta - END \\

        addNbt(item);

        return item;
    }
}
