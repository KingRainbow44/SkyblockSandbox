package tk.skyblocksandbox.skyblocksandbox.listener;

import com.kingrainbow44.customplayer.player.ICustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItem;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class ItemListener implements Listener {

    @EventHandler
    public void onInventoryUpdate(InventoryPickupItemEvent event) {

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ICustomPlayer customPlayer = SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);
        if(!(customPlayer instanceof SkyblockPlayer)) return;
        SkyblockPlayer sbPlayer = (SkyblockPlayer) customPlayer;

        int ability;
        switch(event.getAction()) {
            default:
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                ability = SkyblockItem.INTERACT_RIGHT_CLICK;
                break;
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                ability = SkyblockItem.INTERACT_LEFT_CLICK;
                break;
        }

        SkyblockItem sbItem = sbPlayer.getItemInHand(true);
        if(sbItem == null) return;

        sbItem.ability(ability, sbPlayer);
    }

}
