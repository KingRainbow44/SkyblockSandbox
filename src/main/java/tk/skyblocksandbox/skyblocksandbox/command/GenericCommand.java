package tk.skyblocksandbox.skyblocksandbox.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public final class GenericCommand extends SkyblockCommand {

    private final GenericCommandConsumer method;
    private final SenderType senderType;
    private final String permission;

    public GenericCommand(String name, String desc, String permission, SenderType type, GenericCommandConsumer lambdaMethod, String... aliases) {
        super(name, aliases, desc);

        this.permission = permission;
        this.senderType = type;
        this.method = lambdaMethod;
    }

    public GenericCommand(String name, String desc, String permission, SenderType type, GenericCommandConsumer lambdaMethod) {
        super(name, desc);

        this.permission = permission;
        this.senderType = type;
        this.method = lambdaMethod;
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        boolean continueExecution = true;
        switch(senderType) {
            case ALL:
                break;

            case CONSOLE_SENDER:
                if(!(commandSender instanceof ConsoleCommandSender) || !(commandSender instanceof RemoteConsoleCommandSender)) continueExecution = false;
                break;
            case SPECIFIC_CONSOLE_SENDER:
                if(!(commandSender instanceof ConsoleCommandSender)) continueExecution = false;
                break;
            case SPECIFIC_REMOTE_CONSOLE_SENDER:
                if(!(commandSender instanceof RemoteConsoleCommandSender)) continueExecution = false;
                break;

            case PLAYER_SENDER:
                if(!(commandSender instanceof Player)) continueExecution = false;
                break;
        }

        if(!continueExecution) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return true;
        }

        if(!commandSender.hasPermission(permission) || !permission.matches("all")) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return true;
        }

        if(!method.execute(
                commandSender,
                args
        )) {
            commandSender.sendMessage(UNKNOWN_COMMAND);
            return true;
        }

        return true;
    }

    public enum SenderType {
        ALL,

        CONSOLE_SENDER,
        SPECIFIC_CONSOLE_SENDER,
        SPECIFIC_REMOTE_CONSOLE_SENDER,

        PLAYER_SENDER
    }

    @FunctionalInterface
    public interface GenericCommandConsumer {
        boolean execute(CommandSender CommandSender, String[] String);
    }
}
