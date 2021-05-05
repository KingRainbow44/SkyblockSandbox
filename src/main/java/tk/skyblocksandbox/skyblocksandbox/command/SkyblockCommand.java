package tk.skyblocksandbox.skyblocksandbox.command;

import com.kingrainbow44.customplayer.player.ICustomPlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public abstract class SkyblockCommand extends Command {

    protected SkyblockCommand(String name) {
        super(name);
    }

    protected boolean isConsoleCommandSender(CommandSender sender) {
        return sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender;
    }

    protected boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    protected SkyblockPlayer getSkyblockPlayer(CommandSender sender) {
        if(!isPlayer(sender)) return null;
        Player player = (Player) sender;
        ICustomPlayer customPlayer = SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);
        if(!(customPlayer instanceof SkyblockPlayer)) return null;

        return (SkyblockPlayer) customPlayer;
    }
}
