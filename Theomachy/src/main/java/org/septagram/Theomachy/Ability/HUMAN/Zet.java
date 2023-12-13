package org.septagram.Theomachy.Ability.HUMAN;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Ability.Ability;

public class Zet extends Ability {
	
	private final static String[] des= { 
		"제트 기관은 내연 기관의 일종입니다.",
		ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"시동",
		"불에 타면 높은 확률로 동력이 생겨 빨라집니다.",
		"능력에 의한 가속은 다른 가속 효과와 중첩되지 않습니다.",
		"가솔린 기관보다 가속력이 좋습니다."
	};
	
	public Zet(String playerName) {
		super(playerName, "제트기관", 132, false, true, false, des);
		
		this.rank=4;

	}
	
	public void T_Passive(EntityDamageEvent event){
		Player p=(Player)event.getEntity();
		boolean has=false;
		
		for(PotionEffect e:p.getActivePotionEffects()){
			if(e.getType().equals(PotionEffectType.SPEED)){
				has=true;
			}
		}
		
		if(!has){
			if(event.getCause().equals(DamageCause.FIRE)||event.getCause().equals(DamageCause.FIRE_TICK)||event.getCause().equals(DamageCause.LAVA)){
				
				Random r=new Random();
				
				if(r.nextInt(4)>0){
					p.sendMessage("동력이 생겨 빨라집니다!");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*5, 1));
				}
				
			}
		}
			
	}
	
}
