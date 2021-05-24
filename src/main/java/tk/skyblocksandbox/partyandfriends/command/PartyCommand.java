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
        super("party", new String[]{"p"}, "A replica of Hypixel's party system.");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(isConsoleCommandSender(commandSender)) return true;
        SkyblockPlayer sbPlayer = getSkyblockPlayer(commandSender);

        switch(args.length) {
            default:
                if(!args[0].matches("chat")) return true;

                if(sbPlayer.getCurrentParty() == null) {
                    sbPlayer.sendMessages(
                            "&9&m-----------------------------",
                            "&cYou're not in a party!",
                            "&9&m-----------------------------"
                    );
                    return true;
                }

                StringBuilder message = new StringBuilder();
                for(int i = 1; i != args.length; i++) {
                    message.append(args[i]).append(" ");
                }

                PartyInstance partyInstance = sbPlayer.getCurrentParty();
                for(SkyblockPlayer member : partyInstance.getMembers()) {
                    member.sendMessage("&9Party &8> &e" + sbPlayer.getBukkitPlayer().getDisplayName() + "&f: " + message);
                }
                return true;
            case 0:
                sbPlayer.sendMessages(
                        "&9&m-----------------------------",
                        "&e/party accept <player>",
                        "&e/party invite <player>",
                        "&e/party leave",
                        "&e/party disband",
                        "&e/party chat <message>",
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
                        if(!partyManager.createParty(sbPlayer)) {
                            sbPlayer.sendMessages(
                                    "&9&m-----------------------------",
                                    "&cYou're already in a party!",
                                    "&9&m-----------------------------"
                            );
                        }

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
                                "&e/party chat <message>",
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
                        if(sbPlayer.getCurrentParty() != null) {
                            sbPlayer.sendMessages(
                                    "&9&m-----------------------------",
                                    "&cYou're already in a party!",
                                    "&9&m-----------------------------"
                            );
                            return true;
                        }

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
                        party1.kickMember(sbPlayer, (SkyblockPlayer) SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(kickPlayer));
                        return true;
                    case "chat":
                        if(sbPlayer.getCurrentParty() == null) {
                            sbPlayer.sendMessages(
                                    "&9&m-----------------------------",
                                    "&cYou're not in a party!",
                                    "&9&m-----------------------------"
                            );
                            return true;
                        }

                        StringBuilder message2 = new StringBuilder();
                        for(int i = 1; i != args.length; i++) {
                            message2.append(args[i]).append(" ");
                        }

                        PartyInstance partyInstance2 = sbPlayer.getCurrentParty();
                        for(SkyblockPlayer member : partyInstance2.getMembers()) {
                            member.sendMessage("&9Party &8> &e" + sbPlayer.getBukkitPlayer().getDisplayName() + "&f: " + message2);
                        }
                        return true;
                }
        }
    }
}
