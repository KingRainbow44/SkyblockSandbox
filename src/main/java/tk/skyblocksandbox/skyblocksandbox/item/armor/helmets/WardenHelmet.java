package tk.skyblocksandbox.skyblocksandbox.item.armor.helmets;

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
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public final class WardenHelmet extends SandboxItem {

    public WardenHelmet() {
        super(Material.PLAYER_HEAD, "Warden Helmet", SkyblockItemIds.WARDEN_HELMET);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.rarity = LEGENDARY;
        itemData.itemType = HELMET;

        itemData.isDungeonItem = false;
        itemData.canDungeonize = true;

        itemData.baseHealth = 300;
        itemData.baseDefense = 100;

        itemData.hasAbility = true;
        itemData.abilityName = "Brute Force";
        itemData.abilityDescription = "&7Halves your speed but grants\n" +
                "&c+20% &7base weapon damage for\n" +
                "&7every &a25 &7speed";

        return itemData;
    }

    @Override
    public Collection<String> getLore() {
        return new Lore().genericLore(this);
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
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/e5eb0bd85aaddf0d29ed082eac03fcade43d0ee803b0e8162add28a6379fb54e").getBytes());
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

    @Override
    public void onWear(SkyblockPlayer sbPlayer) {
        sbPlayer.getPlayerData().speed /= 2;
        int damageIncrease, speed = sbPlayer.getPlayerData().speed;
        int remainingSpeed = speed % 25;
        if(remainingSpeed == 0) {
            damageIncrease = speed / 25;
        } else {
            damageIncrease = (int) Math.floor(speed / 25f);
            if(Utility.generateRandomNumber(remainingSpeed, 100) == 100) damageIncrease++;
        }

        if(sbPlayer.getPlayerData().debugStateMessages) sbPlayer.sendMessages("final damage increase: " + damageIncrease);
        for(int i = 0; i <= damageIncrease; i++) {
            sbPlayer.getPlayerData().baseDamageIncrease += 0.2;
        }
    }

    @Override
    public void onRemove(SkyblockPlayer player) {
        player.getPlayerData().speed *= 2;
        player.getPlayerData().baseDamageIncrease = 0.0;
    }
}
