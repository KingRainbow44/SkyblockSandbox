package tk.skyblocksandbox.partyandfriends.party;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.permitable.rank.PermitableRank;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

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

        if(inviter == toInvite) {
            inviter.sendMessages(
                    "&9&m-----------------------------",
                    "&cYou can't invite yourself!",
                    "&9&m-----------------------------"
            );
            return;
        }

        invitedPlayers.add(toInvite);

        TextComponent text = new TextComponent(" Click here to join! ");
        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + inviter.getBukkitPlayer().getDisplayName()));
        text.setColor(ChatColor.GOLD);
        TextComponent text2 = new TextComponent("You have ");
        text2.setColor(ChatColor.YELLOW);
        TextComponent text3 = new TextComponent("60");
        text3.setColor(ChatColor.RED);
        TextComponent text4 = new TextComponent(" seconds to accept.");
        text4.setColor(ChatColor.YELLOW);

        new BukkitRunnable() {
            @Override
            public void run() {
                if(inParty(toInvite)) return;
                if(!invitedPlayers.contains(toInvite)) return;

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
                PermitableRank.formatNameTag(Utility.getRankOfPlayer(inviter).getRankNameTagFormat(), inviter) + " &ehas invited you to join their party!"
        );
        toInvite.getBukkitPlayer().spigot().sendMessage(text2, text3, text4, text);
        toInvite.sendMessages("&9&m-----------------------------");

        for(SkyblockPlayer member : partyMembers) {
            member.sendMessages(
                    "&9&m-----------------------------",
                    PermitableRank.formatNameTag(Utility.getRankOfPlayer(inviter).getRankNameTagFormat(), inviter) + " &einvited&e " + PermitableRank.formatNameTag(Utility.getRankOfPlayer(toInvite).getRankNameTagFormat(), toInvite) + " &eto the party! They",
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
                    "&e" + PermitableRank.formatNameTag(Utility.getRankOfPlayer(player).getRankNameTagFormat(), player) + " joined the party.",
                    "&9&m-----------------------------"
            );
        }

        partyMembers.add(player);
        player.setCurrentParty(this);
        player.setPartyPermissions(0);
    }

    public void removeMember(SkyblockPlayer player) {
        if(!inParty(player)) return;

        player.sendMessages(
                "&9&m-----------------------------",
                "&eYou left the party.",
                "&9&m-----------------------------"
        );

        partyMembers.remove(player);

        for(SkyblockPlayer member : partyMembers) {
            if(member == player) return;
            member.sendMessages(
                    "&9&m-----------------------------",
                    "&e" + PermitableRank.formatNameTag(Utility.getRankOfPlayer(player).getRankNameTagFormat(), player) + " has left the party.",
                    "&9&m-----------------------------"
            );
        }

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
                "&eYou have been kicked from the party by " + PermitableRank.formatNameTag(Utility.getRankOfPlayer(kicker).getRankNameTagFormat(), kicker),
                "&9&m-----------------------------"
        );

        partyMembers.remove(toKick);
        toKick.setCurrentParty(null);

        for(SkyblockPlayer member : partyMembers) {
            member.sendMessages(
                    "&9&m-----------------------------",
                    "&e" + PermitableRank.formatNameTag(Utility.getRankOfPlayer(toKick).getRankNameTagFormat(), toKick) + " has been removed from the party.",
                    "&9&m-----------------------------"
            );
        }
    }

    public List<SkyblockPlayer> getMembers() {
        return partyMembers;
    }

    public void sendMessages(String... messages) {
        for(SkyblockPlayer member : getMembers()) {
            for(String message : messages) {
                member.sendMessage(message);
            }
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
