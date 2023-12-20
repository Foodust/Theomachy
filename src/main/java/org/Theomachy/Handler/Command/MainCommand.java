package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.AbilityModule;
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
			case COMMAND_ABILITY, COMMAND_ABILITY_A -> AbilityCommand.abilitySet(sender, data);
			case COMMAND_ABILITY_LIST -> AbilityModule.listOfAbilityPlayer(sender);
			case COMMAND_HELP -> AbilityCommand.abilityHelp(sender);
			case COMMAND_SPAWN, COMMAND_SPAWN_S -> SpawnCommand.module(sender, command, label, data);
			case COMMAND_TEAM, COMMAND_TEAM_T -> TeamCommand.setTeam(sender, command, label, data);
			case COMMAND_INFO -> TeamCommand.listOfTeam(sender, command, label, data);
			case COMMAND_CLEAR, COMMAND_CLEAR_C -> AbilityCommand.abilityCollTimeClear(sender);
			case COMMAND_BLACKLIST, COMMAND_BLACKLIST_B, COMMAND_BLACKLIST_BLACK -> BlacklistCommand.module(sender);
			case COMMAND_SETTING, COMMAND_SETTING_SET -> SettingCommand.module(sender);
			case COMMAND_GAMBLING, COMMAND_GAMBLING_G -> GamblingCommand.module(sender);
            default -> sender.sendMessage(TheomachyMessage.ERROR_WRONG_COMMAND.getMessage());
        }
	}
	
	public static void xCommandHandler(CommandSender sender, Command command, String label, String[] data)
	{
		String playerName = sender.getName();
		String targetName = data[0];
		Ability ability = GameData.playerAbility.get(playerName);
		if (ability != null)
		{
			if (GameData.onlinePlayer.containsKey(targetName))
				ability.targetSet(sender, targetName);
			else
				sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_ONLINE_PLAYER.getMessage() + targetName);
		}
		else
			sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_WHO_HAVE_ABILITY.getMessage());
	}
}
