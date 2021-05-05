package tk.skyblocksandbox.skyblocksandbox.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemManager;

import java.util.Base64;

public final class SkyblockPlayerData {

    private final SkyblockPlayer player;

    /*
     * Live Stats (can change when ever)
     */
    public int coins;
    public int bits;
    public int location;
    public int currentHealth = 100;
    public int absorptionHealth = 0;
    public int currentMana = 100;

    /*
     * Solid-State Stats (only change on X event)
     */
    public int health = 100;
    public int defense = 0;
    public int strength = 0;
    public int speed = 100;
    public int critChance = 30;
    public int critDamage = 100;
    public int bonusAttackSpeed = 0;
    public int intelligence = 100;
    public int seaCreatureChance = 20;
    public int magicFind = 0;
    public int ferocity = 0;
    public int bonusAbilityDamage = 0;
    public int vanillaMaxHealth = 20;

    public String playingSong = "none";

    public boolean limitedMovement = false;
    public boolean infiniteMana = false;

    public boolean canTakeKnockback = true;

    public boolean canTakeAbilityDamage = false;
    public boolean canUseWitherShield = true;

    public boolean debugStateDamage = false;
    public boolean debugStateMessages = false;

    private final SkyblockPlayerPermissions playerPermissions;
    private final SkyblockPlayerStorage playerStorage;

    public SkyblockPlayerData(SkyblockPlayer player) {
        this.player = player;
        playerPermissions = new SkyblockPlayerPermissions(player);
        playerStorage = new SkyblockPlayerStorage();
    }

    /*
     * Data Import/Export
     */

    public String exportData() {
        JsonObject playerData = new JsonObject();

        /*
         * Stats
         */
        playerData.addProperty("health", health);
        playerData.addProperty("defense", defense);
        playerData.addProperty("strength", strength);
        playerData.addProperty("speed", speed);
        playerData.addProperty("critChance", critChance);
        playerData.addProperty("critDamage", critDamage);
        playerData.addProperty("bonusAttackSpeed", bonusAttackSpeed);
        playerData.addProperty("intelligence", intelligence);
        playerData.addProperty("seaCreatureChance", seaCreatureChance);
        playerData.addProperty("magicFind", magicFind);
        playerData.addProperty("ferocity", ferocity);
        playerData.addProperty("bonusAbilityDamage", bonusAbilityDamage);

        /*
         * Elements
         */
        playerData.addProperty("coins", coins);
        playerData.addProperty("bits", bits);
        playerData.addProperty("canTakeAbilityDamage", canTakeAbilityDamage);

        playerData.addProperty("permissions", playerPermissions.exportData());
        playerData.addProperty("storageData", playerStorage.exportData());

        /*
         * Other
         */
        playerData.addProperty("vanillaMaxHealth", vanillaMaxHealth);

        return Base64.getUrlEncoder().encodeToString(playerData.toString().getBytes());
    }

    public void importData(Object previousData) {
        String rawData;
        if(previousData.toString() == null || previousData.toString().equals("{}")) {
            return;
        } else {
            rawData = previousData.toString();
        }

        byte[] decodedBytes = Base64.getUrlDecoder().decode(rawData);
        String data = new String(decodedBytes);

        JsonElement decodedData = new JsonParser().parse(data);
        JsonObject arrayData = decodedData.getAsJsonObject();

        /*
         * Stats
         */
        health = arrayData.get("health").getAsInt();
        defense = arrayData.get("defense").getAsInt();
        strength = arrayData.get("strength").getAsInt();
        speed = arrayData.get("speed").getAsInt();
        critChance = arrayData.get("critChance").getAsInt();
        critDamage = arrayData.get("critDamage").getAsInt();
        bonusAttackSpeed = arrayData.get("bonusAttackSpeed").getAsInt();
        intelligence = arrayData.get("intelligence").getAsInt();
        seaCreatureChance = arrayData.get("seaCreatureChance").getAsInt();
        magicFind = arrayData.get("magicFind").getAsInt();
        ferocity = arrayData.get("ferocity").getAsInt();
        bonusAbilityDamage = arrayData.get("bonusAbilityDamage").getAsInt();

        /*
         * Elements
         */
        coins = arrayData.get("coins").getAsInt();
        bits = arrayData.get("bits").getAsInt();
        canTakeAbilityDamage = arrayData.get("canTakeAbilityDamage").getAsBoolean();

        /*
         * Classes
         */
        playerPermissions.importData(arrayData.get("permissions").toString());
        playerStorage.importData(arrayData.get("storageData").toString());

        /*
         * Other
         */
        vanillaMaxHealth = arrayData.get("vanillaMaxHealth").getAsInt();
        player.updateMaxHP();
    }

    /*
     * Player Data Methods
     */
    public void damage(int damage) {
        currentHealth -= damage;
    }

    /*
     * Get Stats Functions
     */
    public int getFinalMaxHealth() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int health = this.health;

        if(useHelmet) health += ((SkyblockItem) sbHelmet).getItemData().finalHealth();
        if(useChestplate) health += ((SkyblockItem) sbChestplate).getItemData().finalHealth();
        if(useLeggings) health += ((SkyblockItem) sbLeggings).getItemData().finalHealth();
        if(useBoots) health += ((SkyblockItem) sbBoots).getItemData().finalHealth();
        if(useMainhand) health += ((SkyblockItem) sbHolding).getItemData().finalHealth();
        if(useOffhand) health += ((SkyblockItem) sbOffhand).getItemData().finalHealth();

        return health;
    }

    public int getFinalDefense() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int defense = this.defense;

        if(useHelmet) defense += ((SkyblockItem) sbHelmet).getItemData().finalDefense();
        if(useChestplate) defense += ((SkyblockItem) sbChestplate).getItemData().finalDefense();
        if(useLeggings) defense += ((SkyblockItem) sbLeggings).getItemData().finalDefense();
        if(useBoots) defense += ((SkyblockItem) sbBoots).getItemData().finalDefense();
        if(useMainhand) defense += ((SkyblockItem) sbHolding).getItemData().finalDefense();
        if(useOffhand) defense += ((SkyblockItem) sbOffhand).getItemData().finalDefense();

        return defense;
    }

    public int getFinalStrength() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int strength = this.strength;

        if(useHelmet) strength += ((SkyblockItem) sbHelmet).getItemData().finalStrength();
        if(useChestplate) strength += ((SkyblockItem) sbChestplate).getItemData().finalStrength();
        if(useLeggings) strength += ((SkyblockItem) sbLeggings).getItemData().finalStrength();
        if(useBoots) strength += ((SkyblockItem) sbBoots).getItemData().finalStrength();
        if(useMainhand) strength += ((SkyblockItem) sbHolding).getItemData().finalStrength();
        if(useOffhand) strength += ((SkyblockItem) sbOffhand).getItemData().finalStrength();

        return strength;
    }

    public int getFinalDamage() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int damage = 0;

        if(useHelmet) damage += ((SkyblockItem) sbHelmet).getItemData().finalDamage();
        if(useChestplate) damage += ((SkyblockItem) sbChestplate).getItemData().finalDamage();
        if(useLeggings) damage += ((SkyblockItem) sbLeggings).getItemData().finalDamage();
        if(useBoots) damage += ((SkyblockItem) sbBoots).getItemData().finalDamage();
        if(useMainhand) damage += ((SkyblockItem) sbHolding).getItemData().finalDamage();
        if(useOffhand) damage += ((SkyblockItem) sbOffhand).getItemData().finalDamage();

        return damage;
    }

    public int getFinalTrueDefense() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int trueDefense = 0;

        if(useHelmet) trueDefense += ((SkyblockItem) sbHelmet).getItemData().finalTrueDefense();
        if(useChestplate) trueDefense += ((SkyblockItem) sbChestplate).getItemData().finalTrueDefense();
        if(useLeggings) trueDefense += ((SkyblockItem) sbLeggings).getItemData().finalTrueDefense();
        if(useBoots) trueDefense += ((SkyblockItem) sbBoots).getItemData().finalTrueDefense();
        if(useMainhand) trueDefense += ((SkyblockItem) sbHolding).getItemData().finalTrueDefense();
        if(useOffhand) trueDefense += ((SkyblockItem) sbOffhand).getItemData().finalTrueDefense();

        return trueDefense;
    }

    public int getFinalIntelligence() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int intelligence = this.intelligence;

        if(useHelmet) intelligence += ((SkyblockItem) sbHelmet).getItemData().finalIntelligence();
        if(useChestplate) intelligence += ((SkyblockItem) sbChestplate).getItemData().finalIntelligence();
        if(useLeggings) intelligence += ((SkyblockItem) sbLeggings).getItemData().finalIntelligence();
        if(useBoots) intelligence += ((SkyblockItem) sbBoots).getItemData().finalIntelligence();
        if(useMainhand) intelligence += ((SkyblockItem) sbHolding).getItemData().finalIntelligence();
        if(useOffhand) intelligence += ((SkyblockItem) sbOffhand).getItemData().finalIntelligence();

        return intelligence;
    }

    public int getFinalCritChance() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int critChance = this.critChance;

        if(useHelmet) critChance += ((SkyblockItem) sbHelmet).getItemData().finalCritChance();
        if(useChestplate) critChance += ((SkyblockItem) sbChestplate).getItemData().finalCritChance();
        if(useLeggings) critChance += ((SkyblockItem) sbLeggings).getItemData().finalCritChance();
        if(useBoots) critChance += ((SkyblockItem) sbBoots).getItemData().finalCritChance();
        if(useMainhand) critChance += ((SkyblockItem) sbHolding).getItemData().finalCritChance();
        if(useOffhand) critChance += ((SkyblockItem) sbOffhand).getItemData().finalCritChance();

        return critChance;
    }

    public int getFinalCritDamage() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int critDamage = this.critDamage;

        if(useHelmet) critDamage += ((SkyblockItem) sbHelmet).getItemData().finalCritDamage();
        if(useChestplate) critDamage += ((SkyblockItem) sbChestplate).getItemData().finalCritDamage();
        if(useLeggings) critDamage += ((SkyblockItem) sbLeggings).getItemData().finalCritDamage();
        if(useBoots) critDamage += ((SkyblockItem) sbBoots).getItemData().finalCritDamage();
        if(useMainhand) critDamage += ((SkyblockItem) sbHolding).getItemData().finalCritDamage();
        if(useOffhand) critDamage += ((SkyblockItem) sbOffhand).getItemData().finalCritDamage();

        return critDamage;
    }

    public int getFinalAttackSpeed() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int attackSpeed = this.bonusAttackSpeed;

        if(useHelmet) attackSpeed += ((SkyblockItem) sbHelmet).getItemData().finalAttackSpeed();
        if(useChestplate) attackSpeed += ((SkyblockItem) sbChestplate).getItemData().finalAttackSpeed();
        if(useLeggings) attackSpeed += ((SkyblockItem) sbLeggings).getItemData().finalAttackSpeed();
        if(useBoots) attackSpeed += ((SkyblockItem) sbBoots).getItemData().finalAttackSpeed();
        if(useMainhand) attackSpeed += ((SkyblockItem) sbHolding).getItemData().finalAttackSpeed();
        if(useOffhand) attackSpeed += ((SkyblockItem) sbOffhand).getItemData().finalAttackSpeed();

        return attackSpeed;
    }

    public int getFinalSpeed() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int speed = this.speed;

        if(useHelmet) speed += ((SkyblockItem) sbHelmet).getItemData().finalSpeed();
        if(useChestplate) speed += ((SkyblockItem) sbChestplate).getItemData().finalSpeed();
        if(useLeggings) speed += ((SkyblockItem) sbLeggings).getItemData().finalSpeed();
        if(useBoots) speed += ((SkyblockItem) sbBoots).getItemData().finalSpeed();
        if(useMainhand) speed += ((SkyblockItem) sbHolding).getItemData().finalSpeed();
        if(useOffhand) speed += ((SkyblockItem) sbOffhand).getItemData().finalSpeed();

        return speed;
    }

    public int getFinalSeaCreatureChance() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int seaCreatureChance = this.seaCreatureChance;

        if(useHelmet) seaCreatureChance += ((SkyblockItem) sbHelmet).getItemData().finalSeaCreatureChance();
        if(useChestplate) seaCreatureChance += ((SkyblockItem) sbChestplate).getItemData().finalSeaCreatureChance();
        if(useLeggings) seaCreatureChance += ((SkyblockItem) sbLeggings).getItemData().finalSeaCreatureChance();
        if(useBoots) seaCreatureChance += ((SkyblockItem) sbBoots).getItemData().finalSeaCreatureChance();
        if(useMainhand) seaCreatureChance += ((SkyblockItem) sbHolding).getItemData().finalSeaCreatureChance();
        if(useOffhand) seaCreatureChance += ((SkyblockItem) sbOffhand).getItemData().finalSeaCreatureChance();

        return seaCreatureChance;
    }

    public int getFinalMagicFind() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int magicFind = this.magicFind;

        if(useHelmet) magicFind += ((SkyblockItem) sbHelmet).getItemData().finalMagicFind();
        if(useChestplate) magicFind += ((SkyblockItem) sbChestplate).getItemData().finalMagicFind();
        if(useLeggings) magicFind += ((SkyblockItem) sbLeggings).getItemData().finalMagicFind();
        if(useBoots) magicFind += ((SkyblockItem) sbBoots).getItemData().finalMagicFind();
        if(useMainhand) magicFind += ((SkyblockItem) sbHolding).getItemData().finalMagicFind();
        if(useOffhand) magicFind += ((SkyblockItem) sbOffhand).getItemData().finalMagicFind();

        return magicFind;
    }

    public int getFinalPetLuck() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int petLuck = 0;

        if(useHelmet) petLuck += ((SkyblockItem) sbHelmet).getItemData().finalPetLuck();
        if(useChestplate) petLuck += ((SkyblockItem) sbChestplate).getItemData().finalPetLuck();
        if(useLeggings) petLuck += ((SkyblockItem) sbLeggings).getItemData().finalPetLuck();
        if(useBoots) petLuck += ((SkyblockItem) sbBoots).getItemData().finalPetLuck();
        if(useMainhand) petLuck += ((SkyblockItem) sbHolding).getItemData().finalPetLuck();
        if(useOffhand) petLuck += ((SkyblockItem) sbOffhand).getItemData().finalPetLuck();

        return petLuck;
    }

    public int getFinalAbilityDamage() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int abilityDamage = this.bonusAbilityDamage;

        if(useHelmet) abilityDamage += ((SkyblockItem) sbHelmet).getItemData().finalAbilityDamage();
        if(useChestplate) abilityDamage += ((SkyblockItem) sbChestplate).getItemData().finalAbilityDamage();
        if(useLeggings) abilityDamage += ((SkyblockItem) sbLeggings).getItemData().finalAbilityDamage();
        if(useBoots) abilityDamage += ((SkyblockItem) sbBoots).getItemData().finalAbilityDamage();
        if(useMainhand) abilityDamage += ((SkyblockItem) sbHolding).getItemData().finalAbilityDamage();
        if(useOffhand) abilityDamage += ((SkyblockItem) sbOffhand).getItemData().finalAbilityDamage();

        return abilityDamage;
    }

    public int getFinalFerocity() {
        SkyblockItemManager itemManager = SkyblockSandbox.getManagement().getItemManager();
        PlayerInventory inventory = player.getBukkitPlayer().getInventory();

        ItemStack helmet = inventory.getHelmet(); boolean useHelmet = true;
        ItemStack chestplate = inventory.getChestplate(); boolean useChestplate = true;
        ItemStack leggings = inventory.getLeggings(); boolean useLeggings = true;
        ItemStack boots = inventory.getBoots(); boolean useBoots = true;
        ItemStack holding = inventory.getItemInMainHand(); boolean useMainhand = true;
        ItemStack offhand = inventory.getItemInOffHand(); boolean useOffhand = true;

        Object sbHelmet = itemManager.isSkyblockItem(helmet);
        if(!(sbHelmet instanceof SkyblockItem)) useHelmet = false;
        Object sbChestplate = itemManager.isSkyblockItem(chestplate);
        if(!(sbChestplate instanceof SkyblockItem)) useChestplate = false;
        Object sbLeggings = itemManager.isSkyblockItem(leggings);
        if(!(sbLeggings instanceof SkyblockItem)) useLeggings = false;
        Object sbBoots = itemManager.isSkyblockItem(boots);
        if(!(sbBoots instanceof SkyblockItem)) useBoots = false;
        Object sbHolding = itemManager.isSkyblockItem(holding);
        if(!(sbHolding instanceof SkyblockItem)) useMainhand = false;
        Object sbOffhand = itemManager.isSkyblockItem(offhand);
        if(!(sbOffhand instanceof SkyblockItem)) useOffhand = false;

        int ferocity = this.ferocity;

        if(useHelmet) ferocity += ((SkyblockItem) sbHelmet).getItemData().finalFerocity();
        if(useChestplate) ferocity += ((SkyblockItem) sbChestplate).getItemData().finalFerocity();
        if(useLeggings) ferocity += ((SkyblockItem) sbLeggings).getItemData().finalFerocity();
        if(useBoots) ferocity += ((SkyblockItem) sbBoots).getItemData().finalFerocity();
        if(useMainhand) ferocity += ((SkyblockItem) sbHolding).getItemData().finalFerocity();
        if(useOffhand) ferocity += ((SkyblockItem) sbOffhand).getItemData().finalFerocity();

        return ferocity;
    }

    /*
     * Other Get Functions
     */

    public SkyblockPlayerPermissions getPermissionsData() {
        return playerPermissions;
    }
}
