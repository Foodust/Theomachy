package org.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;


public class Asclepius extends Ability {
    private final static String[] des = {
            AbilityInfo.Asclepius.getName() + "는 의술의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "치료 Ⅰ",
            "자신의 체력을 완전히 회복합니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "치료 Ⅱ",
            "주변에 있는 자신을 제외한 아군의 체력을 완전히 회복합니다."};

    public Asclepius(String playerName) {
        super(playerName, AbilityInfo.Asclepius, true, false, false, des);
        messageModule.logInfo(playerName + abilityName);

        this.normalSkillCoolTime = 60;
        this.rareSkillCoolTime = 120;
        this.normalSkillStack = 1;
        this.rareSkillStack = 5;

        this.rank = AbilityRank.B;
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
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillCoolTime)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            player.setHealth(20);
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            List<Player> targetList = playerHandler.getNearByTeamMembers(player, 5, 5, 5);
            if (!targetList.isEmpty()) {
                skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
                messageModule.sendPlayer(player,"자신을 제외한 모든 팀원의 체력을 회복합니다.");
                messageModule.sendPlayer(player,ChatColor.GREEN + "체력을 회복한 플레이어 목록");
                for (Player enemy : targetList) {
                    enemy.setHealth(20);
                    enemy.sendMessage(ChatColor.YELLOW + "의술의 신의 능력으로 모든 체력을 회복합니다.");
                    messageModule.sendPlayer(player,ChatColor.GOLD + enemy.getName());
                }
            } else
                messageModule.sendPlayer(player,"사용 가능한 대상이 없습니다.");
        }
    }
}
