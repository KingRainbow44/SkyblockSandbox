package tk.skyblocksandbox.skyblocksandbox.storage;

import java.util.Map;

public interface IStoragePage {

    Map<Integer, Object> getItemsInStorage();

    void putItemIntoStorage(int slot, Object item);

    void removeItemFromStorage(int slot);

    void swapStorage(IStoragePage newStorage);

}
