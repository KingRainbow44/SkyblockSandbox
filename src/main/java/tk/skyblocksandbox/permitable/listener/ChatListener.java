package tk.skyblocksandbox.permitable.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tk.skyblocksandbox.permitable.rank.PermitableRank;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(player);

        PermitableRank.AvailableRanks rank = sbPlayer.getPlayerData().rank;
        PermitableRank sbRank = PermitableRank.getRankByEnum(rank);

        event.setFormat(sbRank.getRankChatFormat());
    }

}
