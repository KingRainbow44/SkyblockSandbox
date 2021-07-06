package tk.skyblocksandbox.dungeonsandbox.dungeon;

import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.permitable.rank.PermitableRank;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.HashMap;
import java.util.Map;

public final class DungeonManager {

    private final Map<PartyInstance, DungeonInstance> dungeons = new HashMap<>();

    public void createNewDungeon(Dungeon.Types dungeonType, PartyInstance party) {
        DungeonInstance dungeonInstance = new DungeonInstance(dungeonType, party);
        dungeons.put(party, dungeonInstance);

        try {
            party.sendMessages(
                    "&9&m-----------------------------",
                    PermitableRank.formatNameTag(Utility.getRankOfPlayer(party.getLeader()).getRankNameTagFormat(), party.getLeader()) + " &eentered &r" + dungeonInstance.getDungeonFriendlyName() + "&e, Floor " + Utility.toRomanNumeral(dungeonInstance.getDungeonFloor()) + "!",
                    "&9&m-----------------------------"
            );
        } catch (Exception e) {
            party.sendMessages(
                    "&9&m-----------------------------",
                    PermitableRank.formatNameTag(Utility.getRankOfPlayer(party.getLeader()).getRankNameTagFormat(), party.getLeader()) + " &eentered &r" + dungeonInstance.getDungeonFriendlyName() + "&e, Floor " + dungeonInstance.getDungeonFloor() + "!",
                    "&9&m-----------------------------"
            );
        }

        dungeonInstance.startDungeon();
    }

}
