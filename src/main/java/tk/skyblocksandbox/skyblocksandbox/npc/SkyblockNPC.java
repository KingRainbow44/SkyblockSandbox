package tk.skyblocksandbox.skyblocksandbox.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;
import tk.skyblocksandbox.skyblocksandbox.npc.traits.SkyblockEntityTrait;

public final class SkyblockNPC {

    private final NPC npc;

    public SkyblockNPC(String npcName) {
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        npc.addTrait(SkyblockEntityTrait.class);
    }

    public void setEntityData(SkyblockEntityData entityData) {
        if(!npc.hasTrait(SkyblockEntityTrait.class)) return;

        SkyblockEntityTrait trait = npc.getOrAddTrait(SkyblockEntityTrait.class);
        trait.setEntityData(entityData);
    }

    public NPC getNpc() {
        return npc;
    }

    /*
     * Static Methods
     */

    public static boolean isNpc(Entity entity) {
        return entity.hasMetadata("NPC");
    }

}
