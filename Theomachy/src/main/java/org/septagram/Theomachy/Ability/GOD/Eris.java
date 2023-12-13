package org.septagram.Theomachy.Ability.GOD;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilitySet;
import org.septagram.Theomachy.Theomachy;

public class Eris extends Ability{

	private final static String[] des= {
			AbilitySet.Eris.getName() + "는 불화의 여신입니다.",
			ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"시기",
			"자신을 공격한 플레이어를 20% 확률로 밀쳐냅니다."};
	
	public Eris(String playerName) {
		
		super(playerName, AbilitySet.Eris, false, true, false, des);
		
		this.rank=3;
		
	}

	public void passiveSkill(EntityDamageByEntityEvent event) {
		Player eris = (Player)event.getEntity();
		Player damager = (Player) event.getDamager();
		Random random = new Random();
		int rn = random.nextInt(5);
		if(eris.getName().equals(playerName)){
			if(rn==0){
				Location psloc = eris.getLocation();
				Location daloc = psloc;
				daloc.setX(psloc.getX()+5);
				daloc.setZ(psloc.getZ()+5);
				Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{damager.teleport(daloc);});
				eris.sendMessage(ChatColor.RED+"상대를 밀쳤습니다!");
				damager.sendMessage(ChatColor.RED+"에리스에 의해 밀쳐졌습니다.");
			}
		}
	}
	
}
