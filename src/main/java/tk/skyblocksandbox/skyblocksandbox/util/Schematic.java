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
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class Schematic {

    public static boolean pasteSchematic(Location location, String schematicName) {
        String schematicFinal = SkyblockSandbox.getInstance().getDataFolder().getAbsolutePath() + "/schematics/" + schematicName + ".schem";
        File schematicFile = new File(schematicFinal);

        if(!schematicFile.exists()) return false;

        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
        if(format == null) return false;

        try {
            ClipboardReader reader = format.getReader(new FileInputStream(schematicFile));
            Clipboard clipboard = reader.read();

            com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(location.getWorld());

            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1);
            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(BlockVector3.at(location.getX(), location.getY(), location.getZ())).ignoreAirBlocks(true).build();

            Operations.complete(operation);
            editSession.flushSession();
        } catch (IOException | WorldEditException e) {
            if(e instanceof WorldEditException) {
                e.printStackTrace();
            }

            return false;
        }

        return true;
    }

}
