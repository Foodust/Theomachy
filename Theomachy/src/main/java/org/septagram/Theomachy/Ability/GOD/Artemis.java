package org.septagram.Theomachy.Ability.GOD;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Timer.CoolTime;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Artemis extends Ability
{	
	private final static String[] des= {
			AbilityInfo.Artemis.getName() + "는 사냥과 달의 신입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"화살 생성",
			   "화살 만듭니다.",
			   ChatColor.BLUE+"【고급】 " + ChatColor.WHITE +"활 생성",
			   "활을 만듭니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"사냥 기술",
			   "화살로 공격당한 플레이어는 15%의 확률로 즉사합니다."};
	
	public Artemis(String playerName)
	{
		super(playerName, AbilityInfo.Artemis, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =20;
		this.secondSkillCoolTime =180;
		this.firstSkillStack =7;
		this.secondSkillStack =15;
		
		this.rank= AbilityRank.A;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
				case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL,firstSkillStack, firstSkillCoolTime);
			World world = player.getWorld();
			Location location = player.getLocation();
			world.dropItem(location, new ItemStack(Material.ARROW, 1));
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.RARE)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, secondSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, AbilityCase.RARE,secondSkillStack,  secondSkillCoolTime);
			World world = player.getWorld();
			Location location = player.getLocation();
			world.dropItem(location, new ItemStack(Material.BOW, 1));
		}
	}
	
	public void passiveSkill(EntityDamageByEntityEvent event)
	{
		
		Arrow arrow = (Arrow) event.getDamager();
		Player player = (Player) arrow.getShooter();
		Player target = (Player) event.getEntity();
		if (!CoolTime.commonSkillCoolTime.containsKey(target.getName()+"1"))
		{
			Random random = new Random();
			if (random.nextInt(20) <= 2)
			{
				
				event.setDamage(100);
				player.sendMessage("화살이 상대방의 심장을 꿰뚫었습니다!");
				target.sendMessage("아르테미스의 화살에 즉사하였습니다.");
			}
		}
	}	
}
