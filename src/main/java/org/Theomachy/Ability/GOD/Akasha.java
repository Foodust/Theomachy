package org.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;


public class Akasha extends Ability {
    private final static String[] des = {
            AbilityInfo.Akasha.getName() + "는 고통과 향락의 여신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "향락",
            "주변에 있는 아군에게 기쁨을 주어 15초간 속도와 재생을 부여 합니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "고통",
            "주변에 있는 적군에게 고통을 주어 6초간 혼란하게 합니다."};

    public Akasha(String playerName) {
        super(playerName, AbilityInfo.Akasha, true, false, false, des);
        messageModule.logInfo(playerName + abilityName);
        this.normalSkillCoolTime = 60;
        this.normalSkillStack = 10;
        this.rareSkillCoolTime = 100;
        this.rareSkillStack = 20;
        this.normalDuration = 15;
        this.rareDuration = 6;
        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player,skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, material, normalSkillStack)) {
            skillHandler.Use(player, material, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            List<Player> nearPlayers = playerHandler.getNearByTeamMembers(player, 20, 20, 20);
            for (Player nearPlayer : nearPlayers) {
                messageModule.sendPlayer(player,ChatColor.DARK_PURPLE + "향락" + ChatColor.WHITE + "이 당신을 즐겁게합니다!");
                nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, normalDuration * 20, 0));
                nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, normalDuration * 20, 0));
            }
        }

    }

    private void rightAction(Player player) {

        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, material, rareSkillStack)) {
            List<Player> entityList = playerHandler.getNearByNotTeamMembers(player, 15, 15, 15);
            if (entityList.isEmpty()) {
                messageModule.sendPlayer(player,ChatColor.RED + "주변에 상대팀이 없습니다");
                return;
            }
            skillHandler.Use(player, material, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            for (Player enemy : entityList) {
                enemy.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rareDuration * 20, 0));
                enemy.setHealth(enemy.getHealth() - 4);
            }

        }
    }

}
