package tk.skyblocksandbox.dungeonsandbox.dungeon;

import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.HashMap;
import java.util.Map;

public final class DungeonManager {

    private final Map<PartyInstance, Dungeon> dungeons = new HashMap<>();

    public void createNewDungeon(Dungeon dungeonType, PartyInstance party) {
        dungeons.put(party, dungeonType);
        dungeonType.initializeDungeon();

        try {
            party.sendMessages(
                    "&9&m-----------------------------",
                    "&e" + party.getLeader().getName() + " entered &c" + dungeonType.getDungeonName() + "&e, Floor " + Utility.toRomanNumeral(dungeonType.getDungeonFloor()) + "!",
                    "&9&m-----------------------------"
            );
        } catch (Exception e) {
            party.sendMessages(
                    "&9&m-----------------------------",
                    "&e" + party.getLeader().getName() + " entered &c" + dungeonType.getDungeonName() + "&e, Floor " + dungeonType.getDungeonFloor() + "!",
                    "&9&m-----------------------------"
            );
        }
    }

    public void destroy(PartyInstance party, boolean forcibly) {
        Dungeon dungeon = dungeons.getOrDefault(party, null);
        if(dungeon == null) return;

        dungeon.destroy(forcibly);
    }

}
