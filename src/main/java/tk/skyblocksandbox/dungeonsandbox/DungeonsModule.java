package tk.skyblocksandbox.dungeonsandbox;

import tk.skyblocksandbox.skyblocksandbox.module.SandboxModule;

public final class DungeonsModule extends SandboxModule {

    private static DungeonsModule instance;

    public DungeonsModule() {
        super("DungeonsModule", LOAD_ON_PLUGIN, 0.1);
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
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

}