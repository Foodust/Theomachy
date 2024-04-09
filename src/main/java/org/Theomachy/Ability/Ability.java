package org.Theomachy.Ability;





import de.slikey.effectlib.EffectManager;
import org.Theomachy.Handler.Handler.PlayerHandler;
import org.Theomachy.Handler.Handler.SkillHandler;
import org.Theomachy.Handler.Module.PlayerModule;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
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
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;

import java.util.List;

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
	public AbilityRank rank;
	public int normalSkillCoolTime =-1;
	public int rareSkillCoolTime =-1;
	public int normalSkillStack =-1;
	public int rareSkillStack =-1;

	public Material material = Material.COBBLESTONE;
	public Material skillMaterial =Material.BLAZE_ROD;
	public final PlayerModule playerModule = new PlayerModule();
	public final SkillHandler skillHandler = new SkillHandler();
	public final EffectManager effectManage = Theomachy.getEffectManage();
	public final PlayerHandler playerHandler = new PlayerHandler();
	public Ability(String playerName, AbilityInfo abilityInfo, boolean activeType, boolean passiveType, boolean buffType, String[] des)
	{
		this.playerName=playerName;
		this.abilityName= abilityInfo.getName();
		this.abilityCode = abilityInfo.getIndex();
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
		sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_TARGET_FOR_ABILITY.getMessage());
	}
	
	public void passiveSkill(ProjectileLaunchEvent event, Player player){}

	public void passiveSkill(EntityExplodeEvent event) {}

	public void passiveSkillSnow(EntityDamageByEntityEvent event) {}

}
