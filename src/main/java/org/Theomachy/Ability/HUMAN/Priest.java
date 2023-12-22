package org.Theomachy.Ability.HUMAN;

import java.util.List;
import java.util.Random;

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

import org.Theomachy.Checker.MouseEventChecker;




public class Priest extends Ability {
    private final Material material = Material.COBBLESTONE;
    private final static String[] des = {
            AbilityInfo.Priest.getName() + "는 신의 가호를 받을 수 있는 능력입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "신의 은총 Ⅰ",
            "자신에게 랜덤으로 버프 5초간 를 적용합니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "신의 은총 Ⅱ",

            "자신의 팀원 모두에게 랜덤으로 버프를 5초간 적용합니다."};
    private final int normalDuration;
    private final int rareDuration;

    public Priest(String playerName) {
        super(playerName, AbilityInfo.Priest, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);

        this.normalSkillCoolTime = 35;
        this.normalSkillStack = 30;
        this.normalDuration = 5;

        this.rareSkillCoolTime = 90;
        this.rareSkillStack = 45;
        this.rareDuration = 5;
        this.rank = AbilityRank.B;

    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {

        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, material, normalSkillStack)) {
            skillHandler.Use(player, material, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            Random random = new Random();
            if (random.nextInt(2) == 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, normalDuration * 20, 0));
                player.sendMessage(ChatColor.LIGHT_PURPLE + "데미지 저항 효과가 적용되었습니다.");
            }
            if (random.nextInt(2) == 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, normalDuration * 20, 0));
                player.sendMessage(ChatColor.RED + "데미지 증가 효과가 적용되었습니다.");
            }
            if (random.nextInt(2) == 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, normalDuration * 20, 0));
                player.sendMessage(ChatColor.GOLD + "체력회복속도 증가 효과가 적용되었습니다.");
            }
            if (random.nextInt(2) == 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, normalDuration * 20, 0));
                player.sendMessage(ChatColor.AQUA + "이동속도 증가 효과가 적용되었습니다.");
            }
            if (random.nextInt(2) == 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, normalDuration * 20, 0));
                player.sendMessage(ChatColor.GREEN + "빠른 채광 효과가 적용되었습니다.");
            }
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, material, rareSkillStack)) {
            skillHandler.Use(player, material, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            List<Player> targetList = playerHandler.getTeamMember(player);
            Random random = new Random();
            for (Player team : targetList) {
                if (random.nextInt(2) == 0) {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, rareDuration * 20, 0));
                    team.sendMessage(ChatColor.LIGHT_PURPLE + "데미지 저항 효과가 적용되었습니다.");
                }
                if (random.nextInt(2) == 0) {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, rareDuration * 20, 0));
                    team.sendMessage(ChatColor.RED + "데미지 증가 효과가 적용되었습니다.");
                }
                if (random.nextInt(2) == 0) {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, rareDuration * 20, 0));
                    team.sendMessage(ChatColor.GOLD + "체력회복속도 증가 효과가 적용되었습니다.");
                }
                if (random.nextInt(2) == 0) {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, rareDuration * 20, 0));
                    team.sendMessage(ChatColor.AQUA + "이동속도 증가 효과가 적용되었습니다.");
                }
                if (random.nextInt(2) == 0) {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, rareDuration * 20, 0));
                    team.sendMessage(ChatColor.GREEN + "빠른 채광 효과가 적용되었습니다.");
                }
            }
        }
    }
}
