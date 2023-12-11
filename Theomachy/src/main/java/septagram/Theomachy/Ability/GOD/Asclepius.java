package septagram.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;
import septagram.Theomachy.Utility.GetPlayerList;

public class Asclepius extends Ability
{
	private final static String[] des= {
			   "아스킬리피어스는 의술의 신입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"치료 Ⅰ",
			   "자신의 체력을 완전히 회복합니다.",
				ChatColor.RED+"【고급】 "+ChatColor.WHITE+"치료 Ⅱ",
			   "주변에 있는 자신을 제외한 아군의 체력을 완전히 회복합니다."};
	
	public Asclepius(String playerName)
	{
		super(playerName,"아스클리피어스", 10, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=60;
		this.cool2=120;
		this.sta1=1;
		this.sta2=5;
		
		this.rank=2;
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
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, cool1))
		{
			Skill.Use(player, Material.COBBLESTONE, sta1, 1, cool1);
			player.setHealth(20);
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta2))
		{
			List<Player> targetList = GetPlayerList.getNearByTeamMembers(player, 5, 5, 5);
			if (!targetList.isEmpty())
			{
				Skill.Use(player, Material.COBBLESTONE, sta2, 2, cool2);
				player.sendMessage("자신을 제외한 모든 팀원의 체력을 회복합니다.");
				player.sendMessage(ChatColor.GREEN+"체력을 회복한 플레이어 목록");
				for (Player e : targetList)
				{
					e.setHealth(20);
					e.sendMessage(ChatColor.YELLOW+"의술의 신의 능력으로 모든 체력을 회복합니다.");
					player.sendMessage(ChatColor.GOLD+e.getName());
				}
			}
			else
				player.sendMessage("사용 가능한 대상이 없습니다.");
		}
	}
}
