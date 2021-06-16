package tk.skyblocksandbox.skyblocksandbox.listener;

import com.kingrainbow44.customplayer.player.CustomPlayerManager;
import com.kingrainbow44.customplayer.player.ICustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import tk.skyblocksandbox.dungeonsandbox.player.DungeonPlayer;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Music;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

public final class PlayerListener implements Listener {



    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CustomPlayerManager playerManager = SkyblockSandbox.getApi().getPlayerManager();

        if(SkyblockSandbox.getConfiguration().dungeonCatacombsEnabled) {
            playerManager.addPlayer(player, new DungeonPlayer(player));
        } else {
            playerManager.addPlayer(player, new SkyblockPlayer(player));
        }

        event.setJoinMessage(Utility.colorize("&a[+] " + player.getDisplayName()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        CustomPlayerManager playerManager = SkyblockSandbox.getApi().getPlayerManager();

        playerManager.removePlayer(player);
        event.setQuitMessage(Utility.colorize("&c[-] " + player.getDisplayName()));
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
