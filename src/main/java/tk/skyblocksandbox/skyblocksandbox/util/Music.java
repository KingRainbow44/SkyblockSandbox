package tk.skyblocksandbox.skyblocksandbox.util;

import com.kingrainbow44.customplayer.player.ICustomPlayer;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Music {

    private static final Map<String, RadioSongPlayer> songs = new HashMap<>();

    public static final String DUNGEON_DRAMA = "DUNGEON_DRAMA";
    public static final String THE_WATCHER = "THE_WATCHER";
    public static final String SKY_OF_TREES = "SKY_OF_TREES";
    public static final String BREATHLESS_ENCOUNTER = "BREATHLESS_ENCOUNTER";
    public static final String BLASTIN_BANTER_BATTLE = "BLASTIN_BANTER_BATTLE";
    public static final String MYTHIC_WARFARE = "MYTHIC_WARFARE";

    public static void initRSP(String songId, Song song) {
        RadioSongPlayer rsp = new RadioSongPlayer(song);

        rsp.setRepeatMode(
                RepeatMode.ALL
        );
        rsp.setPlaying(true);

        songs.put(songId, rsp);
    }

    public static Song getNbsByName(String nbs) {
        return NBSDecoder.parse(new File(SkyblockSandbox.getInstance().getDataFolder() + "/nbs/" + Utility.changeCase(nbs, false) + ".nbs"));
    }

    public static boolean playMusic(SkyblockPlayer player, String nbs) {
        Song song;
        String songId;
        switch(nbs) {
            default:
                return false;
            case DUNGEON_DRAMA:
                songId = "dungeon_drama";
                song = getNbsByName(songId);
                break;
            case THE_WATCHER:
                songId = "the_watcher";
                song = getNbsByName(songId);
                break;
            case SKY_OF_TREES:
                songId = "sky_of_trees";
                song = getNbsByName(songId);
                break;
            case BREATHLESS_ENCOUNTER:
                songId = "breathless_encounter";
                song = getNbsByName(songId);
                break;
            case BLASTIN_BANTER_BATTLE:
                songId = "bastin_banter_battle";
                song = getNbsByName(songId);
                break;
            case MYTHIC_WARFARE:
                songId = "mythic_warfare";
                song = getNbsByName(songId);
                break;
        }

        if(song == null) return false;

        if(songs.getOrDefault(songId, null) == null) {
            initRSP(songId, song);
        }

        RadioSongPlayer rsp = songs.getOrDefault(songId, null);
        if(rsp == null) return false;

        if(!player.getPlayerData().playingSong.equals("none")) {
            cancelMusic(player);
        }

        rsp.addPlayer(player.getBukkitPlayer());
        player.getPlayerData().playingSong = songId;

        for(UUID uuid : rsp.getPlayerUUIDs()) {
            if(uuid.equals(player.getBukkitPlayer().getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public static boolean playMusicAll(String nbs) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            ICustomPlayer customPlayer = SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);
            if(!(customPlayer instanceof SkyblockPlayer)) return false;

            SkyblockPlayer sbPlayer = (SkyblockPlayer) customPlayer;
            playMusic(sbPlayer, nbs);
        }

        return true;
    }

    public static boolean cancelMusic(String songId) {
        RadioSongPlayer rsp = songs.getOrDefault(songId, null);
        if(rsp == null) {
            Bukkit.getLogger().warning("Unable to cancel song " + songId + ", no RSP is registered with " + songId + ".");
            return false;
        }

        for(UUID uuid : rsp.getPlayerUUIDs()) {
            rsp.removePlayer(uuid);
        }

        rsp.destroy();

        songs.remove(songId);
        Bukkit.getLogger().info("Nuked RSP " + songId + "!");

        return true;
    }

    public static boolean cancelMusic(SkyblockPlayer player) {
        String songId = player.getPlayerData().playingSong;
        if(songId.matches("none")) {
            player.sendMessage("&cYour not listening to any music!");
            return false;
        }

        RadioSongPlayer rsp = songs.getOrDefault(songId, null);
        if(rsp == null) {
            player.sendMessage("&cUnable to cancel music. Not listening to one or RSP is null.");
            return false;
        }

        rsp.removePlayer(player.getBukkitPlayer());
        player.getPlayerData().playingSong = "none";

        return true;
    }

}
