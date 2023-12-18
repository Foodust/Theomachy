package org.Theomachy.Enum;

import org.bukkit.ChatColor;

public enum CommonMessage {
    BLACKLIST(ChatColor.BLACK + "블랙리스트");

    private final String message;

    CommonMessage(String message) {
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
