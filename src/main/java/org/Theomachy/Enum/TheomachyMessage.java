package org.Theomachy.Enum;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

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

    // command info t
    EXPLAIN_COMMAND(ChatColor.AQUA + "【           " + ChatColor.WHITE + "신들의 전쟁 명령어 일람" + ChatColor.AQUA + "          】"),
    EXPLAIN_GAME_START(ChatColor.YELLOW + "/t start            " + ChatColor.WHITE + "게임을 시작합니다."),
    EXPLAIN_GAME_STOP(ChatColor.YELLOW + "/t stop              " + ChatColor.WHITE + "게임을 중지합니다."),
    EXPLAIN_ABILITY_SET(ChatColor.YELLOW + "/t ability (a)      " + ChatColor.WHITE + "능력을 설정합니다"),
    EXPLAIN_ABILITY_LIST(ChatColor.YELLOW + "/t alist          " + ChatColor.WHITE + "능력 리스트를 확인합니다."),
    EXPLAIN_ABILITY_HELP(ChatColor.YELLOW + "/t help           " + ChatColor.WHITE + "자신의 능력을 확인합니다."),
    EXPLAIN_TEAM_SPAWN(ChatColor.YELLOW + "/t spawn (s)         " + ChatColor.AQUA + "<TeamName>   " + ChatColor.WHITE + "해당 팀의 스폰지역을 설정합니다."),
    EXPLAIN_TEAM_ADD(ChatColor.YELLOW + "/t team (t)            " + ChatColor.AQUA + "<TeamName>  " + ChatColor.RED + "<Player>  " + ChatColor.WHITE + "플레이어를 팀에 등록합니다."),
    EXPLAIN_TEAM_LIST(ChatColor.YELLOW + "/t info              " + ChatColor.AQUA + "<TeamName>     " + ChatColor.WHITE + "해당 팀의 멤버를 확인합니다."),
    EXPLAIN_CLEAR(ChatColor.YELLOW + "/t clear (c)              " + ChatColor.WHITE + "쿨타임을 초기화합니다."),
    EXPLAIN_BLACKLIST(ChatColor.YELLOW + "/t blacklist (black,b)  		" + ChatColor.WHITE + "블랙리스트 시스템을 엽니다."),
    EXPLAIN_SETTING(ChatColor.YELLOW + "/t setting (set)             " + ChatColor.WHITE + "설정 시스템을 엽니다."),
    EXPLAIN_GAMBLING(ChatColor.YELLOW + "/t gambling (g)  		    " + ChatColor.WHITE + "뽑기 시스템을 엽니다."),
    EXPLAIN_X_COMMAND(ChatColor.YELLOW + "/x  " + ChatColor.RED + "<Player>      " + ChatColor.WHITE + "해당 플레이어를 자신의 타겟으로 등록합니다"),

    // command info t a
    EXPLAIN_ABILITY_LIST_HELP("/t a help   모든 능력의 코드를 확인합니다."),
    EXPLAIN_ABILITY_SET_RANDOM("/t a random 현재 접속한 모든 플레이어에게 랜덤으로 능력을 할당합니다."),
    EXPLAIN_ABILITY_REMOVE_PLAYER("/t a remove <Player> 해당 플레이어의 능력을 삭제합니다."),
    EXPLAIN_ABILITY_RESET("/t a reset  모든 능력을 초기화 합니다"),
    EXPLAIN_ABILITY_SET_PLAYER("/t a <AbilityCode> <Player>  플레이어에게 해당 능력을 적용합니다."),

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
    COMMAND_REMOVE("remove"),
    COMMAND_RESET("reset"),
    COMMAND_RANDOM("random"),

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
    STARTING_GIVE_ITEM(ChatColor.WHITE + "게임 시작 시 스카이블럭 아이템 지급"),
    STARTING_ENTITY_CLEAR(ChatColor.WHITE + "게임 시작 시 엔티티 제거"),
    IGNORE_BED(ChatColor.WHITE + "침대 무시"),
    FAST_START(ChatColor.WHITE + "빠른 시작"),
    SERVER_AUTO_SAVE(ChatColor.WHITE + "서버 자동 저장"),
    ANIMAL_SPAWN(ChatColor.WHITE + "동물 스폰"),
    MONSTER_SPWAN(ChatColor.WHITE + "몬스터 스폰"),
    GAMBLING_ACCEPT(ChatColor.WHITE + "뽑기 허용"),

    // game end
    WHO_BREAK_DIAMOND(ChatColor.WHITE + "가 다이아몬드 블럭을 부쉈습니다!"),

    // info message
    INFO_REMOVE_PLAYER_ABILITY("플레이어의 능력을 삭제하였습니다."),
    INFO_REMOVE_ALL_PLAYER_ABILITY(ChatColor.AQUA + "관리자가 모두의 능력을 초기화 하였습니다."),
    INFO_RESET_AND_RANDOM_ABILITY("모든 능력을 삭제한 후 재 추첨합니다."),
    INFO_AVAILABLE_PLAYERS(ChatColor.DARK_AQUA + "인식된 플레이어 목록"),
    INFO_SET_ALL_PLAYER_ABILITY("모두에게 능력이 적용되었습니다."),
    INFO_SET_PLAYER_ABILITY("능력이 할당되었습니다."),
    INFO_CHECK_ABILITY("/t help 로 확인해보세요."),
    INFO_ALL_ABILITY("/t a help 명령어로 능력 코드를 확인하실 수 있습니다."),
    INFO_SET_MESSAGE("되었습니다"),
    INFO_DISABLED(" 비활성화 "),
    INFO_ABLE(" 활성화 "),

    // error message
    ERROR_WRONG_COMMAND(ChatColor.RED + "잘못된 명령입니다."),
    ERROR_CHECK_T_A_COMMAND("/t a 로 명령어를 확인하세요."),
    ERROR_DOES_NOT_HAVE_ABILITY(ChatColor.DARK_RED + "능력이 없습니다."),
    ERROR_DOES_NOT_ONLINE_PLAYER(ChatColor.RED + "온라인 플레이어가 아닙니다."),
    ERROR_DOES_NOT_CHANGE_ABILITY_IN_GAME("게임 시작 후에는 능력을 변경 할 수 없습니다."),
    ERROR_SET_REMOVE_PLAYER_NAME("능력을 삭제 할 플레이어의 이름을 적어주세요."),
    ERROR_WRONG_ABILITY_NUMBER_OR_NAME("능력 혹은 능력 코드 번호를 잘못 입력하셨습니다."),
    ERROR_DOES_NOT_HAVE_ABILITY_PLAYER("플레이어의 능력이 없습니다."),
    ERROR_TOO_MANY_PLAYER("인원이 너무 많습니다. 전부에게 능력을 할당하지 못했을수도 있습니다."),
    ERROR_ABILITY_CODE_IS_INTEGER("능력코드는 정수를 입력해 주세요"),
    ERROR_DOES_NOT_EXIST_PLAYER_NAME("에 해당하는 온라인 유저가 없습니다."),
    ERROR_DOES_NOT_TARGET_FOR_ABILITY("타겟을 사용하는 능력이 아닙니다."),
    ERROR_PERMISSION_DENIED("권한이 없습니다.");
    private final String message;

    TheomachyMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    private static final Map<String, TheomachyMessage> messageInfoMap = new HashMap<>();

    static {
        for (TheomachyMessage theomachyMessage : TheomachyMessage.values()) {
            messageInfoMap.put(theomachyMessage.message, theomachyMessage);
        }
    }

    public static TheomachyMessage getByMessage(String message) {
        return messageInfoMap.get(message);
    }
}
