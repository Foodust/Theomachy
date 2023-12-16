package org.Theomachy.Handler.CommandModule;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.Theomachy.Ability.Ability;
import org.Theomachy.DB.GameData;
import org.Theomachy.Utility.PermissionChecker;

public class AbilityPlayerInfo
{
	public static void showAllAbility(CommandSender sender)
	{
		if (PermissionChecker.Sender(sender))
		{
			if (!GameData.PlayerAbility.isEmpty())
			{
				Collection<Ability> ability = GameData.PlayerAbility.values();
				for (Ability e : ability)
					sender.sendMessage(ChatColor.WHITE+e.playerName+"  :  "+ChatColor.YELLOW+e.abilityName);
			}
			else
			{
				sender.sendMessage("능력이 있는 플레이어가 없습니다.");
			}
		}
	}
}
