package tk.skyblocksandbox.skyblocksandbox.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
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



    /*
     * Other Get Functions
     */

    public SkyblockPlayerPermissions getPermissionsData() {
        return playerPermissions;
    }
}
