package septagram.Theomachy.Ability.GOD;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Demeter extends Ability
{
	private final static String[] des= {
			  "데메테르는 곡식의 신입니다.",
			  ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"수확",
			   "빵을 얻을 수 있습니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"풍요",
			   "항상 배고프지 않고, 체력 회복이 빠릅니다."};
	
	public Demeter(String playerName)
	{
		super(playerName,"데메테르", 4, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=30;
		this.sta1=10;
		
		this.rank=2;
	}

	public void T_Active(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
			switch(EventFilter.PlayerInteract(event))
			{
			case 0:case 1:case 2:case 3:
				Action(player);
				break;
			}
		}
	}

	private void Action(Player player)
	{
		if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))
		{
			Skill.Use(player, Material.COBBLESTONE, sta1, 0,cool1);
			Inventory inventory = player.getInventory();
			inventory.addItem(new ItemStack(Material.BREAD,sta1));
		}
	}
	
	public void T_Passive(FoodLevelChangeEvent event)
	{
		((Player)event.getEntity()).setFoodLevel(20);
		event.setCancelled(true);
	}
	
	public void T_Passive(EntityRegainHealthEvent event)
	{
		event.setAmount(event.getAmount() / 2);
	}
}
