package tk.skyblocksandbox.skyblocksandbox.module;

import org.bukkit.Bukkit;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

import java.util.HashMap;
import java.util.Map;

public final class SandboxModuleManager {

    private final Map<String, SandboxModule> modules = new HashMap<>();

    public void addModule(SandboxModule module) {
        modules.put(module.getModuleName(), module);
    }

    public void callModules(int loadType) {
        SkyblockSandbox.getInstance().getLogger().info("Loading sandbox modules...");
        for(SandboxModule module : modules.values()) {
            if(module.getLoadType() == loadType) {
                Bukkit.getLogger().info("[" + module.getModuleName() + "] Loading " + module.getModuleName() + " v" + module.getVersionNumber());
                module.onLoad();
            }
        }
    }

    public void enableModules() {
        SkyblockSandbox.getInstance().getLogger().info("Enabling sandbox modules...");
        for(SandboxModule module : modules.values()) {
            Bukkit.getLogger().info("[" + module.getModuleName() + "] Enabling " + module.getModuleName() + " v" + module.getVersionNumber());
            module.onEnable();
        }
    }

    public void disableModules() {
        SkyblockSandbox.getInstance().getLogger().info("Disabling sandbox modules...");
        for(SandboxModule module : modules.values()) {
            Bukkit.getLogger().info("[" + module.getModuleName() + "] Disabling " + module.getModuleName() + " v" + module.getVersionNumber());
            module.onDisable();
        }
    }

}