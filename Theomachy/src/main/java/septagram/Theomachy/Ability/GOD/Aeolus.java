package septagram.Theomachy.Ability.GOD;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Timer.Skill.WizardWindTimer;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.DirectionChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.GetPlayerList;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Aeolus extends Ability{
	
	private final static String[] des= {
			"아이올로스는 폭풍과 바람의 신입니다.",
			ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"자연 바람",			
			"주변에 있는 아군에게 상쾌한 바람으로 빠르고 건강하게 합니다.",
			ChatColor.RED+"【고급】 "+ChatColor.WHITE+"폭풍",
			"주변에 있는 적을 강한 바람으로 밀어내고 느리고 약하게 합니다."};
	
	public Aeolus(String playerName)
	{
		super(playerName,"아이올로스", 16, true, false, false ,des);
		Theomachy.log.info(playerName+abilityName);
		
		
		this.cool1=60;
		this.sta1=10;
		this.cool2=180;
		this.sta2=32;
		this.rank=4;
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

	private void leftAction(Player player) {
		
		if(CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1)){
			
			Skill.Use(player, Material.COBBLESTONE, sta1, 1, cool1);
			
			List<Player> nearp=GetPlayerList.getNearByTeamMembers(player, 20, 20, 20);
			
			for(Player p:nearp){
				p.sendMessage(ChatColor.AQUA+"상쾌한 바람"+ChatColor.WHITE+"이 당신을 감싸돕니다!");
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*15, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*15, 0));
			}
		}
		
	
	}
	
	private void rightAction(Player player) {
		
		if(CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta2)){
			
			List<Player> entityList = GetPlayerList.getNearByNotTeamMembers(player, 10, 10, 10);
			ArrayList<Player> targetList = new ArrayList<Player>(); 
			for (Entity e : entityList)
				if (e instanceof Player)
					targetList.add((Player) e);
			if (!targetList.isEmpty())
			{
				Skill.Use(player, Material.COBBLESTONE, sta2, 2, cool2);
				Timer t = new Timer();
				Vector v = new Vector(0,0.5,0);
				double vertical = 2.4d;
				double diagonal = vertical*1.4d;
				for (Player e : targetList)
				{
					e.setVelocity(v);
					e.sendMessage(ChatColor.DARK_AQUA+"강력한 바람 때문에 밀려납니다!");
					e.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,20*5, 0));
					e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*5, 0));
				}
				switch(DirectionChecker.PlayerDirection(player))
				{
				case 0:
					v.add(new Vector(0,0,diagonal));
					break;
				case 1:
					v.add(new Vector(-vertical,0,vertical));
					break;
				case 2:
					v.add(new Vector(-diagonal,0,0));
					break;
				case 3:
					v.add(new Vector(-vertical,0,-vertical));
					break;
				case 4:
					v.add(new Vector(0,0,-diagonal));
					break;
				case 5:
					v.add(new Vector(vertical,0,-vertical));
					break;
				case 6:
					v.add(new Vector(diagonal,0,0));
					break;
				case 7:
					v.add(new Vector(vertical,0,vertical));
					break;
				}
				t.schedule(new WizardWindTimer(targetList, v), 200);
			}
			else
				player.sendMessage("능력을 사용할 수 있는 대상이 없습니다.");
			
		}
		
		
	}
	
}
