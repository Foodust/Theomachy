package org.septagram.Theomachy.Ability;

public enum AttackTag {
    BONEATTACK("boneAttack");
    private final String tag;
    AttackTag(String tag) {
        this.tag = tag;
    }
    public  String getTag(){
        return tag;
    }
}
