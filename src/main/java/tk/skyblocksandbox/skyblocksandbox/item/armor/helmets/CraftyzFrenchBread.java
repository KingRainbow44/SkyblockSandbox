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

public final class CraftyzFrenchBread extends SandboxItem {
    public CraftyzFrenchBread() {
        super(Material.PLAYER_HEAD, "Craftteez's French Bread", SkyblockItemIds.CRAFTYZ_FRENCH_BREAD);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore();

        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isDungeonItem = true;
        itemData.canHaveStars = true;

        itemData.baseHealth = 10;
        itemData.baseDefense = 10;
        itemData.baseStrength = 10;
        itemData.baseIntelligence = 10;
        itemData.baseCriticalDamage = 10;
        itemData.baseCriticalStrikeChance = 10;
        itemData.baseAbilityDamage = 10;
        itemData.baseFerocity = 10;
        itemData.basePetLuck = 10;
        itemData.baseMagicFind = 10;
        itemData.baseSeaCreatureChance = 20;
        itemData.baseSpeed = 1;
        itemData.baseBonusAttackSpeed = 10;
        itemData.baseTrueDefense = 5;
        itemData.baseFarmingFortune = 25;

        itemData.baseDamage = 100;

        itemData.rarity = VERY_SPEICAL;
        itemData.itemType = ACCESSORY;

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
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/99acb6e288c7453c7ef43d86bae683e16c50cd023c3a9df8faf60351dfca00c6").getBytes());
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
        World world = player.getWorld();

        world.spawnParticle(Particle.REDSTONE, player.getLocation().add(0, 4, 0), 1, new Particle.DustOptions(Color.fromBGR(170, 140, 80), 2));
    }
}
