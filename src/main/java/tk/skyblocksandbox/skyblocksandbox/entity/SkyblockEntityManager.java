package tk.skyblocksandbox.skyblocksandbox.entity;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.one.Bonzo;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.one.MasterBonzo;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.seven.Necron;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.six.Sadan;
import tk.skyblocksandbox.skyblocksandbox.entity.fishing.Yeti;
import tk.skyblocksandbox.skyblocksandbox.entity.vanilla.Zombie;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class SkyblockEntityManager {

    private final Map<Integer, SandboxEntity> entities = new HashMap<>();
    private int nextId = -1;

    public int registerEntity(SandboxEntity entity) {
        nextId++;
        entities.put(nextId, entity);

        return nextId;
    }

    public void unregisterEntity(int entityId) {
        entities.remove(entityId);
    }

    public SandboxEntity getEntity(Entity entity) {
        if(!entity.hasMetadata("skyblockEntityId")) throw new NullPointerException("Does not contain Skyblock Entity Id.");

        List<MetadataValue> data = entity.getMetadata("skyblockEntityId");

        final SandboxEntity[] sbEntity = {null};
        data.iterator().forEachRemaining(k -> {
            if(entities.getOrDefault(k.asInt(), null) != null) {
                sbEntity[0] = entities.getOrDefault(k.asInt(), null);
            }
        });

        return sbEntity[0];
    }

    public SandboxEntity getEntity(int id) {
        return entities.getOrDefault(id, null);
    }

    public static SandboxEntity parseEntity(String entityId) throws InvalidParameterException {
        switch(entityId) {
            default:
                throw new InvalidParameterException("The entity id: " + entityId + " is not a valid entity id.");
            case "NECRON":
                return new Necron();
            case "SADAN":
                return new Sadan();
            case "BONZO":
                return new Bonzo();
            case "MASTER_BONZO":
                return new MasterBonzo();
            case "YETI":
                return new Yeti();
            case "ZOMBIE":
                return new Zombie();
        }
    }

}
