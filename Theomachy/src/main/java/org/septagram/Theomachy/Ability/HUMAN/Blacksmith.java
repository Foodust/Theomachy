package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Blacksmith extends Ability
{
	private final static String[] des= {
			AbilityInfo.BlackSmith.getName() + "는 다양한 광물을 만들어 낼 수 있는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"철 연성",
			   "코블스톤을 소비하여 철괴 10개를 획득할 수 있습니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"금광석 연성",
			   "철괴를 소비하여 다이아 5개를 얻을 수 있습니다."};
	
	public Blacksmith(String playerName)
	{
		super(playerName, AbilityInfo.BlackSmith, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =300;
		this.secondSkillCoolTime =600;
		this.firstSkillStack =70;
		this.secondSkillStack =20;
		
		this.rank= AbilityRank.S;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
				case RIGHT_CLICK_AIR , RIGHT_CLICK_BLOCK -> rightAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL,firstSkillStack,  firstSkillCoolTime);
			World world = player.getWorld();
			world.dropItem(player.getLocation().add(0,2,0), new ItemStack(Material.IRON_INGOT, 10));
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.RARE)&&PlayerInventory.ItemCheck(player, Material.IRON_INGOT, secondSkillStack))
		{
			Skill.Use(player, Material.IRON_INGOT, AbilityCase.RARE,secondSkillStack, secondSkillCoolTime);
			World world = player.getWorld();
			world.dropItem(player.getLocation().add(0,2,0), new ItemStack(Material.DIAMOND, 5));
		}
	}
}
