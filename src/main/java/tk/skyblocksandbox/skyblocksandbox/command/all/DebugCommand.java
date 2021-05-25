package tk.skyblocksandbox.skyblocksandbox.command.all;

import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.dungeonsandbox.generator.VoidGenerator;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Music;

public final class DebugCommand extends SkyblockCommand {

    public DebugCommand() {
        super("debug");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        SkyblockPlayer sbPlayer = getSkyblockPlayer(sender);
        if(sbPlayer == null) return true;

        if(!sbPlayer.getPlayerData().getPermissionsData().commandDebug) {
            sbPlayer.sendMessage("&fUnknown command. Type \"/help\" for help.");
            return true;
        }

        switch(args.length) {

            case 0:
                sbPlayer.sendMessages("&cInvalid argument! Usage: &e/debug <music|playall|changestat|toggle|entity|warp> [music_id|health|defense|intelligence|strength|damage|messages|reset|kill|check|foldername|village|dungeon_hub] [valid integer]");
                return true;

            case 1:
                switch(args[0]) {
                    case "music":
                        sbPlayer.sendMessages("&cArgument missing! Usage: &e/debug music [music_id|stop] [music_id]");
                        return true;
                    case "changestat":
                        sbPlayer.sendMessage("&cArguments missing! Usage: &e/debug changestat [health|defense|intelligence|strength] [valid integer]");
                        return true;
                    case "toggle":
                        sbPlayer.sendMessage("&cArguments missing! Usage: &e/debug toggle [damage|messages|build|infinitemana]");
                        return true;
                    case "entity":
                        sbPlayer.sendMessage("&cArguments missing! Usage: &e/debug entity [reset|kill]");
                        return true;
                    case "warp":
                        sbPlayer.sendMessage("&cInvalid argument. Usage: &e/debug warp [foldername|village|dungeon_hub]");
                        return true;
                    case "saferestart":
                        if(!sbPlayer.getBukkitPlayer().isOp()) {
                            return true;
                        }

                        CitizensAPI.getNPCRegistry().deregisterAll();
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            player.kickPlayer("Server Restart!");
                        }

                        Bukkit.shutdown();
                        return true;
                }
                return true;

            case 2:
                switch(args[0]) {
                    case "music":
                        if(!args[1].matches("stop")) {
                            sbPlayer.sendMessage("&eAttempting to play '" + args[1] + "'...");
                        }

                        if ("stop".equals(args[1])) {
                            sbPlayer.sendMessage("&eAttempting to cancel music...");
                            if (Music.cancelMusic(sbPlayer)) {
                                sbPlayer.sendMessage("&aSuccessfully canceled music!");
                            }
                            return true;
                        }

                        if (!Music.playMusic(sbPlayer, args[1])) {
                            sbPlayer.sendMessage("&cFailed to play '" + args[1] + "'.");
                        } else {
                            sbPlayer.sendMessage("&aPlaying song: '" + args[1] + "'!");
                        }
                    case "playall":
                        if(!args[1].matches("stop")) {
                            sbPlayer.sendMessage("&eAttempting to play '" + args[1] + "'...");
                        }

                        if ("stop".equals(args[1])) {
                            sbPlayer.sendMessage("&eAttempting to cancel music...");
                            if (Music.cancelMusic(sbPlayer)) {
                                sbPlayer.sendMessage("&aSuccessfully canceled music!");
                            }
                            return true;
                        }
                        if (!Music.playMusicAll(args[1])) {
                            sbPlayer.sendMessage("&cFailed to play '" + args[1] + "'.");
                        } else {
                            sbPlayer.sendMessage("&aPlaying song: '" + args[1] + "'!");
                        }
                        return true;
                    case "changestat":
                        sbPlayer.sendMessage("&cArguments missing! Usage: &e/debug changestat " + args[1] + " [valid integer]");
                        return true;
                    case "toggle":
                        switch(args[1]) {
                            default:
                                sbPlayer.sendMessage("&cInvalid argument. Usage: &e/debug toggle [damage|messages|build|infinitemana]");
                                return true;
                            case "damage":
                                if(sbPlayer.getPlayerData().debugStateDamage) {
                                    sbPlayer.sendMessage("&cDisabled debugging info for damage.");
                                    sbPlayer.getPlayerData().debugStateDamage = false;
                                } else {
                                    sbPlayer.sendMessage("&aEnabled debugging info for damage.");
                                    sbPlayer.getPlayerData().debugStateDamage = true;
                                }
                                return true;
                            case "messages":
                                if(sbPlayer.getPlayerData().debugStateMessages) {
                                    sbPlayer.sendMessage("&cDisabled debugging info.");
                                    sbPlayer.getPlayerData().debugStateMessages = false;
                                } else {
                                    sbPlayer.sendMessage("&aEnabled debugging info.");
                                    sbPlayer.getPlayerData().debugStateMessages = true;
                                }
                                return true;
                            case "build":
                                if(!sbPlayer.getBukkitPlayer().hasPermission("sandbox.build")) {
                                    sbPlayer.sendMessage("&cYou do not have permission to toggle build.");
                                    return true;
                                }

                                if(sbPlayer.getPlayerData().getPermissionsData().buildingEnabled) {
                                    sbPlayer.sendMessage("&cBuild has been disabled.");
                                    sbPlayer.getPlayerData().getPermissionsData().buildingEnabled = false;
                                } else {
                                    sbPlayer.sendMessage("&aBuild has been enabled.");
                                    sbPlayer.getPlayerData().getPermissionsData().buildingEnabled = true;
                                }
                                return true;
                            case "infinitemana":
                                if(sbPlayer.getPlayerData().infiniteMana) {
                                    sbPlayer.sendMessage("&cInfinite Mana has been disabled.");
                                    sbPlayer.getPlayerData().infiniteMana = false;
                                } else {
                                    sbPlayer.sendMessage("&aInfinite Mana has been enabled.");
                                    sbPlayer.getPlayerData().infiniteMana = true;
                                }
                                return true;
                        }
                    case "entity":
                        switch(args[1]) {
                            default:
                                sbPlayer.sendMessage("&cInvalid argument. Usage: &e/debug entity [reset|kill|check] [entity id]");
                                return true;
                            case "reset":
                                sbPlayer.sendMessage("&eAttempting to reset nearby entities...");
                                for(Entity entity : sbPlayer.getBukkitPlayer().getNearbyEntities(10, 10, 10)) {
                                    if(SkyblockSandbox.getManagement().getEntityManager().getEntity(entity) == null) return true;
                                    SandboxEntity sbEntity = SkyblockSandbox.getManagement().getEntityManager().getEntity(entity);

                                    sbEntity.setHealth(sbEntity.getEntityData().health);
                                }
                                return true;
                            case "kill":
                                sbPlayer.sendMessage("&eAttempting to kill all entities...");
                                for(Entity entity : sbPlayer.getBukkitPlayer().getWorld().getEntities()) {
                                    if(entity instanceof Player) return true;

                                    if(SkyblockSandbox.getManagement().getEntityManager().getEntity(entity) == null) {
                                        entity.remove();
                                    } else {
                                        SandboxEntity sbEntity = SkyblockSandbox.getManagement().getEntityManager().getEntity(entity);
                                        sbEntity.kill(true);
                                    }
                                }
                                return true;
                            case "check":
                                sbPlayer.sendMessage("&cInvalid argument. Usage: &e/debug entity check [entity id]");
                                return true;
                        }
                    case "warp":
                        sbPlayer.sendMessage("&7Warping...");
                        switch(args[1]) {
                            default:
                                String worldName = args[1];
                                World world = Bukkit.getWorld(worldName);

                                if(world == null) {
                                    WorldCreator worldCreator = new WorldCreator(worldName);
                                    worldCreator.generator(new VoidGenerator());
                                    world = worldCreator.createWorld();
                                }

                                sbPlayer.getPlayerData().location = SkyblockPlayer.SUBLOC_NONE;
                                sbPlayer.getBukkitPlayer().teleport(new Location(world, 0, 0,0));
                                return true;
                            case "village":
                                sbPlayer.getPlayerData().location = SkyblockPlayer.SUBLOC_VILLAGE;
                                sbPlayer.getBukkitPlayer().teleport(sbPlayer.getSpawn());
                                return true;
                            case "dungeon_hub":
                                sbPlayer.getPlayerData().location = SkyblockPlayer.SUBLOC_DUNGEON_HUB;
                                sbPlayer.getBukkitPlayer().teleport(sbPlayer.getSpawn());
                                return true;
                        }
                }
                return true;

            case 3:
                switch(args[0]) {
                    case "music":
                        switch(args[1]) {
                            case "stop":
                                sbPlayer.sendMessage("&eAttempting to nuke '" + args[2] + "'...");
                                if(Music.cancelMusic(args[2])) {
                                    sbPlayer.sendMessage("&aSuccessfully nuked '" + args[2] + "'...");
                                }
                                return true;
                        }
                        return true;
                    case "changestat":
                        try {
                            int setStat = Integer.parseInt(args[2]);
                            switch(args[1]) {
                                default:
                                    sbPlayer.sendMessage("&cInvalid stat! Types: health, defense, intelligence, strength");
                                    return true;
                                case "intelligence":
                                    sbPlayer.getPlayerData().intelligence = setStat + 100;
                                    sbPlayer.getPlayerData().currentMana = sbPlayer.getPlayerData().intelligence;
                                    sbPlayer.sendMessage("&aSet intelligence to " + args[2] + "!");
                                    return true;
                                case "strength":
                                    sbPlayer.getPlayerData().strength = setStat;
                                    sbPlayer.sendMessage("&aSet strength to " + args[2] + "!");
                                    return true;
                                case "health":
                                    sbPlayer.getPlayerData().health = setStat;
                                    sbPlayer.sendMessage("&aSet health to " + args[2] + "!");
                                    return true;
                                case "defense":
                                    sbPlayer.getPlayerData().defense = setStat;
                                    sbPlayer.sendMessage("&aSet defense to " + args[2] + "!");
                                    return true;
                            }
                        } catch (NumberFormatException e) {
                            sbPlayer.sendMessage("&cInvalid number! The number has to be between 0 and 2,147,483,647.");
                            return true;
                        }
                    case "entity":
                        switch(args[1]) {
                            case "check":
                                try {
                                    int entityId = Integer.parseInt(args[2]);
                                    SandboxEntity entity = SkyblockSandbox.getManagement().getEntityManager().getEntity(entityId);

                                    if(entity != null) {
                                        sbPlayer.sendMessage("&aEntity is valid.");
                                        sbPlayer.getBukkitPlayer().teleport(entity.getBukkitEntity().getLocation());
                                    } else {
                                        sbPlayer.sendMessage("&cEntity is invalid.");
                                    }
                                } catch (NumberFormatException e) {
                                    sbPlayer.sendMessage("&cInvalid number! The number has to be between 0 and 2,147,483,647.");
                                    return true;
                                }
                        }
                        return true;
                }
                return true;
        }

        return true;
    }
}
