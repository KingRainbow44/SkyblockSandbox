package tk.skyblocksandbox.dungeonsandbox.util;

import tk.skyblocksandbox.dungeonsandbox.dungeon.Dungeon;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.HashMap;
import java.util.Map;

public final class Generation {

    private static final Map<AvailableRooms, RoomGenerationTypes> roomSizes = new HashMap<>();

    public Generation() {
        roomSizes.put(AvailableRooms.ENTRANCE, RoomGenerationTypes.ONE_BY_ONE);

        roomSizes.put(AvailableRooms.FLAGS_7, RoomGenerationTypes.TWO_BY_TWO);
    }

    public enum FloorGenerationTypes {
        FLOOR_1,
        FLOOR_2,
        FLOOR_3,
        FLOOR_4,
        FLOOR_5,
        FLOOR_6,
        FLOOR_7,
        FLOOR_8,
        FLOOR_9,
        FLOOR_10
    }

    public enum RoomGenerationTypes {
        ONE_BY_ONE,
        ONE_BY_TWO,
        ONE_BY_THREE,

        TWO_BY_ONE,
        TWO_BY_TWO,

        THREE_BY_ONE
    }

    public enum AvailableRooms { // {ROOM_NAME}_{SECRET_COUNT}
        ENTRANCE,

        FLAGS_7
    }

    /**
     * Generates a random dungeon room.
     * @param dungeon The dungeon the generator will base the generated room on.
     * @return The room type that was genereated
     */
    public AvailableRooms generateRandomRoom(Dungeon dungeon) {
        AvailableRooms room = randRoom();
        while(!validation(room, dungeon)) {
            room = randRoom();
        }

        return room;
    }

    private AvailableRooms randRoom() {
        switch(Utility.generateRandomNumber(1, 2)) {
            default:
            case 1:
                return AvailableRooms.ENTRANCE;
            case 2:
                return AvailableRooms.FLAGS_7;
        }
    }

    private String enumToSchematic(AvailableRooms room) {
        switch(room) {
            case ENTRANCE:
                return "entrance";
            case FLAGS_7:
                return "flags_7";
        }

        return null;
    }

    private boolean validation(AvailableRooms room, Dungeon dungeon) {
        RoomGenerationTypes lastRoom = dungeon.getLastRoomGenerated();
        if(roomSizes.getOrDefault(room, RoomGenerationTypes.ONE_BY_ONE) == lastRoom) return false;

        return true;
    }

}
