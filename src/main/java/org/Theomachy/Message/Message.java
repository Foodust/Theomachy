package org.Theomachy.Message;

import org.bukkit.Bukkit;

public class Message {

    public void broadcastAlam(String message){
        Bukkit.broadcastMessage(TheomachyMessage.INFO_ALAM.getMessage() + message);
    }
}
