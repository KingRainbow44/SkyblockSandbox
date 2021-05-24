package tk.skyblocksandbox.skyblocksandbox.item;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SandboxItem {

    /*
     * The successor to SkyblockItem.
     * Uses NBT instead of PersistentDataContainers to handle data.
     * Can be modified with little to no hassle.
     * Much friendlier with objects.
     * Can be wrapped unlike SkyblockItems'
     */

    protected final Material baseItem;
    protected final String itemName;
    protected final String internalId;

    public SandboxItem(Material baseItem, String itemName, String internalId) {
        this.baseItem = baseItem;
        this.itemName = itemName;
        this.internalId = internalId;
    }

    public ItemStack create() {
        ItemStack item = new ItemStack(baseItem);

        // Item Meta - START \\
        ItemMeta meta = item.getItemMeta();
        if(meta != null) {
            meta.setUnbreakable(true);
            meta.setDisplayName(Utility.rarityToColor(getItemData().rarity) + itemName);
            meta.setLore(new ArrayList<>(getLore()));

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_DYE);
        }
        item.setItemMeta(meta);
        // Item Meta - END \\

        addNbt(item);

        return item;
    }

    /*
     * Get Methods
     */

    public String getItemId() {
        return internalId;
    }

    protected void addNbt(ItemStack item) {
        NBTItem itemNbt = new NBTItem(item, true);
        NBTCompound nbt = itemNbt.addCompound("itemData");

        // NBT Tags - START \\
        nbt.setBoolean("isVanilla", getItemData().isVanilla);
        nbt.setBoolean("isSkyblockMenu", getItemData().isSkyblockMenu);

        nbt.setString("itemId", internalId);
        nbt.setInteger("firstAbilityTrigger", getItemData().abilityTrigger);
        nbt.setInteger("secondAbilityTrigger", getItemData().abilityTrigger2);
        nbt.setInteger("thirdAbilityTrigger", getItemData().abilityTrigger3);

        nbt.setInteger("hotPotatoBooks", 0);
        nbt.setInteger("fumingPotatoBooks", 0);
        nbt.setInteger("reforgeId", 0);
        nbt.setBoolean("recombobulated", false);
        nbt.setBoolean("fragged", false);

        nbt.setBoolean("dungeonized", getItemData().isDungeonItem);
        nbt.setInteger("dungeonStars", 0);

        nbt.setInteger("bonusDamage", 0);
        nbt.setInteger("bonusStrength", 0);
        nbt.setInteger("bonusDefense", 0);
        nbt.setInteger("bonusHealth", 0);
        nbt.setInteger("bonusIntelligence", 0);
        nbt.setInteger("bonusCritDamage", 0);
        nbt.setInteger("bonusCritChance", 0);
        nbt.setInteger("bonusAttackSpeed", 0);
        nbt.setInteger("bonusSpeed", 0);
        nbt.setInteger("bonusTrueDefense", 0);
        nbt.setInteger("bonusSeaCreatureChance", 0);
        nbt.setInteger("bonusMagicFind", 0);
        nbt.setInteger("bonusPetLuck", 0);
        nbt.setInteger("bonusAbilityDamage", 0);
        nbt.setInteger("bonusFerocity", 0);
        nbt.setInteger("bonusFarmingFortune", 0);

        //<editor-fold desc="Enchantments">
        nbt.setInteger("aiming", 0);
        nbt.setInteger("baneofarthropods", 0);
        nbt.setInteger("blessing", 0);
        nbt.setInteger("cleave", 0);
        nbt.setInteger("critical", 0);
        nbt.setInteger("depthstrider", 0);
        nbt.setInteger("enderslayer", 0);
        nbt.setInteger("expertise", 0);
        nbt.setInteger("fireprotection", 0);
        nbt.setInteger("fortune", 0);
        nbt.setInteger("giantkiller", 0);
        nbt.setInteger("impaling", 0);
        nbt.setInteger("lethality", 0);
        nbt.setInteger("luck", 0);
        nbt.setInteger("magnet", 0);
        nbt.setInteger("power", 0);
        nbt.setInteger("protection", 0);
        nbt.setInteger("rejuvenate", 0);
        nbt.setInteger("respite", 0);
        nbt.setInteger("silktouch", 0);
        nbt.setInteger("snipe", 0);
        nbt.setInteger("syphon", 0);
        nbt.setInteger("thunderbolt", 0);
        nbt.setInteger("triplestrike", 0);
        nbt.setInteger("vampirism", 0);
        nbt.setInteger("angler", 0);
        nbt.setInteger("bigbrain", 0);
        nbt.setInteger("caster", 0);
        nbt.setInteger("compact", 0);
        nbt.setInteger("cubism", 0);
        nbt.setInteger("dragonhunter", 0);
        nbt.setInteger("execute", 0);
        nbt.setInteger("featherfalling", 0);
        nbt.setInteger("firststrike", 0);
        nbt.setInteger("frail", 0);
        nbt.setInteger("growth", 0);
        nbt.setInteger("infinitequiver", 0);
        nbt.setInteger("lifesteal", 0);
        nbt.setInteger("luckofthesea", 0);
        nbt.setInteger("overload", 0);
        nbt.setInteger("projectileprotection", 0);
        nbt.setInteger("punch", 0);
        nbt.setInteger("replenish", 0);
        nbt.setInteger("scavenger", 0);
        nbt.setInteger("smelthingtouch", 0);
        nbt.setInteger("spikedhook", 0);
        nbt.setInteger("telekinesis", 0);
        nbt.setInteger("thunderlord", 0);
        nbt.setInteger("trueprotection", 0);
        nbt.setInteger("venomous", 0);
        nbt.setInteger("aquaaffinity", 0);
        nbt.setInteger("blastprotection", 0);
        nbt.setInteger("chance", 0);
        nbt.setInteger("counterstrike", 0);
        nbt.setInteger("cultivating", 0);
        nbt.setInteger("efficiency", 0);
        nbt.setInteger("experience", 0);
        nbt.setInteger("fireaspect", 0);
        nbt.setInteger("flame", 0);
        nbt.setInteger("frostwalker", 0);
        nbt.setInteger("harvesting", 0);
        nbt.setInteger("knockback", 0);
        nbt.setInteger("looting", 0);
        nbt.setInteger("lure", 0);
        nbt.setInteger("piercing", 0);
        nbt.setInteger("prosecute", 0);
        nbt.setInteger("rainbow", 0);
        nbt.setInteger("respiration", 0);
        nbt.setInteger("sharpness", 0);
        nbt.setInteger("silktouch", 0);
        nbt.setInteger("smite", 0);
        nbt.setInteger("sugarrush", 0);
        nbt.setInteger("thorns", 0);
        nbt.setInteger("titankiller", 0);
        nbt.setInteger("vicious", 0);
        //</editor-fold>

        nbt.setInteger("turbo-wheat", 0);
        nbt.setInteger("turbo-carrot", 0);
        nbt.setInteger("turbo-potato", 0);
        nbt.setInteger("turbo-netherwart", 0);
        nbt.setInteger("turbo-cocoa_beans", 0);
        nbt.setInteger("turbo-sugar_cane", 0);
        nbt.setInteger("turbo-cactus", 0);
        nbt.setInteger("turbo-mushrooms", 0);
        nbt.setInteger("turbo-pumpkins", 0);
        nbt.setInteger("turbo-melons", 0);
        // NBT Tags - END \\
    }

    /*
     * Abstract Methods
     */

    public void ability(int action, SkyblockPlayer player) {} // A 'null' method because not all items have an ability.

    public void armorAbility(SkyblockPlayer player) {} // A 'null' method because not all armor pieces have an ability.

    public abstract SkyblockItemData getItemData();

    public abstract Collection<String> getLore();

    /*
     * Constant Expressions
     */

    public static final int PASSIVE = 0;
    public static final int LEFT_CLICK_TRIGGER = 1;
    public static final int RIGHT_CLICK_TRIGGER = 2;
    public static final int SNEAK_TRIGGER = 3;
    public static final int FULL_SET_BONUS = 4;

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

    public static final int PURPLE = -2;
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

}
