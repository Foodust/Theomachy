package septagram.Theomachy.Timer.Skill;

import java.util.Random;
import java.util.TimerTask;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MeteorTimer extends TimerTask
{
	private int count;
	private World world;
	private Player player;
	private Location location;
	private Random random = new Random();
	private final Vector v = new Vector(0d,-20d,0d);
	private final Vector speed = new Vector(0d,-3d, 0d);
	
	
	public MeteorTimer(Player player,Location location, int count)
	{
		this.player=player;
		this.world=player.getWorld();
		this.location=location.add(0,20,0);
		this.count=count;
	}
	
	public void run()
	{
		if (count > 0)
		{
			int X = random.nextInt(11)-5;
			int Z = random.nextInt(11)-5;
			Fireball fireball = world.spawn(location.add(X, 0, Z), Fireball.class);
			fireball.setShooter(player);
			fireball.setDirection(v);
			fireball.setVelocity(speed);
			location.add(-X, 0, -Z);
		}
		else
			this.cancel();
		count--;
	}
}
