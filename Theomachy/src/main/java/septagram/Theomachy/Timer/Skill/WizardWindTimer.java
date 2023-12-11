package septagram.Theomachy.Timer.Skill;

import java.util.List;
import java.util.TimerTask;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WizardWindTimer extends TimerTask
{
	final private List<Player> targetList;
	final private Vector v;
	
	public WizardWindTimer(List<Player> targetList, Vector v)
	{
		this.targetList=targetList;
		this.v=v;
	}
	
	public void run()
	{
		for (Player e : targetList)
			e.setVelocity(v);
	}
}
