package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.BlockFilter;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;

public class Megumin extends Ability {

	private final static String[] des= {
			"이 능력은 메구밍!",
			ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"폭렬 ♪",
			"게임 중 한 번만 영창 후 전방의 블럭에 폭렬 마법을 날립니다.",
			"사용 후 즉시 쓰러집니다."};
	
	public Megumin(String playerName) {
		super(playerName, "메구밍", 128, true, false, false, des);
		
		this.rank=4;
		
		this.sta1=32;
		this.cool1=0;
	}

	public static boolean cancel=false;
	
	public void T_Active(PlayerInteractEvent event){
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
		if(PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1)) {
			if(!cancel) {
				Block block=player.getTargetBlock(null, 25);
				if (BlockFilter.AirToFar(player, block))
				{
					cancel=true;
				}
			}
			else {
				player.sendMessage("더 이상 쓸 수 없습니다...");
			}
		}
	}
	
	public void conditionSet(){
		cancel=false;
	}
	
}
