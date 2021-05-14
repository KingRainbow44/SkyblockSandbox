package tk.skyblocksandbox.partyandfriends.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.partyandfriends.PartyModule;
import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.partyandfriends.party.PartyManager;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class PartyCommand extends SkyblockCommand {

    public PartyCommand() {
        super("party");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(isConsoleCommandSender(commandSender)) return true;
        SkyblockPlayer sbPlayer = getSkyblockPlayer(commandSender);

        switch(args.length) {
            case 0:
                sbPlayer.sendMessages(
                        "&9&m-----------------------------",
                        "&e/party accept <player>",
                        "&e/party invite <player>",
                        "&e/party leave",
                        "&e/party disband",
                        "&9&m-----------------------------"
                );
                return true;
            case 1:
                switch(args[0]) {
                    default:
                        String playerName = args[0];
                        Player player = Bukkit.getPlayer(playerName);
                        if(player == null) return true;

                        SkyblockPlayer invite = (SkyblockPlayer) SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);

                        PartyManager partyManager = PartyModule.getPartyManager();
                        partyManager.createParty(sbPlayer);

                        PartyInstance createParty = partyManager.getPartyFromPlayer(sbPlayer.getBukkitPlayer().getUniqueId());
                        createParty.dispatchInvite(sbPlayer, invite);
                        return true;
                    case "leave":
                        PartyInstance leaveParty = sbPlayer.getCurrentParty();
                        if(leaveParty == null) return true;

                        leaveParty.removeMember(sbPlayer);
                        return true;
                    case "disband":
                        PartyInstance disbandParty = sbPlayer.getCurrentParty();
                        if(disbandParty == null) return true;

                        if(sbPlayer.getPartyPermissions() != 2) {
                            sbPlayer.sendMessages(
                                    "&9&m-----------------------------",
                                    "&cYou have to be the leader to do that!",
                                    "&9&m-----------------------------"
                            );
                            return true;
                        }

                        PartyManager partyManager1 = PartyModule.getPartyManager();
                        partyManager1.disbandParty(sbPlayer);
                        return true;
                    case "debug":
                        sbPlayer.sendMessage("Party Permissions: " + sbPlayer.getPartyPermissions());
                        return true;
                }
            case 2:
                switch(args[0]) {
                    default:
                        sbPlayer.sendMessages(
                                "&9&m-----------------------------",
                                "&e/party accept <player>",
                                "&e/party invite <player>",
                                "&e/party leave",
                                "&e/party disband",
                                "&9&m-----------------------------"
                        );
                        return true;
                    case "invite":
                        String playerName = args[1];
                        Player player = Bukkit.getPlayer(playerName);
                        if(player == null) return true;

                        SkyblockPlayer invite = (SkyblockPlayer) SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);

                        PartyManager partyManager = PartyModule.getPartyManager();
                        partyManager.createParty(sbPlayer);

                        PartyInstance createParty = partyManager.getPartyFromPlayer(sbPlayer.getBukkitPlayer().getUniqueId());
                        createParty.dispatchInvite(sbPlayer, invite);
                        return true;
                    case "accept":
                        String strLeader = args[1];
                        Player leader = Bukkit.getPlayer(strLeader);
                        if(leader == null) return true;

                        PartyManager partyManager1 = PartyModule.getPartyManager();
                        if(partyManager1.getPartyFromPlayer(leader.getUniqueId()) == null) return true;
                        PartyInstance party = partyManager1.getPartyFromPlayer(leader.getUniqueId());

                        if(!party.hasInvite(sbPlayer)) return true;
                        party.addMember(sbPlayer);
                        return true;
                    case "kick":
                        String strKick = args[1];
                        Player kickPlayer = Bukkit.getPlayer(strKick);
                        if(kickPlayer == null) return true;

                        if(sbPlayer.getPartyPermissions() == 0) {
                            sbPlayer.sendMessages(
                                    "&9&m-----------------------------",
                                    "&cYou do not have permission to do that!",
                                    "&9&m-----------------------------"
                            );
                            return true;
                        }

                        PartyInstance party1 = sbPlayer.getCurrentParty();
                        party1.removeMember((SkyblockPlayer) SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(kickPlayer));
                        return true;
                }
        }

        return true;
    }
}
