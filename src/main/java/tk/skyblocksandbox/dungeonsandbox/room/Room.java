package tk.skyblocksandbox.dungeonsandbox.room;

import tk.skyblocksandbox.dungeonsandbox.util.Generation;

import java.util.HashMap;
import java.util.Map;

public enum Room {

    ENTRANCE(Generation.AvailableRooms.ENTRANCE, "entrance", false, true, false, false),
    FAIRY_ROOM(Generation.AvailableRooms.FAIRY_ROOM, "fairy_room", true, true, true, true),
    BLOOD_ROOM(Generation.AvailableRooms.BLOOD_ROOM_SOUTH, "blood_south", true, true, true, true),

    PRISON_CELL_1(Generation.AvailableRooms.PRISON_CELL_1, "prison_cell_1", true, true, true, true),
    BLUE_SKULLS_1(Generation.AvailableRooms.BLUE_SKULLS_1, "blue_skulls_1", true, true, true, true),
    ADMIN_1(Generation.AvailableRooms.ADMIN_1, "admin_1", true, true, true, true),
    OVERGROWN_3(Generation.AvailableRooms.OVERGROWN_3, "overgrown_3", true, true, true, true),

    FLAGS_7(Generation.AvailableRooms.FAIRY_ROOM, "fairy_room", true, true, true, true);

//    roomSizes.put(Generation.AvailableRooms.PRISON_CELL_1, Generation.RoomGenerationTypes.ONE_BY_ONE);
//        roomSizes.put(Generation.AvailableRooms.BLUE_SKULLS_1, Generation.RoomGenerationTypes.ONE_BY_ONE);
//        roomSizes.put(Generation.AvailableRooms.ADMIN_1, Generation.RoomGenerationTypes.ONE_BY_ONE);
//        roomSizes.put(Generation.AvailableRooms.OVERGROWN_3, Generation.RoomGenerationTypes.ONE_BY_ONE);
//
//        roomSizes.put(Generation.AvailableRooms.FLAGS_7, Generation.RoomGenerationTypes.TWO_BY_TWO);

    private final Generation.AvailableRooms room;
    private final String schematicFileName;

    private final boolean allowedNorth;
    private final boolean allowedSouth;
    private final boolean allowedEast;
    private final boolean allowedWest;

    Room(Generation.AvailableRooms roomEnum, String schematicName, boolean allowedNorth, boolean allowedSouth, boolean allowedEast, boolean allowedWest) {
        room = roomEnum;
        schematicFileName = schematicName;

        this.allowedNorth = allowedNorth;
        this.allowedSouth = allowedSouth;
        this.allowedEast = allowedEast;
        this.allowedWest = allowedWest;
    }

    /**
     * @return The directions of which a room's doors can exist in. Use 'north' 'south' 'east' 'west' to get the boolean values.
     */
    public Map<String, Boolean> getAllowedDirections() {
        Map<String, Boolean> directions = new HashMap<>();
        directions.put("north", allowedNorth);
        directions.put("south", allowedSouth);
        directions.put("east", allowedEast);
        directions.put("west", allowedWest);

        return directions;
    }

    /**
     * @return The room enum from AvailableRooms.
     */
    public Generation.AvailableRooms getInternalRoom() {
        return room;
    }

    /**
     * @return The file name used for the schematic.
     */
    public String getFileName() {
        return schematicFileName;
    }

}
