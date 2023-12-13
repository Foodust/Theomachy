package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Theomachy;

public class Stance extends Ability
{
	
	private final static String[] des= {
			   "스탠스는 강한 의지를 갖고 있는 능력입니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"지구력",
			   "모든 데미지 증폭 효과를 무시하며,",
			   "모든 공격에 100% 확률로 밀려나지 않습니다." ,
			   "하지만, 모든 자신의 방어 효과는 무시됩니다."};
	
	public Stance(String playerName)
	{
		super(playerName,"스탠스", 103, false, true, false ,des);
		Theomachy.log.info(playerName+abilityName);
		this.rank=2;
	}
	
	public void passiveSkill(EntityDamageEvent event)
	{
		if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.PROJECTILE)
		{
			Player player = (Player)event.getEntity();
			double damage = event.getDamage();
			player.damage(damage);
			event.setCancelled(true);
		}
	}
	
	
}
