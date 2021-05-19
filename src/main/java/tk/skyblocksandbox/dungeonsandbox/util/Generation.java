package tk.skyblocksandbox.dungeonsandbox.util;

import tk.skyblocksandbox.dungeonsandbox.dungeon.Dungeon;

public final class Generation {

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
     * @return Schematic Name, used with SkyblockSandbox#schematic();
     */
    public static String generateRandomRoom(Dungeon dungeon) {

    }

}
