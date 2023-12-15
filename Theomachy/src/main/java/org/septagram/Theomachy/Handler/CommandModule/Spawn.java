package org.septagram.Theomachy.Handler.CommandModule;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Utility.PermissionChecker;

public class Spawn
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
				Player player=(Player)sender;
				String teamName=data[1];
				Location location = null;
				location.setX(Integer.parseInt(data[2]));
				location.setY(Integer.parseInt(data[3]));
				location.setZ(Integer.parseInt(data[4]));
				GameData.SpawnArea.put(teamName, location);
				player.sendMessage( ChatColor.WHITE + data[2]+" " + data[3]+ " " + data[4] + "가 팀 "+ChatColor.DARK_AQUA+teamName+ChatColor.WHITE+" 의 스폰지역으로 설정되었습니다.");
			}
			else if(data.length == 3)
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
}
