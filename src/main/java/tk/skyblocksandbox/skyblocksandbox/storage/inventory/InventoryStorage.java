package tk.skyblocksandbox.skyblocksandbox.storage.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import tk.skyblocksandbox.skyblocksandbox.storage.IStoragePage;
import tk.skyblocksandbox.skyblocksandbox.util.ItemSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class InventoryStorage implements IStoragePage {

    private final PlayerInventory inventory;

    public InventoryStorage(PlayerInventory inventory) {
        this.inventory = inventory;
        putInventory(inventory);
    }

    private String playerInventory = "";
    private String armorInventory = "";

    private Map<Integer, Object> storage = new HashMap<>();

    @Override
    public final Map<Integer, Object> getItemsInStorage() {
        String[] array = ItemSerializer.playerInventoryToBase64(inventory);

        storage.put(0, array[0]);
        storage.put(1, array[1]);

        return storage;
    }

    @Override
    public void putItemIntoStorage(int slot, Object item) {
        // Do nothing. Cannot write to storage.
    }

    @Override
    public void removeItemFromStorage(int slot) {
        // Do nothing. Cannot write to storage.
    }

    @Override
    public final void swapStorage(IStoragePage newStorage) {
        // Do nothing. Cannot write to storage.
    }

    /*
     * Methods
     */

    public void putInventory(PlayerInventory inventory) {
//        String[] array = ItemSerializer.playerInventoryToBase64(inventory);
//
//        playerInventory = array[0];
//        armorInventory = array[1];
    }

    public void restoreInventory(PlayerInventory inventory) {
        try {
            Inventory base64Inventory = ItemSerializer.fromBase64(playerInventory);
            Inventory base64Armor = ItemSerializer.fromBase64(armorInventory);

            inventory.setContents(base64Inventory.getContents());
            inventory.setArmorContents(base64Armor.getContents());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
