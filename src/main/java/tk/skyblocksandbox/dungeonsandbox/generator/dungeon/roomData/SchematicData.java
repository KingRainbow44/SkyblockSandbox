package tk.skyblocksandbox.dungeonsandbox.generator.dungeon.roomData;

import tk.skyblocksandbox.dungeonsandbox.generator.dungeon.IRoomData;

public final class SchematicData implements IRoomData {

    private final String schematicFileName;

    public SchematicData(String schematicFileName) {
        this.schematicFileName = schematicFileName;
    }

    public String getSchematic() {
        return schematicFileName;
    }

}
