package tk.skyblocksandbox.skyblocksandbox.listener;

import com.kingrainbow44.customplayer.player.ICustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayerPermissions;

public final class WorldListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ICustomPlayer customPlayer = SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);
        if(!(customPlayer instanceof SkyblockPlayer)) return;
        SkyblockPlayer sbPlayer = (SkyblockPlayer) customPlayer;

        SkyblockPlayerPermissions permissions = sbPlayer.getPlayerData().getPermissionsData();
        if(!permissions.buildingEnabled) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ICustomPlayer customPlayer = SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);
        if(!(customPlayer instanceof SkyblockPlayer)) return;
        SkyblockPlayer sbPlayer = (SkyblockPlayer) customPlayer;

        SkyblockPlayerPermissions permissions = sbPlayer.getPlayerData().getPermissionsData();
        if(!permissions.buildingEnabled) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ICustomPlayer customPlayer = SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);
        if(!(customPlayer instanceof SkyblockPlayer)) return;
        SkyblockPlayer sbPlayer = (SkyblockPlayer) customPlayer;

        SkyblockPlayerPermissions permissions = sbPlayer.getPlayerData().getPermissionsData();
        if(!permissions.buildingEnabled) {
            event.setCancelled(true);
        }
    }

}
