package septagram.Theomachy.Timer.Skill;

import java.util.List;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import septagram.Theomachy.DB.GameData;

public class ApollonPlayerScorching extends TimerTask
{
	final String playerName;
	private int count;
	
	public ApollonPlayerScorching(Player player, int count)
	{
		this.playerName=player.getName();
		this.count=count;
		Bukkit.broadcastMessage(ChatColor.RED+"태양이 매우 뜨거워집니다.");
	}
	public void run()
	{
		if(count>0)
		{
			List<Player> playerList = GameData.OnlinePlayer.get(playerName).getWorld().getPlayers();
			for (Player e : playerList)
			{
				if (e.getName() != playerName && e.getLocation().getBlock().getLightLevel() == 15)
					e.setFireTicks(100);
			}
		}
		else
		{
			Bukkit.broadcastMessage("태양이 힘을 잃었습니다.");
			World world = GameData.OnlinePlayer.get(playerName).getWorld();
			world.setTime(18000);
			this.cancel();
		}
		count--;
	}
}
