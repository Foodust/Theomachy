package org.Theomachy.Enum;

import org.bukkit.ChatColor;

public enum CommonMessage {
    BLACKLIST(ChatColor.BLACK + "블랙리스트"),
    NEXT_PAGE(ChatColor.WHITE + "다음 페이지"),
    PREV_PAGE(ChatColor.WHITE + "이전 페이지");

    private final String message;

    CommonMessage(String message) {
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
