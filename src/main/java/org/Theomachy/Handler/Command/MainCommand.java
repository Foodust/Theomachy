package org.Theomachy.Handler.Command;

import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;

import java.util.Objects;

public class MainCommand
{
	public static void tCommandHandler(CommandSender sender, Command command, String label, String[] data)
	{
		TheomachyMessage message = TheomachyMessage.getByMessage(data[0]);
        switch (Objects.requireNonNull(message)) {
			case COMMAND_START -> StartStopCommand.GameReady(sender);
			case COMMAND_STOP -> StartStopCommand.GameStop(sender);
			case COMMAND_ABILITY, COMMAND_ABILITY_A -> AbilitySetCommand.Module(sender, command, label, data);
			case COMMAND_ABILITY_LIST -> AbilityPlayerInfoCommand.showAllAbility(sender);
			case COMMAND_HELP -> HelpCommand.Module(sender, command, label, data);
			case COMMAND_SPAWN, COMMAND_SPAWN_S -> SpawnCommand.Module(sender, command, label, data);
			case COMMAND_TEAM, COMMAND_TEAM_T -> TeamCommand.Module(sender, command, label, data);
			case COMMAND_INFO -> TeamInfoCommand.Module(sender, command, label, data);
			case COMMAND_CLEAR, COMMAND_CLEAR_C -> SpawnCommand.ClearCommand.Module(sender, command, label, data);
			case COMMAND_BLACKLIST, COMMAND_BLACKLIST_B, COMMAND_BLACKLIST_BLACK -> BlacklistCommand.Module(sender);
			case COMMAND_SETTING, COMMAND_SETTING_SET -> SettingCommand.Module(sender);
			case COMMAND_GAMBLING, COMMAND_GAMBLING_G -> GamblingCommand.Module(sender);
            default -> sender.sendMessage(TheomachyMessage.WRONG_COMMAND.getMessage());
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
				sender.sendMessage(TheomachyMessage.DOES_NOT_ONLINE_PLAYER.getMessage() + targetName);
		}
		else
			sender.sendMessage(TheomachyMessage.DOES_NOT_HAVE_ABILITY.getMessage());
	}
}
