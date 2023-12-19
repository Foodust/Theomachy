package org.Theomachy.Handler.Command;

import org.Theomachy.Timer.CoolTimeTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Data.GameData;
import org.Theomachy.Checker.PermissionChecker;

public class SpawnCommand
{
	public static void Module(CommandSender sender, Command command, String label, String[] data)
	{
		if (PermissionChecker.Sender(sender))
		{
			if(data.length==1)
			{
				Player player=(Player)sender;
				World world=player.getWorld();
				Location location=player.getLocation();
				int x=location.getBlockX();
				int y=location.getBlockY();
				int z=location.getBlockZ();
				world.setSpawnLocation(x, y, z);
				player.sendMessage("현재 위치가 모든 플레이어의 스폰지역으로 설정되었습니다.");				
			}
			else if(data.length == 5){
				World world = Bukkit.getWorlds().get(0);
				String teamName=data[1];
				Location location = new Location(world,
						Double.parseDouble(data[2]),
						Double.parseDouble(data[3]),
						Double.parseDouble(data[4]));
				GameData.SpawnArea.put(teamName, location);
				Bukkit.broadcastMessage( ChatColor.WHITE + data[2]+" " + data[3]+ " " + data[4] + "가 팀 "+ChatColor.DARK_AQUA+teamName+ChatColor.WHITE+" 의 스폰지역으로 설정되었습니다.");
			}
			else if(data.length == 2)
			{
				Player player=(Player)sender;
				String teamName=data[1];
				Location location=player.getLocation().getBlock().getLocation();
				location.setX(location.getX()+0.5);
				location.setZ(location.getZ()+0.5);
				location.setYaw((int)player.getLocation().getYaw());
				GameData.SpawnArea.put(teamName, location);
				player.sendMessage("현재 위치가 팀 "+ChatColor.DARK_AQUA+teamName+ChatColor.WHITE+" 의 스폰지역으로 설정되었습니다.");	
			}
			else{
				sender.sendMessage(ChatColor.YELLOW + "/t  spawn(s)   " + ChatColor.AQUA + "<TeamName>  스폰지역으로 설정합니다.");
				sender.sendMessage("좌표로도 등록할 수 있습니다.");
				sender.sendMessage("ex) /t s  팀    X  Y  Z");
			}
		}
	}

	public static class ClearCommand
	{
		public static void Module(CommandSender sender, Command command, String label, String[] data)
		{
			if (PermissionChecker.Sender(sender))
			{
				CoolTimeTimer.init =true;
				Bukkit.broadcastMessage("관리자가 모든 쿨타임을 초기화 하였습니다.");
			}
		}
	}
}
