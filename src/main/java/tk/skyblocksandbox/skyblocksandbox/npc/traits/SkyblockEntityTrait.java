package tk.skyblocksandbox.skyblocksandbox.npc.traits;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class SkyblockEntityTrait extends Trait {

    public SkyblockEntityTrait() {
        super("SkyblockEntity");
    }

    public void setEntityData(SkyblockEntityData entityData) {
        this.entityData = entityData.toString();
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Persist("entityData") String entityData = "";
    @Persist("entityId") int entityId = -1;
    public void load(DataKey key) {
        entityData = key.getString("entityData");
        entityId = key.getInt("entityId");
    }

    public void save(DataKey key) {
        key.setString("entityData", entityData);
        key.setInt("entityId", entityId);
    }

    /*
     * Trait Methods
     */

    public SkyblockEntityData getEntityData() {
        return SkyblockEntityData.parse(entityData);
    }

    public int getEntityId() {
        return entityId;
    }

}
