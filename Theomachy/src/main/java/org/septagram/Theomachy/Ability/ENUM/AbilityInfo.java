package org.septagram.Theomachy.Ability.ENUM;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

public enum AbilityInfo {
    Zeus(1, "제우스"),
    Poseidon(2, "포세이돈"),
    Hades(3, "하데스"),
    Demeter(4, "데메테르"),
    Athena(5, "아테나"),
    Apollon(6, "아폴론"),
    Artemis(7, "아르테미스"),
    Ares(8, "아레스"),
    Hephastus(9, "헤파이스토스"),
    Asclepius(10, "아스킬리피어스"),
    Hermes(11, "헤르메스"),
    Dionysus(12, "디오니소스"),
    Aprodite(13, "아프로디테"),
    Eris(14, "에리스"),
    Morpious(15, "모르피우스"),
    Aeolus(16, "아이올로스"),
    Akasha(17, "아카샤"),
    Horeundal(18, "호른달"),
    Sans(19, "샌즈"),

    //========================================================================================================

    Archer(101, "아쳐"),
    Miner(102, "광부"),
    Stance(103, "스탠스"),
    Teleporter(104, "텔레포터"),
    Bomber(105, "봄버"),
    Creeper(106, "크리퍼"),
    Wizard(107, "마법사"),
    Assasin(108, "암살자"),
    Reflection(109, "가시갑옷"),
    Blinder(110, "블라인더"),
    Invincibility(111, "무적"),
    Clocking(112, "클로킹"),
    BlackSmith(113, "대장장이"),
    Boxer(114, "복서"),
    Priest(115, "사제"),
    Witch(116, "마녀"),
    Meteor(117, "메테오"),
    Sniper(118, "저격수"),
    Voodoo(119, "부두술사"),
    Anorexia(120, "거식증"),
    Bulter(121, "집사"),
    Midoriya(122, "미도리야"),
    Goldspoon(123, "금수저"),
    Bee(124, "여왕벌"),
    Snow(125, "눈사람"),
    Tajja(126, "타짜"),
    AGirl(127, "안락소녀"),
    PokeGo(128, "포케 고!"),
    Tanker(129, "탱커"),
    Gasolin(130, "가솔린"),
    Zet(131, "제트엔진");

    private final int index;
    private final String name;
    private static final Map<Integer, String> abilityInfoMap = new HashMap<>();
    static {
        for (AbilityInfo ability : AbilityInfo.values()) {
            abilityInfoMap.put(ability.index, ability.name);
        }
    }
    AbilityInfo(int index, String name) {
        this.index = index;
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public int getIndex(){
        return this.index;
    }
    public static String getNameByIndex(int index) {
        return abilityInfoMap.getOrDefault(index, "해당하는 이름 없음");
    }
}
