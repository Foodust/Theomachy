package septagram.Theomachy.Ability.GOD;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Hades extends Ability
{	
	private final static String[] des= {
			   "하데스는 죽음의 신입니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"사망 지배",
			   "사망 시 60% 확률로 아이템을 잃지 않습니다." ,
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"나락 Ⅰ",
			   "자신과 주변의 모든 것이 나락으로 떨어집니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"나락 Ⅱ",
			   "자신을 제외한 주변의 모든 것이 나락으로 떨어집니다."};
	
	public Hades(String playerName)
	{
		super(playerName,"하데스", 3, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=100;
		this.cool2=300;
		this.sta1=20;
		this.sta2=35;
		
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
			Skill.Use(player, Material.COBBLESTONE, sta1, 1, cool1);
			Entity entity=player;
			Location location = player.getLocation();
			location.setY(-2.0d);
			List<Entity> entitylist = entity.getNearbyEntities(2, 2, 2);
			for (Entity e : entitylist)
			{
				if (e instanceof LivingEntity)
				{
					e.teleport(location);
					if (e.getType() == EntityType.PLAYER)
						((Player)e).sendMessage("죽음의 신의 능력에 의해 나락으로 떨어집니다.");
				}
			}
			player.teleport(location);
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta2))
		{
			Skill.Use(player, Material.COBBLESTONE, sta2, 2,cool2);
			Entity entity=player;
			Location location = player.getLocation();
			location.setY(-2.0d);
			List<Entity> entitylist = entity.getNearbyEntities(4, 4, 4);
			for (Entity e : entitylist)
			{
				if (e instanceof LivingEntity)
				{
					e.teleport(location);
					if (e.getType() == EntityType.PLAYER)
						((Player)e).sendMessage(ChatColor.RED+"죽음의 신의 능력에 의해 나락으로 떨어집니다.");
				}
			}
		}
	}
	
	private ItemStack[] inventory;
	private ItemStack[] armor;
	public void T_Passive(PlayerDeathEvent event)
	{
		Random r=new Random();
		if (r.nextInt(10) <=6)
		{
			this.inventory=event.getEntity().getInventory().getContents();
			this.armor = event.getEntity().getInventory().getArmorContents();
			event.getDrops().clear();
		}
		else
			event.getEntity().sendMessage("아이템을 모두 잃었습니다.");
	}
	public void T_Passive(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		if (inventory !=null)
			player.getInventory().setContents(inventory);
		if (armor !=null)
		player.getInventory().setArmorContents(armor);
		inventory = null;
		armor = null;
	}
}

