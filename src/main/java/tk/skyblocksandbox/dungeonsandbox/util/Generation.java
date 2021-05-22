package tk.skyblocksandbox.dungeonsandbox.util;

import tk.skyblocksandbox.dungeonsandbox.dungeon.Dungeon;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.HashMap;
import java.util.Map;

public final class Generation {

    private static final Map<AvailableRooms, RoomGenerationTypes> roomSizes = new HashMap<>();

    public Generation() {
        roomSizes.put(AvailableRooms.ENTRANCE, RoomGenerationTypes.ONE_BY_ONE);
        roomSizes.put(AvailableRooms.FAIRY_ROOM, RoomGenerationTypes.ONE_BY_ONE);
        roomSizes.put(AvailableRooms.BLOOD_ROOM_SOUTH, RoomGenerationTypes.ONE_BY_ONE);

        roomSizes.put(AvailableRooms.PRISON_CELL_1, RoomGenerationTypes.ONE_BY_ONE);
        roomSizes.put(AvailableRooms.BLUE_SKULLS_1, RoomGenerationTypes.ONE_BY_ONE);
        roomSizes.put(AvailableRooms.ADMIN_1, RoomGenerationTypes.ONE_BY_ONE);
        roomSizes.put(AvailableRooms.OVERGROWN_3, RoomGenerationTypes.ONE_BY_ONE);

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

    public enum AvailableDoors {
        WITHER_DOOR,
        OPEN_DOOR,
        BLOOD_DOOR
    }

    public enum AvailableRooms { // {ROOM_NAME}_{SECRET_COUNT}
        ENTRANCE,
        FAIRY_ROOM,
        BLOOD_ROOM_SOUTH,

        FLAGS_7, // 2x2

        PRISON_CELL_1, // 1x1
        BLUE_SKULLS_1, // 1x1
        ADMIN_1, // 1x1
        OVERGROWN_3, // 1x1

        MINI_BOSS_1, // Special

        TRAP_HARD_3, // Trap Room
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
        switch(Utility.generateRandomNumber(1, 4)) {
            default:
            case 1:
                return AvailableRooms.FAIRY_ROOM;
            case 2:
                return AvailableRooms.BLOOD_ROOM_SOUTH;
            case 3:
                return AvailableRooms.PRISON_CELL_1;
            case 4:
                return AvailableRooms.OVERGROWN_3;
        }
    }

    public String enumToSchematic(AvailableRooms room) {
        switch(room) {
            default:
                return Utility.changeCase(room.name(), false);
            case BLOOD_ROOM_SOUTH:
                return "blood_south";
        }
    }

    private boolean validation(AvailableRooms room, Dungeon dungeon) {
        RoomGenerationTypes lastRoom = dungeon.getLastRoomGenerated();
//        if(roomSizes.getOrDefault(room, RoomGenerationTypes.ONE_BY_ONE) == lastRoom) return false;

        if(room == AvailableRooms.FAIRY_ROOM && dungeon.fairyRoomGenerated) return false; else dungeon.fairyRoomGenerated = true;
        if(room == AvailableRooms.BLOOD_ROOM_SOUTH && dungeon.bloodRoomGenerated) return false; else dungeon.bloodRoomGenerated = true;

        return true;
    }

}
