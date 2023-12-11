package septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Creeper extends Ability
{
	private boolean plasma = false;
	private final static String[] des= {
			   "크리퍼는 몬스터형 능력입니다.",
			   "블레이즈 로드를 통해 능력을 사용하면" ,
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"펑!",
			   "크리퍼와 같은 폭발력의 폭발을 일으킵니다." ,
			   "번개를 맞은 적이 있다면 폭발력이 두 배로 증가합니다.",
			   "번개 카운팅은 사망 시 초기화됩니다."};
	
	public Creeper(String playerName)
	{
		super(playerName,"크리퍼", 106, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=60;
		this.sta1=20;
		
		this.rank=3;
	}
	
	public void T_Active(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
			switch(EventFilter.PlayerInteract(event))
			{
			case 0:case 1:
				leftAction(player);
				break;
			}
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))
		{
			Skill.Use(player, Material.COBBLESTONE, sta1, 0, cool1);
			World world = player.getWorld();
			Location location = player.getLocation();
			float power = plasma ? 3.0f : 6.0f;
			player.setHealth(0);
			world.createExplosion(location, power);
		}
	}
	
	public void T_Passive(EntityDamageEvent event)
	{
		if (event.getCause() == DamageCause.LIGHTNING)
		{
			this.plasma = true;
			((Player)event.getEntity()).sendMessage(ChatColor.AQUA+"플라즈마 크리퍼 모드 활성화!");
		}
	}
	
	public void T_Passive(PlayerDeathEvent event)
	{
		this.plasma=false;
	}
}
