package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.Theomachy.Ability.Ability;
import org.Theomachy.ENUM.AbilityInfo;
import org.Theomachy.ENUM.AbilityRank;

public class Tanker extends Ability {


    private final static String[] des = {
            AbilityInfo.Tanker.getName() + "는 덜 아픕니다",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "M",
            "가격 데미지를 1/5로 줄여 받습니다"};
    int damageReduce;
    public Tanker(String playerName) {
        super(playerName, AbilityInfo.Tanker, false, true, false, des);
        this.rank = AbilityRank.A;
        this.damageReduce = 5;
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player tanker = (Player) event.getEntity();
        Player damage = (Player) event.getDamager();
        if (tanker.getName().equals(playerName)) {
            event.setDamage(event.getDamage() % damageReduce);
        }
    }

}
