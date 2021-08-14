package tk.skyblocksandbox.skyblocksandbox.runnable;

import com.kingrainbow44.customplayer.player.ICustomPlayer;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.area.SkyblockLocation;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class RegionCheck implements Runnable {

    private int run = -1;

    @Override
    public void run() {
        run++;

        if(run % 20 == 0) {
            for(ICustomPlayer customPlayer : SkyblockSandbox.getApi().getPlayerManager().getPlayers().values()) {
                if(!(customPlayer instanceof SkyblockPlayer)) return;

                Player player = customPlayer.getBukkitPlayer();
                SkyblockPlayer sbPlayer = (SkyblockPlayer) customPlayer;

                String worldName = player.getWorld().getWorldFolder().getName();

                if(worldName.matches(SkyblockSandbox.getConfiguration().hubWorld)) {
                    return;
                }

                if(worldName.matches(SkyblockSandbox.getConfiguration().dungeonHubWorld)) {
                    sbPlayer.getPlayerData().location = SkyblockLocation.DUNGEON_HUB;
                    return;
                }
            }
        }
    }
}
