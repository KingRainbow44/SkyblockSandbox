package tk.skyblocksandbox.permitable.rank;

import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.ArrayList;
import java.util.Collection;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class PermitableRank {

    private final String rankName;
    private final String chatFormat;
    private final String nameFormat;
    private final Collection<String> permissions;

    public PermitableRank(String rankName, String chatFormat, String nameFormat, Collection<String> permissions) {
        this.rankName = rankName;
        this.chatFormat = chatFormat;
        this.nameFormat = nameFormat;
        this.permissions = permissions;
    }

    public PermitableRank(String rankName, String chatFormat, String nameFormat, Collection<String> permissions, AvailableRanks inheritFrom) {
        this.rankName = rankName;
        this.chatFormat = chatFormat;
        this.nameFormat = nameFormat;
        this.permissions = permissions;

        permissions.addAll(getRankByEnum(inheritFrom).getPermissions());
    }

    public String getRankName() {
        return rankName;
    }

    public String getRankChatFormat() {
        return colorize(chatFormat);
    }

    public String getRankNameTagFormat() {
        return colorize(nameFormat);
    }

    public static String formatNameTag(String format, Player player) {
        return format.replaceAll("PLAYER", player.getDisplayName());
    }

    public static String formatNameTag(String format, SkyblockPlayer player) {
        return format.replaceAll("PLAYER", player.getBukkitPlayer().getDisplayName());
    }

    public Collection<String> getPermissions() {
        return permissions;
    }

    public static AvailableRanks getRankByString(String rankName) {
        switch(Utility.changeCase(rankName, true)) {
            default:
            case "DEFAULT":
                return AvailableRanks.DEFAULT;
            case "VIP":
                return AvailableRanks.VIP;
            case "VIP+":
            case "VIP_PLUS":
                return AvailableRanks.VIP_PLUS;
            case "MVP":
                return AvailableRanks.MVP;
            case "MVP+":
            case "MVP_PLUS":
                return AvailableRanks.MVP_PLUS;
            case "MVP++":
            case "MVP_PLUS_PLUS":
                return AvailableRanks.MVP_PLUS_PLUS;
            case "HELPER":
                return AvailableRanks.HELPER;
            case "MODERATOR":
                return AvailableRanks.MODERATOR;
            case "ADMIN":
                return AvailableRanks.ADMIN;
            case "OWNER":
                return AvailableRanks.OWNER;
        }
    }

    public static PermitableRank getRankByEnum(AvailableRanks rank) {
        Collection<String> permissions = new ArrayList<>();
        switch(rank) {
            default:
            case DEFAULT:
                permissions.add("skyblocksandbox.command.item");
                return new PermitableRank(
                        "Default",
                        "&7%1$s: %2$s",
                        "PLAYER",
                        permissions
                );
            case VIP:
                permissions.add("skyblocksandbox.command.item");
                permissions.add("dungeonssandbox.command.joindungeon");
                return new PermitableRank(
                        "VIP",
                        "&a[VIP] %1$s&f: %2$s",
                        "&a[VIP] PLAYER",
                        permissions, AvailableRanks.DEFAULT
                );
            case VIP_PLUS:
                permissions.add("skyblocksandbox.command.item");
                permissions.add("dungeonssandbox.command.joindungeon");
                return new PermitableRank(
                        "VIP+",
                        "&a[VIP&6+&a] %1$s&f: %2$s",
                        "&a[VIP&6+&a] PLAYER",
                        permissions, AvailableRanks.VIP
                );
            case MVP:
                permissions.add("skyblocksandbox.command.item");
                permissions.add("dungeonssandbox.command.joindungeon");
                return new PermitableRank(
                        "MVP",
                        "&b[MVP] %1$s&f: %2$s",
                        "&b[MVP] PLAYER",
                        permissions, AvailableRanks.VIP_PLUS
                );
            case MVP_PLUS:
                permissions.add("skyblocksandbox.command.item");
                permissions.add("dungeonssandbox.command.joindungeon");
                return new PermitableRank(
                        "MVP+",
                        "&b[MVP&c+&b] %1$s&f: %2$s",
                        "&b[MVP&c+&b] PLAYER",
                        permissions, AvailableRanks.MVP
                );
            case GOD:
                return new PermitableRank(
                        "MVP+",
                        "&e[GOD] %1$s&f: %2$s",
                        "&e[GOD] PLAYER",
                        permissions, AvailableRanks.MVP_PLUS
                );
            case MVP_PLUS_PLUS:
                permissions.add("skyblocksandbox.command.item");
                permissions.add("dungeonssandbox.command.joindungeon");
                return new PermitableRank(
                        "MVP++",
                        "&6[MVP&c++&6] %1$s&f: %2$s",
                        "&b[MVP&c++&6] PLAYER",
                        permissions, AvailableRanks.MVP_PLUS
                );
            case YOUTUBE:
                return new PermitableRank(
                        "YouTube",
                        "&c[&fYOUTUBE&c] %1$s&f: %2$s",
                        "&c[&fYOUTUBE&c] PLAYER",
                        permissions, AvailableRanks.MVP_PLUS_PLUS
                );
            case HELPER:
                return new PermitableRank(
                        "Helper",
                        "&9[HELPER] %1$s&f: %2$s",
                        "&9[HELPER] PLAYER",
                        permissions, AvailableRanks.MVP_PLUS_PLUS
                );
            case MODERATOR:
                return new PermitableRank(
                        "Moderator",
                        "&2[MOD] %1$s&f: %2$s",
                        "&2[MOD] PLAYER",
                        permissions, AvailableRanks.HELPER
                );
            case ADMIN:
                permissions.add("dungeonssandbox.command.joindungeon");
                permissions.add("skyblocksandbox.command.item");
                permissions.add("skyblocksandbox.command.debug");
                permissions.add("skyblocksandbox.command.setblock");
                permissions.add("skyblocksandbox.command.summon");
                permissions.add("sandbox.build");
                return new PermitableRank(
                        "Admin",
                        "&c[ADMIN] %1$s&f: %2$s",
                        "&c[ADMIN] PLAYER",
                        permissions
                );
            case OWNER:
                return new PermitableRank(
                        "Owner",
                        "&c[OWNER] %1$s&f: %2$s",
                        "&c[OWNER] PLAYER",
                        permissions
                );
        }
    }

    public enum AvailableRanks {
        DEFAULT,

        VIP,
        VIP_PLUS,

        MVP,
        MVP_PLUS,
        GOD,
        MVP_PLUS_PLUS,

        YOUTUBE,

        HELPER,
        MODERATOR,

        ADMIN,
        OWNER
    }

}
