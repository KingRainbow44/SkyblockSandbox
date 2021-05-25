package tk.skyblocksandbox.skyblocksandbox.item.armor.helmets;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.entity.Player;
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

public final class FlamingoMask extends SandboxItem {
    public FlamingoMask() {
        super(Material.PLAYER_HEAD, "Flamingo Mask", SkyblockItemIds.FLAMINGO_MASK);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore();

        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.baseHealth = 350;
        itemData.baseDefense = 250;
        itemData.baseCriticalStrikeChance = 20;

        itemData.rarity = EPIC;
        itemData.itemType = HELMET;

        itemData.hasAbility = true;
        itemData.abilityTrigger = SNEAK_TRIGGER;
        itemData.abilityName = "Lock Knees";
        itemData.abilityCost = 5;
        itemData.abilityDescription = "&7Lock your knees and take\n" +
                "&7No Knockback while shiftng!";

        return itemData;
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
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/d213afef315a81c6e579e6ee480f0ff63df90a2625868b9c80497d96e9be9493").getBytes());
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
    public void armorAbility(SkyblockPlayer sbPlayer) {
        Player player = sbPlayer.getBukkitPlayer();
        if(player.isSneaking() && sbPlayer.manaCheck(5, "Lock Knees")) {
            sbPlayer.getPlayerData().canTakeKnockback = false;
        } else {
            sbPlayer.getPlayerData().canTakeKnockback = true;
        }
    }
}
