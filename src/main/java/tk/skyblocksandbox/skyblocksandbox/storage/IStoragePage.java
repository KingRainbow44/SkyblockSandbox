package tk.skyblocksandbox.skyblocksandbox.storage;

import java.util.Map;

public interface IStoragePage {

    public Map<Integer, Object> getItemsInStorage();

    public void putItemIntoStorage(int slot, Object item);

    public void removeItemFromStorage(int slot);

    public void swapStorage(IStoragePage newStorage);

}
