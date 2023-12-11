package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Tajja extends Ability {

	private final static String[] des= {
			"타짜는 손놀림이 빠른 능력입니다.",
			ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"밑장빼기",
			"능력 사용 시 인벤토리에 가장 먼저 있는 검이 소비됩니다.",
			"능력 사용 후 맨손으로 가격 시 소비된 검의 데미지만큼",
			"데미지를 줄 수 있습니다.",
			"단, 10번 쓰면 검을 쓸 수 없습니다."
	};
	
	private final int coolTime0=60;
	private final int stack0=10;
	
	public Tajja(String playerName) {
		super(playerName, "타짜", 126, true, false, false, des);
		
		this.rank=4;
		this.cool1=coolTime0;
		this.sta1=10;
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

	private int sword=0;
	private int time=-1;
	
	private void leftAction(Player player) {
		if(CoolTimeChecker.Check(player, 0) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, stack0)) {
			if(sword==0) {
				for(ItemStack i:player.getInventory().getContents()) {
					try {
                        switch (i.getType()) {
                            case WOODEN_SWORD -> {
                                sword = 4;
                                player.getInventory().remove(i);
                            }
                            case STONE_SWORD -> {
                                sword = 5;
                                player.getInventory().remove(i);
                            }
                            case IRON_SWORD -> {
                                sword = 6;
                                player.getInventory().remove(i);
                            }
                            case GOLDEN_SWORD -> {
                                sword = 7;
                                player.getInventory().remove(i);
                            }
                            default -> {
                            }
                        }
					}catch(NullPointerException e) {}
				}player.sendMessage("손은 눈보다 빠르다.");
				Skill.Use(player, Material.COBBLESTONE, stack0, 0, coolTime0);
				time=10;
			}
		}
	}
	
	
	public void T_Passive(EntityDamageByEntityEvent event) {
		Player p=(Player) event.getDamager();
			if(p.getName().equals(this.playerName)) {
				if(p.getItemInHand().getType().equals(Material.AIR)) {
					if(sword!=0) {
						switch(time) {
						case 1:
							event.setDamage(sword);
							p.sendMessage("동작그만, 밑장 빼기냐.");
							sword=0;
							time=-1;
							break;
						default:
							event.setDamage(sword);
							time--;
							break;
						}
					}
				}
				
			}
	}

}
