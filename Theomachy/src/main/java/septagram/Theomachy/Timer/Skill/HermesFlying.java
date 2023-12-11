package septagram.Theomachy.Timer.Skill;

import java.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HermesFlying extends TimerTask
{
	final Player player;
	private int count = 3;
	
	public HermesFlying(Player player)
	{
		this.player=player;
		player.sendMessage("비행 할 수 있습니다.");
	}
	
	public void run()
	{
		if (count == 0)
		{
			player.sendMessage(ChatColor.RED+"비행시간이 종료되었습니다.");
			player.setAllowFlight(false);
			player.setFallDistance(0);
			this.cancel();
		}
		else
		player.sendMessage("비행시간이 "+ChatColor.AQUA+count+ChatColor.WHITE+"초 남았습니다.");
		count--;
	}
}
