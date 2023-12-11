package septagram.Theomachy.Utility;

import org.bukkit.entity.Player;

public class DirectionChecker
{
	public static int PlayerDirection(Player player)
	{
		int direction = (int) player.getLocation().getYaw();
		if (direction>=0)
		{
			if (direction >= 338 || direction <= 22)
				return 0;
			else if (direction >= 23 && direction <= 67)
				return 1;
			else if (direction >= 68 && direction <= 112)
				return 2;
			else if (direction >= 113 && direction <= 157)
				return 3;
			else if (direction >= 158 && direction <= 202)
				return 4;
			else if (direction >= 203 && direction <= 247)
				return 5;
			else if (direction >= 248 && direction <= 292)
				return 6;
			else
				return 7;
		}
		else
		{
			if (direction >= -22 || direction <= -338)
				return 0;
			else if (direction >= -337 && direction <= -293)
				return 1;
			else if (direction >= -292 && direction <= -248)
				return 2;
			else if (direction >= -247 && direction <= -203)
				return 3;
			else if (direction >= -202 && direction <= -158)
				return 4;
			else if (direction >= -157 && direction <= -113)
				return 5;
			else if (direction >= -112 && direction <= -68)
				return 6;
			else
				return 7;
		}
	}		
	
}
