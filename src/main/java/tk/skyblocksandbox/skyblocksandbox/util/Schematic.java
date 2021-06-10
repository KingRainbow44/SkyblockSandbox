package tk.skyblocksandbox.skyblocksandbox.util;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.PasteBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class Schematic {

    public static boolean pasteSchematic(Location location, String schematicName, boolean enableLogging) {
        String schematicFinal = SkyblockSandbox.getInstance().getDataFolder().getAbsolutePath() + "/schematics/" + schematicName + ".schem";
        File schematicFile = new File(schematicFinal);

        if(!schematicFile.exists()) {
            Bukkit.getLogger().warning("Unable to paste schematic " + schematicName + ".schem: Cannot find file.");
            return false;
        }

        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
        if(format == null) {
            Bukkit.getLogger().warning("Unable to paste schematic " + schematicName + ".schem: Cannot find file/File is null/Unable to read schematic.");
            return false;
        }

        try {
            ClipboardReader reader = format.getReader(new FileInputStream(schematicFile));
            Clipboard clipboard = reader.read();

            com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(location.getWorld());

            final Operation[] operation = new Operation[1];
            final EditSession[] editSession = new EditSession[1];
            new Thread(() -> {
                editSession[0] = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1);
                operation[0] = new ClipboardHolder(clipboard).createPaste(editSession[0]).to(BlockVector3.at(location.getX(), location.getY(), location.getZ())).ignoreAirBlocks(true).build();

                Operations.complete(operation[0]);
                editSession[0].flushSession();
            }).start();
        } catch (IOException | WorldEditException e) {
            Bukkit.getLogger().warning("Unable to paste schematic " + schematicName + ".schem: Exception caught. Check below for stack-trace.");
            e.printStackTrace();
            return false;
        }

        if(enableLogging) Bukkit.getLogger().info(schematicName + ".schem was pasted successfully!");
        return true;
    }

    public static boolean pasteSchematic(Location location, String schematicName, boolean enableLogging, AffineTransform rotation) {
        String schematicFinal = SkyblockSandbox.getInstance().getDataFolder().getAbsolutePath() + "/schematics/" + schematicName + ".schem";
        File schematicFile = new File(schematicFinal);

        if(!schematicFile.exists()) {
            Bukkit.getLogger().warning("Unable to paste schematic " + schematicName + ".schem: Cannot find file.");
            return false;
        }

        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
        if(format == null) {
            Bukkit.getLogger().warning("Unable to paste schematic " + schematicName + ".schem: Cannot find file/File is null/Unable to read schematic.");
            return false;
        }

        try {
            ClipboardReader reader = format.getReader(new FileInputStream(schematicFile));
            Clipboard clipboard = reader.read();

            com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(location.getWorld());

            ClipboardHolder paste = new ClipboardHolder(clipboard);
            paste.setTransform(rotation);

            final Operation[] operation = new Operation[1];
            final EditSession[] editSession = new EditSession[1];
            new Thread(() -> {
                editSession[0] = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1);
                operation[0] = new ClipboardHolder(clipboard).createPaste(editSession[0]).to(BlockVector3.at(location.getX(), location.getY(), location.getZ())).ignoreAirBlocks(true).build();
            }).start();

            Operations.complete(operation[0]);
            editSession[0].flushSession();
        } catch (IOException | WorldEditException e) {
            Bukkit.getLogger().warning("Unable to paste schematic " + schematicName + ".schem: Exception caught. Check below for stack-trace.");
            e.printStackTrace();
            return false;
        }

        if(enableLogging) Bukkit.getLogger().info(schematicName + ".schem was pasted successfully!");
        return true;
    }

}
