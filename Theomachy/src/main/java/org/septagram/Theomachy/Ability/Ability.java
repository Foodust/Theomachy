package org.septagram.Theomachy.Ability;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Ability
{
	public final String playerName;
	public final String abilityName;
	public final int abilityCode;
	public final boolean activeType;
	public final boolean passiveType;
	public final boolean buffType;
	public boolean flag = false;
	public final String[] description;
	public int rank;
	public int firstSkillCoolTime =-1;
	public int secondSkillCoolTime =-1;
	public int firstSkillStack =-1;
	public int secondSkillStack =-1;
	
	public Ability(String playerName, String abilityName, int abilityCode, boolean activeType, boolean passiveType, boolean buffType, String[] des)
	{
		this.playerName=playerName;
		this.abilityName=abilityName;
		this.abilityCode=abilityCode;
		this.activeType=activeType;
		this.passiveType=passiveType;
		this.buffType=buffType;
		this.description=des;
	}
	
	public void description(){}
	
	public void activeSkill(PlayerInteractEvent event){}
	
	public void passiveSkill(BlockBreakEvent event){}
	
	public void passiveSkill(PlayerDeathEvent event){}
	
	public void passiveSkill(FoodLevelChangeEvent event){}
	
	public void passiveSkill(EntityRegainHealthEvent event){}
	
	public void passiveSkill(EntityDamageByEntityEvent event){}
	
	public void passiveSkill(EntityDamageEvent event) {}
	
	public void passiveSkill(SignChangeEvent event) {}
	
	public void passiveSkill(BlockPlaceEvent event) {}
	
	public void passiveSkill(PlayerRespawnEvent event) {}
	
	public void initialize(){}
	
	public void initializeReset(){}
	
	public void buff(){}
	
	public void passiveSkill(PlayerMoveEvent event){}
	
	public void targetSet(CommandSender sender, String targetName)
	{
		sender.sendMessage("타겟을 사용하는 능력이 아닙니다.");
	}
	
	public void passiveSkill(ProjectileLaunchEvent event, Player player){}

	public void passiveSkill(EntityExplodeEvent event) {}

	public void passiveSkillSnow(EntityDamageByEntityEvent event) {}

	
	
}
