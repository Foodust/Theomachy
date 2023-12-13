package org.septagram.Theomachy.Ability.HUMAN;

import java.util.List;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Timer.CoolTime;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.DirectionChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Assasin extends Ability
{
	private final static String[] des= {
			   "암살자는 민첩한 몸놀림을 가지는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"더블 점프",
			   "점프한 후 현재 보는 방향으로 점프를 한 번 더 할 수 있습니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"기습",
			   "주변에 있는 적의 등으로 순간이동 합니다."};
	
	public Assasin(String playerName)
	{
		super(playerName,"암살자", 108, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		this.cool1=1;
		this.cool2=15;
		this.sta1=0;
		this.sta2=15;
		
		this.rank=2;
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
		Location temp = player.getLocation();
		Block b = temp.add(0,-1,0).getBlock();
		if ((b.getType()== Material.AIR) || (b.getType()==Material.SNOW) || (b.getType()==Material.STONE_SLAB))
		{	
			if ((!CoolTime.COOL0.containsKey(playerName+"0") && (PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))))
			{
			CoolTime.COOL0.put(playerName+"0", cool1);
			PlayerInventory.ItemRemove(player, Material.COBBLESTONE, sta1);
			World world = player.getWorld();
			Location location = player.getLocation();
			Vector v = player.getEyeLocation().getDirection();
			v.setY(0.5);			
			player.setVelocity(v);
			world.playEffect(location, Effect.ENDER_SIGNAL, 1);
			}
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta2))
		{
			boolean flag = true;
			List<Entity> entityList = player.getNearbyEntities(10, 10, 10);
			for (Entity e : entityList)
			{
				if (e instanceof Player)
				{
					Player target = (Player) e;
					
					String targetTeamName = GameData.PlayerTeam.get(target.getName());
					String playerTeamName = GameData.PlayerTeam.get(player.getName());
					if ((targetTeamName == null) || !(targetTeamName.equals(playerTeamName)))
					{
						Skill.Use(player, Material.COBBLESTONE, sta2, 2, cool2);
						Location fakeLocation = player.getLocation();
						Location location = target.getLocation();
						World world = player.getWorld();
						List<Player> playerlist = world.getPlayers();
						for (Player each : playerlist)
							each.hidePlayer(player);
						switch(DirectionChecker.PlayerDirection(target))
						{
						case 0:
							location.add(0,0,-1);
							break;
						case 1:
							location.add(0.7,0,-0.7);
							break;
						case 2:
							location.add(1,0,0);
							break;
						case 3:
							location.add(0.7,0,0.7);
							break;
						case 4:
							location.add(0,0,1);
							break;
						case 5:
							location.add(-0.7,0,0.7);
							break;
						case 6:
							location.add(-1,0,0);
							break;
						case 7:
							location.add(-0.7,0,-0.7);
							break;
						}
						Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{player.teleport(location);});
						world.dropItem(fakeLocation.add(0,1,0), new ItemStack(Material.POPPY, 1));
						for (Player each : playerlist)
							each.showPlayer(player);
						flag=false;
						break;
					}
				}
			}
			if (flag)
				player.sendMessage("스킬을 사용 할 수 있는 상대가 없습니다.");
		}
	}
}
