package tk.skyblocksandbox.permitable;

import tk.skyblocksandbox.permitable.command.RankCommand;
import tk.skyblocksandbox.permitable.listener.ChatListener;
import tk.skyblocksandbox.skyblocksandbox.module.SandboxModule;

public final class PermissionModule extends SandboxModule {

    private static PermissionManager permissionManager;

    public PermissionModule() {
        super("Permitable", LOAD_ON_PLUGIN, 0.1);
    }

    @Override
    public void onEnable() {
        permissionManager = new PermissionManager();

        registerListener(new ChatListener());

        registerCommand(new RankCommand());

        getLogger().info("Enabled Permitable.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled Permitable.");
    }

    /*
     * Static Methods
     */

    public static PermissionManager getPermissionManager() {
        return permissionManager;
    }
}
