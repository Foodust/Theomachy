package org.Theomachy.Message;

import org.bukkit.ChatColor;

public enum TheomachyMessage {
    // tip
    TIP1(ChatColor.AQUA + "팁 1 : " + ChatColor.WHITE + "능력을 확인 하려면 " + ChatColor.GREEN + "/t help" + ChatColor.WHITE + " 를 입력합니다"),
    TIP2(ChatColor.AQUA + "팁 2 : " + ChatColor.WHITE + "중앙 건물 양 옆 섬에도 아이템이 숨겨져 있습니다. "),
    TIP3(ChatColor.AQUA + "팁 3 : " + ChatColor.WHITE + "각 팀의 다이아 몬드 블럭을 파괴 할 시 승리합니다."),
    TIP4(ChatColor.AQUA + "팁 4 : " + ChatColor.GREEN + "/t gamble " + ChatColor.WHITE + "명령어로 뽑기를 할 수 있습니다."),
    TIP5(ChatColor.AQUA + "팁 5 : " + ChatColor.WHITE + ChatColor.WHITE + "블레이즈 로드는 제작대에서 나무 막대를 세로로 일렬로 놓으면 만들 수 있습니다."),
    TIP6(ChatColor.AQUA + "팁 6 : \n"
            + ChatColor.AQUA + "【일반】" + ChatColor.WHITE + " 능력은 블레이즈 로드를 좌클릭하여\n"
            + ChatColor.RED + "【고급】" + ChatColor.WHITE + " 능력은 블레이즈 로드를 우클릭하여 이용합니다\n"
            + ChatColor.WHITE + "자세한 내용은 언제나 능력 설명을 참고합시다\n"),
    ERROR_REPORTING(ChatColor.GREEN + "오류 제보는 " + ChatColor.WHITE + "seunhoo@naver.com " + ChatColor.GREEN + "또는" + ChatColor.WHITE + "프덧#6170"),

    // command info
    EXPLAIN_COMMAND(ChatColor.AQUA+"【           "+ChatColor.WHITE+"신들의 전쟁 명령어 일람"+ChatColor.AQUA+"          】"),
    EXPLAIN_GAME_START(ChatColor.YELLOW + ("/t start            ") + ChatColor.WHITE + ("게임을 시작합니다.")),
    EXPLAIN_GAME_STOP(ChatColor.YELLOW + ("/t stop              ") + ChatColor.WHITE + ("게임을 중지합니다.")),
    EXPLAIN_ABILITY_SET(ChatColor.YELLOW + ("/t ability(a)      ") + ChatColor.WHITE + ("능력을 설정합니다")),
    EXPLAIN_ABILITY_LIST(ChatColor.YELLOW + ("/t alist          ") + ChatColor.WHITE + ("적용된 능력을 확인합니다.")),
    EXPLAIN_ABILITY_HELP(ChatColor.YELLOW + ("/t help           ") + ChatColor.WHITE + ("자신의 능력을 확인합니다.")),
    EXPLAIN_TEAM_SPAWN(ChatColor.YELLOW + ("/t spawn(s)         ") + ChatColor.AQUA + ("<TeamName>   ") + ChatColor.WHITE + ("해당 팀의 스폰지역을 설정합니다.")),
    EXPLAIN_TEAM_ADD(ChatColor.YELLOW + ("/t team(t)            ") + ChatColor.AQUA + ("<TeamName>  ") + ChatColor.RED + ("<Player>  ") + ChatColor.WHITE + ("플레이어를 팀에 등록합니다.")),
    EXPLAIN_TEAM_LIST(ChatColor.YELLOW + ("/t info              ") + ChatColor.AQUA + ("<TeamName>     ") + ChatColor.WHITE + ("해당 팀의 멤버를 확인합니다.")),
    EXPLAIN_CLEAR(ChatColor.YELLOW + ("/t clear(c)              ") + ChatColor.WHITE + ("쿨타임을 초기화합니다.")),
    EXPLAIN_BLACKLIST(ChatColor.YELLOW + ("/t blacklist(black,b)  			") + ChatColor.WHITE + ("블랙리스트 시스템을 엽니다.")),
    EXPLAIN_SETTING(ChatColor.YELLOW + ("/t setting(set)  			") + ChatColor.WHITE + ("설정 시스템을 엽니다.")),
    EXPLAIN_GAMBLING(ChatColor.YELLOW + ("/t gambling(g)  			") + ChatColor.WHITE + ("뽑기 시스템을 엽니다.")),
    EXPLAIN_X_COMMAND(ChatColor.YELLOW + "/x  " + ChatColor.RED+ "<Player>		" +ChatColor.WHITE+ "해당 플레이어를 자신의 타겟으로 등록합니다"),
    // command

    COMMAND_T("t"),
    COMMAND_X("x"),
    COMMAND_START("start"),
    COMMAND_STOP("stop"),
    COMMAND_ABILITY("ability"),
    COMMAND_ABILITY_A("a"),
    COMMAND_ABILITY_LIST("alist"),
    COMMAND_HELP("help"),
    COMMAND_SPAWN("spawn"),
    COMMAND_SPAWN_S("s"),
    COMMAND_TEAM("team"),
    COMMAND_TEAM_T("t"),
    COMMAND_INFO("info"),
    COMMAND_CLEAR("clear"),
    COMMAND_CLEAR_C("c"),
    COMMAND_BLACKLIST("blacklist"),
    COMMAND_BLACKLIST_BLACK("black"),
    COMMAND_BLACKLIST_B("b"),
    COMMAND_SETTING("setting"),
    COMMAND_SETTING_SET("set"),
    COMMAND_GAMBLING("gambling"),
    COMMAND_GAMBLING_G("g"),

    // setting
    BLACKLIST_YML("blacklist.yml"),
    CUSTOM_BLASE_LOD_RECIPE("custom_blaze_rod_recipe"),
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
    GAMBLING_ACCEPT(ChatColor.WHITE + "뽑기 허용"),

    // game end
    WHO_BREAK_DIAMOND(ChatColor.WHITE + "가 다이아몬드 블럭을 부쉈습니다!"),

    // error message
    WRONG_COMMAND("잘못된 명령입니다."),
    DOES_NOT_HAVE_ABILITY("능력이 없습니다."),
    DOES_NOT_ONLINE_PLAYER("온라인 플레이어가 아닙니다.");
    private final String message;

    TheomachyMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public static TheomachyMessage getByMessage(String message) {
        for (TheomachyMessage theomachyMessage : values()) {
            if (theomachyMessage.getMessage().equals(message))
                return theomachyMessage;
        }
        return null;
    }
}
