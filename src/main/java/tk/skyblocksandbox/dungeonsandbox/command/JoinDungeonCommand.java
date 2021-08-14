package tk.skyblocksandbox.dungeonsandbox.command;

import org.bukkit.command.CommandSender;
import tk.skyblocksandbox.dungeonsandbox.DungeonsModule;
import tk.skyblocksandbox.dungeonsandbox.deprecated.Dungeon;
import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class JoinDungeonCommand extends SkyblockCommand {

    public JoinDungeonCommand() {
        super("joindungeon");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(isConsoleCommandSender(commandSender)) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return true;
        }

        SkyblockPlayer sbPlayer = getSkyblockPlayer(commandSender);

        switch(args.length) {
            case 0:
            case 1:
                sbPlayer.sendMessage("&cInvalid argument(s). Usage: &e/joindungeon {catacombs} {1}");
                return true;
            case 2:
                switch(args[0]) {
                    case "catacombs":
                        if(!sbPlayer.inParty()) {
                            sbPlayer.sendMessages("&cYou have to be in a party to use this feature!");
                            return true;
                        }

                        PartyInstance party = sbPlayer.getCurrentParty();
                        if(sbPlayer.getPartyPermissions() != 2) {
                            sbPlayer.sendMessages("&cYou have to be the party leader to use this feature!");
                            return true;
                        }

                        try {
                            int floor = Integer.parseInt(args[1]);
//                            DungeonsModule.getDungeonManager().createNewDungeon(Dungeon.getDungeon(Dungeon.CATACOMBS, floor), party); // TODO: Fix when available.
                        } catch (NumberFormatException e) {
                            sbPlayer.sendMessage("&cInvalid dungeon floor! Usage: &e/joindungeon catacombs {1}");
                            return true;
                        }
                        return true;
                }
                return true;
        }
        return true;
    }
}
