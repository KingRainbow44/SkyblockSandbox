package tk.skyblocksandbox.skyblocksandbox.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class SkyblockEntityData {

    /*
     * Stats
     */
    public long health = 100;
    public float defense = 0;
    public int damage = 0;
    public int speed = 100;

    public int level = 1;

    public int vanillaHealth = 1;

    /*
     * Flags
     */
    public boolean isHostile = true;
    public boolean isNpc = false;
    public boolean isBoss = false;
    public boolean isUndead = false;
    public boolean isArthropod = false;

    public boolean canTakeKnockback = true;

    /*
     * Armor Data
     */
    public ItemStack helmet = null;
    public ItemStack chestplate = null;
    public ItemStack leggings = null;
    public ItemStack boots = null;

    /*
     * Item Data
     */
    public ItemStack mainHand = null;
    public ItemStack offHand = null;

    /*
     * Skin Data
     */
    public String skinName = ""; // The Player Name. The Player who owns the skin.
    public String skinSignature = ""; // The Skin Signature. Received from https://mineskin.org/.
    public String skinData = ""; // The Skin Data. The JSON Data from Base64 decoding profile data.

    /*
     * Variables
     */
    public String entityName = "";

    /*
     * SkyblockEntity Trait
     */

    public String toJson() {
        JsonObject entityData = new JsonObject();

        entityData.addProperty("health", health);
        entityData.addProperty("defense", defense);
        entityData.addProperty("damage", damage);
        entityData.addProperty("speed", speed);

        entityData.addProperty("level", level);
        entityData.addProperty("entityName", entityName);
        entityData.addProperty("vanillaHealth", vanillaHealth);

        entityData.addProperty("isHostile", isHostile);
        entityData.addProperty("isNpc", isNpc);
        entityData.addProperty("isBoss", isBoss);
        entityData.addProperty("isUndead", isUndead);
        entityData.addProperty("isArthropod", isArthropod);
        entityData.addProperty("canTakeKnockback", canTakeKnockback);

        entityData.addProperty("skinName", skinName);
        entityData.addProperty("skinSignature", skinSignature);
        entityData.addProperty("skinData", skinData);

        return entityData.toString();
    }

    public static SkyblockEntityData parse(String entityData) {
        SkyblockEntityData parsedData = new SkyblockEntityData();

        if(entityData == null || entityData.equals("{}")) {
            return parsedData;
        }

        JsonElement decodedData = new JsonParser().parse(entityData);
        JsonObject arrayData = decodedData.getAsJsonObject();

        parsedData.health = arrayData.get("health").getAsInt();
        parsedData.defense = arrayData.get("defense").getAsInt();
        parsedData.damage = arrayData.get("damage").getAsInt();
        parsedData.speed = arrayData.get("speed").getAsInt();

        parsedData.level = arrayData.get("level").getAsInt();
        parsedData.entityName = arrayData.get("entityName").getAsString();
        parsedData.vanillaHealth = arrayData.get("vanillaHealth").getAsInt();

        parsedData.isHostile = arrayData.get("isHostile").getAsBoolean();
        parsedData.isNpc = arrayData.get("isNpc").getAsBoolean();
        parsedData.isBoss = arrayData.get("isBoss").getAsBoolean();
        parsedData.isUndead = arrayData.get("isUndead").getAsBoolean();
        parsedData.isArthropod = arrayData.get("isArthropod").getAsBoolean();
        parsedData.canTakeKnockback = arrayData.get("canTakeKnockback").getAsBoolean();

        parsedData.skinName = arrayData.get("skinName").getAsString();
        parsedData.skinSignature = arrayData.get("skinSignature").getAsString();
        parsedData.skinData = arrayData.get("skinData").getAsString();

        return parsedData;
    }

}