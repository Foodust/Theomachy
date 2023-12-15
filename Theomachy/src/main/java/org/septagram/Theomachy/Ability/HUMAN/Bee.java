package org.septagram.Theomachy.Ability.HUMAN;

import java.util.Random;

import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Bee extends Ability {

	public final static String[] des= {
			AbilityInfo.Bee.getName()+ "은 벌들의 제왕입니다.",
			NamedTextColor.YELLOW+"【패시브】 "+NamedTextColor.WHITE+"공격",
			"자신을 공격해 온 적에게 75%의 확률로 중독되게 합니다.",
			NamedTextColor.AQUA+"【일반】 "+NamedTextColor.WHITE+"페로몬",
			"목표로 지정해 둔 상대를 자신의 위치로 끌어옵니다.",
			"목표 지정: /x <대상>"};


	private String abilitytarget;
	
	public Bee(String playerName) {
		super(playerName, AbilityInfo.Bee, true, true, false, des);
		this.normalSkillCoolTime =180;
		this.normalSkillStack =32;
		this.rank= AbilityRank.A;
	}

	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR,LEFT_CLICK_BLOCK -> leftAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{
			if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack))
		{
			if(abilitytarget!=null){
				if(player.getName().equals(abilitytarget)){
					player.sendMessage(NamedTextColor.RED+"목표는 본인이 아니어야 합니다.");
				}
				else{
					Player target = GameData.OnlinePlayer.get(abilitytarget);
					Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);

					player.sendMessage(NamedTextColor.YELLOW+" 페로몬 "+NamedTextColor.WHITE+"을 이용하여 목표를 유혹했습니다!");
					target.sendMessage(NamedTextColor.YELLOW+" 페로몬 "+NamedTextColor.WHITE+"에 유혹당했습니다!");

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
				this.abilitytarget = targetName;
				sender.sendMessage("타겟을 등록했습니다.   "+NamedTextColor.GREEN+targetName);
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
				e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
				e.sendMessage(NamedTextColor.GOLD+"벌에게 쏘였습니다! 자나깨나 벌조심.");
			}
			
		}
		
	}
	
}
