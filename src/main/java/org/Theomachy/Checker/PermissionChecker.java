package org.Theomachy.Checker;

import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionChecker
{
	public static boolean Player(Player player)
	{
		if (player.isOp())
			return true;
		else
		{
			player.sendMessage(TheomachyMessage.ERROR_PERMISSION_DENIED.getMessage());
			return false;
		}
	}
	
	public static boolean Sender(CommandSender sender)
	{
		if (sender.isOp())
			return true;
		else
		{
			sender.sendMessage(TheomachyMessage.ERROR_PERMISSION_DENIED.getMessage());
			return false;
		}
	}
}
