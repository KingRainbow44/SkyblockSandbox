package tk.skyblocksandbox.dungeonsandbox;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.dungeonsandbox.classes.AbilityManager;
import tk.skyblocksandbox.dungeonsandbox.command.JoinDungeonCommand;
import tk.skyblocksandbox.dungeonsandbox.dungeon.DungeonManager;
import tk.skyblocksandbox.skyblocksandbox.command.GenericCommand;
import tk.skyblocksandbox.skyblocksandbox.module.SandboxModule;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileLockInterruptionException;
import java.util.Objects;

public final class DungeonsModule extends SandboxModule {

    private static DungeonsModule instance;
    private static DungeonManager dungeonManager;
    private static AbilityManager abilityManager;

    public DungeonsModule() {
        super("DungeonsModule", LOAD_ON_PLUGIN, 0.2);
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        dungeonManager = new DungeonManager(); abilityManager = new AbilityManager();

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
                    // Clean directory.
                    FileUtils.deleteDirectory(file);

                    // Delete directory.
                    if(file.exists()) {
                        World world = Bukkit.getWorld(file.getName());
                        if(world != null) {
                            if(!world.getWorldFolder().delete()) {
                                Bukkit.getLogger().info(
                                        ChatColor.GRAY + "[DEBUG] Not able to use Java.IO.File#delete() to clear " + file.getName() + "."
                                );
                            }
                        }
                    }
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
            boolean south = Boolean.parseBoolean(args[1]);
            boolean east = Boolean.parseBoolean(args[2]);
            boolean west = Boolean.parseBoolean(args[3]);

            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            armorStand.setGravity(false); armorStand.setBasePlate(false); armorStand.setInvulnerable(true); armorStand.setInvisible(true);
            armorStand.setCustomName("Dungeon Room");

            ItemStack item = new ItemStack(Material.STICK);
            NBTItem nbt = new NBTItem(item, true);

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

        registerCommand(new GenericCommand("getrd", "Read room data from a random & nearby room identifier.", "dungeonssandbox.command.addroomdata", GenericCommand.SenderType.PLAYER_SENDER, (sender, args) -> {
            Player player = (Player) sender;

            Entity entityFound = null;
            for(Entity entity : player.getNearbyEntities(3, 3, 3)) {
                if(!(entity instanceof ArmorStand)) continue;
                EntityEquipment equipment = ((ArmorStand) entity).getEquipment(); if(equipment == null) {
                    player.sendMessage(ChatColor.RED + "Armor stand found didn't have equipment.");
                    continue;
                }

                if(equipment.getItemInMainHand().getType() == Material.STICK) {
                    entityFound = entity;
                    NBTItem nbt = new NBTItem(equipment.getItemInMainHand());
                    if(!nbt.hasKey("dungeonRoomData")) {
                        player.sendMessage(ChatColor.RED + "Armor stand found didn't have room data.");
                        continue;
                    }

                    NBTCompound roomData = nbt.getCompound("dungeonRoomData");
                    NBTCompound tag = roomData.getCompound("generationData");

                    SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(player);
                    sbPlayer.sendMessages(
                            "&aRoom Identifier located at:",
                            "&eX: " + entity.getLocation().getX(),
                            "&eY: " + entity.getLocation().getY(),
                            "&eZ: " + entity.getLocation().getZ(),
                            "&aData: " + "allowedNorth: " + tag.getBoolean("allowedNorth") + " allowedSouth: " + tag.getBoolean("allowedSouth") + " allowedEast: " + tag.getBoolean("allowedEast") + " allowedWest: " + tag.getBoolean("allowedWest")
                    );
                } else {
                    player.sendMessage(ChatColor.RED + "Armor stand found didn't have a stick with room data on it.");
                }
            }

            if(entityFound == null) {
                player.sendMessage(ChatColor.RED + "Unable to find room identifier.");
            }

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

    public static AbilityManager getAbilityManager() {
        return abilityManager;
    }

}