package org.Theomachy.Message;

import org.Theomachy.Data.VersionData;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public enum TheomachyMessage {
    // difficult
    DIFFICULT_PEACEFUL(ChatColor.GREEN + "평화로움"),
    DIFFICULT_EASY(ChatColor.AQUA + "쉬움"),
    DIFFICULT_NORMAL(ChatColor.YELLOW + "보통"),
    DIFFICULT_HARD(ChatColor.RED + "어려움"),
    // tip
    TIP1(ChatColor.AQUA + "【팁 1】  " + ChatColor.WHITE + "능력을 확인 하려면 " + ChatColor.GREEN + "/t help" + ChatColor.WHITE + " 를 입력합니다"),
    TIP2(ChatColor.AQUA + "【팁 2】  " + ChatColor.WHITE + "중앙 건물 양 옆 섬에도 아이템이 숨겨져 있습니다. "),
    TIP3(ChatColor.AQUA + "【팁 3】  " + ChatColor.WHITE + "각 팀의 다이아 몬드 블럭을 파괴 할 시 승리합니다."),
    TIP4(ChatColor.AQUA + "【팁 4】  " + ChatColor.GREEN + "/t gamble " + ChatColor.WHITE + "명령어로 뽑기를 할 수 있습니다."),
    TIP5(ChatColor.AQUA + "【팁 5】  " + ChatColor.WHITE + ChatColor.WHITE + "블레이즈 로드는 제작대에서 나무 막대를 세로로 일렬로 놓으면 만들 수 있습니다."),
    TIP6(ChatColor.AQUA + "【팁 6】 \n"
            + ChatColor.AQUA + "【일반】" + ChatColor.WHITE + " 능력은 블레이즈 로드를 좌클릭하여\n"
            + ChatColor.RED + "【고급】" + ChatColor.WHITE + " 능력은 블레이즈 로드를 우클릭하여 이용합니다\n"
            + ChatColor.WHITE + "자세한 내용은 언제나 능력 설명을 참고합시다\n"),
    TIP_ERROR_REPORTING(ChatColor.GREEN + "오류 제보는 " + ChatColor.WHITE + "seunhoo@naver.com " + ChatColor.GREEN + "또는" + ChatColor.WHITE + "프덧#6170"),

    // explain
    EXPLAIN_ABILITY_NORMAL(ChatColor.GREEN + "~ 일반 능력 | 블레이즈 막대 좌클릭~"),
    EXPLAIN_ABILITY_RARE(ChatColor.YELLOW + "~ 고급 능력 | 블레이즈 막대 우클릭 ~"),
    EXPLAIN_ABILITY_PASSIVE(ChatColor.GREEN + "~ 패시브 능력 ~"),
    EXPLAIN_CHECK_THE_ABILITY_DESCRIPTION(ChatColor.WHITE + "능력 설명을 확인하세요."),

    EXPLAIN_COOL_TIME(ChatColor.WHITE + "쿨타임: " ),
    EXPLAIN_COOL_DOWN(" 초 소요"),
    EXPLAIN_COBBLE_STONE(ChatColor.WHITE + "조약돌: "),
    EXPLAIN_SPEND_COUNT("개 소모"),
    EXPLAIN_ABILITY_NAME(ChatColor.AQUA + "【능력 이름】 "),
    EXPLAIN_ABILITY_EXPLAIN(ChatColor.DARK_AQUA + "【능력 설명】"),
    EXPLAIN_RANK("랭크: " ),

    // command info t
    EXPLAIN_THEOMACHY_COMMAND(ChatColor.AQUA + "【           " + ChatColor.WHITE + "신들의 전쟁 명령어 일람" + ChatColor.AQUA + "          】"),
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
    EXPLAIN_CHECK_ABILITY("/t help 로 확인해보세요."),
    // command info t a
    EXPLAIN_ABILITY_LIST_HELP(ChatColor.YELLOW + "/t a help " + ChatColor.WHITE + "  모든 능력의 코드를 확인합니다."),
    EXPLAIN_ABILITY_SET_RANDOM(ChatColor.YELLOW + "/t a random " + ChatColor.WHITE + "현재 접속한 모든 플레이어에게 랜덤으로 능력을 할당합니다."),
    EXPLAIN_ABILITY_REMOVE_PLAYER(ChatColor.YELLOW + "/t a remove" + ChatColor.RED + " <Player>" + ChatColor.RED + "  해당 플레이어의 능력을 삭제합니다."),
    EXPLAIN_ABILITY_RESET(ChatColor.YELLOW + "/t a reset  " + ChatColor.WHITE + "모든 능력을 초기화 합니다"),
    EXPLAIN_ABILITY_SET_PLAYER(ChatColor.YELLOW + "/t a " + ChatColor.GREEN + "<AbilityCode>" + ChatColor.RED + " <Player>" + ChatColor.WHITE + "  플레이어에게 해당 능력을 적용합니다."),
    EXPLAIN_CHECK_ABILITY_CODE("/t a help 명령어로 능력 코드를 확인하실 수 있습니다."),

    // command info t team
    EXPLAIN_HOW_TO_SET_SPAWN(ChatColor.YELLOW + "/t  spawn(s)   " + ChatColor.AQUA + "<TeamName>  스폰지역으로 설정합니다."),
    EXPLAIN_HOW_TO_SET_SPAWN_X_Y_Z("좌표로도 등록할 수 있습니다."),
    EXPLAIN_HOW_TO_SET_SPAWN_X_Y_Z_EXAMPLE("ex) /t s  팀    X  Y  Z"),
    EXPLAIN_HOW_TO_SET_TEAM(ChatColor.YELLOW + ("/t  team(t)   ") + ChatColor.AQUA + ("<TeamName>  ") + ChatColor.RED + ("<Player>  ") + ChatColor.WHITE + ("플레이어를 팀에 등록합니다.")),
    EXPLAIN_HOW_TO_SET_TEAM_MESSAGE("한번에 다수의 플레이어를 한 팀에 등록 할 수 있습니다."),
    EXPLAIN_HOW_TO_SET_TEAM_EXAMPLE("ex) /t t  팀   플레이어1   플레이어2   플레이어3"),
    EXPLAIN_HOW_TO_SET_TEAM_RANDOM(ChatColor.YELLOW + "/t  team(t)   random" + ChatColor.BLUE + ("<TeamName1>  ") + ChatColor.RED + ("<TeamName2>  ")),
    EXPLAIN_HOW_TO_SET_TEAM_RANDOM_MESSAGE("무작위로 팀을 배정합니다"),
    EXPLAIN_HOW_TO_SET_TEAM_RANDOM_EXAMPLE("ex) /t t  random   팀이름1   팀이름2 "),

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
    COMMAND_ON("ON"),
    COMMAND_OFF("OFF"),

    // setting
    SETTING_BLACKLIST_YML("blacklist.yml"),
    SETTING_CUSTOM_BLASE_LOD_RECIPE("custom_blaze_rod_recipe"),
    SETTING_BLACKLIST(ChatColor.BLACK + "블랙리스트"),
    SETTING_ABILITY_INFO(ChatColor.YELLOW + "능력 정보"),
    SETTING(ChatColor.BLUE + "설정"),
    SETTING_MENU(ChatColor.GREEN + "편의 기능"),
    SETTING_TIP(ChatColor.AQUA + "팁"),
    SETTING_GAMBLING(ChatColor.GREEN + "뽑기"),
    SETTING_NEXT_PAGE(ChatColor.WHITE + "다음 페이지"),
    SETTING_CURRENT_PAGE(ChatColor.WHITE + "현재 페이지"),
    SETTING_PREV_PAGE(ChatColor.WHITE + "이전 페이지"),
    SETTING_STARTING_INVENTORY_CLEAR(ChatColor.WHITE + "게임 시작 시 인벤토리 클리어"),
    SETTING_STARTING_GIVE_ITEM(ChatColor.WHITE + "게임 시작 시 스카이블럭 아이템 지급"),
    SETTING_STARTING_ENTITY_CLEAR(ChatColor.WHITE + "게임 시작 시 엔티티 제거"),
    SETTING_IGNORE_BED(ChatColor.WHITE + "침대 무시"),
    SETTING_FAST_START(ChatColor.WHITE + "빠른 시작"),
    SETTING_SERVER_AUTO_SAVE(ChatColor.WHITE + "서버 자동 저장"),
    SETTING_ANIMAL_SPAWN(ChatColor.WHITE + "동물 스폰"),
    SETTING_DIFFICULT(ChatColor.WHITE + "난이도"),
    SETTING_MONSTER_SPAWN(ChatColor.WHITE + "몬스터 스폰"),
    SETTING_GAMBLING_ACCEPT(ChatColor.WHITE + "뽑기 허용"),
    SETTING_DEBUG_MODE(ChatColor.WHITE + "디버그 모드"),

    // game end
    END_WHO_BREAK_DIAMOND(ChatColor.WHITE + "가 다이아몬드 블럭을 부쉈습니다!"),

    // ScoreBoard
    SCOREBOARD_HEALTH_BAR("HealthBar"),
    SCOREBOARD_HEALTH(ChatColor.GREEN + "♥"),

    // info message
    INFO_PLUGIN_ENABLED(VersionData.name + "플러그인이 활성화되었습니다.   " + VersionData.buildNumber + "  " + VersionData.version),
    INFO_LOADING_DEFAULT_SETTING(VersionData.name +"게임의 설정 불러오는 중입니다."),
    INFO_PLUGIN_DEFAULT_SETTING(VersionData.name + "플러그인의 기본 설정을 적용 중입니다."),
    INFO_LIST_OF_ACCEPT_ABILITY("등록된 능력"),
    INFO_BAR(VersionData.name + "========================================"),
    INFO_GREETING(ChatColor.AQUA + " 님 환영합니다."),
    INFO_ADMIN_START_GAME(") 가 게임을 시작하였습니다."),
    INFO_ADMIN_STOP_GAME(") 가 게임을 종료하였습니다."),
    INFO_SETTING_THIS_POSITION_DEFAULT_SPAWN_POINT("현재 위치가 모든 플레이어의 스폰지역으로 설정되었습니다."),
    INFO_SETTING_THIS_POSITION_TEAM_SPAWN_POINT(ChatColor.WHITE + " 의 스폰지역으로 설정되었습니다."),
    INFO_COOL_TIME_CLEAR(ChatColor.YELLOW + "관리자가 모든 쿨타임을 초기화 하였습니다."),
    INFO_REMOVE_PLAYER_ABILITY(ChatColor.DARK_RED + "의 능력을 삭제하였습니다."),
    INFO_REMOVE_ALL_PLAYER_ABILITY(ChatColor.AQUA + "관리자가 모두의 능력을 초기화 하였습니다."),
    INFO_RESET_AND_RANDOM_ABILITY("모든 능력을 삭제한 후 재 추첨합니다."),
    INFO_AVAILABLE_PLAYERS(ChatColor.DARK_AQUA + "인식된 플레이어 목록"),
    INFO_SET_ALL_PLAYER_ABILITY("모두에게 능력이 적용되었습니다."),
    INFO_SET_PLAYER_ABILITY("능력이 할당되었습니다."),
    INFO_SET(" 에 등록되었습니다."),
    INFO_CHANGE_TEAM(" 의 팀이 변경되었습니다.    "),
    INFO_CHECK_MY_TEAM(ChatColor.YELLOW + "자신의 팀을 확인합니다"),
    INFO_SET_MESSAGE("되었습니다"),
    INFO_DISABLED(" 비활성화 "),
    INFO_ABLE(" 활성화 "),
    INFO_BEFORE_SECOND("초 전"),
    INFO_CAN_ABILITY(ChatColor.GOLD + "능력을 다시 사용할 수 있습니다."),
    INFO_USING_ABILITY(ChatColor.YELLOW + "능력을 사용했습니다!"),
    INFO_ACTIVATE_ABILITY(ChatColor.YELLOW+"능력이 활성화 되었습니다."),
    INFO_GAMBLING1(ChatColor.WHITE + "조약돌 32개를 소모해 다양한 아이템을"),
    INFO_GAMBLING2(ChatColor.WHITE + "뽑을 수 있습니다."),
    INFO_GAMBLING_MESSAGE_1(ChatColor.AQUA + "% 확률로 조약돌 20 ~ 60개 당첨 되었습니다!"),
    INFO_GAMBLING_MESSAGE_2(ChatColor.GOLD + "% 확률로 나무 원목 3개 당첨 되었습니다!"),
    INFO_GAMBLING_MESSAGE_3(ChatColor.BLUE + "% 확률로 아무것도 당첨되지 않았습니다!"),
    INFO_GAMBLING_MESSAGE_4(ChatColor.GREEN + "% 확률로 철괴 4개 당첨 되었습니다!"),
    INFO_GAMBLING_MESSAGE_5(ChatColor.DARK_RED + "% 확률로 다이아몬드 10개 당첨 되었습니다!!!"),
    INFO_NORMAL_ABILITY(ChatColor.AQUA + "[일반]  "),
    INFO_RARE_ABILITY(ChatColor.RED + "[고급]  "),
    INFO_GOD(ChatColor.GOLD + " 【 신 】 "),
    INFO_HUMAN(ChatColor.AQUA + " 【 인간 】 "),
    INFO_JUJUTSU_KAISEN(ChatColor.GREEN + " 【 주술회전 】 "),
    INFO_KIMETSU_NO_YAIBA(ChatColor.RED + " 【 귀멸의 칼날 】 "),
    INFO_TOTAL_COUNT("총합"),
    INFO_PLAYER("플레이어 "),
    INFO_NEED("필요한 개수 : "),
    INFO_COOL_TIME_IS("쿨타임이 "),
    INFO_ABILITY_COOL_TIME_IS("능력의 쿨타임이 "),
    INFO_COOL_TIME_LEFT("초 남았습니다!"),

    // error message
    ERROR_DOES_NOT_WHO_HAVE_ABILITY("는" + ChatColor.DARK_RED + "능력이 없습니다."),
    ERROR_DOES_NOT_HAVE_ABILITY("능력이 없습니다."),
    ERROR_DOES_NOT_ONLINE_PLAYER(ChatColor.RED + "온라인 플레이어가 아닙니다."),
    ERROR_DOES_NOT_CHANGE_ABILITY_IN_GAME("게임 시작 후에는 능력을 변경 할 수 없습니다."),
    ERROR_DOES_NOT_HAVE_ABILITY_PLAYER("플레이어의 능력이 없습니다."),
    ERROR_DOES_NOT_HAVE_ABILITY_ALL_PLAYER("능력이 있는 플레이어가 없습니다."),
    ERROR_DOES_NOT_EXIST_PLAYER_NAME("에 해당하는 온라인 유저가 없습니다."),
    ERROR_DOES_NOT_TARGET_FOR_ABILITY("타겟을 사용하는 능력이 아닙니다."),
    ERROR_DOES_NOT_HAVE_COBBLE_STONE(ChatColor.RED + "조약돌이 부족합니다."),
    ERROR_DOES_NOT_START_GAME("게임이 시작되지 않았습니다."),
    ERROR_DOES_NOT_HAVE_PLAYER_IN_THERE_TEAM("해당 팀에 팀원이 없습니다."),
    ERROR_DOES_NOT_HAVE_TEAM("소속된 팀이 없습니다."),
    ERROR_DOES_NOT_ACCESS_BLACKLIST_FILE("[신들의 전쟁] 블랙리스트가 파일로 저장되었습니다. 절대로 플러그인 폴더 내에 blacklist.yml을 건들지 마십시오."),
    ERROR_THIS_COMMAND_IS_NOT_ALLOWED(ChatColor.RED + "이 기능은 잠겨있습니다."),
    ERROR_WRONG_COMMAND(ChatColor.RED + "잘못된 명령입니다."),
    ERROR_CHECK_T_A_COMMAND("/t a 로 명령어를 확인하세요."),
    ERROR_SET_REMOVE_PLAYER_NAME("능력을 삭제 할 플레이어의 이름을 적어주세요."),
    ERROR_WRONG_ABILITY_NUMBER_OR_NAME("능력 혹은 능력 코드 번호를 잘못 입력하셨습니다."),
    ERROR_TOO_MANY_PLAYER("인원이 너무 많습니다. 전부에게 능력을 할당하지 못했을수도 있습니다."),
    ERROR_ABILITY_CODE_IS_INTEGER("능력코드는 정수를 입력해 주세요"),
    ERROR_PERMISSION_DENIED("권한이 없습니다."),
    ERROR_THIS_COMMAND_EXECUTE_IN_GAME("이 명령어는 게임에서 실행해 주십시오."),
    ERROR_DEBUG_IS_ON(ChatColor.RED + "디버그 모드가 활성화 되어있습니다.\n 디버그 모드를 종료 해주세요"),
    ERROR_GAME_ALREADY_STARTED(ChatColor.AQUA + "게임이 이미 시작되었습니다."),
    ERROR_COOL_TIMER("쿨타이머에 에러가 발생하여 쿨타임이 1초 느려집니다."),
    ERROR_DEACTIVATE_ABILITY(ChatColor.RED + "능력이 비활성화 되었습니다."),
    ERROR_TOO_FAR_ENTITY(ChatColor.RED + "대상과의 거리가 너무 멉니다."),
    ERROR_TOO_FAR_TARGET(ChatColor.RED + "목표와의 거리가 너무 멉니다."),
    ERROR_NOT_ENOUGH_COBBLESTONE("조약돌이 부족합니다."),
    ERROR_NOT_ENOUGH_OAK("나무가 부족합니다."),
    ERROR_NOT_ENOUGH_IRON("철괴가 부족합니다.");
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
