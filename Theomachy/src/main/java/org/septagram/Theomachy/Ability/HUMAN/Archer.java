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
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Archer extends Ability
{
	private final static String[] des= {
			   AbilityInfo.Archer.getName() + "입니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"정확함",
			   "활 공격 데미지가 1.4배로 상승합니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"화살 생성",
			   "화살을 만듭니다.",
			   ChatColor.BLUE+"【고급】 "+ChatColor.WHITE+"활 생성",
			   "활을 만듭니다."};
	
	public Archer(String playerName)
	{
		super(playerName, AbilityInfo.Archer, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		this.firstSkillCoolTime =10;
		this.secondSkillCoolTime =60;
		this.firstSkillStack =5;
		this.secondSkillStack =15;
		this.rank= AbilityRank.B;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR,LEFT_CLICK_BLOCK -> leftAction(player);
				case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> rightAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL,firstSkillStack,  firstSkillCoolTime);
			World world = player.getWorld();
			Location location = player.getLocation();
			world.dropItem(location, new ItemStack(Material.ARROW, 1));
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.RARE)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, secondSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE,  AbilityCase.RARE,secondSkillStack, secondSkillCoolTime);
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
