package tk.skyblocksandbox.skyblocksandbox.item.armor.chestplates;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
import java.util.Collection;

public final class PurplesPupleCloak extends SandboxItem {
    public PurplesPupleCloak() {
        super(Material.LEATHER_CHESTPLATE, "Purple's Purple Cloak", SkyblockItemIds.PURPLES_PURPLE_CLOAK);
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
        itemData.canHaveStars = false;

        itemData.baseIntelligence = 1;

        itemData.rarity = PURPLE;
        itemData.itemType = CHESTPLATE;

        return itemData;
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

            meta.setColor(Color.fromRGB(86, 80, 199));
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

        world.spawnParticle(Particle.REDSTONE, player.getLocation().add(0, 4, 0), 1, new Particle.DustOptions(Color.fromBGR(150, 80, 200), 2));
    }
}
