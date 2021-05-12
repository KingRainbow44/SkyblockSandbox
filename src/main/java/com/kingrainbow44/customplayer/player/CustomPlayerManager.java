package com.kingrainbow44.customplayer.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CustomPlayerManager {

    private final Map<UUID, ICustomPlayer> players = new HashMap<>();

    public void addPlayer(Player player, ICustomPlayer customPlayer) {
        customPlayer.onRegister();
        players.put(player.getUniqueId(), customPlayer);
    }

    public void removePlayer(Player player) {
        if(isCustomPlayer(player) != null) {
            ICustomPlayer customPlayer = isCustomPlayer(player);
            customPlayer.onUnregister();
        }
        players.remove(player.getUniqueId());
    }

    public ICustomPlayer isCustomPlayer(Player player) {
        return players.getOrDefault(player.getUniqueId(), null);
    }

}
