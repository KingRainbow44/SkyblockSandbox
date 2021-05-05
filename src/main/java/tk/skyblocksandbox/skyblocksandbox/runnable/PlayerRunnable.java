package tk.skyblocksandbox.skyblocksandbox.runnable;

import com.kingrainbow44.customplayer.player.CustomPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public class PlayerRunnable implements Runnable {

    private int ticks = -1;

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        ticks++;

        if(ticks % 20 == 0) { // Every second, run code:
            CustomPlayerManager playerManager = SkyblockSandbox.getApi().getPlayerManager();

            for(Player bukkitPlayer : Bukkit.getOnlinePlayers()) {
                if(!(playerManager.isCustomPlayer(bukkitPlayer) instanceof SkyblockPlayer)) return;
                SkyblockPlayer sbPlayer = (SkyblockPlayer) playerManager.isCustomPlayer(bukkitPlayer);

                sbPlayer.updateHud();
            }
        }
    }
}
