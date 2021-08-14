package tk.skyblocksandbox.skyblocksandbox.scoreboard;

import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class HubScoreboard extends SkyblockScoreboard {

    public HubScoreboard(SkyblockPlayer player) {
        super("skyblock.hub", player);
    }

    public void setScoreboard() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDateTime dateTime = LocalDateTime.now();
        if(sbPlayer.getBits() > 0) {
            board.setAll(
                    Utility.colorize("&7" + dtf.format(dateTime) + " &8mini1A"),
                    Utility.colorize("&e "),
                    Utility.colorize(" &fEarly Summer 1st"),
                    Utility.colorize(" &70:00 am &e☀"),
                    Utility.colorize(" &7⏣ " + sbPlayer.getPlayerData().location.getDisplayName()),
                    Utility.colorize("&a "),
                    Utility.colorize("&fPiggy: &6" + Utility.commafy("" + sbPlayer.getCoins())),
                    Utility.colorize("&fBits: &b" + Utility.commafy("" + sbPlayer.getBits())),
                    Utility.colorize("&b "),
                    Utility.colorize("&eanother.skyblocksandbox.tk")
            );
        } else {
            board.setAll(
                    Utility.colorize("&7" + dtf.format(dateTime) + " &8mini1A"),
                    Utility.colorize("&e "),
                    Utility.colorize(" &fEarly Summer 1st"),
                    Utility.colorize(" &70:00 am &e☀"),
                    Utility.colorize(" &7⏣ " + sbPlayer.getPlayerData().location.getDisplayName()),
                    Utility.colorize("&a "),
                    Utility.colorize("&fPiggy: &6" + Utility.commafy("" + sbPlayer.getCoins())),
                    Utility.colorize("&b "),
                    Utility.colorize("&eanother.skyblocksandbox.tk")
            );
        }
    }

    public void updateScoreboard() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDateTime dateTime = LocalDateTime.now();

        if(sbPlayer.getBits() > 0) {
            if(board.getLines().size() == 9) setScoreboard();
            board.set(Utility.colorize("&fBits: &b" + Utility.commafy("" + sbPlayer.getBits())), 3);
            board.set(Utility.colorize("&fPiggy: &6" + Utility.commafy("" + sbPlayer.getCoins())), 4);

            board.set(Utility.colorize("&7" + dtf.format(dateTime) + " &8mini1A"), 10);
            board.set(Utility.colorize(" &7⏣ " + sbPlayer.getPlayerData().location.getDisplayName()), 6);
        } else {
            board.set(Utility.colorize("&fPiggy: &6" + Utility.commafy("" + sbPlayer.getCoins())), 3);
            board.set(Utility.colorize("&7" + dtf.format(dateTime) + " &8mini1A"), 9);
            board.set(Utility.colorize(" &7⏣ " + sbPlayer.getPlayerData().location.getDisplayName()), 5);
        }
    }

}
