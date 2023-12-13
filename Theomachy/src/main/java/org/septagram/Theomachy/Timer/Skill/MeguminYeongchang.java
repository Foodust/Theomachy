package org.septagram.Theomachy.Timer.Skill;

import java.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.HUMAN.Megumin;

public class MeguminYeongchang extends TimerTask
{
	final Player player;
	final Block block;
	private int count = 3;
	
	public MeguminYeongchang(Player player, Block block)
	{
		this.player=player;
		this.block=block;
		player.sendMessage("영창을 시작합니다!!");
	}
	
	public void run()
	{
		if (count == 0)
		{
			player.getWorld().createExplosion(block.getLocation(), 6.0f);
			player.getInventory().remove(new ItemStack(Material.COBBLESTONE, 32));
			player.setHealth(0);
			player.sendMessage("폭렬 마법의 효과로 쓰러졌습니다!");
			
			Megumin.cancel=true;
			
		}
		else
		player.sendMessage("영창 완료까지 "+ChatColor.AQUA+count+ChatColor.WHITE+"초 남았습니다.");
		count--;
	}
}
