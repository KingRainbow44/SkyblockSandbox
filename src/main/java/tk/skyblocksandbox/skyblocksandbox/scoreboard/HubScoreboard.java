package tk.skyblocksandbox.skyblocksandbox.scoreboard;

import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

public class HubScoreboard extends SkyblockScoreboard {

    public HubScoreboard(SkyblockPlayer player) {
        super("skyblock.hub", player);
    }

    public void setScoreboard() {
        if(sbPlayer.getBits() > 0) {
            board.setAll(
                    Utility.colorize("&73/1/21 &8mini0A"),
                    Utility.colorize("&e "),
                    Utility.colorize(" &fEarly Summer 0th"),
                    Utility.colorize(" &70:00 am &e☀"),
                    Utility.colorize(" &7⏣ " + sbPlayer.getLocation(true)),
                    Utility.colorize("&a "),
                    Utility.colorize("&fPiggy: &6" + Utility.commafy("" + sbPlayer.getCoins())),
                    Utility.colorize("&fBits: &b" + Utility.commafy("" + sbPlayer.getBits())),
                    Utility.colorize("&b "),
                    Utility.colorize("&eanother.skyblocksandbox.tk")
            );
        } else {
            board.setAll(
                    Utility.colorize("&73/1/21 &8mini0A"),
                    Utility.colorize("&e "),
                    Utility.colorize(" &fEarly Summer 0th"),
                    Utility.colorize(" &70:00 am &e☀"),
                    Utility.colorize(" &7⏣ " + sbPlayer.getLocation(true)),
                    Utility.colorize("&a "),
                    Utility.colorize("&fPiggy: &6" + Utility.commafy("" + sbPlayer.getCoins())),
                    Utility.colorize("&b "),
                    Utility.colorize("&eanother.skyblocksandbox.tk")
            );
        }
    }

    public void updateScoreboard() {
        board.set(Utility.colorize("&fPiggy: &6" + Utility.commafy("" + sbPlayer.getCoins())), 4);
        if(sbPlayer.getBits() > 0) {
            if(board.getLines().size() == 9) setScoreboard();
            board.set(Utility.colorize("&fBits: &b" + Utility.commafy("" + sbPlayer.getBits())), 3);
        }
    }

}
