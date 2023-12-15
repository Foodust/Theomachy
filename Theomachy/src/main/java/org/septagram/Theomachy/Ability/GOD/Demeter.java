package org.septagram.Theomachy.Ability.GOD;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Demeter extends Ability
{
	private final static String[] des= {
			AbilityInfo.Demeter.getName() + "는 곡식의 신입니다.",
			  NamedTextColor.AQUA+"【일반】 "+NamedTextColor.WHITE+"수확",
			   "빵을 얻을 수 있습니다.",
			   NamedTextColor.YELLOW+"【패시브】 "+NamedTextColor.WHITE+"풍요",
			   "항상 배고프지 않고, 체력 회복이 빠릅니다."};
	
	public Demeter(String playerName)
	{
		super(playerName, AbilityInfo.Demeter, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.normalSkillCoolTime =30;
		this.normalSkillStack =10;
		
		this.rank= AbilityRank.B;
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
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
			Inventory inventory = player.getInventory();
			inventory.addItem(new ItemStack(Material.BREAD, normalSkillStack));
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
