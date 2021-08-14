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
        if(!new File(SkyblockSandbox.getInstance().getDataFolder() + "/nbs/" + Utility.changeCase(nbs, false) + ".nbs").exists()) return false;

        Song song = getNbsByName(nbs);

        if(song == null) return false;

        if(songs.getOrDefault(nbs, null) == null) {
            initRSP(nbs, song);
        }

        RadioSongPlayer rsp = songs.getOrDefault(nbs, null);
        if(rsp == null) return false;

        if(!player.getPlayerData().playingSong.equals("none")) {
            cancelMusic(player);
        }

        rsp.addPlayer(player.getBukkitPlayer());
        player.getPlayerData().playingSong = nbs;

        for(UUID uuid : rsp.getPlayerUUIDs()) {
            if(uuid.equals(player.getBukkitPlayer().getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public static boolean playMusicAll(String nbs) {
        if(!new File(SkyblockSandbox.getInstance().getDataFolder() + "/nbs/" + Utility.changeCase(nbs, false) + ".nbs").exists()) return false;

        for(Player player : Bukkit.getOnlinePlayers()) {
            ICustomPlayer customPlayer = SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);
            if(!(customPlayer instanceof SkyblockPlayer)) return false;

            SkyblockPlayer sbPlayer = (SkyblockPlayer) customPlayer;
            if(!playMusic(sbPlayer, nbs)) return false;
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
