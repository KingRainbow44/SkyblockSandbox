package com.kingrainbow44.customplayer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomPlayerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        sendNotice();
    }

    @Override
    public void onDisable() {
        sendNotice();
    }

    private void sendNotice() {
        saveDefaultConfig();

        FileConfiguration config = getConfig();
        if(!config.getBoolean("dismissWarning")) {
            getLogger().warning("It looks like this is your first time loading CustomPlayer. (or you haven't disabled the warning)");
            getLogger().warning("CustomPlayer is a Bukkit API and should be shaded in your plugin jar.");
            getLogger().warning("CustomPlayer should NOT be run as a plugin and used as a dependency at the same time.");
            getLogger().warning("If this is being used for development purposes, set 'dismissWarning' to 'true' in the plugin configuration.");
        }
    }
}
