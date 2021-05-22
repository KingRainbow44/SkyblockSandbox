package tk.skyblocksandbox.partyandfriends.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import tk.skyblocksandbox.partyandfriends.PartyModule;
import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class ChatCommand extends SkyblockCommand {

    public ChatCommand() {
        super("chat");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(isConsoleCommandSender(commandSender)) {
            commandSender.sendMessage(ChatColor.RED + "You have to execute this command in-game!");
            return true;
        }

        SkyblockPlayer sbPlayer = getSkyblockPlayer(commandSender);

        switch(args.length) {
            default:
            case 0:
                sbPlayer.sendMessage("&cInvalid argument(s). Usage: &e/chat {party|reply|skyblock-coop}");
                return true;
            case 1:
                switch(args[0]) {
                    default:
                        sbPlayer.sendMessage("&cInvalid argument(s). Usage: &e/chat {all|party|reply|skyblock-coop}");
                        return true;
                    case "a":
                    case "all":
                        sbPlayer.sendMessages("&aYou are now in &6ALL &achat.");
                        sbPlayer.getPlayerData().currentChat = PartyModule.ChatTypes.ALL_CHAT;
                        return true;
                    case "p":
                    case "party":
                        PartyInstance partyInstance = sbPlayer.getCurrentParty();
                        if(partyInstance == null) {
                            sbPlayer.sendMessages("&cYou have to be in a party to use that!");
                            return true;
                        }

                        sbPlayer.sendMessages("&aYou are now in &6PARTY &achat.");
                        sbPlayer.getPlayerData().currentChat = PartyModule.ChatTypes.PARTY_CHAT;
                        return true;
                    case "r":
                    case "reply":
                        sbPlayer.sendMessage("&cThis feature hasn't been implemented yet!");
                        return true;
                    case "coop":
                    case "skyblock-coop":
                        sbPlayer.sendMessage("&cThis feature hasn't been implemented yet!");
                        return true;
                }
        }
    }
}
