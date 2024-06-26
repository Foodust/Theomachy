package org.Theomachy.Ability.GOD;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;

public class Ares extends Ability
{
	
	private final static String[] des= {
			AbilityInfo.Ares.getName() + "는 전쟁의 신입니다.",
			ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"잔혹함",
			"모든 공격 데미지가 1.5배 상승합니다." ,
			ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"예리함",
			"10% 확률로 공격을 회피합니다"};
	
	public Ares(String playerName)
	{
		super(playerName, AbilityInfo.Ares, false, true, false, des);
		messageModule.logInfo(playerName+abilityName);
		
		this.rank= AbilityRank.A;
		
	}
	
	public void passiveSkill(EntityDamageByEntityEvent event)
	{		
		Player player = (Player) event.getEntity();
		if (!player.getName().equals(playerName)) //공격
			event.setDamage((int) (event.getDamage()*1.5));
		else											//피격
		{
			Random random = new Random();
			if (random.nextInt(10) == 0) 	//1/2 확률
			{
				event.setCancelled(true);
				messageModule.sendPlayer(player,"회피했습니다!");
			}
		}
	}
}
