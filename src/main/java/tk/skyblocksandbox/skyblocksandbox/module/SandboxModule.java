package tk.skyblocksandbox.skyblocksandbox.module;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.event.Listener;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;

public abstract class SandboxModule {

    private final String moduleName;
    private final int load;
    private final double versionNumber;

    private final ModuleLogger logger;

    public static final int LOAD_ON_PLUGIN = 0;
    public static final int LOAD_ON_ENABLE = 1;
    public static final int LOAD_ON_DATABASE = 2;

    public SandboxModule(String moduleName, int load, double versionNumber) {
        this.moduleName = moduleName;
        this.load = load;
        this.versionNumber = versionNumber;

        logger = new ModuleLogger(this);
    }

    public final String getModuleName() {
        return moduleName;
    }

    public final int getLoadType() {
        return load;
    }

    public final double getVersionNumber() {
        return versionNumber;
    }

    public final ModuleLogger getLogger() {
        return logger;
    }

    public final void registerCommand(SkyblockCommand command) {
        SkyblockSandbox.getInstance().registerCommand(command);
    }

    public final void registerListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, SkyblockSandbox.getInstance());
    }

    /*
     * Abstract functions
     */

    /**
     * Called after the load event & plugin enable.
     */
    public void onEnable() {}

    /**
     * Called when the plugin disables.
     */
    public void onDisable() {}

    /**
     * Called when the "load" event happens.
     */
    public void onLoad() {}

}