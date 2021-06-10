package tk.skyblocksandbox.skyblocksandbox.player;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItemStack;
import tk.skyblocksandbox.skyblocksandbox.pet.SkyblockPet;
import tk.skyblocksandbox.skyblocksandbox.storage.IStoragePage;
import tk.skyblocksandbox.skyblocksandbox.storage.StoragePage;
import tk.skyblocksandbox.skyblocksandbox.storage.inventory.InventoryStorage;
import tk.skyblocksandbox.skyblocksandbox.storage.pets.PetStorage;
import tk.skyblocksandbox.skyblocksandbox.util.ItemSerializer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class SkyblockPlayerStorage {

    private final Map<Integer, StoragePage> enderchest = new HashMap<>();
    private final Map<Integer, StoragePage> storage = new HashMap<>();

    private final InventoryStorage inventoryStorage;
    private final PetStorage pets = new PetStorage();

    private final SkyblockPlayer sbPlayer;

    public SkyblockPlayerStorage(SkyblockPlayer sbPlayer) {
        this.sbPlayer = sbPlayer;
        inventoryStorage = new InventoryStorage(sbPlayer.getBukkitPlayer().getInventory());
    }

    public String exportData() {
        JsonObject storageData = new JsonObject();

        pets.getItemsInStorage().forEach((slot, item) -> storageData.addProperty(
                "pet_" + slot,
                ItemSerializer.convertItemStackToString((ItemStack) item)
        ));

        inventoryStorage.putInventory(sbPlayer.getBukkitPlayer().getInventory());
        inventoryStorage.getItemsInStorage().forEach((slot, item) -> storageData.addProperty(
                "inventory_" + slot,
                (String) item
        ));

        return Base64.getUrlEncoder().encodeToString(storageData.toString().getBytes());
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
        JsonObject jsonObject = decodedData.getAsJsonObject();

        Map<String, Object> attributes = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        for(Map.Entry<String, JsonElement> entry : entrySet){
            attributes.put(entry.getKey(), jsonObject.get(entry.getKey()));
        }

        attributes.forEach((key, val) -> {
            int slot = Utility.getLastIntFromString(key);

            if(key != null && key.contains("pet_")) {
                if(val instanceof String) {
                    pets.putItemIntoStorage(slot, ItemSerializer.convertStringToItemStack((String) val));
                } else if (val instanceof JsonPrimitive) {
                    inventoryStorage.putItemIntoStorage(slot, ItemSerializer.convertStringToItemStack(
                            ((JsonPrimitive) val).getAsString()
                    ));
                }
            }

            if(key != null || key.contains("inventory_")) {
                if(val instanceof String) {
                    inventoryStorage.putItemIntoStorage(slot, ItemSerializer.convertStringToItemStack((String) val));
                } else if (val instanceof JsonPrimitive) {
                    inventoryStorage.putItemIntoStorage(slot, ItemSerializer.convertStringToItemStack(
                            ((JsonPrimitive) val).getAsString()
                    ));
                }
            } inventoryStorage.restoreInventory(sbPlayer.getBukkitPlayer().getInventory());
        });
    }

    public Map<Integer, StoragePage> getEnderChest() {
        return enderchest;
    }

    public Map<Integer, StoragePage> getStorage() {
        return storage;
    }

    public IStoragePage getPetStorage() {
        return pets;
    }

    public IStoragePage getInventoryStorage() {
        return inventoryStorage;
    }

    public void addToEnderChest(int slot, int page, SandboxItemStack item) {
        StoragePage storagePage = enderchest.getOrDefault(page, null);
        if(storagePage == null) return;

        storagePage.putItemIntoStorage(slot, item);
    }

    public void addToStorage(int slot, StoragePage backpack) {
        StoragePage storagePage = storage.getOrDefault(slot, null);
        if(storagePage == null) return;

        storagePage.putItemIntoStorage(slot, backpack);
    }

    public void addPetToMenu(SkyblockPet pet) {

    }
}
