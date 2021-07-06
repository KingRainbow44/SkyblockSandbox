package tk.skyblocksandbox.dungeonsandbox.dungeon;

import tk.skyblocksandbox.dungeonsandbox.catacombs.FloorOne;
import tk.skyblocksandbox.partyandfriends.party.PartyInstance;

public final class DungeonInstance {

    private final Dungeon.Types dungeonType;
    private final PartyInstance party;

    private Dungeon dungeon;

    public DungeonInstance(Dungeon.Types dungeonType, PartyInstance party) {
        this.dungeonType = dungeonType;
        this.party = party;
    }

    public String getDungeonFriendlyName() {
        return dungeon.getFriendlyName();
    }

    public int getDungeonFloor() {
        return dungeon.getDungeonFloor();
    }

    public void startDungeon() {
        switch(dungeonType) {
            default:
            case THE_CATACOMBS_0:
                break;
            case THE_CATACOMBS_1:
                dungeon = new FloorOne();
                break;
        }

        dungeon.initializeDungeon();
        dungeon.warpParty(party);
    }

}
