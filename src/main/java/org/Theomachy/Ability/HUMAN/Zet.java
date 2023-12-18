package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;

public class Zet extends Ability {

    private final static String[] des = {
            AbilityInfo.Zet.getName() + "은 내연 기관의 일종입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "시동",
            "불에 타면 10초간 빨라집니다.",
            "능력에 의한 가속은 다른 가속 효과와 중첩되지 않습니다.",
            "가솔린 기관보다 가속력이 좋습니다."
    };

    private final int passiveDuration;
    private final int passiveAmplifier;

    public Zet(String playerName) {
        super(playerName, AbilityInfo.Zet, false, true, false, des);
        this.passiveDuration = 10;
        this.passiveAmplifier = 3;
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
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, passiveDuration * 20, passiveAmplifier));
            }
        }

    }

}
