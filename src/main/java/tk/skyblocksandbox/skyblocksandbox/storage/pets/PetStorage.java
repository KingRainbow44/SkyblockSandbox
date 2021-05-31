package tk.skyblocksandbox.skyblocksandbox.storage.pets;

import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.storage.IStoragePage;

import java.util.*;

public final class PetStorage implements IStoragePage {

    public PetStorage() {

    }

    private Map<Integer, Object> storage = new HashMap<>();
    private final List<ItemStack> pets = new ArrayList<>();

    /*
     * Interface Methods
     */

    @Override
    public final Map<Integer, Object> getItemsInStorage() {
        return getPets();
    }

    @Override
    public void putItemIntoStorage(int slot, Object item) {
        if(!(item instanceof ItemStack)) throw new IllegalArgumentException("Cannot supply a non-ItemStack to the Pets Storage.");
        addPet((ItemStack) item);
    }

    @Override
    public void removeItemFromStorage(int slot) {
        storage.remove(slot);
    }

    @Override
    public final void swapStorage(IStoragePage newStorage) {
        storage = newStorage.getItemsInStorage();
    }

    /*
     * Class Methods
     */

    /**
     * @return Pets. In order.
     */
    public Map<Integer, Object> getPets() {
        // TODO: Organize Pets
        storage.clear();

        for(int i = 0; i <= pets.size(); i++) {
            storage.put(i, pets.get(i));
        }

        return storage;
    }

    /**
     * @param item The pet to add to the storage.
     */
    public void addPet(ItemStack item) {
        pets.add(item);
    }

}
