package tk.skyblocksandbox.skyblocksandbox.command.all;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
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
                sbPlayer.sendMessages("&cInvalid argument! Usage: &e/debug <music|playall|changestat|toggle|entity> [music_id|health|defense|intelligence|strength|damage|messages|reset] [valid integer]");
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
                        sbPlayer.sendMessage("&cArguments missing! Usage: &e/debug toggle [damage|messages]");
                        return true;
                    case "entity":
                        sbPlayer.sendMessage("&cArguments missing! Usage: &e/debug entity [reset]");
                        return true;
                }
                return true;

            case 2:
                switch(args[0]) {
                    case "music":
                        if(!args[1].matches("stop")) {
                            sbPlayer.sendMessage("&eAttempting to play '" + args[1] + "'...");
                        }

                        boolean played;
                        switch(args[1]) {
                            default:
                                sbPlayer.sendMessage("&cInvalid argument! The music id is invalid.");
                                return true;
                            case "stop":
                                sbPlayer.sendMessage("&eAttempting to cancel music...");
                                if(Music.cancelMusic(sbPlayer)) {
                                    sbPlayer.sendMessage("&aSuccessfully canceled music!");
                                }
                                return true;
                            case "dungeon_drama":
                                played = Music.playMusic(sbPlayer, Music.DUNGEON_DRAMA);
                                break;
                            case "the_watcher":
                                played = Music.playMusic(sbPlayer, Music.THE_WATCHER);
                                break;
                            case "sky_of_trees":
                                played = Music.playMusic(sbPlayer, Music.SKY_OF_TREES);
                                break;
                            case "breathless_encounter":
                                played = Music.playMusic(sbPlayer, Music.BREATHLESS_ENCOUNTER);
                                break;
                            case "blastin_banter_battle":
                                played = Music.playMusic(sbPlayer, Music.BLASTIN_BANTER_BATTLE);
                                break;
                            case "mythic_warfare":
                                played = Music.playMusic(sbPlayer, Music.MYTHIC_WARFARE);
                                break;
                        }

                        if(!played) {
                            sbPlayer.sendMessage("&cFailed to play '" + args[1] + "'.");
                        } else {
                            sbPlayer.sendMessage("&aPlaying song: '" + args[1] + "'!");
                        }
                        return true;
                    case "playall":
                        sbPlayer.sendMessage("&eAttempting to play '" + args[1] + "'...");

                        boolean played2;
                        switch(args[1]) {
                            default:
                                sbPlayer.sendMessage("&cInvalid argument! The music id is invalid.");
                                return true;
                            case "stop":
                                sbPlayer.sendMessage("&eAttempting to cancel music...");
                                if(Music.cancelMusic(sbPlayer)) {
                                    sbPlayer.sendMessage("&aSuccessfully canceled music!");
                                }
                                return true;
                            case "dungeon_drama":
                                played2 = Music.playMusicAll(Music.DUNGEON_DRAMA);
                                break;
                            case "the_watcher":
                                played2 = Music.playMusicAll(Music.THE_WATCHER);
                                break;
                            case "sky_of_trees":
                                played2 = Music.playMusicAll(Music.SKY_OF_TREES);
                                break;
                            case "breathless_encounter":
                                played2 = Music.playMusicAll(Music.BREATHLESS_ENCOUNTER);
                                break;
                            case "blastin_banter_battle":
                                played2 = Music.playMusicAll(Music.BLASTIN_BANTER_BATTLE);
                                break;
                            case "mythic_warfare":
                                played2 = Music.playMusicAll(Music.MYTHIC_WARFARE);
                                break;
                        }

                        if(!played2) {
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
                                sbPlayer.sendMessage("&cInvalid argument. Usage: &e/debug toggle [damage|messages]");
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
                        }
                    case "entity":
                        switch(args[1]) {
                            default:
                                sbPlayer.sendMessage("&cInvalid argument. Usage: &e/debug entity [reset]");
                                return true;
                            case "reset":
                                sbPlayer.sendMessage("&eAttempting to reset all entities...");
                                for(Entity entity : sbPlayer.getBukkitPlayer().getNearbyEntities(10, 10, 10)) {
                                    if(SkyblockSandbox.getManagement().getEntityManager().getEntity(entity) == null) return true;
                                    SkyblockEntity sbEntity = SkyblockSandbox.getManagement().getEntityManager().getEntity(entity);

                                    sbEntity.setHealth(sbEntity.getEntityData().health);
                                }
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
                        }
                }
                return true;
        }

        return true;
    }
}
