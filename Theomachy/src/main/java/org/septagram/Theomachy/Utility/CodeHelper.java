package org.septagram.Theomachy.Utility;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.septagram.Theomachy.DB.AbilityData;
import org.septagram.Theomachy.Theomachy;

public class CodeHelper
{

	public static void ShowAbilityCodeNumber(CommandSender sender)
	{
		if (sender instanceof Player)
			showCode(sender);
		else
			Theomachy.log.info("이 명령어는 게임에서 실행해 주십시오.");
	}
	
	private static void showCode(CommandSender sender)
	{
		
		sender.sendMessage(ChatColor.GOLD+" 【 신 】 ");
		
		for(int i = 1; i<= AbilityData.GOD_ABILITY_NUMBER; i++) {
			sender.sendMessage(ChatColor.YELLOW + "【 "+i+" 】"+ChatColor.WHITE+ReturnAbilityName.name(i));
		}
		
		sender.sendMessage(ChatColor.AQUA+" 【 인간 】 ");
		
		for(int i=101;i<=AbilityData.HUMAN_ABILITY_NUMBER+100;i++) {
			sender.sendMessage(ChatColor.YELLOW + "【 "+i+" 】"+ChatColor.WHITE+ReturnAbilityName.name(i));
		}
		
	}
	
}
