package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Blacksmith extends Ability
{
	private final static String[] des= {
			   "대장장이는 다양한 광물을 만들어 낼 수 있는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"철 연성",
			   "코블스톤을 소비하여 철괴 10개를 획득할 수 있습니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"금광석 연성",
			   "철괴를 소비하여 다이아 5개를 얻을 수 있습니다."};
	
	public Blacksmith(String playerName)
	{
		super(playerName,"대장장이", 5, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =300;
		this.secondSkillCoolTime =600;
		this.firstSkillStack =70;
		this.secondSkillStack =20;
		
		this.rank=4;
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
			world.dropItem(player.getLocation().add(0,2,0), new ItemStack(Material.IRON_INGOT, 10));
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, Material.IRON_INGOT, secondSkillStack))
		{
			Skill.Use(player, Material.IRON_INGOT, secondSkillStack, 2, secondSkillCoolTime);
			World world = player.getWorld();
			world.dropItem(player.getLocation().add(0,2,0), new ItemStack(Material.DIAMOND, 5));
		}
	}
}
