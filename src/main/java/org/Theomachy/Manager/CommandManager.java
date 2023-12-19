package org.Theomachy.Manager;

import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;
import org.Theomachy.Theomachy;
import org.Theomachy.Handler.Command.MainCommand;

import java.util.Objects;


public class CommandManager implements CommandExecutor
{
	public CommandManager(Theomachy t)
	{
		Objects.requireNonNull(t.getCommand("t")).setExecutor(this);
		Objects.requireNonNull(t.getCommand("x")).setExecutor(this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] data) {

		if (label.equals(TheomachyMessage.COMMAND_T.getMessage()))
		{
			if (data.length==0) //설명 보기
			{
				sender.sendMessage(TheomachyMessage.EXPLAIN_COMMAND.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_GAME_START.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_GAME_STOP.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_LIST.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_SET.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_HELP.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_TEAM_SPAWN.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_TEAM_ADD.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_TEAM_LIST.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_CLEAR.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_BLACKLIST.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_SETTING.getMessage());
				sender.sendMessage(TheomachyMessage.EXPLAIN_GAMBLING.getMessage());
			}
			else
				MainCommand.tCommandHandler(sender, command, label, data);
		}
		else if (label.equalsIgnoreCase(TheomachyMessage.COMMAND_X.getMessage()))
		{
			if (data.length==0) //설명 보기
				sender.sendMessage(TheomachyMessage.EXPLAIN_X_COMMAND.getMessage());
			else
				MainCommand.xCommandHandler(sender, command, label, data);
		}
		return true;
	}
}
