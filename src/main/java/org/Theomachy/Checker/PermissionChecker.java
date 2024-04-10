package org.Theomachy.Checker;

import org.Theomachy.Handler.Module.source.MessageModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionChecker
{
	private final MessageModule messageModule =new MessageModule();
	public boolean Player(Player player){
		if (player.isOp())
			return true;
		else{
			messageModule.sendPlayer(player,TheomachyMessage.ERROR_PERMISSION_DENIED.getMessage());
			return false;
		}
	}
	
	public boolean Player(CommandSender sender){
		if (sender.isOp())
			return true;
		else{
			messageModule.sendPlayer(sender,TheomachyMessage.ERROR_PERMISSION_DENIED.getMessage());
			return false;
		}
	}
}
