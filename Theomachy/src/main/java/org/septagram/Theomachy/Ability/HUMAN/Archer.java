package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Archer extends Ability
{
	private final static String[] des= {
			   "궁수입니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"정확함",
			   "활 공격 데미지가 1.4배로 상승합니다.",
			   ChatColor.AQUA+"【일반/고급】 "+ChatColor.WHITE+"화살/활 생성",
			   "일반능력으로 화살을, 고급 능력으로 활을 만듭니다."};
	
	public Archer(String playerName)
	{
		super(playerName,"아처", 101, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		this.firstSkillCoolTime =20;
		this.secondSkillCoolTime =60;
		this.firstSkillStack =7;
		this.secondSkillStack =15;
		
		this.rank=2;
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
			case 2:case 3:
				rightAction(player);
				break;
			}
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, firstSkillStack, 1, firstSkillCoolTime);
			World world = player.getWorld();
			Location location = player.getLocation();
			world.dropItem(location, new ItemStack(Material.ARROW, 1));
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, secondSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, secondSkillStack, 2, secondSkillCoolTime);
			World world = player.getWorld();
			Location location = player.getLocation();
			world.dropItem(location, new ItemStack(Material.BOW, 1));
		}
	}
	
	public void passiveSkill(EntityDamageByEntityEvent event)
	{
		int damage = (int)(event.getDamage());
		event.setDamage((int) (damage*1.4));
	}
}
