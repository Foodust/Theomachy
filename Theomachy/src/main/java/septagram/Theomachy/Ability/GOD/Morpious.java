package septagram.Theomachy.Ability.GOD;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Morpious extends Ability{

	private final static String[] des= {
			"모르피우스는 잠의 신입니다.",
			ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"수면",
			"목표로 지정한 적을 1분간 잠들게 합니다.",
			"목표 지정: /x <대상>"};

	private String abilitytarget;
	
	public Morpious(String playerName) {
		super(playerName, "모르피우스", 15, true, false, false, des);
		
		this.rank=3;
		
		this.cool1=180;
		this.sta1=32;
	}
	
	public void T_Active(PlayerInteractEvent event){
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

	private void leftAction(Player player){
		if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, cool1))
		{
			String[] team = new String[2];
			team[0]=GameData.PlayerTeam.get(player.getName());
			team[1]=GameData.PlayerTeam.get(abilitytarget);
					
			if(team[0]!=team[1]){
			if(abilitytarget!=null){
				if(player.getName().equals(abilitytarget)){
					player.sendMessage(ChatColor.RED+"목표는 본인이 아니어야 합니다.");
				}
				
				else{
					Player target = GameData.OnlinePlayer.get(abilitytarget);
					Skill.Use(player, Material.COBBLESTONE, sta1, 0, cool1);
					player.sendMessage(ChatColor.GRAY+"목표를 잠재웠습니다!");
					target.sendMessage(ChatColor.GRAY+"착한 어른이는 일찍 자고 일찍 일어나야 해요~");
					target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1200,0), true);
					target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1200, 3), true);
				}
				
			}
			else{
				player.sendMessage("목표를 설정해주십시오. (목표 설정법: /x <목표>)");
			}
			}
			else{
				player.sendMessage(ChatColor.GRAY+"본인의 팀이므로 잠을 재울 수 없습니다!");
			}
		}
	}
	
	public void targetSet(CommandSender sender, String targetName)
	{
			if (!playerName.equals(targetName))
			{
				this.abilitytarget = targetName;
				sender.sendMessage("타겟을 등록했습니다.   "+ChatColor.GREEN+targetName);
			}
			else
				sender.sendMessage("자기 자신을 목표로 등록 할 수 없습니다.");
	}
}
