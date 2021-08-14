package tk.skyblocksandbox.skyblocksandbox.deprecated;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Deprecated
public abstract class SkyblockItem {

    /*
     * Attributes
     */
    protected String itemName;
    protected Collection<String> lore;
    protected Material vanillaItem;
    protected String itemId;
    protected SkyblockItemData itemData;

    /*
     * Constants
     */
    public static final int PASSIVE = 0;
    public static final int LEFT_CLICK_TRIGGER = 1;
    public static final int RIGHT_CLICK_TRIGGER = 2;
    public static final int SNEAK_TRIGGER = 3;

    public static final int INTERACT_RIGHT_CLICK = 0;
    public static final int INTERACT_LEFT_CLICK = 1;

    public static final int SWORD = 0;
    public static final int BOW = 1;
    public static final int HELMET = 2;
    public static final int CHESTPLATE = 3;
    public static final int LEGGINGS = 4;
    public static final int BOOTS = 5;
    public static final int ACCESSORY = 6;
    public static final int REFORGE_STONE = 7;
    public static final int FISHING_ROD = 8;
    public static final int PICKAXE = 9;
    public static final int AXE = 10;
    public static final int SHOVEL = 11;
    public static final int HOE = 12;
    public static final int SHEARS = 13;
    public static final int BREWING_INGREDIENT = 14;
    public static final int ITEM = 15;
    public static final int OTHER = -1;

    public static final int NONE = -1;
    public static final int COMMON = 0;
    public static final int UNCOMMON = 1;
    public static final int RARE = 2;
    public static final int EPIC = 3;
    public static final int LEGENDARY = 4;
    public static final int MYTHIC = 5;

    public static final int SPECIAL = 6;
    public static final int VERY_SPEICAL = 7;
    public static final int SUPREME = 8;

    public SkyblockItem(String itemName, Material vanillaItem, String itemId) {
        this.itemName = itemName;
        this.vanillaItem = vanillaItem;
        this.itemId = itemId;

        this.itemData = getItemData();
        this.lore = getLore();
    }

    public ItemStack createItem() {
        ItemStack finalItem = new ItemStack(vanillaItem);
        ItemMeta meta = finalItem.getItemMeta();

        assert meta != null;
        meta.setDisplayName(Utility.colorize(itemName));
        meta.setUnbreakable(true);
        meta.setLore(new ArrayList<>(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);

        // Storage
        meta.getPersistentDataContainer().set(key("id"), PersistentDataType.STRING, getItemId()); // Store Item ID.
        meta.getPersistentDataContainer().set(key("abilityTrigger"), PersistentDataType.INTEGER, getItemData().abilityTrigger); // Store Item ID.

        // Attributes
        meta.getPersistentDataContainer().set(key("dungeonStars"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("hotPotatoBooks"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("fumingPotatoBooks"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("reforgeId"), PersistentDataType.INTEGER, 0);

        meta.getPersistentDataContainer().set(key("recombobulated"), PersistentDataType.BYTE, (byte) 0);
        meta.getPersistentDataContainer().set(key("fragged"), PersistentDataType.BYTE, (byte) 0);

        if(getItemData().isSkyblockMenu) {
            meta.getPersistentDataContainer().set(key("skyblockMenu"), PersistentDataType.BYTE, (byte) 0);
        }

        // Additive Stats
        meta.getPersistentDataContainer().set(key("bonusDamage"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusStrength"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusDefense"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusHealth"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusIntelligence"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusCritChance"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusCritDamage"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusAttackSpeed"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusSpeed"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusTrueDefense"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusSeaCreatureChance"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusMagicFind"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusPetLuck"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bonusAbilityDamage"), PersistentDataType.INTEGER, 0);

        // Enchantments
        meta.getPersistentDataContainer().set(key("aiming"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("baneofarthropods"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("blessing"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("cleave"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("critical"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("depthstrider"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("enderslayer"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("expertise"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("fireprotection"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("fortune"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("giantkiller"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("impaling"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("lethality"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("luck"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("magnet"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("power"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("protection"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("rejuvenate"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("respite"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("silktouch"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("snipe"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("syphon"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("thunderbolt"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("triplestrike"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("vampirism"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("angler"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("bigbrain"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("caster"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("compact"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("cubism"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("dragonhunter"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("execute"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("featherfalling"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("firststrike"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("frail"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("growth"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("infinitequiver"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("lifesteal"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("luckofthesea"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("overload"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("projectileprotection"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("punch"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("replenish"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("scavenger"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("smelthingtouch"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("spikedhook"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("telekinesis"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("thunderlord"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("trueprotection"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("venomous"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("aquaaffinity"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("blastprotection"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("chance"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("counterstrike"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("cultivating"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("efficiency"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("experience"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("fireaspect"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("flame"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("frostwalker"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("harvesting"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("knockback"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("looting"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("lure"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("piercing"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("prosecute"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("rainbow"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("respiration"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("sharpness"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("silktouch"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("smite"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("sugarrush"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("thorns"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("titankiller"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(key("turbocrop"), PersistentDataType.STRING, "WHEAT_0");
        meta.getPersistentDataContainer().set(key("vicious"), PersistentDataType.INTEGER, 0);

        finalItem.setItemMeta(meta);

        return finalItem;
    }

    /*
     * Gets
     */

    public String getItemId() {
        return itemId;
    }

    public void ability(int action, SkyblockPlayer player) {}

    public abstract Collection<String> getLore();
    public abstract SkyblockItemData getItemData();

    /*
     * QoL
     */

    private NamespacedKey key(String key) {
        return new NamespacedKey(SkyblockSandbox.getInstance(), key);
    }

//    /*
//     * Static Gets
//     */
//    public static SkyblockItem toSkyblockItem(ItemStack item) {
//        if(!DataContainerAPI.validityCheck(item, SkyblockSandbox.getInstance(), "id", PersistentDataType.STRING)) return null;
//        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
//
//        Object rawId = DataContainerAPI.get(dataContainer, SkyblockSandbox.getInstance(), "id", PersistentDataType.STRING);
//        if(!(rawId instanceof String)) return null;
//
//        if(SkyblockSandbox.getManagement().getItemManager().isSkyblockItem(item) == null) {
//            // TODO: Vanilla to SkyblockItem
//            return null;
//        } else {
//            return (SkyblockItem) SkyblockSandbox.getManagement().getItemManager().isSkyblockItem(item);
//        }
//    }

}