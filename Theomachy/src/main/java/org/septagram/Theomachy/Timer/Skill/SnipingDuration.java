package org.septagram.Theomachy.Timer.Skill;

import java.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.septagram.Theomachy.Ability.HUMAN.Sniper;

public class SnipingDuration extends TimerTask {

	final Sniper sniper;
	final Player player;
	int count;
	
	public SnipingDuration(Player player, Sniper sniper, int count)
	{
		this.sniper=sniper;
		this.player = player;
		this.count = count;
	}
	
	@Override
	public void run()
	{
		if (count > 0)
			player.sendMessage(ChatColor.RED+"[스나이핑 모드] "+ChatColor.WHITE+count+"초 전");
		else if (count == 0)
		{
			player.sendMessage(ChatColor.RED+"[스나이핑 모드] "+ChatColor.AQUA+"ON");
			sniper.sniping = true;
		}
		if (!player.isSneaking())
		{
			sniper.ready=false;
			sniper.sniping=false;
			player.sendMessage(ChatColor.RED+"[스나이핑 모드] "+ChatColor.RED+"OFF");
			this.cancel();
		}
		count--;
	}

}
