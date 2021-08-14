package tk.skyblocksandbox.skyblocksandbox;

import fr.minuskube.inv.InventoryManager;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityManager;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemFactory;
import tk.skyblocksandbox.skyblocksandbox.util.sign.SignMenuFactory;

public final class SkyblockManager {

    private final SkyblockItemFactory itemManager;
    private final SkyblockEntityManager entityManager;

    private InventoryManager inventoryManager;
    private SignMenuFactory signManager;

    public SkyblockManager() {
        itemManager = new SkyblockItemFactory();
        entityManager = new SkyblockEntityManager();
    }

    public SkyblockItemFactory getItemManager() {
        return itemManager;
    }

    public SkyblockEntityManager getEntityManager() {
        return entityManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public SignMenuFactory getSignManager() {
        return signManager;
    }

    /*
     * Call from SkyblockSandbox
     */
    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public void setSignManager(SignMenuFactory signManager) {
        this.signManager = signManager;
    }

}
