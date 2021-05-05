package tk.skyblocksandbox.skyblocksandbox.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityManager;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import java.security.InvalidParameterException;

public class SummonCommand extends SkyblockCommand {

    public SummonCommand() {
        super("summon");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        SkyblockPlayer sbPlayer = getSkyblockPlayer(sender);
        if(sbPlayer == null) {
            sender.sendMessage("Unknown command. Type \"/help\" for help.");
            return true;
        }

        if(!sbPlayer.getPlayerData().getPermissionsData().commandSummon) {
            sbPlayer.sendMessage("&fUnknown command. Type \"/help\" for help.");
            return false;
        }

        switch(args.length) {
            default:
            case 0:
                sbPlayer.sendMessage("&cInvalid arguments. Usage: &e/summon {ENTITY_LOCALE} [player|x] [y] [z]");
                return true;
            case 1:
                sbPlayer.sendMessage("&eAttempting to summon " + args[0] + "...");
                try {
                    SkyblockEntity sbEntity = SkyblockEntityManager.parseEntity(args[0]);
                    sbEntity.createEntity(sbPlayer.getBukkitPlayer().getLocation());

                    sbPlayer.sendMessage("&aSuccessfully summoned " + args[0] + "!");
                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                    sbPlayer.sendMessage("&cUnable to summon entity. Check CONSOLE for stack trace or contact an admin.");
                }
                return true;
            case 2:
                sbPlayer.sendMessage("&eAttempting to summon " + args[0] + "...");
                try {
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target == null) {
                        sbPlayer.sendMessage("&cThat player isn't online!");
                        return true;
                    }

                    SkyblockEntity sbEntity = SkyblockEntityManager.parseEntity(args[0]);
                    sbEntity.createEntity(target.getLocation());

                    sbPlayer.sendMessage("&aSuccessfully summoned " + args[0] + "!");
                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                    sbPlayer.sendMessage("&cUnable to summon entity. Check CONSOLE for stack trace or contact an admin.");
                }
                return true;
            case 3:
            case 4:
                sbPlayer.sendMessage("&cNot enough arguments. Usage &e/summon " + args[0] + " {x} {y} {z}");
                return true;
            case 5:
                sbPlayer.sendMessage("&eAttempting to summon " + args[0] + "...");
                try {
                    int xPos = Integer.parseInt(args[1]);
                    int yPos = Integer.parseInt(args[2]);
                    int zPos = Integer.parseInt(args[3]);
                    Location location = new Location(sbPlayer.getBukkitPlayer().getWorld(), xPos, yPos, zPos);

                    SkyblockEntity sbEntity = SkyblockEntityManager.parseEntity(args[0]);
                    sbEntity.createEntity(location);

                    sbPlayer.sendMessage("&aSuccessfully summoned " + args[0] + "!");
                } catch (NumberFormatException | InvalidParameterException e) {
                    e.printStackTrace();
                    sbPlayer.sendMessage("&cUnable to summon entity. Check CONSOLE for stack trace or contact an admin.");
                }
                return true;
        }
    }

}
