package tk.skyblocksandbox.dungeonsandbox;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.dungeonsandbox.command.JoinDungeonCommand;
import tk.skyblocksandbox.dungeonsandbox.dungeon.DungeonManager;
import tk.skyblocksandbox.skyblocksandbox.command.GenericCommand;
import tk.skyblocksandbox.skyblocksandbox.module.SandboxModule;
import tk.skyblocksandbox.skyblocksandbox.util.FileUtils;

import java.io.File;
import java.io.IOException;
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

        registerGenericCommands();
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
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new NullPointerException("Unable to delete dungeon: " + file.getName() + ".");
                }
            }
        }
    }

    private void registerGenericCommands() {
        registerCommand(new GenericCommand("ard", "Adds dungeon room data to an armor stand.", "dungeonssandbox.command.addroomdata", GenericCommand.SenderType.PLAYER_SENDER, (sender, args) -> {
            if(args.length < 4) {
                sender.sendMessage(ChatColor.RED + "Provide 4 boolean arguments. (north, south, east, west)");
                return true;
            }

            Player player = (Player) sender;

            Location location = player.getLocation();
            boolean north = Boolean.parseBoolean(args[0]);
            boolean south = Boolean.parseBoolean(args[0]);
            boolean east = Boolean.parseBoolean(args[0]);
            boolean west = Boolean.parseBoolean(args[0]);

            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            armorStand.setGravity(false); armorStand.setBasePlate(false); armorStand.setInvulnerable(true); armorStand.setInvisible(true);
            armorStand.setCustomName("Dungeon Room");

            ItemStack item = new ItemStack(Material.STICK);
            NBTItem nbt = new NBTItem(item);

            NBTCompound tag = nbt.addCompound("dungeonRoomData");

            NBTCompound generationData = tag.addCompound("generationData");
            generationData.setBoolean("allowedNorth", north);
            generationData.setBoolean("allowedSouth", south);
            generationData.setBoolean("allowedEast", east);
            generationData.setBoolean("allowedWest", west);

            armorStand.getEquipment().setItemInMainHand(item);
            player.sendMessage(ChatColor.GREEN + "Placed room identifier!");
            return true;
        }));
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