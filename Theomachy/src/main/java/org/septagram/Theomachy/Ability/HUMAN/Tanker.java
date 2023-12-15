package org.septagram.Theomachy.Ability.HUMAN;

import java.util.Random;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;

public class Tanker extends Ability {


    private final static String[] des = {
            AbilityInfo.Tanker.getName() + "는 덜 아픕니다",
            NamedTextColor.YELLOW + "【패시브】 " + NamedTextColor.WHITE + "M",
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
