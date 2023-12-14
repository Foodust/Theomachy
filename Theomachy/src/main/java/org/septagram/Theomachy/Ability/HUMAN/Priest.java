package org.septagram.Theomachy.Ability.HUMAN;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.GetPlayerList;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Priest extends Ability
{
	private final Material material=Material.COBBLESTONE;
	private final static String[] des= {
			AbilityInfo.Priest.getName() + "는 신의 가호를 받을 수 있는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"신의 은총 Ⅰ",
			   "자신에게 랜덤으로 버프 5초간 를 적용합니다." ,
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"신의 은총 Ⅱ",
			   "자신의 팀원 모두에게 랜덤으로 버프를 5초간 적용합니다."};
	
	public Priest(String playerName)
	{
		super(playerName, AbilityInfo.Priest, true, false,false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =35;
		this.secondSkillCoolTime =90;
		this.firstSkillStack =30;
		this.secondSkillStack =45;
		
		this.rank=2;
		
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

	private void leftAction(Player player)
	{
		int buffTime = 5 * 20;
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, material, firstSkillStack))
		{
			Skill.Use(player, material, AbilityCase.NORMAL,firstSkillStack, firstSkillCoolTime);
			Random random = new Random();
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, buffTime,0));
				player.sendMessage(ChatColor.LIGHT_PURPLE+"데미지 저항 효과가 적용되었습니다.");
			}
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, buffTime,0));
				player.sendMessage(ChatColor.RED+"데미지 증가 효과가 적용되었습니다.");
			}
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, buffTime,0));
				player.sendMessage(ChatColor.GOLD+"체력회복속도 증가 효과가 적용되었습니다.");
			}
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, buffTime,0));
				player.sendMessage(ChatColor.AQUA+"이동속도 증가 효과가 적용되었습니다.");
			}
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, buffTime,0));
				player.sendMessage(ChatColor.GREEN+"빠른 채광 효과가 적용되었습니다.");
			}
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.RARE)&&PlayerInventory.ItemCheck(player, material, secondSkillStack))
		{
			Skill.Use(player, material, AbilityCase.RARE,secondSkillStack, secondSkillCoolTime);
			List<Player> targetList = GetPlayerList.getTeamMember(player);
			int buffTime = 5 * 20;
            Random random = new Random();
            for (Player e : targetList)
            {
                if (random.nextInt(2)==0)
                {
                    // depre 고쳐 나중에
                    e.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, buffTime,0));
                    e.sendMessage(ChatColor.LIGHT_PURPLE+"데미지 저항 효과가 적용되었습니다.");
                }
                if (random.nextInt(2)==0)
                {
                    e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, buffTime,0));
                    e.sendMessage(ChatColor.RED+"데미지 증가 효과가 적용되었습니다.");
                }
                if (random.nextInt(2)==0)
                {
                    e.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, buffTime,0));
                    e.sendMessage(ChatColor.GOLD+"체력회복속도 증가 효과가 적용되었습니다.");
                }
                if (random.nextInt(2)==0)
                {
                    e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, buffTime,0));
                    e.sendMessage(ChatColor.AQUA+"이동속도 증가 효과가 적용되었습니다.");
                }
                if (random.nextInt(2)==0)
                {
                    e.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, buffTime,0));
                    e.sendMessage(ChatColor.GREEN+"빠른 채광 효과가 적용되었습니다.");
                }
            }
        }
	}
}
