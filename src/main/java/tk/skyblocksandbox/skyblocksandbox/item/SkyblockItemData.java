package tk.skyblocksandbox.skyblocksandbox.item;

public class SkyblockItemData {

    /*
     * Data Flags
     */

    public boolean isDungeonItem = false;
    public boolean canHaveStars = true;

    public boolean canReforge = true;

    public boolean isSkyblockMenu = false;

    public boolean isMaterial = false;

    public int rarity = SandboxItem.COMMON;

    public int baseDamage = 0;
    public int baseStrength = 0;
    public int baseDefense = 0;
    public int baseTrueDefense = 0;
    public int baseHealth = 0;
    public int baseIntelligence = 0;
    public int baseCriticalStrikeChance = 0;
    public int baseCriticalDamage = 0;
    public int baseBonusAttackSpeed = 0;
    public int baseSpeed = 0;
    public int baseSeaCreatureChance = 0;
    public int baseMagicFind = 0;
    public int basePetLuck = 0;
    public int baseAbilityDamage = 0;
    public int baseFerocity = 0;
    public int baseFarmingFortune = 0;

    public int fraggedDamage = 0;
    public int fraggedStrength = 0;
    public int fraggedDefense = 0;
    public int fraggedTrueDefense = 0;
    public int fraggedHealth = 0;
    public int fraggedIntelligence = 0;
    public int fraggedCriticalStrikeChance = 0;
    public int fraggedCriticalDamage = 0;
    public int fraggedBonusAttackSpeed = 0;
    public int fraggedSpeed = 0;
    public int fraggedSeaCreatureChance = 0;
    public int fraggedMagicFind = 0;
    public int fraggedPetLuck = 0;
    public int fraggedAbilityDamage = 0;
    public int fraggedFerocity = 0;
    public int fraggedFarmingFortune = 0;

    /*
     * Abilities
     */

    public boolean hasAbility = false;
    public boolean hasSecondAbility = false;
    public boolean hasThirdAbility = false;

    // Abilities - One \\
    public int abilityTrigger = 0;
    public String abilityName = "";
    public String abilityDescription = "";

    public int abilityCooldown = 0;
    public int abilityCost = 0;

    // Abilities - Two \\
    public int abilityTrigger2 = 0;
    public String abilityName2 = "";
    public String abilityDescription2 = "";

    public int abilityCooldown2 = 0;
    public int abilityCost2 = 0;

    // Abilities - Three \\
    public int abilityTrigger3 = 0;
    public String abilityName3 = "";
    public String abilityDescription3 = "";

    public int abilityCooldown3 = 0;
    public int abilityCost3 = 0;

    /*
     * Attribute Flags
     */
    public int itemType = -1;

    public int finalDamage() {
        return baseDamage + fraggedDamage;
    }

    public int finalStrength() {
        return baseStrength + fraggedStrength;
    }

    public int finalDefense() {
        return baseDefense + fraggedDefense;
    }

    public int finalTrueDefense() {
        return baseTrueDefense + fraggedTrueDefense;
    }

    public int finalHealth() {
        return baseHealth + fraggedHealth;
    }

    public int finalIntelligence() {
        return baseIntelligence + fraggedIntelligence;
    }

    public int finalCritChance() {
        return baseCriticalStrikeChance + fraggedCriticalStrikeChance;
    }

    public int finalCritDamage() {
        return baseCriticalDamage + fraggedCriticalDamage;
    }

    public int finalAttackSpeed() {
        return baseBonusAttackSpeed + fraggedBonusAttackSpeed;
    }

    public int finalSpeed() {
        return baseSpeed + fraggedSpeed;
    }

    public int finalSeaCreatureChance() {
        return baseSeaCreatureChance + fraggedSeaCreatureChance;
    }

    public int finalMagicFind() {
        return baseMagicFind + fraggedMagicFind;
    }

    public int finalPetLuck() {
        return basePetLuck + fraggedPetLuck;
    }

    public int finalAbilityDamage() {
        return baseAbilityDamage + fraggedAbilityDamage;
    }

    public int finalFerocity() {
        return baseFerocity + fraggedFerocity;
    }

}