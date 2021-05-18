package com.kingrainbow44.customplayer.player;

import org.bukkit.entity.Player;

public interface ICustomPlayer {

    public Player getBukkitPlayer();

    public void sendMessage(Object message);

    public void sendMessages(Object... messages);

    public void onRegister();

    public void onUnregister();

}
