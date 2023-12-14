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
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.GetPlayerList;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Witch extends Ability
{
	private final static String[] des= {
			AbilityInfo.Witch.getName() + "는 디버프를 사용하는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"저주 Ⅰ",
			   "주변의 적에게 각종 디버프를 10초 간 적용합니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"저주 Ⅱ",
			   "자신을 공격한 상대는 7% 확률로 5초간 디버프에 걸리게 됩니다."};
	
	public Witch(String playerName)
	{
		super(playerName, AbilityInfo.Witch, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		this.firstSkillCoolTime =60;
		this.firstSkillStack =15;
		this.rank=2;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, material, firstSkillStack))
		{
			List<Player> targetList = GetPlayerList.getNearByNotTeamMembers(player, 10, 10, 10);
			if (targetList != null)
			{
				Skill.Use(player, material, AbilityCase.NORMAL, firstSkillStack, firstSkillCoolTime);
				for (Player e : targetList)
				{
					e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 10 * 20,0));
					e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10 * 20,0));
					e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 * 20,0));
					e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 10 * 20,0));
					e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 10 * 20,0));
					e.sendMessage("마녀에 의해 저주에 걸렸습니다.");
				}
			}
			else
				player.sendMessage("능력을 사용 할 대상이 없습니다.");
		}
	}
	
	public void passiveSkill(EntityDamageByEntityEvent event)
	{
		Player player = (Player)event.getEntity();
		if (player.getName().equals(playerName))
		{
			Random random = new Random();
			int rn = random.nextInt(14);
			if (rn == 0)
			{
				Player target = (Player) event.getDamager();
				target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 5 * 20,0));//*20
				target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 5 * 20 ,0));
				target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20,0));
				target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 5 * 20,0));
				target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5 * 20,0));
				target.sendMessage("마녀에 의해 저주가 걸렸습니다.");
			}
		}
	}
}
