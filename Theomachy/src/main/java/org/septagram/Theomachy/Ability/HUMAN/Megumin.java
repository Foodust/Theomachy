package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Utility.BlockFilter;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;

public class Megumin extends Ability {

	private final static String[] des= {
			"이 능력은 " + AbilityInfo.Megumin.getName() + "!",
			ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"폭렬 ♪",
			"게임 중 한 번만 영창 후 전방의 블럭에 폭렬 마법을 날립니다.",
			"사용 후 즉시 쓰러집니다."};
	
	public Megumin(String playerName) {
		super(playerName, AbilityInfo.Megumin, true, false, false, des);
		
		this.rank=4;
		
		this.firstSkillStack =32;
		this.firstSkillCoolTime =0;
	}

	public static boolean cancel=false;
	
	public void activeSkill(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR,LEFT_CLICK_BLOCK -> leftAction(player);
            }
		}
	}

	private void leftAction(Player player) {
		if(PlayerInventory.ItemCheck(player, material , firstSkillStack)) {
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
	
	public void initialize(){
		cancel=false;
	}
	
}
