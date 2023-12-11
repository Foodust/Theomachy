package septagram.Theomachy.Utility;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventFilter
{
	public static int PlayerInteract(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		if (action.equals(Action.LEFT_CLICK_AIR))
			return 0;
		else if (action.equals(Action.LEFT_CLICK_BLOCK))
			return 1;
		else if (action.equals(Action.RIGHT_CLICK_AIR))
			return 2;
		else if (action.equals(Action.RIGHT_CLICK_BLOCK))
			return 3;
		else
			return -1;
	}
}
