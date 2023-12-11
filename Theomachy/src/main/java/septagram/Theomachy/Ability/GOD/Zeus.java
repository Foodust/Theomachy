package septagram.Theomachy.Ability.GOD;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.BlockFilter;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Zeus extends Ability
{
	private final static String[] des= {
			   "제우스는 신들의 왕이자, 번개의 신입니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"전기 속성",
			   "패시브 능력으로 번개와 폭발 데미지를 받지 않습니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"번개 Ⅰ",
			   "목표 지역에 번개를 떨어뜨립니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"태풍 Ⅱ",
			   "목표 지역에 대량의 번개를 떨어뜨립니다."};
	
	public Zeus(String playerName)
	{
		super(playerName,"제우스", 1, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=90;
		this.cool2=180;
		this.sta1=15;
		this.sta2=30;
		
		this.rank=4;
	}
	
	public void T_Active(PlayerInteractEvent event)
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
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))
		{
			Block block = player.getTargetBlock(null, 50);
			if (BlockFilter.AirToFar(player, block))
			{
				Skill.Use(player, Material.COBBLESTONE, sta1, 1, cool1);
				World world = player.getWorld();
				Location location = block.getLocation();
				world.strikeLightning(location);
			}
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta2))
		{
			Block block = player.getTargetBlock(null, 30);
			if (BlockFilter.AirToFar(player, block))
			{
				Skill.Use(player, Material.COBBLESTONE, sta2, 2, cool2);
				World world = player.getWorld();
				Location location = block.getLocation();
				Random random = new Random();
				for (int i=0; i<5; i++)
				{
					int X = random.nextInt(11)-5;
					int Z = random.nextInt(11)-5;
					location.add(X, 0, Z);
					world.strikeLightning(location);
					location.add(-X, 0, -Z);
				}
			}
		}
	}
	
	public void T_Passive(EntityDamageEvent event)
	{
		if (event.getCause() == DamageCause.LIGHTNING || event.getCause() == DamageCause.ENTITY_EXPLOSION)
			event.setCancelled(true);
	}
}
