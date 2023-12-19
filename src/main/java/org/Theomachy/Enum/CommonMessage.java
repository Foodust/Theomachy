package org.Theomachy.Enum;

import org.bukkit.ChatColor;

public enum CommonMessage {
    BLACKLIST_YML("blacklist.yml"),
    CUSTOM_BLASE_LOD_RECIPE("custom_blaze_rod_recipe"),
    WHO_BREAK_DIAMOND(ChatColor.WHITE + "가 다이아몬드 블럭을 부쉈습니다!"),
    BLACKLIST(ChatColor.BLACK + "블랙리스트"),
    ABILITY_INFO(ChatColor.YELLOW + "능력 정보"),
    SETTING(ChatColor.BLUE + "설정"),
    MENU(ChatColor.GREEN + "편의 기능"),
    TIP(ChatColor.AQUA + "팁"),
    GAMBLING(ChatColor.GREEN + "뽑기"),
    NEXT_PAGE(ChatColor.WHITE + "다음 페이지"),
    CURRENT_PAGE(ChatColor.WHITE + "현재 페이지"),
    PREV_PAGE(ChatColor.WHITE + "이전 페이지"),
    STARTING_INVENTORY_CLEAR(ChatColor.WHITE + "게임 시작 시 인벤토리 클리어"),
    STARTING_GIVE_BASIC_ITEM(ChatColor.WHITE + "게임 시작 시 스카이블럭 아이템 지급"),
    STARTING_ENTITY_CLEAR(ChatColor.WHITE + "게임 시작 시 엔티티 제거"),
    IGNORE_BED(ChatColor.WHITE + "침대 무시"),
    FAST_START(ChatColor.WHITE + "빠른 시작"),
    SERVER_AUTO_SAVE(ChatColor.WHITE + "서버 자동 저장"),
    ANIMAL_SPAWN(ChatColor.WHITE + "동물 스폰"),
    MONSTER_SPWAN(ChatColor.WHITE + "몬스터 스폰"),
    GAMBLING_ACCEPT(ChatColor.WHITE + "뽑기 허용");

    private final String message;

    CommonMessage(String message) {
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }

    public static CommonMessage getByMessage(String message){
        for(CommonMessage commonMessage : values()){
            if(commonMessage.getMessage().equals(message))
                return commonMessage;
        }
        return null;
    }
}
