package org.Theomachy.Handler.CommandModule;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Timer.CoolTime;
import org.Theomachy.Utility.PermissionChecker;

public class CoolTimeHandler
{
	public static void Module(CommandSender sender, Command command, String label, String[] data)
	{
		if (PermissionChecker.Sender(sender))
		{
			CoolTime.init =true;
			Bukkit.broadcastMessage("관리자가 모든 쿨타임을 초기화 하였습니다.");
		}
	}
}