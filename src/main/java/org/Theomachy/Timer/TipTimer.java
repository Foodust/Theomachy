package org.Theomachy.Timer;

import java.util.TimerTask;

import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.Theomachy.Theomachy;

public class TipTimer extends TimerTask
{
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
				Theomachy.log.info("[신들의전쟁] 오토세이브 완료");
			}
		}
		if (count % 180 == 0)
		{
			tipCount++;
			switch (tipCount){
				case 1-> Bukkit.broadcastMessage(TheomachyMessage.TIP1.getMessage());
				case 2-> Bukkit.broadcastMessage(TheomachyMessage.TIP2.getMessage());
				case 3-> Bukkit.broadcastMessage(TheomachyMessage.TIP3.getMessage());
				case 4-> Bukkit.broadcastMessage(TheomachyMessage.TIP4.getMessage());
				case 5-> Bukkit.broadcastMessage(TheomachyMessage.TIP5.getMessage());
				case 6-> Bukkit.broadcastMessage(TheomachyMessage.TIP6.getMessage());
				case 7-> Bukkit.broadcastMessage(TheomachyMessage.HEY_DEVELOPER.getMessage());
				default -> tipCount = 0;
			}
		}
		if (count % 60 == 0)
		{
			long max = (int) (Runtime.getRuntime().maxMemory() / 1048576);
			long free = (int) (Runtime.getRuntime().freeMemory() / 1048576);
			long use = max - free;
			Theomachy.log.info(ChatColor.WHITE+"메모리(MB)   "+ChatColor.AQUA+ use + ChatColor.WHITE+ " / " + ChatColor.YELLOW + max);
			if (free < 3750)
			{
				Theomachy.log.info("메모리 부족, 메모리 청소중...");
				System.gc();
			}
		}
		count++;
	}
}
