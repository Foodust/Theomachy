package org.Theomachy.Handler.Command;

import java.util.Collection;

import org.Theomachy.Enum.TheomachyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Checker.PermissionChecker;

public class AbilityPlayerInfoCommand
{
	public static void showAllAbility(CommandSender sender)
	{
		if (PermissionChecker.Sender(sender))
		{
			if (!GameData.playerAbility.isEmpty())
			{
				Collection<Ability> ability = GameData.playerAbility.values();
				for (Ability e : ability)
					sender.sendMessage(ChatColor.WHITE+e.playerName+"  :  "+ChatColor.YELLOW+e.abilityName);
			}
			else
			{
				sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_HAVE_ABILITY_ALL_PLAYER.getMessage());
			}
		}
	}
}
