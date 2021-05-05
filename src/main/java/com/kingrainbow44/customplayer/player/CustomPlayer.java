package com.kingrainbow44.customplayer.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class CustomPlayer implements ICustomPlayer {

    private final Player bukkitPlayer;

    public CustomPlayer(Player player) {
        bukkitPlayer = player;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    public void sendMessage(String message) {
        bukkitPlayer.sendMessage(message.replace('&', ChatColor.COLOR_CHAR));
    }

    public void sendMessages(String... messages) {
        for(String message : messages) {
            sendMessage(message);
        }
    }


}
