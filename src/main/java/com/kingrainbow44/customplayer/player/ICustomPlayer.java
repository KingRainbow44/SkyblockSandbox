package com.kingrainbow44.customplayer.player;

import org.bukkit.entity.Player;

public interface ICustomPlayer {

    public Player getBukkitPlayer();

    public void sendMessage(String message);

    public void sendMessages(String... messages);

}
