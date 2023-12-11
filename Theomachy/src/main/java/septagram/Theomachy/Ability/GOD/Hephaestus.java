package septagram.Theomachy.Ability.GOD;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Hephaestus extends Ability
{
	private final static String[] des= {
			  "헤파이토스는 불의 신입니다.",
			  ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"화염 속성",
			  "불에 관한 데미지를 일절 받지 않으나, 물에 들어가면 데미지를 입습니다.",
			  ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"용암",
			   "블럭을 클릭하면 용암을 놓습니다. 놓은 용암은 2초 뒤 사라집니다.",};
	
	public Hephaestus(String playerName)
	{
		super(playerName,"헤파이토스", 9, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=10;
		this.sta1=1;
		
		this.rank=2;
	}
	
	public void T_Active(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
			switch(EventFilter.PlayerInteract(event))
			{
			case 1:
				leftAction(player);
				break;
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
			if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))
			{
				Skill.Use(player, Material.COBBLESTONE, sta1, 0, cool1);
				block.setBlockData(Bukkit.createBlockData(Material.LAVA));
				Timer t = new Timer();
				t.schedule(new LavaTimer(block), 2000);
			}
		}
	}
	
	public void T_Passive(EntityDamageEvent event)
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
	
	public void conditionSet()
	{
		Player player = GameData.OnlinePlayer.get(playerName);
		player.setMaximumAir(0);
		player.setRemainingAir(0);
	}
	
	
	public void conditionReSet()
	{
		Player player = GameData.OnlinePlayer.get(playerName);
		player.setMaximumAir(300);
		player.setRemainingAir(300);
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
