package org.septagram.Theomachy.Ability.HUMAN;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Clocking extends Ability
{
	private List<Player> targetList;
	private final static String[] des= {
			AbilityInfo.Clocking.getName() + " 일정 시간 자신의 몸을 숨길 수 있는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"감추기",
			   "자신의 모습을 7초간 감출 수 있습니다.",
			   "감춘 상태에서 상대방을 공격할 시 다시 모습이 나타나게 되며,",
			   "공격 당한 상대는 20% 확률로 사망합니다."};
	
	public Clocking(String playerName)
	{
		super(playerName, AbilityInfo.Clocking, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =60;
		this.firstSkillStack =25;
		
		this.rank=3;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK-> leftAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&& PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL,firstSkillStack,  firstSkillCoolTime);
			targetList = player.getWorld().getPlayers();
			for (Player enemy : targetList)
				enemy.hidePlayer(player);
			Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), ()->{
				try{
					if (GameData.PlayerAbility.get(player.getName()).flag)
					{
						player.sendMessage("은신 시간이 종료되었습니다.");
						GameData.PlayerAbility.get(player.getName()).flag = false;
					}
					for (Player enemy : targetList)
						enemy.showPlayer(player);
				}
				catch (Exception e)
				{
					Bukkit.broadcastMessage(e.getLocalizedMessage());
				}
			},7 * 20);

			super.flag = true;
		}
	}
	
	public void passiveSkill(EntityDamageByEntityEvent event)
	{
		if (flag)
		{
			Player player = (Player) event.getDamager();
			if (player.getName().equals(this.playerName))
			{
				targetList = player.getWorld().getPlayers();
				for (Player enemy : targetList)
					enemy.showPlayer(player);
				Random random = new Random();
				if (random.nextInt(5)==0)
				{
					Player target = (Player) event.getEntity();
					event.setDamage(100);
					target.sendMessage("알 수 없는 이유로 인해 즉사 하였습니다.");
					player.sendMessage("상대가 즉사 하였습니다.");
				}
			}
			super.flag = false;
		}
	}
}
