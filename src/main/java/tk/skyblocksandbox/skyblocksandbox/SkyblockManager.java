package tk.skyblocksandbox.skyblocksandbox;

import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityManager;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemManager;

public final class SkyblockManager {

    private final SkyblockItemManager itemManager;
    private final SkyblockEntityManager entityManager;

    public SkyblockManager() {
        itemManager = new SkyblockItemManager();
        entityManager = new SkyblockEntityManager();
    }

    public SkyblockItemManager getItemManager() {
        return itemManager;
    }

    public SkyblockEntityManager getEntityManager() {
        return entityManager;
    }

}
