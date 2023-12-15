package org.septagram.Theomachy.Ability.HUMAN;

import java.util.Random;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;

public class Zet extends Ability {

    private final static String[] des = {
            AbilityInfo.Zet.getName() + "은 내연 기관의 일종입니다.",
            NamedTextColor.YELLOW + "【패시브】 " + NamedTextColor.WHITE + "시동",
            "불에 타면 10초간 빨라집니다.",
            "능력에 의한 가속은 다른 가속 효과와 중첩되지 않습니다.",
            "가솔린 기관보다 가속력이 좋습니다."
    };

    private final int duration;
    private final int amplifier;

    public Zet(String playerName) {
        super(playerName, AbilityInfo.Zet, false, true, false, des);
        this.duration = 10;
        this.amplifier = 3;
        this.rank = AbilityRank.B;
    }

    public void passiveSkill(EntityDamageEvent event) {
        Player p = (Player) event.getEntity();
        boolean has = false;

        for (PotionEffect e : p.getActivePotionEffects()) {
            if (e.getType().equals(PotionEffectType.SPEED)) {
                has = true;
            }
        }

        if (!has) {
            if (event.getCause().equals(DamageCause.FIRE) || event.getCause().equals(DamageCause.FIRE_TICK) || event.getCause().equals(DamageCause.LAVA)) {
                p.sendMessage("동력이 생겨 빨라집니다!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration * 20, amplifier));
            }
        }

    }

}
