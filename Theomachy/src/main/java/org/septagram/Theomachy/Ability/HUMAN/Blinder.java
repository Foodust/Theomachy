package org.septagram.Theomachy.Ability.HUMAN;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.GetPlayerList;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Blinder extends Ability
{
	private final static String[] des= {
			AbilityInfo.Blinder.getName() + "는 상대방의 시야를 가리는 능력입니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"블라인딩 Ⅰ",
			   "자신을 공격한 상대는 일정 확률로 시야가 가려집니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"블라인딩 Ⅱ",
			   "주변의 적의 시야를 가립니다."};
	
	public Blinder(String playerName)
	{
		super(playerName, AbilityInfo.Blinder, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =30;
		this.firstSkillStack =10;
		
		this.rank= AbilityRank.A;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK-> leftAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			List<Player> targetList = GetPlayerList.getNearByNotTeamMembers(player, 5, 5, 5);
			if (!targetList.isEmpty())
			{
				Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, firstSkillStack, firstSkillCoolTime);
				player.sendMessage("주변의 적의 시야를 가립니다.");
				for (Player e : targetList)
				{
					e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 0), true);
					e.sendMessage("블라인더에 의해 시야가 어두워집니다.");
				}
			}
			else
				player.sendMessage("사용 가능한 대상이 없습니다.");
		}
	}
	
	public void passiveSkill(EntityDamageByEntityEvent event)
	{
		Player player = (Player) event.getEntity();
		if (player.getName().equals(this.playerName))
		{
			Random random = new Random();
			if (random.nextInt(10) == 0)
			{
				Player target = (Player) event.getDamager();
				target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0), true);
				target.sendMessage("블라인더에 의해 시야가 어두워집니다.");
			}
		}
	}
}
