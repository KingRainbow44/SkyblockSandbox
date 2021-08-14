package tk.skyblocksandbox.dungeonsandbox.util;

import org.bukkit.World;
import tk.skyblocksandbox.dungeonsandbox.dungeon.Dungeon;
import tk.skyblocksandbox.dungeonsandbox.generator.IGenerator;
import tk.skyblocksandbox.dungeonsandbox.generator.dungeon.Rooms;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.Collection;

public final class GenerationUtil {

    @Deprecated // For now. Might change later.
    public static void generateIn(World world, IGenerator generator) {
        generator.onGenerate(world);
    }

    public static Rooms random(Dungeon dungeon, Collection<Rooms> excludeFromGeneration) {
        int roomCount = Rooms.values().length - 1;
        int roomIndex = Utility.generateRandomNumber(0, roomCount);

        Rooms room = Rooms.values()[roomIndex];
        if(excludeFromGeneration.contains(room)) return GenerationUtil.random(dungeon, excludeFromGeneration);

        if(dungeon.roomsBeforeBlood == 0 && !dungeon.bloodRoomGenerated) {
            room = Rooms.BLOOD;
        }

        if(dungeon.roomsBeforeFairy == 0 && !dungeon.fairyRoomGenerated) {
            room = Rooms.FAIRY;
        }

        if(dungeon.roomLayout.get(room) != null) return GenerationUtil.random(dungeon, excludeFromGeneration);
        if(dungeon.fairyRoomGenerated && room == Rooms.FAIRY) return GenerationUtil.random(dungeon, excludeFromGeneration);
        if(dungeon.bloodRoomGenerated && room == Rooms.BLOOD) return GenerationUtil.random(dungeon, excludeFromGeneration);

        if(!dungeon.fairyRoomGenerated && dungeon.roomsBeforeFairy > 0) dungeon.roomsBeforeFairy--;
        if(!dungeon.bloodRoomGenerated && dungeon.roomsBeforeBlood > 0) dungeon.roomsBeforeBlood--;

        if(room == Rooms.FAIRY) dungeon.fairyRoomGenerated = true;
        if(room == Rooms.BLOOD) dungeon.bloodRoomGenerated = true;

        return room;
    }

}
