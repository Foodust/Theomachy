package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.source.SpawnModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Checker.PermissionChecker;

public class SpawnCommand  {
	private final SpawnModule spawnModule = new SpawnModule();
	private final PermissionChecker permissionChecker = new PermissionChecker();
	public void module(CommandSender sender, Command command, String label, String[] data)
	{
		if (permissionChecker.Player(sender)) {
			spawnModule.spawnSetting(sender, data);
		}
	}
}
