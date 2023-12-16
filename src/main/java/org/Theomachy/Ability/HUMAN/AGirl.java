package org.Theomachy.Ability.HUMAN;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Ability.ENUM.AbilityCase;
import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Ability.ENUM.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.CoolTimeChecker;
import org.Theomachy.Utility.EventFilter;
import org.Theomachy.Utility.GetPlayerList;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Utility.Skill;

public class AGirl extends Ability {

    private final static String[] des = {
            AbilityInfo.AGirl.getName() + "는 귀여움으로 상대를 아사시킵니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "가짜 연약함",
            "주변의 적을 자신의 앞으로 끌어옵니다.",
            "끌려 온 플레이어들의 배고픔 지수는 0이 되고",
            "1초간 강력한 슬로우에 걸립니다."};
    private final int normalDuration;

    public AGirl(String playerName) {
        super(playerName, AbilityInfo.AGirl, true, false, false, des);

        this.normalSkillCoolTime = 60;
        this.normalSkillStack = 15;
        this.normalDuration = 1;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {

            Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);


            for (Player e : GetPlayerList.getNearByNotTeamMembers(player, 5, 0, 5)) {
                Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
                    e.teleport(player);
                });
                e.setFoodLevel(0);
                e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, normalDuration * 20, 200));
                e.sendMessage(ChatColor.GREEN + AbilityInfo.AGirl.getName() + ChatColor.WHITE + "에게 이끌려 갑니다!");
            }
        }
    }

}
