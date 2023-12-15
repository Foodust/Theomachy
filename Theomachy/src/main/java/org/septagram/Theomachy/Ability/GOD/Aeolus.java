package org.septagram.Theomachy.Ability.GOD;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.DirectionChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.GetPlayerList;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Aeolus extends Ability {

    private final static String[] des = {
            AbilityInfo.Aeolus.getName() + "는 폭풍과 바람의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "자연 바람",
            "주변에 있는 아군에게 15초간 상쾌한 바람으로 빠르고 건강하게 합니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "폭풍",
            "주변에 있는 적을 강한 바람으로 밀어내고 5초간 느리고 약하게 합니다."};

    private final int normalDuration;
    private final int rareDuration;

    public Aeolus(String playerName) {
        super(playerName, AbilityInfo.Aeolus, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 60;
        this.normalSkillStack = 10;
        this.rareSkillCoolTime = 180;
        this.rareSkillStack = 32;
        this.normalDuration = 15;
        this.rareDuration = 5;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, material, normalSkillStack)) {
            Skill.Use(player, material, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            List<Player> nearPlayers = GetPlayerList.getNearByTeamMembers(player, 20, 20, 20);
            for (Player nearPlayer : nearPlayers) {
                nearPlayer.sendMessage(ChatColor.AQUA + "상쾌한 바람" + ChatColor.WHITE + "이 당신을 감싸돕니다!");
                nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, normalDuration * 20, 0));
                nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, normalDuration * 20, 0));
            }
        }
    }

    private void rightAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, material, rareSkillStack)) {
            List<Player> entityList = GetPlayerList.getNearByNotTeamMembers(player, 10, 10, 10);
            ArrayList<Player> targetList = new ArrayList<Player>();
            for (Player e : entityList)
                if (e != null)
                    targetList.add(e);
            if (!targetList.isEmpty()) {
                Skill.Use(player, material, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
                Vector vector = new Vector(0, 0.5, 0);
                double vertical = 2.4d;
                double diagonal = vertical * 1.4d;
                for (Player enemy : targetList) {
                    enemy.setVelocity(vector);
                    enemy.sendMessage(ChatColor.DARK_AQUA + "강력한 바람 때문에 밀려납니다!");
                    enemy.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, rareDuration * 20, 0));
                    enemy.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, rareDuration * 20, 0));
                }
                switch (DirectionChecker.PlayerDirection(player)) {
                    case 0 -> vector.add(new Vector(0, 0, diagonal));
                    case 1 -> vector.add(new Vector(-vertical, 0, vertical));
                    case 2 -> vector.add(new Vector(-diagonal, 0, 0));
                    case 3 -> vector.add(new Vector(-vertical, 0, -vertical));
                    case 4 -> vector.add(new Vector(0, 0, -diagonal));
                    case 5 -> vector.add(new Vector(vertical, 0, -vertical));
                    case 6 -> vector.add(new Vector(diagonal, 0, 0));
                    case 7 -> vector.add(new Vector(vertical, 0, vertical));
                }
                Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                    for (Player enemy : targetList)
                        enemy.setVelocity(vector);
                }, 2 * 20);

            } else
                player.sendMessage("능력을 사용할 수 있는 대상이 없습니다.");
        }
    }
}
