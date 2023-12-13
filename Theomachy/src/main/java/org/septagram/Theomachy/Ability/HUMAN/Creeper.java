package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Creeper extends Ability
{
	private boolean plasma = false;
	private final static String[] des= {
			   "크리퍼는 몬스터형 능력입니다.",
			   "블레이즈 로드를 통해 능력을 사용하면" ,
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"펑!",
			   "크리퍼와 같은 폭발력의 폭발을 일으킵니다." ,
			   "번개를 맞은 적이 있다면 폭발력이 두 배로 증가합니다.",
			   "번개 카운팅은 사망 시 초기화됩니다."};
	
	public Creeper(String playerName)
	{
		super(playerName,"크리퍼", 106, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =60;
		this.firstSkillStack =20;
		
		this.rank=3;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
			switch(EventFilter.PlayerInteract(event))
			{
			case 0:case 1:
				leftAction(player);
				break;
			}
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, firstSkillStack, 0, firstSkillCoolTime);
			World world = player.getWorld();
			Location location = player.getLocation();
			float power = plasma ? 3.0f : 6.0f;
			player.setHealth(0);
			world.createExplosion(location, power);
		}
	}
	
	public void passiveSkill(EntityDamageEvent event)
	{
		if (event.getCause() == DamageCause.LIGHTNING)
		{
			this.plasma = true;
			((Player)event.getEntity()).sendMessage(ChatColor.AQUA+"플라즈마 크리퍼 모드 활성화!");
		}
	}
	
	public void passiveSkill(PlayerDeathEvent event)
	{
		this.plasma=false;
	}
}
