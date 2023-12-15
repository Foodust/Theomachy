package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Utility.*;

import java.util.List;
import java.util.Random;

public class Snow extends Ability {

	private final static String[] des= {
			AbilityInfo.Snow + "는 눈을 이용합니다.",
			ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"폭설",
			"죽을 때마다 공격 지수가 1씩 상승 하고 최대 7까지 상승합니다.",
			"눈덩이를 맞춰도 상대가 밀려나지 않습니다.",
			ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"얼음 속성",
			"불에 의한 데미지를 2배로 받습니다.",
			ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"공격 지수",
			"현재 공격 지수를 확인 합니다.",
			ChatColor.AQUA+"【고급】 "+ChatColor.WHITE+"눈덩이 변환",
			"눈덩이를 1개 얻습니다.",};

	private int attack;
	public Snow(String playerName) {
		super(playerName, AbilityInfo.Snow, true, true, false, des);
		this.firstSkillCoolTime =0;
		this.firstSkillStack =0;
		this.secondSkillCoolTime = 20;
		this.secondSkillStack = 5;
		this.attack = 0;
		this.rank= AbilityRank.A;
	}
	public void activeSkill(PlayerInteractEvent event){
		Player player=event.getPlayer();
		if(PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)){
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> player.sendMessage("공격 지수 : " + attack);
				case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
		}
	}
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.RARE)&&PlayerInventory.ItemCheck(player, material, secondSkillStack))
		{
			Skill.Use(player, material, AbilityCase.RARE,secondSkillStack, secondSkillCoolTime);
			World world = player.getWorld();
			Location location = player.getLocation();
			world.dropItem(location, new ItemStack(Material.SNOWBALL, 3));
		}
	}
	public void passiveSkill(PlayerDeathEvent event) {
		if(attack<8){
			event.getEntity().sendMessage(ChatColor.RED+"공격 지수가 증가하고 있습니다!");
			attack++;
		}
	}
	
	public void passiveSkillSnow(EntityDamageByEntityEvent event){
		Player p=(Player)event.getEntity();		
		event.setCancelled(true);
		p.damage(attack);
	}
	
	public void passiveSkill(EntityDamageEvent event){
		DamageCause dc=event.getCause();
		if(dc.equals(DamageCause.FIRE_TICK)||dc.equals(DamageCause.LAVA)||dc.equals(DamageCause.FIRE)){
			event.setDamage(event.getDamage()*2);
		}
	}
	@Override
	public void initialize(){
		GameData.OnlinePlayer.get(playerName).getInventory().addItem(new ItemStack(Material.SNOWBALL, 8));
	}
}
