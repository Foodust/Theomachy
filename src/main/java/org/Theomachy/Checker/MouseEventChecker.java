package org.Theomachy.Checker;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MouseEventChecker
{
	public static Action PlayerInteract(PlayerInteractEvent event)
	{
        return event.getAction();
	}
}
