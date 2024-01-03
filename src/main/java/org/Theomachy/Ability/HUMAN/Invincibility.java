package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Timer.CoolTimeTimer;


public class Invincibility extends Ability
{
	private final static String[] des= {
			AbilityInfo.Invincibility.getName() +  "은 일정시간 데미지를 받지 않을 수 있는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"천하장사",
			   " 자신을 7초간 무적 상태로 만듭니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"숨 돌리기",
			   "자신에게 체력 회복 버프를 5초 동안 시전합니다."};

	private final int rareDuration;
	public Invincibility(String playerName)
	{
		super(playerName, AbilityInfo.Invincibility, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.normalSkillCoolTime =50;
		this.normalSkillStack =30;

		this.rareSkillCoolTime =120;
		this.rareSkillStack =50;
		this.rareDuration = 5;
		this.rank= AbilityRank.A;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (playerModule.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (event.getAction()) {
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK-> leftAction(player);
				case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (skillHandler.Check(player, AbilityCase.NORMAL)&&playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack))
		{
			skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);

			CoolTimeTimer.commonSkillCoolTime.put(playerName+"1", 7);
		}
	}
	
	private void rightAction(Player player)
	{
		if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack))
		{
			skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, rareDuration * 20, 0));
		}
	}
	
	public void passiveSkill(EntityDamageEvent event)
	{
		if (CoolTimeTimer.commonSkillCoolTime.containsKey(playerName+"1"))
		{
			event.setCancelled(true);
			event.getEntity().setFireTicks(0);
		}
	}
}
