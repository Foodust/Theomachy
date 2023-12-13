package org.septagram.Theomachy.Ability.GOD;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilitySet;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Demeter extends Ability
{
	private final static String[] des= {
			AbilitySet.Demeter.getName() + "는 곡식의 신입니다.",
			  ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"수확",
			   "빵을 얻을 수 있습니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"풍요",
			   "항상 배고프지 않고, 체력 회복이 빠릅니다."};
	
	public Demeter(String playerName)
	{
		super(playerName, AbilitySet.Demeter, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =30;
		this.firstSkillStack =10;
		
		this.rank=2;
	}

	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR,LEFT_CLICK_BLOCK,RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> Action(player);
            }
		}
	}

	private void Action(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL,firstSkillStack, firstSkillCoolTime);
			Inventory inventory = player.getInventory();
			inventory.addItem(new ItemStack(Material.BREAD, firstSkillStack));
		}
	}
	
	public void passiveSkill(FoodLevelChangeEvent event)
	{
		((Player)event.getEntity()).setFoodLevel(20);
		event.setCancelled(true);
	}
	
	public void passiveSkill(EntityRegainHealthEvent event)
	{
		event.setAmount(event.getAmount() / 2);
	}
}
