package tk.skyblocksandbox.skyblocksandbox.deprecated;

import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.deprecated.SkyblockItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;

@Deprecated
public final class VanillaItemData extends SkyblockItemData {

    public String id = "";
    public Integer abilityTrigger;

    public Integer dungeonStars = 0;
    public Integer hotPotatoBooks = 0;
    public Integer fumingPotatoBooks = 0;
    public Integer reforgeId = -1;

    public Byte recombobulated = (byte) 0;
    public Byte fragged = (byte) 0;

    public Byte skyblockMenu = (byte) 0;

    public Integer bonusDamage = 0;
    public Integer bonusStrength = 0;
    public Integer bonusDefense = 0;
    public Integer bonusHealth = 0;
    public Integer bonusIntelligence = 0;
    public Integer bonusCritChance = 0;
    public Integer bonusCritDamage = 0;
    public Integer bonusAttackSpeed = 0;
    public Integer bonusSpeed = 0;
    public Integer bonusTrueDefense = 0;
    public Integer bonusSeaCreatureChance = 0;
    public Integer bonusMagicFind = 0;
    public Integer bonusPetLuck = 0;
    public Integer bonusAbilityDamage = 0;

    public boolean importData(ItemStack item) {
        Object rawItem = SkyblockSandbox.getManagement().getItemManager().isSkyblockItem(item);
        if (!(rawItem instanceof SkyblockItem)) return false;

        SkyblockItem sbItem = (SkyblockItem) rawItem;
        SkyblockItemData sbItemData = sbItem.getItemData();

        // Import Data - START \\
        itemType = sbItemData.itemType;

        hasAbility = sbItemData.hasAbility;
        abilityTrigger = sbItemData.abilityTrigger;
        abilityName = sbItemData.abilityName;
        abilityDescription = sbItemData.abilityDescription;

        abilityCooldown = sbItemData.abilityCooldown;
        abilityCost = sbItemData.abilityCost;

        isDungeonItem = sbItemData.isDungeonItem;
        canHaveStars = sbItemData.canHaveStars;

        canReforge = sbItemData.canReforge;

        isSkyblockMenu = sbItemData.isSkyblockMenu;

        rarity = sbItemData.rarity;

        baseDamage = sbItemData.baseDamage;
        baseStrength = sbItemData.baseStrength;
        baseDefense = sbItemData.baseDefense;
        baseTrueDefense = sbItemData.baseTrueDefense;
        baseHealth = sbItemData.baseHealth;
        baseIntelligence = sbItemData.baseIntelligence;
        baseCriticalStrikeChance = sbItemData.baseCriticalStrikeChance;
        baseCriticalDamage = sbItemData.baseCriticalDamage;
        baseBonusAttackSpeed = sbItemData.baseBonusAttackSpeed;
        baseSpeed = sbItemData.baseSpeed;
        baseSeaCreatureChance = sbItemData.baseSeaCreatureChance;
        baseMagicFind = sbItemData.baseMagicFind;
        basePetLuck = sbItemData.basePetLuck;
        baseAbilityDamage = sbItemData.baseAbilityDamage;
        baseFerocity = sbItemData.baseFerocity;

        fraggedDamage = sbItemData.fraggedDamage;
        fraggedStrength = sbItemData.fraggedStrength;
        fraggedDefense = sbItemData.fraggedDefense;
        fraggedTrueDefense = sbItemData.fraggedTrueDefense;
        fraggedHealth = sbItemData.fraggedHealth;
        fraggedIntelligence = sbItemData.fraggedIntelligence;
        fraggedCriticalStrikeChance = sbItemData.fraggedCriticalStrikeChance;
        fraggedCriticalDamage = sbItemData.fraggedCriticalDamage;
        fraggedBonusAttackSpeed = sbItemData.fraggedBonusAttackSpeed;
        fraggedSpeed = sbItemData.fraggedSpeed;
        fraggedSeaCreatureChance = sbItemData.fraggedSeaCreatureChance;
        fraggedMagicFind = sbItemData.fraggedMagicFind;
        fraggedPetLuck = sbItemData.fraggedPetLuck;
        fraggedAbilityDamage = sbItemData.fraggedAbilityDamage;
        fraggedFerocity = sbItemData.fraggedFerocity;
        // Import Data - END \\



        return true;
    }

}
