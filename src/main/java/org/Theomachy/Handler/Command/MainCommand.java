package org.Theomachy.Handler.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;

public class MainCommand
{
	public static void tCommandHandler(CommandSender sender, Command command, String label, String[] data)
	{
        switch (data[0]) {
            case "start" -> StartStopCommand.GameReady(sender);
            case "stop" -> StartStopCommand.GameStop(sender);
            case "ability", "a" -> AbilitySetCommand.Module(sender, command, label, data);
            case "alist" -> AbilityPlayerInfoCommand.showAllAbility(sender);
            case "help" -> HelpCommand.Module(sender, command, label, data);
            case "spawn", "s" -> SpawnCommand.Module(sender, command, label, data);
            case "team", "t" -> TeamCommand.Module(sender, command, label, data);
            case "info", "i" -> TeamInfoCommand.Module(sender, command, label, data);
            case "clear", "c" -> SpawnCommand.ClearCommand.Module(sender, command, label, data);
            case "black" -> BlacklistCommand.Module(sender);
            case "set" -> SettingCommand.Module(sender);
            case "con" -> GamblingCommand.Module(sender);
            case "tip" -> TipCommand.Module(sender);
            default -> sender.sendMessage("잘못된 명령입니다.");
        }
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
