package tk.skyblocksandbox.skyblocksandbox.entity;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.four.MasterThorn;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.four.Thorn;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.one.Bonzo;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.one.MasterBonzo;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.seven.MasterNecron;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.seven.Necron;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.six.MasterSadan;
import tk.skyblocksandbox.skyblocksandbox.entity.catacombs.six.Sadan;
import tk.skyblocksandbox.skyblocksandbox.entity.fishing.Yeti;
import tk.skyblocksandbox.skyblocksandbox.entity.sandbox.UltimateMasterBonzo;
import tk.skyblocksandbox.skyblocksandbox.entity.sandbox.UltimateMasterNecron;
import tk.skyblocksandbox.skyblocksandbox.entity.sandbox.UltimateMasterSadan;
import tk.skyblocksandbox.skyblocksandbox.entity.sandbox.UltimateMasterThorn;
import tk.skyblocksandbox.skyblocksandbox.entity.vanilla.Zombie;
import tk.skyblocksandbox.skyblocksandbox.npc.traits.SkyblockEntityTrait;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class SkyblockEntityManager {

    private static final Map<Integer, SandboxEntity> entities = new HashMap<>();

    public int registerEntity(SandboxEntity entity) {
        entities.put(
                entity.getBukkitEntity().getEntityId(),
                entity
        );

        return entity.getBukkitEntity().getEntityId();
    }

    public void unregisterEntity(int entityId) {
        entities.remove(entityId);
    }

    public SandboxEntity getEntity(Entity entity) {

        if(entity.hasMetadata("NPC")) {
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);
            SkyblockEntityTrait entityTrait = npc.getOrAddTrait(SkyblockEntityTrait.class);

            return entities.get(entityTrait.getEntityId());
        }

        return entities.get(entity.getEntityId());
    }

    public SandboxEntity getEntity(int id) {
        return entities.get(id);
    }

    public static SandboxEntity parseEntity(String entityId) throws InvalidParameterException {
        switch(entityId) {
            default:
                throw new InvalidParameterException("The entity locale: " + entityId + " is not a valid entity id.");
            case "BONZO":
                return new Bonzo();
            case "MASTER_BONZO":
                return new MasterBonzo();
            case "ULTIMATE_MASTER_BONZO":
                return new UltimateMasterBonzo();
            case "THORN":
                return new Thorn();
            case "MASTER_THORN":
                return new MasterThorn();
            case "ULTIMATE_MASTER_THORN":
                return new UltimateMasterThorn();
            case "SADAN":
                return new Sadan();
            case "MASTER_SADAN":
                return new MasterSadan();
            case "ULTIMATE_MASTER_SADAN":
                return new UltimateMasterSadan();
            case "NECRON":
                return new Necron();
            case "MASTER_NECRON":
                return new MasterNecron();
            case "ULTIMATE_MASTER_NECRON":
                return new UltimateMasterNecron();

            case "YETI":
                return new Yeti();
            case "ZOMBIE":
                return new Zombie();
        }
    }

}
