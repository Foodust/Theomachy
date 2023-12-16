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
import org.Theomachy.Ability.ENUM.AbilityCase;
import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Ability.ENUM.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.Checker.CoolTimeChecker;
import org.Theomachy.Utility.Checker.MouseEventChecker;
import org.Theomachy.Handler.Handler.PlayerHandler;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillCoolTimeHandler;

public class Blinder extends Ability {
    private final static String[] des = {
            AbilityInfo.Blinder.getName() + "는 상대방의 시야를 가리는 능력입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "블라인딩 Ⅰ",
            "자신을 공격한 상대는 일정 확률로 2초간 시야가 가려집니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "블라인딩 Ⅱ",
            "주변의 적의 시야를 5초간 가립니다."};

    private final int passiveDuration;
    private final int normalDuration;
    public Blinder(String playerName) {
        super(playerName, AbilityInfo.Blinder, true, true, false, des);
        Theomachy.log.info(playerName + abilityName);

        this.passiveDuration = 2;

        this.normalSkillCoolTime = 30;
        this.normalSkillStack = 10;
        this.normalDuration = 5;

        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            List<Player> targetList = PlayerHandler.getNearByNotTeamMembers(player, 5, 5, 5);
            if (!targetList.isEmpty()) {
                SkillCoolTimeHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                player.sendMessage("주변의 적의 시야를 가립니다.");
                for (Player e : targetList) {
                    e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, normalDuration * 20, 0));
                    e.sendMessage("블라인더에 의해 시야가 어두워집니다.");
                }
            } else
                player.sendMessage("사용 가능한 대상이 없습니다.");
        }
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();
        if (player.getName().equals(this.playerName)) {
            Random random = new Random();
            if (random.nextInt(10) == 0) {
                Player target = (Player) event.getDamager();
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, passiveDuration * 20, 0));
                target.sendMessage("블라인더에 의해 시야가 어두워집니다.");
            }
        }
    }
}
