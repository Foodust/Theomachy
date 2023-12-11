package septagram.Theomachy.Ability;

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
	public int cool1=-1;
	public int cool2=-1;
	public int sta1=-1;
	public int sta2=-1;
	
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
	
	public void T_Active(PlayerInteractEvent event){}
	
	public void T_Passive(BlockBreakEvent event){}
	
	public void T_Passive(PlayerDeathEvent event){}
	
	public void T_Passive(FoodLevelChangeEvent event){}
	
	public void T_Passive(EntityRegainHealthEvent event){}
	
	public void T_Passive(EntityDamageByEntityEvent event){}
	
	public void T_Passive(EntityDamageEvent event) {}
	
	public void T_Passive(SignChangeEvent event) {}
	
	public void T_Passive(BlockPlaceEvent event) {}
	
	public void T_Passive(PlayerRespawnEvent event) {}
	
	public void conditionSet(){}
	
	public void conditionReSet(){}
	
	public void buff(){}
	
	public void T_Passive(PlayerMoveEvent event){}
	
	public void targetSet(CommandSender sender, String targetName)
	{
		sender.sendMessage("타겟을 사용하는 능력이 아닙니다.");
	}
	
	public void T_Passive(ProjectileLaunchEvent event, Player player){}

	public void T_Passive(EntityExplodeEvent event) {}

	public void T_PassiveSnow(EntityDamageByEntityEvent event) {}

	
	
}
