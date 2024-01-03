package org.Theomachy.Ability.HUMAN;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Data.GameData;
import org.Theomachy.Theomachy;


public class Bee extends Ability {

	public final static String[] des= {
			AbilityInfo.Bee.getName()+ "은 벌들의 제왕입니다.",
			ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"공격",
			"자신을 공격해 온 적에게 75%의 확률로 3초간 중독되게 합니다.",
			ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"페로몬",
			"목표로 지정해 둔 상대를 자신의 위치로 끌어옵니다.",
			"목표 지정: /x <대상>"};


	private String abilityTarget;
	private final int passiveDuration;
	public Bee(String playerName) {
		super(playerName, AbilityInfo.Bee, true, true, false, des);
		this.normalSkillCoolTime =180;
		this.normalSkillStack =32;
		this.passiveDuration = 3;
		this.rank= AbilityRank.A;
	}

	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (playerModule.InHandItemCheck(player,skillMaterial))
		{
            switch (event.getAction()) {
				case LEFT_CLICK_AIR,LEFT_CLICK_BLOCK -> leftAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{
			if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack))
		{
			if(abilityTarget !=null){
				if(player.getName().equals(abilityTarget)){
					player.sendMessage(ChatColor.RED+"목표는 본인이 아니어야 합니다.");
				}
				else{
					Player target = GameData.onlinePlayer.get(abilityTarget);
					skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);

					player.sendMessage(ChatColor.YELLOW+" 페로몬 "+ChatColor.WHITE+"을 이용하여 목표를 유혹했습니다!");
					target.sendMessage(ChatColor.YELLOW+" 페로몬 "+ChatColor.WHITE+"에 유혹당했습니다!");

					Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{target.teleport(player);});
				}
			}
			else{
				player.sendMessage("목표를 설정해주십시오. (목표 설정법: /x <목표>)");
			}
		}});
	}
	
	public void targetSet(CommandSender sender, String targetName)
	{
			if (!playerName.equals(targetName))
			{
				this.abilityTarget = targetName;
				sender.sendMessage("타겟을 등록했습니다.   "+ChatColor.GREEN+targetName);
			}
			else
				sender.sendMessage("자기 자신을 목표로 등록 할 수 없습니다.");
	}
	
	public void passiveSkill(EntityDamageByEntityEvent event) {
		Player p=(Player) event.getEntity();
		Player e=(Player) event.getDamager();
		
		if(p.getName().equals(this.playerName)) {
			Random r=new Random();
			
			if(r.nextInt(4)<=2) {
				e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, passiveDuration * 20, 0));
				e.sendMessage(ChatColor.GOLD+"벌에게 쏘였습니다! 자나깨나 벌조심.");
			}
			
		}
		
	}
	
}
