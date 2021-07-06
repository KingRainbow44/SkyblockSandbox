package tk.skyblocksandbox.skyblocksandbox.command.all;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import tk.skyblocksandbox.dungeonsandbox.generator.VoidGenerator;
import tk.skyblocksandbox.skyblocksandbox.area.SkyblockLocation;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class WarpCommand extends SkyblockCommand {

    public WarpCommand() {
        super("warp", new String[0], "Warp throughout Skyblock's vast islands!");
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if(isConsoleCommandSender(commandSender)) {
            commandSender.sendMessage(ChatColor.RED + "Execute this in-game!");
            return true;
        }

        SkyblockPlayer sbPlayer = getSkyblockPlayer(commandSender);

        if(args.length < 1) {
            sbPlayer.sendMessages("&cProvide an island name!");
            return true;
        }

        switch(args[0]) {
            default:
                if(!commandSender.hasPermission("skyblocksandbox.command.warp.world")) {
                    sbPlayer.sendMessages("&cProvide an island name!");
                    return true;
                } else {
                    String worldName = args[0];
                    World world = Bukkit.getWorld(worldName);

                    if(world == null) {
                        WorldCreator worldCreator = new WorldCreator(worldName);
                        worldCreator.generator(new VoidGenerator());
                        world = worldCreator.createWorld();
                    }

                    sbPlayer.changeLocation(SkyblockLocation.NONE);
                    sbPlayer.getBukkitPlayer().teleport(new Location(world, 0, 0,0));
                }
                break;
            case "hub":
            case "village":
                sbPlayer.teleportTo(SkyblockLocation.VILLAGE);
                break;
            case "dungeon_hub":
                sbPlayer.teleportTo(SkyblockLocation.DUNGEON_HUB);
                return true;
        }

        sbPlayer.sendMessage("&7Warping...");
        return true;
    }
}
