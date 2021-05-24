package tk.skyblocksandbox.permitable.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.permitable.rank.PermitableRank;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class RankCommand extends SkyblockCommand {

    public RankCommand() {
        super("rank");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {

        if(isConsoleCommandSender(commandSender)) {
            switch(args.length) {
                default:
                case 0:
                case 1:
                    return true;
                case 2:
                    Player player = Bukkit.getPlayer(args[0]);
                    if(player == null) return true;

                    String rankName = args[1];
                    SkyblockPlayer sbPlayer = getSkyblockPlayer(commandSender);

                    sbPlayer.getPlayerData().rank = PermitableRank.getRankByString(
                            rankName
                    );
                    return true;
            }
        }

        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer((Player) commandSender);
        if(!sbPlayer.getBukkitPlayer().hasPermission("skyblocksandbox.command.rank")) {
            sbPlayer.sendMessages(UNKNOWN_COMMAND);
            return true;
        }

        switch(args.length) {
            default:
            case 0:
                sbPlayer.sendMessages(
                        "&cInvalid argument(s)! Usage: &e/rank {PLAYER} {DEFAULT|VIP|VIP+|MVP|MVP+|MVP++|ADMIN|OWNER}"
                );
                return true;
            case 1:
                sbPlayer.sendMessages(
                        "&cInvalid argument(s)! Usage: &e/rank " + args[0] + " {DEFAULT|VIP|VIP+|MVP|MVP+|MVP++|ADMIN|OWNER}"
                );
                return true;
            case 2:
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    sbPlayer.sendMessages("&cInvalid player! They aren't online!");
                    return true;
                }

                SkyblockPlayer sbTarget = SkyblockPlayer.getSkyblockPlayer(target);
                String rankName = args[1];

                PermitableRank.AvailableRanks rank = PermitableRank.getRankByString(
                        rankName
                );
                sbTarget.getPlayerData().rank = rank;

                sbPlayer.sendMessages("&aSuccessfully set " + target.getDisplayName() + "'s rank!");
                sbTarget.sendMessage("&aYou are now a(n) " + rank.name() + "!");
                return true;
        }
    }
}
