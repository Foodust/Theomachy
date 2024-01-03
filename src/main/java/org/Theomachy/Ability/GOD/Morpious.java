package org.Theomachy.Ability.GOD;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Data.GameData;


import java.util.Objects;

public class Morpious extends Ability {

    private final static String[] des = {
            AbilityInfo.Morpious.getName() + "는 잠의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "수면",
            "목표로 지정한 적을 1분간 잠들게 합니다.",
            "목표 지정: /x <대상>"};

    private String abilityTarget;

    public Morpious(String playerName) {
        super(playerName, AbilityInfo.Morpious, true, false, false, des);
        this.normalSkillCoolTime = 180;
        this.normalSkillStack = 32;
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
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillCoolTime)) {
            String[] team = new String[2];
            team[0] = GameData.playerTeam.get(player.getName());
            team[1] = GameData.playerTeam.get(abilityTarget);

            if (!Objects.equals(team[0], team[1])) {
                if (abilityTarget != null) {
                    if (player.getName().equals(abilityTarget)) {
                        player.sendMessage(ChatColor.RED + "목표는 본인이 아니어야 합니다.");
                    } else {
                        Player target = GameData.onlinePlayer.get(abilityTarget);
                        skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                        player.sendMessage(ChatColor.GRAY + "목표를 잠재웠습니다!");
                        target.sendMessage(ChatColor.GRAY + "착한 어른이는 일찍 자고 일찍 일어나야 해요~");
                        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1200, 0));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1200, 3));
                    }

                } else {
                    player.sendMessage("목표를 설정해주십시오. (목표 설정법: /x <목표>)");
                }
            } else {
                player.sendMessage(ChatColor.GRAY + "본인의 팀이므로 잠을 재울 수 없습니다!");
            }
        }
    }

    public void targetSet(CommandSender sender, String targetName) {
        if (!playerName.equals(targetName)) {
            this.abilityTarget = targetName;
            sender.sendMessage("타겟을 등록했습니다.   " + ChatColor.GREEN + targetName);
        } else
            sender.sendMessage("자기 자신을 목표로 등록 할 수 없습니다.");
    }
}
