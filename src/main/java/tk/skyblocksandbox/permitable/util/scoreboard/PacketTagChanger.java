package tk.skyblocksandbox.permitable.util.scoreboard;

import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_16_R3.ScoreboardTeamBase;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Collections;

public class PacketTagChanger {

    private static PacketPlayOutScoreboardTeam packet; // The packet that will be sent to the player

    /**
     * This is the main method to change the players name.
     *
     * @param toSendTo   The player that you want to send the packet
     * @param player     The player which name will be changed
     * @param prefix     The prefix that the 'player' will have
     * @param suffix     The suffix that the 'player' will have
     * @param teamAction To CREATE, UPDATE, OR DESTROY the fake team
     */
    public static void changeNameTag(Player toSendTo, Player player, String prefix, String suffix, TeamAction teamAction) {
        sendPacket(toSendTo, teamPacket(player, prefix, suffix, teamAction));
    }

    private static PacketPlayOutScoreboardTeam teamPacket(Player player, String prefix, String suffix, TeamAction teamAction) {
        packet = new PacketPlayOutScoreboardTeam();
        setField("a", player.getName());
        setField("b", player.getName());
        setField("c", Color(prefix));
        setField("d", Color(suffix));
        setField("e", ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e);

        switch (teamAction) {
            case CREATE:
                addPlayer(player);
                break;
            case UPDATE:
                setField("h", 2);
                break;
            case DESTROY:
                setField("h", 1);
        }

        return packet;
    }

    private static void addPlayer(Player player) {
        setField("g", Collections.singleton(player.getName()));
    }

    private static String Color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private static void sendPacket(Player player, PacketPlayOutScoreboardTeam packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private static void setField(String field, Object value) {
        try {
            Field f = packet.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(packet, value);
            f.setAccessible(false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}
