package septagram.Theomachy.Timer.Skill;

import java.util.List;
import java.util.TimerTask;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WizardDisasterTimer extends TimerTask
{
	final private List<Player> targetList;
	final private Player player;
	
	public WizardDisasterTimer(List<Player> targetList, Player player)
	{
		this.targetList=targetList;
		this.player=player;
	}
	
	public void run()
	{
		World world = player.getWorld();
		for (Player e : targetList)
		{
			Location location = e.getLocation();
			world.strikeLightning(location);
			e.setFireTicks(100);
		}
	}
}
