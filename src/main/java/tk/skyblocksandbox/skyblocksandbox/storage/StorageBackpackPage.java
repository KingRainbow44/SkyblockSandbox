package tk.skyblocksandbox.skyblocksandbox.storage;

import java.util.HashMap;
import java.util.Map;

public class StorageBackpackPage implements IStoragePage {

    private Map<Integer, Object> storage = new HashMap<>();

    @Override
    public final Map<Integer, Object> getItemsInStorage() {
        return storage;
    }

    @Override
    public void putItemIntoStorage(int slot, Object item) {
        storage.replace(slot, item);
    }

    @Override
    public void removeItemFromStorage(int slot) {
        storage.remove(slot);
    }

    @Override
    public final void swapStorage(IStoragePage newStorage) {
        storage = newStorage.getItemsInStorage();
    }

}
