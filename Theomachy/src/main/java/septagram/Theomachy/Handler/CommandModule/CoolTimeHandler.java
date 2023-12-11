package septagram.Theomachy.Handler.CommandModule;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import septagram.Theomachy.Timer.CoolTime;
import septagram.Theomachy.Utility.PermissionChecker;

public class CoolTimeHandler
{
	public static void Module(CommandSender sender, Command command, String label, String[] data)
	{
		if (PermissionChecker.Sender(sender))
		{
			CoolTime.ini=true;
			Bukkit.broadcastMessage("관리자가 모든 쿨타임을 초기화 하였습니다.");
			
		}
	}
}