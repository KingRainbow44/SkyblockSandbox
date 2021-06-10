package tk.skyblocksandbox.skyblocksandbox.util.sign;

import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import java.util.Arrays;

public abstract class BasicSign {

    private final SkyblockPlayer sbPlayer;
    private String[] lines = null;

    /**
     * The constructor to create a basic sign. No text, no nothing.
     * @param sbPlayer The player to send the sign to.
     */
    public BasicSign(SkyblockPlayer sbPlayer) {
        this.sbPlayer = sbPlayer;
    }

    /**
     * The constructor to create a basic sign with preset lines.
     * @param sbPlayer The player to send the sign to.
     * @param lines The lines to put on a sign. Use 'null' to create a blank line. Only reads 0-3 on the array.
     */
    public BasicSign(SkyblockPlayer sbPlayer, String... lines) {
        this.sbPlayer = sbPlayer;

        this.lines = new String[4];
        for(int i = 0; i <= 3; i++) {
            if(lines[i] == null) {
                this.lines[i] = "";
            } else {
                this.lines[i] = lines[i];
            }
        }
    }

    /**
     * Constructs and sends the sign to the specified player.
     */
    public final void build() {
        SignMenuFactory factory = SkyblockSandbox.getManagement().getSignManager();
        SignMenuFactory.Menu menu = factory.newMenu(Arrays.asList(lines))
                .reopenIfFail(true)
                .response(((player, strings) -> {
                    run(strings);

                    return true;
                }));

        menu.open(sbPlayer.getBukkitPlayer());
    }

    /**
     * Called when the player closes the UI.
     */
    public abstract void run(String[] lines);

}
