package org.septagram.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.GetPlayerList;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Akasha extends Ability{
	private final static String[] des= {
			"아카샤는 고통과 향락의 여신입니다.",
			ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"향락",
			"주변에 있는 아군에게 기쁨을 주어 15초간 빠르고(SPEED) 건강(REGENERATION)하게 합니다.",
			ChatColor.RED+"【고급】 "+ChatColor.WHITE+"고통",
			"주변에 있는 적군에게 고통을 주어 6초간 혼란하게 합니다."};
	public Akasha(String playerName)
	{
		super(playerName,"아카샤", 17, true, false, false ,des);
		Theomachy.log.info(playerName+abilityName);
		this.firstSkillCoolTime =60;
		this.firstSkillStack =10;
		this.secondSkillCoolTime =100;
		this.secondSkillStack =20;
		this.rank=4;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR,LEFT_CLICK_BLOCK -> leftAction(player);
				case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> rightAction(player);
            }
		}
	}

	private void leftAction(Player player) {
		if(CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, material, firstSkillStack)){
			Skill.Use(player, material, firstSkillStack, 1, firstSkillCoolTime);
			List<Player> nearPlayers = GetPlayerList.getNearByTeamMembers(player, 20, 20, 20);
			for(Player nearPlayer : nearPlayers){
				nearPlayer.sendMessage(ChatColor.DARK_PURPLE+"향락"+ChatColor.WHITE+"이 당신을 즐겁게합니다!");
				nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*15, 0));
				nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*15, 0));
			}
		}
		
	}
	
	private void rightAction(Player player) {
		
		if(CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player,material, secondSkillStack)){
			
			List<Player> entityList = GetPlayerList.getNearByNotTeamMembers(player, 10, 10, 10);
			if(entityList.isEmpty()){
				player.sendMessage(ChatColor.RED +"주변에 상대팀이 없습니다");
				return;
			}
			Skill.Use(player,material,secondSkillStack, 2, secondSkillCoolTime);
			for(Player e:entityList){
				e.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,6 * 20, 0));
				e.setHealth(e.getHealth()-4);
			}
			
		}
	}
	
}
