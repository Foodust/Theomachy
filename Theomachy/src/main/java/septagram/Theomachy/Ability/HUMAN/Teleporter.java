package septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Utility.BlockFilter;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Teleporter extends Ability
{
	private String abilitytarget;
	private final static String[] des= {
		   	  "텔레포터는 순간이동을 돕는 마법사입니다.",
		   	ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"텔레포팅",
			"25칸 이내의 목표 지점으로 텔레포트합니다." ,
			ChatColor.RED+"【고급】 "+ChatColor.WHITE+"치환",
			"타겟에 등록해 둔 자신의 팀원과 위치를 치환합니다.",
			"목표 지정: /x <대상>"};
	
	public Teleporter(String playerName)
	{
		super(playerName,"텔레포터", 104, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=25;
		this.cool2=30;
		this.sta1=15;
		this.sta2=25;
		
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
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))
		{
			Block block = player.getTargetBlock(null, 25);
			if (BlockFilter.AirToFar(player, block))
			{
				Location location0 = block.getLocation();
				Location location1 = block.getLocation();
				location0.setY(location0.getY()+1);
				location1.setY(location1.getY()+2);
				Block block0 = location0.getBlock();
				Block block1 = location1.getBlock();
				if ((block0.getType()==Material.AIR || block1.getType() == Material.SNOW)&&block1.getType()==Material.AIR)
				{
					Skill.Use(player, Material.COBBLESTONE, sta1, 1, cool1);
					Location plocation = player.getLocation();
					Location tlocation = block.getLocation();
					tlocation.setPitch(plocation.getPitch());
					tlocation.setYaw(plocation.getYaw());
					tlocation.setY(tlocation.getY()+1);
					tlocation.setX(tlocation.getX()+0.5);
					tlocation.setZ(tlocation.getZ()+0.5);
					player.teleport(tlocation);
				}
				else
					player.sendMessage("텔레포트 할 수 있는 공간이 없어 텔레포트에 실패 했습니다.");
			}
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta2))
		{
			if (abilitytarget != null)
			{
				Player target = GameData.OnlinePlayer.get(abilitytarget);
				if (target != null)
				{
					Location location = player.getLocation();
					location.setY(location.getY()-1);
					Skill.Use(player, Material.COBBLESTONE, sta2, 2, cool2);
					Location tloc = target.getLocation();
					Location ploc = player.getLocation();
					target.teleport(ploc);
					player.teleport(tloc);
					target.sendMessage("텔레포터의 능력에 의해 위치가 텔레포터의 위치로 변경되었습니다.");
				}
				else
					player.sendMessage("플레이어가 온라인이 아닙니다.");
			}
			else
				player.sendMessage("타겟이 지정되지 않았습니다. (타겟 등록법 : /x <Player>)");
		}
	}
	
	public void targetSet(CommandSender sender, String targetName)
	{
		String playerTeamName = GameData.PlayerTeam.get(playerName);
		String targetTeamName = GameData.PlayerTeam.get(targetName);
		if (playerTeamName != null &&
			targetTeamName != null &&
			playerTeamName.equals(targetTeamName))
		{
			if (!playerName.equals(targetName))
			{
				this.abilitytarget = targetName;
				sender.sendMessage("타겟을 등록했습니다.   "+ChatColor.GREEN+targetName);
			}
			else
				sender.sendMessage("자기 자신을 타겟으로 등록 할 수 없습니다.");
		}
		else
			sender.sendMessage("자신의 팀의 멤버가 아닙니다.");		
	}
}
