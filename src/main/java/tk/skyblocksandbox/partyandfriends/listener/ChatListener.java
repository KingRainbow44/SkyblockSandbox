package tk.skyblocksandbox.partyandfriends.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tk.skyblocksandbox.partyandfriends.PartyModule;
import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(SkyblockPlayer.getSkyblockPlayer(player) == null) return;

        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(player);
        PartyModule.ChatTypes chatType = sbPlayer.getPlayerData().currentChat;

        switch(chatType) {
            default:
            case ALL_CHAT:
                return;
            case PARTY_CHAT:
                event.setCancelled(true);

                PartyInstance partyInstance = sbPlayer.getCurrentParty();
                if(partyInstance == null) {
                    sbPlayer.sendMessages("&cYou have to be in a party to use that!");
                    sbPlayer.getPlayerData().currentChat = PartyModule.ChatTypes.ALL_CHAT;
                    return;
                }

                partyInstance.sendMessages(
                        "&9Party &8> &e" + player.getDisplayName() + "&f: " + event.getMessage()
                );
                return;
        }
    }

}
