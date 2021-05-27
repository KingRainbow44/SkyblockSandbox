package tk.skyblocksandbox.skyblocksandbox.runnable;

import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.npc.traits.SkyblockEntityTrait;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;

public final class EntityRunnable implements Runnable {

    private long ticks = -1;

    @Override
    public void run() {
        ticks++;

        if(ticks % 10 == 0) {
            CitizensAPI.getNPCRegistry().iterator().forEachRemaining(npc -> {
                if(npc.hasTrait(SkyblockEntityTrait.class)) {
                    SkyblockEntityTrait entityTrait = npc.getOrAddTrait(SkyblockEntityTrait.class);

                    for(Entity entity : npc.getEntity().getNearbyEntities(4, 4, 4)) {
                        if(entity instanceof Player && !entity.hasMetadata("NPC")) {
                            Calculator.damage(SkyblockPlayer.getSkyblockPlayer((Player) entity), entityTrait.getEntityData().damage, true);
                        }
                    }
                }
            });
        }
    }
}
