package org.septagram.Theomachy.Timer.Skill;

import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.septagram.Theomachy.Theomachy;

public class HoreunTimer extends TimerTask{
	
	final Player player;
	private int count = 3;
	private Location loc;
	
	public HoreunTimer(Player player, Location loc)
	{
		this.player=player;
		this.loc=loc;
	}
	
	public void run()
	{

		if (count == 0)
		{
			player.sendMessage("10초 전의 위치로 되돌아갑니다!");
			Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{player.teleport(loc);});
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*2, 0));
			this.cancel();
		}
		else {
			player.sendMessage(ChatColor.AQUA + String.valueOf(count) + ChatColor.WHITE + "초 뒤 되돌아갑니다.");
		}

		count--;
	}

}
