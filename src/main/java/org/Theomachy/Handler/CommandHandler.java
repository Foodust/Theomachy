package org.Theomachy.Handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Ability.Ability;
import org.Theomachy.DB.GameData;
import org.Theomachy.Handler.CommandModule.AbilityPlayerInfo;
import org.Theomachy.Handler.CommandModule.AbilitySet;
import org.Theomachy.Handler.CommandModule.Blacklist;
import org.Theomachy.Handler.CommandModule.Convi;
import org.Theomachy.Handler.CommandModule.CoolTimeHandler;
import org.Theomachy.Handler.CommandModule.GUISetting;
import org.Theomachy.Handler.CommandModule.GameHandler;
import org.Theomachy.Handler.CommandModule.Help;
import org.Theomachy.Handler.CommandModule.Spawn;
import org.Theomachy.Handler.CommandModule.Team;
import org.Theomachy.Handler.CommandModule.TeamInfo;
import org.Theomachy.Handler.CommandModule.Tip;

public class CommandHandler
{
	public static void tCommandHandler(CommandSender sender, Command command, String label, String[] data)
	{
		if (data[0].equals("start"))
			GameHandler.GameReady(sender);
		else if (data[0].equals("stop"))
			GameHandler.GameStop(sender);
		else if (data[0].equals("ability") || data[0].equals("a"))
			AbilitySet.Module(sender, command, label, data);
		else if (data[0].equals("alist"))
			AbilityPlayerInfo.showAllAbility(sender);
		else if (data[0].equals("help"))
			Help.Module(sender, command, label, data);
		else if (data[0].equals("spawn") || data[0].equals("s"))
			Spawn.Module(sender, command, label, data);
		else if (data[0].equals("team") || data[0].equals("t" ))
			Team.Module(sender, command, label, data);
		else if (data[0].equals("info") || data[0].equals("i"))
			TeamInfo.Module(sender, command, label, data);
		else if (data[0].equals("clear") || data[0].equals("c"))
			CoolTimeHandler.Module(sender, command, label, data);
		else if (data[0].equals("black")) 
			Blacklist.Module(sender);
		else if (data[0].equals("set"))
			GUISetting.Module(sender);
		else if (data[0].equals("con"))
			Convi.Module(sender);
		else if (data[0].equals("tip"))
			Tip.Module(sender);
		else
			sender.sendMessage("잘못된 명령입니다.");
	}
	
	public static void xCommandHandler(CommandSender sender, Command command, String label, String[] data)
	{
		String playerName = sender.getName();
		String targetName = data[0];
		Ability ability = GameData.PlayerAbility.get(playerName);
		if (ability != null)
		{
			if (GameData.OnlinePlayer.containsKey(targetName))
				ability.targetSet(sender, targetName);
			else
				sender.sendMessage("온라인 플레이어가 아닙니다.  "+targetName);
		}
		else
			sender.sendMessage("능력이 없습니다.");
	}
}
