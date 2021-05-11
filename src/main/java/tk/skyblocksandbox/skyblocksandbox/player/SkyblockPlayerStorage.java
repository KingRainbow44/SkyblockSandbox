package tk.skyblocksandbox.skyblocksandbox.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItemStack;
import tk.skyblocksandbox.skyblocksandbox.storage.StoragePage;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public final class SkyblockPlayerStorage {

    private final Map<Integer, StoragePage> enderchest = new HashMap<>();
    private final Map<Integer, StoragePage> storage = new HashMap<>();

    public String exportData() {
        JsonObject playerData = new JsonObject();



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


    }

    public Map<Integer, StoragePage> getEnderChest() {
        return enderchest;
    }

    public Map<Integer, StoragePage> getStorage() {
        return storage;
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
}
