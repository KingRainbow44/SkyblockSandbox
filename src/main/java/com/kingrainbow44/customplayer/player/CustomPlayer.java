package com.kingrainbow44.customplayer.player;

import net.md_5.bungee.api.chat.TextComponent;
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

    public void sendMessage(Object message) {
        if(message instanceof String) {
            bukkitPlayer.sendMessage(message.toString().replace('&', ChatColor.COLOR_CHAR));
        } else if (message instanceof TextComponent) {
            bukkitPlayer.spigot().sendMessage((TextComponent) message);
        }
    }

    public void sendMessages(Object... messages) {
        for(Object message : messages) {
            sendMessage(message);
        }
    }

    public void onRegister() {

    }

    public void onUnregister() {

    }

}
