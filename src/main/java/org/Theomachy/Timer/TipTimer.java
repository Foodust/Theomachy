package org.Theomachy.Timer;

import java.util.TimerTask;

import org.Theomachy.Data.VersionData;
import org.Theomachy.Handler.Module.source.MessageModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.Theomachy.Theomachy;

public class TipTimer extends TimerTask
{
	private final MessageModule messageModule = new MessageModule();
	int count= 1;
	int tipCount = 0;
	@Override
	public void run()
	{
//		if (!GameModule.Ready){
//			this.cancel();
//		}
		if (count%600 == 0)
		{
			if (Theomachy.SERVER_AUTO_SAVE)
			{
				Bukkit.getServer().savePlayers();
				messageModule.logInfo( VersionData.name + "오토세이브 완료");
			}
		}
		if (count % 180 == 0)
		{
			tipCount++;
			switch (tipCount){
				case 1-> messageModule.broadcastMessage(TheomachyMessage.TIP1.getMessage());
				case 2-> messageModule.broadcastMessage(TheomachyMessage.TIP2.getMessage());
				case 3-> messageModule.broadcastMessage(TheomachyMessage.TIP3.getMessage());
				case 4-> messageModule.broadcastMessage(TheomachyMessage.TIP4.getMessage());
				case 5-> messageModule.broadcastMessage(TheomachyMessage.TIP5.getMessage());
				case 6-> messageModule.broadcastMessage(TheomachyMessage.TIP6.getMessage());
				case 7-> messageModule.broadcastMessage(TheomachyMessage.HEY_DEVELOPER.getMessage());
				default -> tipCount = 0;
			}
		}
		if (count % 60 == 0)
		{
			long max = (int) (Runtime.getRuntime().maxMemory() / 1048576);
			long free = (int) (Runtime.getRuntime().freeMemory() / 1048576);
			long use = max - free;
			messageModule.logInfo(ChatColor.WHITE+"메모리(MB)   "+ChatColor.AQUA+ use + ChatColor.WHITE+ " / " + ChatColor.YELLOW + max);
			if (free < 3750)
			{
				messageModule.logInfo("메모리 부족, 메모리 청소중...");
				System.gc();
			}
		}
		count++;
	}
}
