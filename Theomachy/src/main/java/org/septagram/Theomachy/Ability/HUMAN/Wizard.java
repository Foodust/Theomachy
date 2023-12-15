package org.septagram.Theomachy.Ability.HUMAN;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.DirectionChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Wizard extends Ability
{
	private final static String[] des= {
			AbilityInfo.Wizard.getName() + "는 신의 능력을 빌려 쓰는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"날려버리기",
			   "일반능력은 주변 10칸 모든 플레이어를 자신이 보는 방향으로 모두 날려버립니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"신의 심판",
			   "주변의 사람들을 공중으로 띄운 후 번개를 떨어뜨립니다.",
			   "고급능력 발동 시 패널티로 자신의 체력이 반으로 줄어듭니다."};

	public Wizard(String playerName)
	{
		super(playerName, AbilityInfo.Wizard, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime=180;
		this.secondSkillCoolTime=300;
		this.firstSkillStack=25;
		this.secondSkillStack=45;
		this.rank= AbilityRank.S;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_BLOCK,LEFT_CLICK_AIR -> leftClickAction(player);
				case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> rightClickAction(player);
            }
		}
	}

	private void leftClickAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, material, firstSkillStack))
		{
			List<Entity> entityList = player.getNearbyEntities(10, 10, 10);
			ArrayList<Player> targetList = new ArrayList<Player>(); 
			for (Entity e : entityList)
				if (e instanceof Player)
					targetList.add((Player) e);
			if (!targetList.isEmpty())
			{
				Skill.Use(player, material, AbilityCase.NORMAL,firstSkillStack,  firstSkillCoolTime);
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
		if (CoolTimeChecker.Check(player, AbilityCase.RARE)&&PlayerInventory.ItemCheck(player, material, secondSkillStack)) {
			List<Entity> entityList = player.getNearbyEntities(5, 5, 5);
			ArrayList<Player> targetList = new ArrayList<Player>(); 
			for (Entity e : entityList)
				if (e instanceof Player)
					targetList.add((Player) e);
			if (!targetList.isEmpty())
			{
				Skill.Use(player, material, AbilityCase.RARE,secondSkillStack,  secondSkillCoolTime);
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
				},1 * 20);
			}
			player.sendMessage("능력을 사용할 수 있는 대상이 없습니다.");
		}
	}
}
