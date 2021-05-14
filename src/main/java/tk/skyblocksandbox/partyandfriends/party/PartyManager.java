package tk.skyblocksandbox.partyandfriends.party;

import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PartyManager {

    private final Map<UUID, PartyInstance> parties = new HashMap<>();

    public boolean createParty(SkyblockPlayer sbPlayer) {
        if(parties.getOrDefault(sbPlayer.getBukkitPlayer().getUniqueId(), null) != null) {
            return false;
        }

        PartyInstance party = new PartyInstance(sbPlayer);
        parties.put(sbPlayer.getBukkitPlayer().getUniqueId(), party);
        return true;
    }

    public void disbandParty(SkyblockPlayer sbLeader) {
        PartyInstance party = parties.get(sbLeader.getBukkitPlayer().getUniqueId());
        if(party == null) return;

        parties.remove(sbLeader.getBukkitPlayer().getUniqueId());
        for(SkyblockPlayer members : party.getMembers()) {
            members.setCurrentParty(null);
            members.sendMessages(
                    "&9&m-----------------------------",
                    "&eThe party was disbanded by " + sbLeader.getBukkitPlayer().getDisplayName(),
                    "&9&m-----------------------------"
            );
        }
    }

    public PartyInstance getPartyFromPlayer(UUID partyLeader) {
        return parties.getOrDefault(partyLeader, null);
    }

}
