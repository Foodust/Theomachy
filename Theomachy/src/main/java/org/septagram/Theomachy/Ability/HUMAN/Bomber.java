package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Bomber extends Ability
{

	private Location tntLocation;
	private final static String[] des= {
			AbilityInfo.Bomber.getName() + "는 폭발을 다루는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"폭파",
			   "좌클릭으로 해당 위치에 보이지 않는 TNT를 설치하며" ,
			   "우클릭으로 어디서든 폭발시킬 수 있습니다."};
	
	public Bomber(String playerName)
	{
		super(playerName, AbilityInfo.Bomber, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =30;
		this.firstSkillStack =25;
		
		this.rank= AbilityRank.A;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_BLOCK -> leftAction(player);
				case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> rightAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		Block block = player.getTargetBlock(null, 5);
		if (block.getType() != Material.AIR)
		{
			Location location = block.getLocation();
			location.setY(location.getY()+1);
			this.tntLocation = location;
			player.sendMessage("해당 블럭에 폭탄이 설치되었습니다.");			
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			if (tntLocation != null)
			{
				Skill.Use(player, Material.COBBLESTONE,AbilityCase.NORMAL, firstSkillStack, firstSkillCoolTime);
				World world = player.getWorld();
				world.createExplosion(tntLocation, 2.0f, true);
				tntLocation = null;
				player.sendMessage("TNT가 폭발했습니다!");
				
			}
			else
				player.sendMessage("TNT가 설치되지 않았습니다.");
		}
	}
}
