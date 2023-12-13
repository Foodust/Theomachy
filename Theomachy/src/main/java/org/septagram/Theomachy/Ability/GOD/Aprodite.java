package org.septagram.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.GetPlayerList;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Aprodite extends Ability{
	
	private final static String[] des= {
			  "아프로디테는 미의 신입니다.",
			  ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"매혹",
			   "가까이 있는 사람들을 끌어올 수 있습니다.",
			   "자신이 블록 위에 서 있고 웅크리지 않아야 발동합니다."};
	
	public Aprodite(String playerName)
	{
		super(playerName, "아프로디테", 13, true, false, false, des);
		Theomachy.log.info(playerName+"아프로디테");
		
		this.cool1=500;
		this.sta1=64;
		
		this.rank=4;
	}
	
	public void T_Active(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
			switch(EventFilter.PlayerInteract(event))
			{
			case 0:case 1:
				leftAction(player);
				break;
			}
		}
	}

	private void leftAction(Player player) {
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1)) {
			if(!player.isSneaking() && !player.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
				Skill.Use(player, Material.COBBLESTONE, sta1, 0, cool1);
				try {
					List<Player> list=GetPlayerList.getNearByNotTeamMembers(player, 20, 20, 20);


					for(Player e:list) {
						Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{e.teleport(player);});
						e.sendMessage(ChatColor.YELLOW+"미의 여신에게 이끌려갑니다!");
					}
				}catch(Exception e) {}
				
				player.sendMessage("미로 다른 사람들을 홀렸습니다.");
			}else {
				player.sendMessage(ChatColor.RED+"웅크리고 있거나 발 밑의 블록이 없어 능력이 발동되지 않았습니다.");
			}
		}

	}
	
}
