package org.septagram.Theomachy.Ability.HUMAN;

import java.util.List;
import java.util.Random;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
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
			   NamedTextColor.AQUA+"【일반】 "+NamedTextColor.WHITE+"신의 은총 Ⅰ",
			   "자신에게 랜덤으로 버프 5초간 를 적용합니다." ,
			   NamedTextColor.RED+"【고급】 "+NamedTextColor.WHITE+"신의 은총 Ⅱ",
			   "자신의 팀원 모두에게 랜덤으로 버프를 5초간 적용합니다."};
	private int buffTime;
	public Priest(String playerName)
	{
		super(playerName, AbilityInfo.Priest, true, false,false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.normalSkillCoolTime =35;
		this.rareSkillCoolTime =90;
		this.normalSkillStack =30;
		this.rareSkillStack =45;
		this.buffTime = 5 *20;
		this.rank= AbilityRank.B;
		
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

		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, material, normalSkillStack))
		{
			Skill.Use(player, material, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
			Random random = new Random();
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, buffTime,0));
				player.sendMessage(NamedTextColor.LIGHT_PURPLE+"데미지 저항 효과가 적용되었습니다.");
			}
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, buffTime,0));
				player.sendMessage(NamedTextColor.RED+"데미지 증가 효과가 적용되었습니다.");
			}
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, buffTime,0));
				player.sendMessage(NamedTextColor.GOLD+"체력회복속도 증가 효과가 적용되었습니다.");
			}
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, buffTime,0));
				player.sendMessage(NamedTextColor.AQUA+"이동속도 증가 효과가 적용되었습니다.");
			}
			if (random.nextInt(2)==0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, buffTime,0));
				player.sendMessage(NamedTextColor.GREEN+"빠른 채광 효과가 적용되었습니다.");
			}
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.RARE)&&PlayerInventory.ItemCheck(player, material, rareSkillStack))
		{
			Skill.Use(player, material, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
			List<Player> targetList = GetPlayerList.getTeamMember(player);
            Random random = new Random();
            for (Player team : targetList)
            {
                if (random.nextInt(2)==0)
                {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, buffTime,0));
                    team.sendMessage(NamedTextColor.LIGHT_PURPLE+"데미지 저항 효과가 적용되었습니다.");
                }
                if (random.nextInt(2)==0)
                {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, buffTime,0));
                    team.sendMessage(NamedTextColor.RED+"데미지 증가 효과가 적용되었습니다.");
                }
                if (random.nextInt(2)==0)
                {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, buffTime,0));
                    team.sendMessage(NamedTextColor.GOLD+"체력회복속도 증가 효과가 적용되었습니다.");
                }
                if (random.nextInt(2)==0)
                {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, buffTime,0));
                    team.sendMessage(NamedTextColor.AQUA+"이동속도 증가 효과가 적용되었습니다.");
                }
                if (random.nextInt(2)==0)
                {
                    team.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, buffTime,0));
                    team.sendMessage(NamedTextColor.GREEN+"빠른 채광 효과가 적용되었습니다.");
                }
            }
        }
	}
}
