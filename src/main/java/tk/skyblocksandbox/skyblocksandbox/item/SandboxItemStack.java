package tk.skyblocksandbox.skyblocksandbox.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

public final class SandboxItemStack {

    private final ItemStack item;
    private final NBTItem nbtItem;

    private final SandboxItem sandboxItem;

    public SandboxItemStack(ItemStack item) {
        this.item = item;
        this.nbtItem = new NBTItem(item);

        sandboxItem = toSandboxItem(item);
    }

    public ItemStack getBukkitItemStack() {
        return item;
    }

    public NBTItem getNbtItem() {
        return nbtItem;
    }

    public String getInternalId() {
        return nbtItem.getString("itemId");
    }

    /*
     * Skyblock Stats Functions
     */

    public Integer getDamage() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseDamage;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedDamage;
        }

        finalStat += nbtItem.getInteger("bonusDamage");

        return finalStat;
    }

    public Integer getStrength() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseStrength;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedStrength;
        }

        finalStat += nbtItem.getInteger("bonusStrength");

        return finalStat;
    }

    public Integer getDefense() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseDefense;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedDefense;
        }

        finalStat += nbtItem.getInteger("bonusDefense");

        return finalStat;
    }

    public Integer getTrueDefense() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseTrueDefense;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedTrueDefense;
        }

        finalStat += nbtItem.getInteger("bonusTrueDefense");

        return finalStat;
    }

    public Integer getHealth() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseHealth;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedHealth;
        }

        finalStat += nbtItem.getInteger("bonusHealth");

        return finalStat;
    }

    public Integer getIntelligence() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseIntelligence;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedIntelligence;
        }

        finalStat += nbtItem.getInteger("bonusIntelligence");

        return finalStat;
    }

    public Integer getCritDamage() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseCriticalDamage;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedCriticalDamage;
        }

        finalStat += nbtItem.getInteger("bonusCritDamage");

        return finalStat;
    }

    public Integer getCritChance() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseCriticalStrikeChance;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedCriticalStrikeChance;
        }

        finalStat += nbtItem.getInteger("bonusCritChance");

        return finalStat;
    }

    public Integer getAttackSpeed() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseBonusAttackSpeed;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedBonusAttackSpeed;
        }

        finalStat += nbtItem.getInteger("bonusAttackSpeed");

        return finalStat;
    }

    public Integer getSpeed() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseSpeed;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedSpeed;
        }

        finalStat += nbtItem.getInteger("bonusSpeed");

        return finalStat;
    }

    public Integer getSeaCreatureChance() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseSeaCreatureChance;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedSeaCreatureChance;
        }

        finalStat += nbtItem.getInteger("bonusSeaCreatureChance");

        return finalStat;
    }

    public Integer getMagicFind() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseMagicFind;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedMagicFind;
        }

        finalStat += nbtItem.getInteger("bonusMagicFind");

        return finalStat;
    }

    public Integer getPetLuck() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().basePetLuck;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedPetLuck;
        }

        finalStat += nbtItem.getInteger("bonusPetLuck");

        return finalStat;
    }

    public Integer getAbilityDamage() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseAbilityDamage;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedAbilityDamage;
        }

        finalStat += nbtItem.getInteger("bonusAbilityDamage");

        return finalStat;
    }

    public Integer getFarmingFortune() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseFarmingFortune;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedFarmingFortune;
        }

        finalStat += nbtItem.getInteger("bonusFarmingFortune");

        return finalStat;
    }

    public Integer getFerocity() {
        if(!isSandboxItem(item)) return 0;

        int finalStat = sandboxItem.getItemData().baseFerocity;

        if(isFragged()) {
            finalStat += sandboxItem.getItemData().fraggedFerocity;
        }

        finalStat += nbtItem.getInteger("bonusFerocity");

        return finalStat;
    }

    /*
     * Basic Gets
     */

    public boolean isFragged() {
        return nbtItem.getBoolean("fragged");
    }

    /*
     * Static Gets
     */

    public static boolean isSandboxItem(ItemStack item) {
        if(item == null) return false;
        if(item.getType() == Material.AIR) return false;

        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.hasKey("itemId");
    }

    public static SandboxItem toSandboxItem(ItemStack item) {
        if(!isSandboxItem(item)) return new BukkitSandboxItem(new ItemStack(Material.STONE)).toSandboxItem();
        NBTItem nbtItem = new NBTItem(item);

        if(nbtItem.getBoolean("isVanilla")) return new BukkitSandboxItem(item).toSandboxItem();

        SkyblockItemFactory itemManager = SkyblockSandbox.getManagement().getItemManager();
        return itemManager.getRegisteredItems().getOrDefault(nbtItem.getString("itemId"), null);
    }
}
