package tk.skyblocksandbox.skyblocksandbox.module;

import org.bukkit.Bukkit;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

public final class ModuleLogger {

    private final SandboxModule module;

    public ModuleLogger(SandboxModule module) {
        this.module = module;
    }

    public void info(String message) {
        Bukkit.getLogger().info("[" + module.getModuleName() + "] " + Utility.colorize(message));
    }

    public void warning(String message) {
        Bukkit.getLogger().warning("[" + module.getModuleName() + "] " + Utility.colorize(message));
    }

    public void severe(String message) {
        Bukkit.getLogger().severe("[" + module.getModuleName() + "] " + Utility.colorize(message));
    }

}