package org.Theomachy.Enum;

import org.bukkit.ChatColor;

public enum CommonMessage {
    BLACKLIST(ChatColor.BLACK + "블랙리스트"),
    ABILITY_INFO(ChatColor.YELLOW + "능력 정보"),
    SETTING(ChatColor.BLUE + "설정"),
    MENU(ChatColor.GREEN + "편의 기능"),
    TIP(ChatColor.AQUA + "팁"),
    GAMBLING(ChatColor.GREEN + "뽑기"),
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
