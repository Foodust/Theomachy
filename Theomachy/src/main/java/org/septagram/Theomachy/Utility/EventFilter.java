package org.septagram.Theomachy.Utility;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventFilter
{
	public static Action PlayerInteract(PlayerInteractEvent event)
	{
        return event.getAction();
	}
}
