package org.Theomachy.Ability.HUMAN;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;


public class Witch extends Ability {
    private final static String[] des = {
            AbilityInfo.Witch.getName() + "는 디버프를 사용하는 능력입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "저주 Ⅰ",
            "주변의 적에게 각종 디버프를 10초 간 적용합니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "저주 Ⅱ",
            "자신을 공격한 상대는 10% 확률로 5초간 각종 디버프에 걸리게 됩니다."};

    int normalDuration;
    int passiveDuration;

    public Witch(String playerName) {
        super(playerName, AbilityInfo.Witch, true, false, false, des);
        messageModule.logInfo(playerName + abilityName);
        this.normalSkillCoolTime = 60;
        this.normalSkillStack = 15;
        this.passiveDuration = 5;
        this.normalDuration = 10;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player,skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, material, normalSkillStack)) {
            List<Player> targetList = playerHandler.getNearByNotTeamMembers(player, 10, 10, 10);
            if (!targetList.isEmpty()) {
                skillHandler.Use(player, material, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                for (Player enemy : targetList) {
                    enemy.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, normalDuration * 20, 0));
                    enemy.addPotionEffect(new PotionEffect(PotionEffectType.POISON, normalDuration * 20, 0));
                    enemy.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, normalDuration * 20, 0));
                    enemy.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, normalDuration * 20, 0));
                    enemy.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, normalDuration * 20, 0));
                    enemy.sendMessage("마녀에 의해 저주에 걸렸습니다.");
                }
            } else
                messageModule.sendPlayer(player,"능력을 사용 할 대상이 없습니다.");
        }
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();
        if (player.getName().equals(playerName)) {
            Random random = new Random();
            int rn = random.nextInt(14);
            if (rn == 0) {
                Player target = (Player) event.getDamager();
                target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, passiveDuration * 20, 0));//*20
                target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, passiveDuration * 20, 0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, passiveDuration * 20, 0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, passiveDuration * 20, 0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, passiveDuration * 20, 0));
                target.sendMessage("마녀에 의해 저주가 걸렸습니다.");
            }
        }
    }
}
