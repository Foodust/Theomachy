package org.Theomachy.Ability.GOD;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;

public class Dionysus extends Ability {
    private final static String[] des = {
            AbilityInfo.Dionysus.getName() + "는 술의 신입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "알코올 중독",
            "10% 확률로 자신을 공격한 10초간 상대의 시야를 5초간 어지럽히는 동시에",
            "상대의 이동 속도, 공격력을 낮춥니다."};
    private final int passiveDuration;

    public Dionysus(String playerName) {
        super(playerName, AbilityInfo.Dionysus, false, true, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.passiveDuration = 5;
        this.rank = AbilityRank.A;
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();
        if (player.getName().equals(playerName)) {
            Random random = new Random();
            int rn = random.nextInt(10);
            if (rn == 0) {
                Player target = (Player) event.getDamager();
                // def
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, passiveDuration * 20, 0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, passiveDuration * 20, 0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, passiveDuration * 20, 0));
                target.sendMessage("술에 취해서 정신이 없습니다!");
                player.sendMessage("상대방에게 술을 먹였습니다.");
            }
        }
    }
}
