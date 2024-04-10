package org.Theomachy.Handler.Manager;

import org.Theomachy.Handler.Module.source.MessageModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;
import org.Theomachy.Theomachy;
import org.Theomachy.Handler.Command.MainCommand;

import java.util.Objects;


public class CommandManager  implements CommandExecutor
{
	private final MessageModule messageModule = new MessageModule();
	private final MainCommand mainCommand = new MainCommand();
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
				messageModule.sendPlayer(sender, TheomachyMessage.EXPLAIN_THEOMACHY_COMMAND.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_GAME_START.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_GAME_STOP.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_ABILITY_LIST.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_ABILITY_SET.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_ABILITY_HELP.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_TEAM_SPAWN.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_TEAM_ADD.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_TEAM_LIST.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_CLEAR.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_BLACKLIST.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_SETTING.getMessage());
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_GAMBLING.getMessage());
			}
			else
				mainCommand.tCommandHandler(sender, command, label, data);
		}
		else if (label.equalsIgnoreCase(TheomachyMessage.COMMAND_X.getMessage()))
		{
			if (data.length==0) //설명 보기
				messageModule.sendPlayer(sender,TheomachyMessage.EXPLAIN_X_COMMAND.getMessage());
			else
				mainCommand.xCommandHandler(sender, command, label, data);
		}
		return true;
	}
}
