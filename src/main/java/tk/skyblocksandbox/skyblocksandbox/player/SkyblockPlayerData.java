package tk.skyblocksandbox.skyblocksandbox.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItemStack;

import java.nio.charset.StandardCharsets;
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
    public int petLuck = 0;
    public int ferocity = 0;
    public int bonusAbilityDamage = 0;
    public int trueDefense = 0;

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
//        playerData.addProperty("storageData", playerStorage.exportData());

        /*
         * Other
         */
        playerData.addProperty("infiniteMana", infiniteMana);

        return Base64.getUrlEncoder().encodeToString(playerData.toString().getBytes(StandardCharsets.UTF_8));
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
        health = arrayData.get("health").getAsInt(); currentHealth = health;
        defense = arrayData.get("defense").getAsInt();
        strength = arrayData.get("strength").getAsInt();
        speed = arrayData.get("speed").getAsInt();
        critChance = arrayData.get("critChance").getAsInt();
        critDamage = arrayData.get("critDamage").getAsInt();
        bonusAttackSpeed = arrayData.get("bonusAttackSpeed").getAsInt();
        intelligence = arrayData.get("intelligence").getAsInt(); currentMana = intelligence;
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
        playerPermissions.importData(arrayData.get("permissions"));
//        playerStorage.importData(arrayData.get("storageData").toString());

        /*
         * Other
         */
        infiniteMana = arrayData.get("infiniteMana").getAsBoolean();
    }

    /*
     * Player Data Methods
     */
    public void damage(int damage) {
        currentHealth -= damage;
    }

    public void heal(int health) {
        int finalHealth = health + currentHealth;
        if(finalHealth > health) {
            currentHealth = getFinalMaxHealth();
        } else {
            currentHealth = finalHealth;
        }
    }

    public Integer getAbsorptionHealth() {
        return absorptionHealth;
    }

    public void addMana(int mana) {
        int finalMana = mana + currentMana;
        if(finalMana > getFinalIntelligence()) {
            currentMana = getFinalIntelligence();
        } else {
            currentMana = finalMana;
        }
    }

    /*
     * Get Stats Functions
     */

    public Integer getFinalDamage() {
        int finalStat = 0;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getDamage();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getDamage();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getDamage();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getDamage();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getDamage();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getDamage();

        return finalStat;
    }

    public Integer getFinalStrength() {
        int finalStat = strength;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getStrength();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getStrength();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getStrength();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getStrength();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getStrength();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getStrength();

        return finalStat;
    }

    public Integer getFinalDefense() {
        int finalStat = defense;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getDefense();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getDefense();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getDefense();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getDefense();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getDefense();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getDefense();

        return finalStat;
    }

    public Integer getFinalTrueDefense() {
        int finalStat = trueDefense;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getTrueDefense();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getTrueDefense();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getTrueDefense();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getTrueDefense();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getTrueDefense();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getTrueDefense();

        return finalStat;
    }

    public Integer getFinalMaxHealth() {
        int finalStat = health;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getHealth();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getHealth();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getHealth();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getHealth();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getHealth();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getHealth();

        return finalStat;
    }

    public Integer getFinalIntelligence() {
        int finalStat = intelligence;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getIntelligence();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getIntelligence();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getIntelligence();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getIntelligence();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getIntelligence();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getIntelligence();

        return finalStat;
    }

    public Integer getFinalCritChance() {
        int finalStat = critChance;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getCritChance();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getCritChance();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getCritChance();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getCritChance();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getCritChance();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getCritChance();

        return finalStat;
    }

    public Integer getFinalCritDamage() {
        int finalStat = critDamage;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getCritDamage();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getCritDamage();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getCritDamage();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getCritDamage();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getCritDamage();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getCritDamage();

        return finalStat;
    }

    public Integer getFinalAttackSpeed() {
        int finalStat = bonusAttackSpeed;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getAttackSpeed();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getAttackSpeed();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getAttackSpeed();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getAttackSpeed();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getAttackSpeed();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getAttackSpeed();

        return finalStat;
    }

    public Integer getFinalSpeed() {
        int finalStat = speed;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getSpeed();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getSpeed();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getSpeed();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getSpeed();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getSpeed();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getSpeed();

        return finalStat;
    }

    public Integer getFinalSeaCreatureChance() {
        int finalStat = seaCreatureChance;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getSeaCreatureChance();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getSeaCreatureChance();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getSeaCreatureChance();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getSeaCreatureChance();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getSeaCreatureChance();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getSeaCreatureChance();

        return finalStat;
    }

    public Integer getFinalMagicFind() {
        int finalStat = magicFind;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getMagicFind();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getMagicFind();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getMagicFind();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getMagicFind();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getMagicFind();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getMagicFind();

        return finalStat;
    }

    public Integer getFinalPetLuck() {
        int finalStat = petLuck;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getPetLuck();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getPetLuck();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getPetLuck();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getPetLuck();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getPetLuck();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getPetLuck();

        return finalStat;
    }

    public Integer getFinalAbilityDamage() {
        int finalStat = bonusAbilityDamage;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getAbilityDamage();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getAbilityDamage();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getAbilityDamage();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getAbilityDamage();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getAbilityDamage();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getAbilityDamage();

        return finalStat;
    }

    public Integer getFinalFerocity() {
        int finalStat = ferocity;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getFerocity();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getFerocity();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getFerocity();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getFerocity();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getFerocity();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getFerocity();

        return finalStat;
    }

    public Integer getFinalFarmingFortune() {
        int finalStat = ferocity;

        PlayerInventory inventory = player.getBukkitPlayer().getInventory();
        boolean useHelmet = false; boolean useChestplate = false; boolean useLeggings = false; boolean useBoots = false;
        boolean useHand = false; boolean useOffHand = false;

        if(inventory.getHelmet() != null) useHelmet = true;
        if(inventory.getChestplate() != null) useChestplate = true;
        if(inventory.getLeggings() != null) useLeggings = true;
        if(inventory.getBoots() != null) useBoots = true;

        if(inventory.getItemInMainHand().getType() != Material.AIR) useHand = true;
        if(inventory.getItemInOffHand().getType() != Material.AIR) useOffHand = true;

        if(useHelmet) finalStat += new SandboxItemStack(inventory.getHelmet()).getFarmingFortune();
        if(useChestplate) finalStat += new SandboxItemStack(inventory.getChestplate()).getFarmingFortune();
        if(useLeggings) finalStat += new SandboxItemStack(inventory.getLeggings()).getFarmingFortune();
        if(useBoots) finalStat += new SandboxItemStack(inventory.getBoots()).getFarmingFortune();
        if(useHand) finalStat += new SandboxItemStack(inventory.getItemInMainHand()).getFarmingFortune();
        if(useOffHand) finalStat += new SandboxItemStack(inventory.getItemInOffHand()).getFarmingFortune();

        return finalStat;
    }

    /*
     * Other Get Functions
     */

    public SkyblockPlayerPermissions getPermissionsData() {
        return playerPermissions;
    }
}
