package septagram.Theomachy.Timer.Skill;

import java.util.List;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import septagram.Theomachy.DB.GameData;

public class ClockingTimer extends TimerTask
{
	List<Player> targetList;
	Player player;
	
	public ClockingTimer(List<Player> targetList, Player player)
	{
		this.targetList=targetList;
		this.player=player;
	}
	
	public void run()
	{
		try{
		if (GameData.PlayerAbility.get(player.getName()).flag)
		{
			player.sendMessage("은신 시간이 종료되었습니다.");
			GameData.PlayerAbility.get(player.getName()).flag = false;
		}
		for (Player e : targetList)
			e.showPlayer(player);
		}
		catch (Exception e)
		{
			Bukkit.broadcastMessage(e.getLocalizedMessage()+"");
		}
		this.cancel();
		
	}
}
