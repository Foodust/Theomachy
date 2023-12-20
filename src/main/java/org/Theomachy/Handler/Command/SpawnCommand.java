package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.SpawnModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Checker.PermissionChecker;

public class SpawnCommand
{
	public static void module(CommandSender sender, Command command, String label, String[] data)
	{
		if (PermissionChecker.Sender(sender)) {
			SpawnModule.spawnSetting(sender, data);
		}
	}
}
