package tk.skyblocksandbox.dungeonsandbox.generator.dungeon;

import tk.skyblocksandbox.dungeonsandbox.generator.dungeon.roomData.RoomSecret;
import tk.skyblocksandbox.dungeonsandbox.generator.dungeon.roomData.SchematicData;

public enum Rooms {

    ENTRANCE("Entrance", 1, 1, false, true, false, false,
            new SchematicData("entrance")
    ),
    FAIRY("Fairy", 1, 1, true, true, true, true,
            new SchematicData("fairy_room")
    ),
    BLOOD("Blood", 1, 1, false, true, false, false,
            new SchematicData("blood_room_south")
    );

    public String roomIdentifier;

    public int length = 1;
    public int width = 1;

    public int totalSecrets = 0;
    public boolean canGenerateNorth = false;
    public boolean canGenerateSouth = false;
    public boolean canGenerateEast = false;
    public boolean canGenerateWest = false;

    public IRoomData[] roomData;

    private boolean hasSchematicData = false;

    Rooms(String roomIdentifier, int length, int width, boolean canGenerateNorth, boolean canGenerateSouth, boolean canGenerateEast, boolean canGenerateWest, IRoomData... roomData) {
        this.length = length; this.width = width;
        this.canGenerateNorth = canGenerateNorth; this.canGenerateSouth = canGenerateSouth;
        this.canGenerateEast = canGenerateEast; this.canGenerateWest = canGenerateWest;
        this.roomData = roomData;

        this.roomIdentifier = roomIdentifier;
        validateRoomData();
    }

    private void validateRoomData() {
        for(IRoomData rd : roomData) {
            if(hasSchematicData && rd instanceof SchematicData) throw new RuntimeException("Room " + roomIdentifier + " contained multiple instances of SchematicData.");

            if(rd instanceof SchematicData) hasSchematicData = true;
            if(rd instanceof RoomSecret) totalSecrets++;
        }

        if(!hasSchematicData) throw new NullPointerException("Room " + roomIdentifier + " did not contain schematic data.");
    }

    public String getSchematic() {
       for(IRoomData rd : roomData) {
           if(rd instanceof SchematicData) {
               return ((SchematicData) rd).getSchematic();
           }
       }

       throw new NullPointerException("Room " + roomIdentifier + " did not contain schematic data.");
    }

}
