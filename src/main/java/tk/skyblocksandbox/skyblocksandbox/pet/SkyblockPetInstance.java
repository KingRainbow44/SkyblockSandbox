package tk.skyblocksandbox.skyblocksandbox.pet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.UUID;

public final class SkyblockPetInstance {

    private final ItemStack bukkitPet;
    private final NBTItem nbtPet;

    private final NBTCompound petData;

    public SkyblockPetInstance(ItemStack petAsItem) {
        bukkitPet = petAsItem;
        nbtPet = new NBTItem(petAsItem, true);

        petData = nbtPet.getCompound("itemData").getCompound("petData");
    }

    /*
     * Get Raw Data
     */

    public ItemStack getBukkitPet() {
        return bukkitPet;
    }

    public NBTItem getPetItemNbt() {
        return nbtPet;
    }

    public NBTCompound getPetData() {
        return petData;
    }

    /*
     * Get Parsed Data
     */

    public String getPetName() {
        return petData.getString("petName");
    }

    public int getPetLevel() {
        return petData.getInteger("petLevel");
    }

    public long getPetExperience() {
        return petData.getLong("petExperience");
    }

    /*
     * Set Parsed Data
     */

    public void addPetLevel() {
        petData.setInteger("petLevel",
                petData.getInteger("petLevel") + 1
        );
    }

    public void addPetExperience(long experience) {
        petData.setLong("petExperience",
                petData.getLong("petExperience") + experience
        );
    }

    public void setPetLevel(int level) {
        petData.setInteger("petLevel", level);
    }

    public void setPetExperience(long experience) {
        petData.setLong("petExperience", experience);
    }

    public void setSkin(String username) throws IOException {
        SkullMeta meta = (SkullMeta) bukkitPet.getItemMeta();

        // Get Skin Texture
        URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
        InputStreamReader reader_0 = new InputStreamReader(url_0.openStream()); String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

        URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
        InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
        JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
        String texture = textureProperty.get("value").getAsString();

        // Set Skin Texture
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        String textureData = new String(Base64.decodeBase64(texture));
        JsonElement skinUrl = new JsonParser().parse(textureData).getAsJsonObject().get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url");

        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", skinUrl.getAsString()).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;

        try {
            profileField = meta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        bukkitPet.setItemMeta(meta);
    }

    /*
     * Static Get Methods
     */

    public static boolean isPet(ItemStack pet) {
        if(pet == null || pet.getType() == Material.AIR) {
            return false;
        }

        NBTItem nbtPet = new NBTItem(pet, true);
        if(!nbtPet.hasKey("itemData")) {
            return false;
        }
        NBTCompound itemData = nbtPet.getCompound("itemData");

        return itemData.getBoolean("isPet");
    }


}
