package org.Theomachy.Ability.HUMAN;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import org.Theomachy.Ability.Ability;
import org.Theomachy.ENUM.AbilityCase;
import org.Theomachy.ENUM.AbilityInfo;
import org.Theomachy.ENUM.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.Checker.CoolTimeChecker;
import org.Theomachy.Utility.Checker.DirectionChecker;
import org.Theomachy.Utility.Checker.MouseEventChecker;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillCoolTimeHandler;

public class Wizard extends Ability
{
	private final static String[] des= {
			AbilityInfo.Wizard.getName() + "는 신의 능력을 빌려 쓰는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"날려버리기",
			   "일반능력은 주변 10칸 모든 플레이어를 자신이 보는 방향으로 모두 날려버립니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"신의 심판",
			   "주변의 사람들을 공중으로 띄운 후 번개를 떨어뜨립니다.",
			   "고급능력 발동 시 패널티로 자신의 체력이 반으로 줄어듭니다."};

	private final int rareDuration;
	public Wizard(String playerName)
	{
		super(playerName, AbilityInfo.Wizard, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.normalSkillCoolTime =180;
		this.normalSkillStack =25;

		this.rareSkillCoolTime =300;
		this.rareSkillStack =45;
		this.rareDuration = 1;
		this.rank= AbilityRank.S;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (MouseEventChecker.PlayerInteract(event)) {
				case LEFT_CLICK_BLOCK,LEFT_CLICK_AIR -> leftClickAction(player);
				case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> rightClickAction(player);
            }
		}
	}

	private void leftClickAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, material, normalSkillStack))
		{
			List<Entity> entityList = player.getNearbyEntities(10, 10, 10);
			ArrayList<Player> targetList = new ArrayList<Player>(); 
			for (Entity e : entityList)
				if (e instanceof Player)
					targetList.add((Player) e);
			if (!targetList.isEmpty())
			{
				SkillCoolTimeHandler.Use(player, material, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
				Vector v = new Vector(0,0.5,0);
				double vertical = 2.4d;
				double diagonal = vertical*1.4d;
				for (Player enemy : targetList)
				{
					enemy.setVelocity(v);
					enemy.sendMessage("마법사의 능력에 의해 날아갑니다.");
				}
                switch (DirectionChecker.PlayerDirection(player)) {
                    case 0 -> v.add(new Vector(0, 0, diagonal));
                    case 1 -> v.add(new Vector(-vertical, 0, vertical));
                    case 2 -> v.add(new Vector(-diagonal, 0, 0));
                    case 3 -> v.add(new Vector(-vertical, 0, -vertical));
                    case 4 -> v.add(new Vector(0, 0, -diagonal));
                    case 5 -> v.add(new Vector(vertical, 0, -vertical));
                    case 6 -> v.add(new Vector(diagonal, 0, 0));
                    case 7 -> v.add(new Vector(vertical, 0, vertical));
                }
				Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(),()->{
					for (Player e : targetList)
						e.setVelocity(v);
				},2 * 2);
			}
			else
				player.sendMessage("능력을 사용할 수 있는 대상이 없습니다.");
		}
	}
	
	private void rightClickAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.RARE)&&PlayerInventory.ItemCheck(player, material, rareSkillStack)) {
			List<Entity> entityList = player.getNearbyEntities(10, 10, 10);
			ArrayList<Player> targetList = new ArrayList<Player>(); 
			for (Entity e : entityList)
				if (e instanceof Player)
					targetList.add((Player) e);
			if (!targetList.isEmpty())
			{
				SkillCoolTimeHandler.Use(player, material, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
				player.setHealth( player.getHealth() / 2 );
				Vector v = new Vector(0,1.6,0);
				for (Player e : targetList)
				{
					e.setVelocity(v);
					e.sendMessage(ChatColor.RED+"마법사의 고급능력에 당했습니다!");
				}
				Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(),()->{
					World world = player.getWorld();
					for (Player e : targetList)
					{
						Location location = e.getLocation();
						world.strikeLightning(location);
						e.setFireTicks(100);
					}
				},rareDuration * 20L);
			}
			player.sendMessage("능력을 사용할 수 있는 대상이 없습니다.");
		}
	}
}
