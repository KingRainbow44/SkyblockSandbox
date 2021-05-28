package tk.skyblocksandbox.skyblocksandbox.command;

import com.kingrainbow44.customplayer.player.ICustomPlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class SkyblockCommand extends Command implements TabCompleter {

    protected static final String UNKNOWN_COMMAND = "Unknown command. Type \"/help\" for help.";

    protected SkyblockCommand(String name) {
        super(name);
    }

    protected SkyblockCommand(String name, String[] aliases) {
        super(name, "", "", Arrays.asList(aliases));
    }

    protected SkyblockCommand(String name, String[] aliases, String description) {
        super(name, description, "/skyblocksandbox:" + name + " help", Arrays.asList(aliases));
    }

    protected SkyblockCommand(String name, String description) {
        super(name, description, "/skyblocksandbox:" + name + " help", Collections.emptyList());
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> usage = new ArrayList<>();

        usage.add("help");

        return usage;
    }
}
