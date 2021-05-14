package tk.skyblocksandbox.partyandfriends.party;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import java.util.*;

public final class PartyInstance {

    private final SkyblockPlayer leader;
    private final List<SkyblockPlayer> partyMembers = new ArrayList<>();
    private final List<SkyblockPlayer> invitedPlayers = new ArrayList<>();

    private final Map<SkyblockPlayer, Integer> permissions = new HashMap<>();

    public PartyInstance(SkyblockPlayer sbLeader) {
        leader = sbLeader;
        partyMembers.add(sbLeader);
        permissions.put(sbLeader, 2);

        sbLeader.setCurrentParty(this);
    }

    public void dispatchInvite(SkyblockPlayer inviter, SkyblockPlayer toInvite) {
        if(inParty(toInvite)) {
            inviter.sendMessages(
                    "&9&m-----------------------------",
                    "&cThat player is already in your party!",
                    "&9&m-----------------------------"
            );
            return;
        }

        invitedPlayers.add(toInvite);
        TextComponent text = new TextComponent("Click here to join!");
        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + inviter.getBukkitPlayer().getDisplayName()));
        text.setColor(ChatColor.GOLD);

        new BukkitRunnable() {
            @Override
            public void run() {
                if(inParty(toInvite)) return;

                invitedPlayers.remove(toInvite);
                toInvite.sendMessages(
                        "&9&m-----------------------------",
                        "&eThe party invite from " + inviter.getBukkitPlayer().getDisplayName() + " has expired.",
                        "&9&m-----------------------------"
                );

                for(SkyblockPlayer member : partyMembers) {
                    member.sendMessages(
                            "&9&m-----------------------------",
                            "&eThe party invite to " + toInvite.getBukkitPlayer().getDisplayName() + " has expired",
                            "&9&m-----------------------------"
                    );
                }
            }
        }.runTaskLater(SkyblockSandbox.getInstance(), 20*60L);

        toInvite.sendMessages(
                "&9&m-----------------------------",
                "&e" + inviter.getBukkitPlayer().getDisplayName() + " has invited you to join their party!",
                "&eYou have &c60 &eseconds to accept. " + text,
                "&9&m-----------------------------"
        );

        for(SkyblockPlayer member : partyMembers) {
            member.sendMessages(
                    "&9&m-----------------------------",
                    "&e" + inviter.getBukkitPlayer().getDisplayName() + " invited " + toInvite.getBukkitPlayer().getDisplayName() + " to the party! They",
                    "&ehave &c60 &eseconds to accept.",
                    "&9&m-----------------------------"
            );
        }
    }

    public void addMember(SkyblockPlayer player) {
        if(inParty(player)) return;
        invitedPlayers.remove(player);

        player.sendMessages(
                "&9&m-----------------------------",
                "&eYou have joined " + leader.getBukkitPlayer().getDisplayName() + "'s party!",
                "&9&m-----------------------------"
        );

        for(SkyblockPlayer member : partyMembers) {
            member.sendMessages(
                    "&9&m-----------------------------",
                    "&e" + player.getBukkitPlayer().getDisplayName() + " joined the party.",
                    "&9&m-----------------------------"
            );
        }

        partyMembers.add(player);
        player.setPartyPermissions(0);
    }

    public void removeMember(SkyblockPlayer player) {
        if(!inParty(player)) return;

        player.sendMessages(
                "&9&m-----------------------------",
                "&eYou left the party.",
                "&9&m-----------------------------"
        );

        for(SkyblockPlayer member : partyMembers) {
            if(member == player) return;
            member.sendMessages(
                    "&9&m-----------------------------",
                    "&e" + player.getBukkitPlayer().getDisplayName() + " has left the party.",
                    "&9&m-----------------------------"
            );
        }

        partyMembers.remove(player);
        player.setCurrentParty(null);
    }

    public void kickMember(SkyblockPlayer kicker, SkyblockPlayer toKick) {
        if(!inParty(toKick)) {
            kicker.sendMessages(
                    "&9&m-----------------------------",
                    "&cThat player isn't in the party.",
                    "&9&m-----------------------------"
            );
            return;
        }

        toKick.sendMessages(
                "&9&m-----------------------------",
                "&eYou have been kicked from the party by " + kicker.getBukkitPlayer().getDisplayName(),
                "&9&m-----------------------------"
        );

        partyMembers.remove(toKick);
        toKick.setCurrentParty(null);

        for(SkyblockPlayer member : partyMembers) {
            member.sendMessages(
                    "&9&m-----------------------------",
                    "&e" + toKick.getBukkitPlayer().getDisplayName() + " has been removed from the party.",
                    "&9&m-----------------------------"
            );
        }
    }

    public List<SkyblockPlayer> getMembers() {
        return partyMembers;
    }

    public void sendMessages(String... messages) {
        for(SkyblockPlayer member : getMembers()) {
            member.sendMessages(messages);
        }
    }

    public void setPermissions(SkyblockPlayer player, int permission) {
        permissions.put(player, permission);
    }

    /*
     * Get Functions
     */

    public boolean inParty(SkyblockPlayer player) {
        return partyMembers.contains(player);
    }

    public SkyblockPlayer getLeader() {
        return leader;
    }

    public boolean hasInvite(SkyblockPlayer player) {
        return invitedPlayers.contains(player);
    }

    public int getPlayerPermissions(SkyblockPlayer player) {
        return permissions.getOrDefault(player, 0);
    }

}
