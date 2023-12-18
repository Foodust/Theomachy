package org.Theomachy.ENUM;

public enum AbilityRank {
    S("S"),

    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F");

    private final String rank;
    AbilityRank(String s) {
        this.rank = s;
    }
    public String getRank(){
        return this.rank;
    }
}
