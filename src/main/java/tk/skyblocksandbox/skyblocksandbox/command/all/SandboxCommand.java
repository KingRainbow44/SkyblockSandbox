package tk.skyblocksandbox.skyblocksandbox.command.all;

import com.kingrainbow44.customplayer.player.ICustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayerPermissions;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

public final class SandboxCommand extends SkyblockCommand {

    public SandboxCommand() {
        super("sandbox");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        SkyblockPlayer sbPlayer = getSkyblockPlayer(sender);

        switch(args.length) {
            case 0:
                if(sbPlayer == null) return true;
                sbPlayer.sendMessages(
                        "&a&m---------------&r&bSkyblock Sandbox&r&a&m---------------",
                        "&eVersion: &b" + SkyblockSandbox.getVersion(),
                        " "
                );

                if(sbPlayer.getBukkitPlayer().isOp() && SkyblockSandbox.getConfiguration().noDatabaseConfig)  {
                    sbPlayer.sendMessages(
                            "&e&m--------------------&r&eWARNING&r&e&m--------------------",
                            "&eSkyblockSandbox does NOT have a database setup.",
                            "&eAs no database is connected, most features will NOT work as intended.",
                            "&eSkyblockSandbox uses a MySQL database to store data.",
                            "&eAs most people do not know how to setup a MySQL database,",
                            "&ein the future there will be a JSON database option.",
                            "&e&m--------------------&r&eWARNING&r&e&m--------------------",
                            " "
                    );
                }

                sbPlayer.sendMessages(
                        ""
                );
                return true;
            case 3:
                if(!sender.hasPermission("skyblocksandbox.operator.permissions")) {
                    if(sbPlayer == null) return true;
                    sbPlayer.sendMessages(
                            "&a&m---------------&r&bSkyblock Sandbox&r&a&m---------------",
                            "&eVersion: &b" + SkyblockSandbox.getVersion(),
                            " "
                    );

                    if(sbPlayer.getBukkitPlayer().isOp() && SkyblockSandbox.getConfiguration().noDatabaseConfig)  {
                        sbPlayer.sendMessages(
                                "&e&m--------------------&r&eWARNING&r&e&m--------------------",
                                "&eSkyblockSandbox does NOT have a database setup.",
                                "&eAs no database is connected, most features will NOT work as intended.",
                                "&eSkyblockSandbox uses a MySQL database to store data.",
                                "&eAs most people do not know how to setup a MySQL database,",
                                "&ein the future there will be a JSON database option.",
                                "&e&m--------------------&r&eWARNING&r&e&m--------------------",
                                " "
                        );
                    }

                    sbPlayer.sendMessages(
                            ""
                    );
                    return true;
                }

                Player player = Bukkit.getPlayer(args[0]);
                if(player == null) {
                    sender.sendMessage(ChatColor.RED + "Invalid player name! This player isn't online!");
                    return true;
                }

                ICustomPlayer customPlayer = SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);
                if(!(customPlayer instanceof SkyblockPlayer)) {
                    sender.sendMessage(ChatColor.RED + "Invalid player. They are not a Skyblock Player!");
                    return true;
                }

                SkyblockPlayer skyblockPlayer = (SkyblockPlayer) customPlayer;
                SkyblockPlayerPermissions playerPermissions = skyblockPlayer.getPlayerData().getPermissionsData();

                switch(args[1]) {
                    case "give":
                    case "grant":
                        switch(args[2]) {
                            default:
                                playerPermissions.grantPermission(args[2]);
                                sender.sendMessage(Utility.colorize("&aGranted " + player.getDisplayName() + " the permission: &e" + args[2] + "&a."));
                                return true;
                            case "command.debug":
                                playerPermissions.commandDebug = true;
                                sender.sendMessage(Utility.colorize("&aGranted " + player.getDisplayName() + " the permission of using '&e/debug&a'."));
                                return true;
                            case "command.item":
                                playerPermissions.commandItem = true;
                                sender.sendMessage(Utility.colorize("&aGranted " + player.getDisplayName() + " the permission of using '&e/item&a'."));
                                return true;
                            case "command.summon":
                                playerPermissions.commandSummon = true;
                                sender.sendMessage(Utility.colorize("&aGranted " + player.getDisplayName() + " the permission of using '&e/summon&a'."));
                                return true;
                            case "command.setblock":
                                playerPermissions.commandSetBlock = true;
                                sender.sendMessage(Utility.colorize("&aGranted " + player.getDisplayName() + " the permission of using '&e/setblock&a'."));
                                return true;
                            case "sandbox.build":
                                playerPermissions.buildingAllowed = true;
                                sender.sendMessage(Utility.colorize("&aGranted " + player.getDisplayName() + " the permission of building."));
                                return true;
                        }
                    case "remove":
                    case "revoke":
                        switch(args[2]) {
                            default:
                                playerPermissions.revokePermission(args[2]);
                                sender.sendMessage(Utility.colorize("&cRevoked " + player.getDisplayName() + " of the permission: &e" + args[2]));
                                return true;
                            case "command.debug":
                                playerPermissions.commandDebug = false;
                                sender.sendMessage(Utility.colorize("&cRevoked " + player.getDisplayName() + "'s permission of using '&e/debug&c'."));
                                return true;
                            case "command.item":
                                playerPermissions.commandItem = false;
                                sender.sendMessage(Utility.colorize("&cRevoked " + player.getDisplayName() + "'s permission of using '&e/item&c'."));
                                return true;
                            case "command.summon":
                                playerPermissions.commandSummon = false;
                                sender.sendMessage(Utility.colorize("&cRevoked " + player.getDisplayName() + "'s permission of using '&e/summon&c'."));
                                return true;
                            case "command.setblock":
                                playerPermissions.commandSetBlock = false;
                                sender.sendMessage(Utility.colorize("&cRevoked " + player.getDisplayName() + "'s permission of using '&e/setblock&c'."));
                                return true;
                            case "sandbox.build":
                                playerPermissions.buildingAllowed = false;
                                sender.sendMessage(Utility.colorize("&cRevoked " + player.getDisplayName() + "'s permission of building."));
                                return true;
                        }
                }
                return true;
        }

        return true;
    }
}
