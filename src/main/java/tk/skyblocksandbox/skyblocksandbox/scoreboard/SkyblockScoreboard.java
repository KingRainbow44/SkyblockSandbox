package tk.skyblocksandbox.skyblocksandbox.scoreboard;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

public abstract class SkyblockScoreboard {

    public String scoreboardType;
    public Player player;
    public SkyblockPlayer sbPlayer;
    public BPlayerBoard board;

    public SkyblockScoreboard(String scoreboardType, SkyblockPlayer player) {
        board = Netherboard.instance().createBoard(player.getBukkitPlayer(), Utility.colorize("&e&lSKYBLOCK"));
        this.scoreboardType = scoreboardType;
        this.player = player.getBukkitPlayer();
        this.sbPlayer = player;

        setScoreboard();
    }

    public abstract void setScoreboard();


    public abstract void updateScoreboard();
}