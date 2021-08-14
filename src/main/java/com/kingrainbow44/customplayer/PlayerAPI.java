package com.kingrainbow44.customplayer;

import com.kingrainbow44.customplayer.player.CustomPlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerAPI {

    private final JavaPlugin plugin;
    private CustomPlayerManager playerManager;

    public PlayerAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Initializes the PlayerAPI. This should be called in the 'onEnable()' method in your JavaPlugin.
     * This should only be called if the PlayerAPI has a valid JavaPlugin instance.
     */
    public void initialize() {
        if(plugin == null || !plugin.isEnabled()) {
            throw new RuntimeException("Invalid/Null JavaPlugin instance in PlayerAPI.");
        }

        playerManager = new CustomPlayerManager();
    }

    public CustomPlayerManager getPlayerManager() {
        if(plugin == null || !plugin.isEnabled()) {
            throw new RuntimeException("Invalid/Null JavaPlugin instance in PlayerAPI.");
        }

        return playerManager;
    }

}
