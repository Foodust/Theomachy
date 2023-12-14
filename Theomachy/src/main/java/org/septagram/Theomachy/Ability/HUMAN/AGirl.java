package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.GetPlayerList;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class AGirl extends Ability{

	private final static String[] des= {
			AbilityInfo.AGirl.getName() + "는 귀여움으로 상대를 아사시킵니다.",
			ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"가짜 연약함",
			"주변의 적을 자신의 앞으로 끌어옵니다.",
			"끌려 온 플레이어들의 배고픔 지수는 0이 됩니다."};
	
	public AGirl(String playerName) {
		super(playerName, AbilityInfo.AGirl, true, false, false, des);
		
		this.rank= 3;
		this.firstSkillCoolTime =60;
		this.firstSkillStack =15;
	}
	
	public void activeSkill(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
			switch (EventFilter.PlayerInteract(event))
			{
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
			}
		}
	}

	private void leftAction(Player player) {
		if(CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack)) {
			
			Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL,firstSkillStack, firstSkillCoolTime);


			for(Player e:GetPlayerList.getNearByNotTeamMembers(player, 5, 0, 5)) {
				Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{e.teleport(player);});
				e.setFoodLevel(0);
				e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 200));
				e.sendMessage(ChatColor.GREEN+"안락소녀"+ChatColor.WHITE+"에게 이끌려 갑니다!");
			}
		}
	}

}
