package tk.skyblocksandbox.dungeonsandbox;

import tk.skyblocksandbox.dungeonsandbox.command.JoinDungeonCommand;
import tk.skyblocksandbox.dungeonsandbox.dungeon.DungeonManager;
import tk.skyblocksandbox.skyblocksandbox.module.SandboxModule;

public final class DungeonsModule extends SandboxModule {

    private static DungeonsModule instance;
    private static DungeonManager dungeonManager;

    public DungeonsModule() {
        super("DungeonsModule", LOAD_ON_PLUGIN, 0.1);
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        dungeonManager = new DungeonManager();

        registerCommand(new JoinDungeonCommand());

        getLogger().info("Enabled DungeonsSandbox.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled DungeonsSandbox.");
    }

    /*
     * Static Methods
     */

    public static DungeonsModule getInstance() {
        return instance;
    }

    public static DungeonManager getDungeonManager() {
        return dungeonManager;
    }

}