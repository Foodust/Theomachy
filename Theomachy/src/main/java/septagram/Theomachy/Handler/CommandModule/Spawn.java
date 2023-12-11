package septagram.Theomachy.Handler.CommandModule;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Utility.PermissionChecker;

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
			else
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
		}
	}
}
