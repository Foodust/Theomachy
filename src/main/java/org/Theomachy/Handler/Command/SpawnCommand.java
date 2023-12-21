package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.SpawnModule;
import org.Theomachy.Utility.DefaultUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Checker.PermissionChecker;

public class SpawnCommand extends DefaultUtil {
	public void module(CommandSender sender, Command command, String label, String[] data)
	{
		if (PermissionChecker.Sender(sender)) {
			spawnModule.spawnSetting(sender, data);
		}
	}
}
