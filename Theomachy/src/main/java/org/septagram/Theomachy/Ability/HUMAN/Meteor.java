package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.util.Vector;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Timer.Skill.MeteorTimer;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

import java.util.Random;

public class Meteor extends Ability
{
	private final static String[] des= {
			   "메테오는 유성을 소환하는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"유성 소환",
			   " 자신의 위치를 기준으로 넓은 범위에 유성을 떨어뜨립니다." ,
			   "블럭은 메테오의 폭발에 파괴되지 않습니다."};
	
	public Meteor(String playerName)
	{
		super(playerName,"메테오", 117, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.firstSkillCoolTime =110;
		this.firstSkillStack =20;
		
		this.rank=4;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
                case 0, 1 -> leftAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{	
		if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack))
		{
			Skill.Use(player, Material.COBBLESTONE, firstSkillStack, 0, firstSkillCoolTime);
			Location location = player.getLocation();
//			Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(),()->{
				Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(),()->{
					World world = player.getWorld();
					Vector v = new Vector(0d,-20d,0d);
					Vector speed = new Vector(0d,-3d, 0d);
					for(int count = 30 ; count > 0; count--){
						Random random = new Random();
						int X = random.nextInt(11) - 5;
						int Z = random.nextInt(11)-5;
						Fireball fireball = world.spawn(location.add(X, 0, Z), Fireball.class);
						fireball.setShooter(player);
						fireball.setDirection(v);
						fireball.setVelocity(speed);
						location.add(-X, 0, -Z);
					}
				},2 * 10);//}
//			,0);
		}
	}
}
