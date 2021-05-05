package tk.skyblocksandbox.skyblocksandbox.entity;

import com.kingrainbow44.persistentdatacontainers.DataContainerAPI;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.seven.Necron;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public final class SkyblockEntityManager {

    private final Map<Integer, SkyblockEntity> entities = new HashMap<>();
    private int nextId = -1;

    public int registerEntity(SkyblockEntity entity) {
        nextId++;
        entities.put(nextId, entity);

        return nextId;
    }

    public void unregisterEntity(int entityId) {
        entities.remove(entityId);
    }

    public SkyblockEntity getEntity(int entityId) {
        return entities.getOrDefault(entityId, null);
    }

    public SkyblockEntity getEntity(Entity entity) {
        if(DataContainerAPI.has(entity, SkyblockSandbox.getInstance(), "entityUUID", PersistentDataType.STRING)) return null;
        Object rawUUID = DataContainerAPI.get(entity.getPersistentDataContainer(), SkyblockSandbox.getInstance(), "entiityUUID", PersistentDataType.INTEGER);
        if(!(rawUUID instanceof Integer)) return null;

        return getEntity((int) rawUUID);
    }

    public static SkyblockEntity parseEntity(String entityId) throws InvalidParameterException {
        switch(entityId) {
            default:
                throw new InvalidParameterException("The entity id: " + entityId + " is not a valid entity id.");
            case "NECRON":
                return new Necron();
        }
    }

}
