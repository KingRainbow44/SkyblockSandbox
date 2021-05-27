package tk.skyblocksandbox.dungeonsandbox;

import org.bukkit.Bukkit;
import tk.skyblocksandbox.dungeonsandbox.command.JoinDungeonCommand;
import tk.skyblocksandbox.dungeonsandbox.dungeon.DungeonManager;
import tk.skyblocksandbox.skyblocksandbox.module.SandboxModule;

import java.io.File;
import java.nio.channels.FileLockInterruptionException;
import java.util.Objects;

public final class DungeonsModule extends SandboxModule {

    private static DungeonsModule instance;
    private static DungeonManager dungeonManager;

    public DungeonsModule() {
        super("DungeonsModule", LOAD_ON_PLUGIN, 0.2);
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        dungeonManager = new DungeonManager();

        registerCommand(new JoinDungeonCommand());

        clearExistingDungeons();

        getLogger().info("Enabled DungeonsSandbox.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled DungeonsSandbox.");
    }

    private void clearExistingDungeons() {
        for (File file : Objects.requireNonNull(Bukkit.getServer().getWorldContainer().listFiles())){
            if(file.getName().contains("dungeon_") && !file.getName().contains("hub") && file.isDirectory()) {
                if(!file.delete()) {
                    throw new NullPointerException("Unable to destroy the dungeon: " + file.getName() + ". Delete manually and restart the server.");
                }
            }
        }
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