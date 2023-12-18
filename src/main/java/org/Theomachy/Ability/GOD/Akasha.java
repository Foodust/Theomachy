package org.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.ENUM.AbilityCase;
import org.Theomachy.ENUM.AbilityInfo;
import org.Theomachy.ENUM.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Utility.Checker.CoolTimeChecker;
import org.Theomachy.Utility.Checker.MouseEventChecker;
import org.Theomachy.Handler.Handler.PlayerHandler;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillCoolTimeHandler;

public class Akasha extends Ability {
    private final static String[] des = {
            AbilityInfo.Akasha.getName() + "는 고통과 향락의 여신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "향락",
            "주변에 있는 아군에게 기쁨을 주어 15초간 속도와 재생을 부여 합니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "고통",
            "주변에 있는 적군에게 고통을 주어 6초간 혼란하게 합니다."};

    private final int rareDuration;
    private final int normalDuration;

    public Akasha(String playerName) {
        super(playerName, AbilityInfo.Akasha, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
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
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, material, normalSkillStack)) {
            SkillCoolTimeHandler.Use(player, material, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            List<Player> nearPlayers = PlayerHandler.getNearByTeamMembers(player, 20, 20, 20);
            for (Player nearPlayer : nearPlayers) {
                nearPlayer.sendMessage(ChatColor.DARK_PURPLE + "향락" + ChatColor.WHITE + "이 당신을 즐겁게합니다!");
                nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, normalDuration * 20, 0));
                nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, normalDuration * 20, 0));
            }
        }

    }

    private void rightAction(Player player) {

        if (CoolTimeChecker.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, material, rareSkillStack)) {
            List<Player> entityList = PlayerHandler.getNearByNotTeamMembers(player, 15, 15, 15);
            if (entityList.isEmpty()) {
                player.sendMessage(ChatColor.RED + "주변에 상대팀이 없습니다");
                return;
            }
            SkillCoolTimeHandler.Use(player, material, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            for (Player enemy : entityList) {
                enemy.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * rareDuration, 0));
                enemy.setHealth(enemy.getHealth() - 4);
            }

        }
    }

}
