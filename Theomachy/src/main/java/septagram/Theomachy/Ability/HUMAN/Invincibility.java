package septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Timer.CoolTime;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Invincibility extends Ability
{

	private final static String[] des= {
			   "무적은 일정시간 데미지를 받지 않을 수 있는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"천하장사",
			   "일정 시간 자신을 무적 상태로 만듭니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"숨 돌리기",
			   "자신에게 체력 회복 버프를 시전합니다."};
	
	public Invincibility(String playerName)
	{
		super(playerName,"무적", 111, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=50;
		this.cool2=120;
		this.sta1=30;
		this.sta2=50;
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
			case 2:case 3:
				rightAction(player);
				break;
			}
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))
		{
			Skill.Use(player, Material.COBBLESTONE, sta1, 1, cool1);
			{
				CoolTime.COOL0.put(playerName+"1", 7);
			}
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))
		{
			Skill.Use(player, Material.COBBLESTONE, sta1, 2, cool1);
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 500, 0), true);
		}
	}
	
	public void T_Passive(EntityDamageEvent event)
	{
		if (CoolTime.COOL0.containsKey(playerName+"1"))
		{
			event.setCancelled(true);
			event.getEntity().setFireTicks(0);
		}
	}
}
