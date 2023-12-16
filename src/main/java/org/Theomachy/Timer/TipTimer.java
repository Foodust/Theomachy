package org.Theomachy.Timer;

import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.Theomachy.Theomachy;
import org.Theomachy.Handler.CommandModule.GameHandler;

public class TipTimer extends TimerTask
{
	int count=1;
	
	public void run()
	{
		if (!GameHandler.Ready)
			this.cancel();
		if (count%1200 == 0)
		{
			if (Theomachy.AUTO_SAVE)
			{
				Bukkit.getServer().savePlayers();
				Theomachy.log.info("[신들의전쟁] 오토세이브 완료");
			}
			
		}
		if (count%720 == 0)
		{
			long max = (int) (Runtime.getRuntime().maxMemory() / 1048576);
			long free = (int) (Runtime.getRuntime().freeMemory() / 1048576);
			long use = max - free;
			Bukkit.broadcastMessage(ChatColor.WHITE+"메모리(MB)   "+ChatColor.AQUA+String.valueOf(use)+ChatColor.WHITE+" / "+ChatColor.YELLOW+String.valueOf(max));
			
			if (free < 375)
			{
				System.out.println("메모리 부족, 메모리 청소중...");
				System.gc();
				long free2 = (Runtime.getRuntime().freeMemory() / 1048576);
				System.out.println("현재 사용 가능 메모리 : "+free2+"MB");
			}
		}
		count++;
	}
}
