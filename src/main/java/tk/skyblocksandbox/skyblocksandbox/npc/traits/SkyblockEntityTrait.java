package tk.skyblocksandbox.skyblocksandbox.npc.traits;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;

public final class SkyblockEntityTrait extends Trait {

    public SkyblockEntityTrait() {
        super("SkyblockEntity");
    }

    public void setEntityData(SkyblockEntityData entityData) {
        this.entityData = entityData.toString();
    }

    @Persist("entityData") String entityData = "";
    public void load(DataKey key) {
        entityData = key.getString("entityData");
    }

    public void save(DataKey key) {
        key.setRaw("entityData", entityData);
    }

    @EventHandler
    public void onDamage(NPCLeftClickEvent event) {
        Player player = event.getClicker();
        NPC npc = event.getNPC();

        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(player);
        Calculator.damage(SkyblockEntity.getSkyblockEntityFromNPC(npc), sbPlayer, true);
    }

    /*
     * Trait Methods
     */

    public SkyblockEntityData getEntityData() {
        return SkyblockEntityData.parse(entityData);
    }

}
