package tk.skyblocksandbox.skyblocksandbox.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItem;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class ItemCommand extends SkyblockCommand {

    public ItemCommand() {
        super("item");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        SkyblockPlayer sbPlayer = getSkyblockPlayer(sender);
        if(sbPlayer == null) {
            sender.sendMessage("Unknown command. Type \"/help\" for help.");
            return true;
        }

        if(!sbPlayer.getPlayerData().getPermissionsData().commandItem) {
            sbPlayer.sendMessage("&fUnknown command. Type \"/help\" for help.");
            return false;
        }

        switch(args.length) {
            default:
            case 0:
                sbPlayer.sendMessage("&cNot enough arguments. Usage: '/item {ITEM_LOCALE} (player)'");
                return true;
            case 1:
                if(args[0].matches("list")) {
                    sbPlayer.sendMessage("&eList of Skyblock Item IDs:");
                    for(SkyblockItem sbItem : SkyblockSandbox.getManagement().getItemManager().getRegisteredItems().values()) {
                        sbPlayer.sendMessage("&a- " + sbItem.getItemId());
                    }
                } else {
                    String itemId = args[0];
                    Object sbItem = SkyblockSandbox.getManagement().getItemManager().isSkyblockItem(itemId);

                    if(!(sbItem instanceof SkyblockItem)) {
                        sbPlayer.sendMessage("&cInvalid item. Use '/item list' to get a list of items.");
                        return true;
                    }

                    sbPlayer.getBukkitPlayer().getInventory().addItem(((SkyblockItem) sbItem).createItem());
                    sbPlayer.sendMessage("&aAdded " + args[0] + " to your inventory!");
                }
                return true;
            case 2:
                String itemId = args[0];
                Object sbItem = SkyblockSandbox.getManagement().getItemManager().isSkyblockItem(itemId);

                if(!(sbItem instanceof SkyblockItem)) {
                    sbPlayer.sendMessage("&cInvalid item. Use '/item list' to get a list of items.");
                    return true;
                }

                String playerName = args[1];
                Player bukkitPlayer = Bukkit.getPlayer(playerName);
                if(bukkitPlayer == null) {
                    sbPlayer.sendMessage("&cInvalid player name. That player is not online!");
                    return true;
                }

                bukkitPlayer.getInventory().addItem(((SkyblockItem) sbItem).createItem());
                bukkitPlayer.sendMessage(ChatColor.GREEN + "You received a(n) " + ((SkyblockItem) sbItem).getItemId() + " from an admin!");

                sbPlayer.sendMessage("&aAdded " + ((SkyblockItem) sbItem).getItemId() + " to " + bukkitPlayer.getDisplayName() + "'s inventory!");
                return true;
        }
    }
}
