package org.septagram.Theomachy.Ability.GOD;

import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilitySet;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Hephaestus extends Ability
{
	private final static String[] des= {
			AbilitySet.Hephastus.getName() + "는 불의 신입니다.",
			  ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"화염 속성",
			  "불에 관한 데미지를 일절 받지 않으나, 물에 들어가면 데미지를 입습니다.",
			  ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"용암",
			   "블럭을 클릭하면 용암을 놓습니다. 놓은 용암은 사라지지 않습니다.",};
	
	public Hephaestus(String playerName)
	{
		super(playerName, AbilitySet.Hephastus, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =30;
		this.firstSkillStack =15;
		
		this.rank=3;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
			switch(EventFilter.PlayerInteract(event))
			{
				case LEFT_CLICK_BLOCK -> leftAction(player);
			}
		}
	}

	private void leftAction(Player player)
	{
		Location location = player.getTargetBlock(null, 5).getLocation();
		location.setY(location.getY()+1);
		Block block = location.getBlock();
		if (block.getType() == Material.AIR)
		{
			if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
			{
				Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL,firstSkillStack, firstSkillCoolTime);
				block.setBlockData(Bukkit.createBlockData(Material.LAVA));
				Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(),()->{
					new LavaTimer(block);
				},2 * 20,0);
			}
		}
	}
	
	public void passiveSkill(EntityDamageEvent event)
	{
		Player player = (Player) event.getEntity();
		DamageCause dc = event.getCause();
		if (dc.equals(DamageCause.LAVA) ||
			dc.equals(DamageCause.FIRE) ||
			dc.equals(DamageCause.FIRE_TICK))
		{
			event.setCancelled(true);
			player.setFireTicks(0);
		}
		else if (dc.equals(DamageCause.DROWNING))
			event.setDamage(event.getDamage() / 2);
	}
	
	public void initialize()
	{
		Player player = GameData.OnlinePlayer.get(playerName);
		Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{
			player.setMaximumAir(0);
			player.setRemainingAir(0);
		});

	}
	public void initializeReset()
	{
		Player player = GameData.OnlinePlayer.get(playerName);
		Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()-> {
			player.setMaximumAir(300);
			player.setRemainingAir(300);
		});
	}

	class LavaTimer extends TimerTask
	{
		Block block;
		LavaTimer(Block block)
		{
			this.block=block;
		}
		public void run()
		{
			block.setBlockData(Bukkit.createBlockData(Material.AIR));
		}
	}
	
}
