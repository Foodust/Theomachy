package org.septagram.Theomachy.Handler.CommandModule;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.DB.GameData;

public class Help {

	public static void Module(CommandSender sender, Command command, String label, String[] data)
	{
		String playerName=sender.getName();
		Ability ability= GameData.PlayerAbility.get(playerName);
		if (ability != null)
		{
			Inventory inventory= Bukkit.createInventory(null, 18, Component.text( ChatColor.BLACK + ":::::::: 능력 정보 ::::::::"));
			ItemStack abilityName=new ItemStack(Material.ITEM_FRAME);
			ItemStack abilityDescription=new ItemStack(Material.BOOK);
			ItemStack abilityFirstCoolTime=new ItemStack(Material.CLOCK);
			ItemStack abilitySecondCoolTime=new ItemStack(Material.CLOCK);
			ItemStack rankStack = new ItemStack(Material.AIR);
			
			ItemMeta abilityNameItemMeta = abilityName.getItemMeta();
			ItemMeta abilityDescriptionItemMeta = abilityDescription.getItemMeta();
			ItemMeta abilityFirstCoolTimeItemMeta = abilityFirstCoolTime.getItemMeta();
			ItemMeta abilitySecondCoolTimeItemMeta = abilitySecondCoolTime.getItemMeta();
			ItemMeta rankItemMeta = rankStack.getItemMeta();
			
			abilityNameItemMeta.displayName(Component.text( ChatColor.AQUA+"【능력 이름】 "+ChatColor.WHITE+ability.abilityName));
			abilityDescriptionItemMeta.displayName(Component.text( ChatColor.DARK_AQUA+"【능력 설명】"));
			
			List<Component> descriptions=new ArrayList<Component>();
			for(int j=0;j<ability.description.length;j++) {
				descriptions.add(Component.text(ChatColor.WHITE+ability.description[j]));
			}
			abilityDescriptionItemMeta.lore(descriptions);;
			
			abilityName.setItemMeta(abilityNameItemMeta);
			abilityDescription.setItemMeta(abilityDescriptionItemMeta);

			switch(ability.rank) {
			case F:
				rankStack=new ItemStack(Material.ROTTEN_FLESH);
				rankItemMeta=rankStack.getItemMeta();
				rankItemMeta.displayName(Component.text( "랭크: "+ChatColor.GRAY+AbilityRank.F.getRank()));
				rankStack.setItemMeta(rankItemMeta);
				break;
			case C:
				rankStack=new ItemStack(Material.IRON_INGOT);
				rankItemMeta=rankStack.getItemMeta();
				rankItemMeta.displayName(Component.text("랭크: "+ChatColor.WHITE+ AbilityRank.C.getRank()));
				rankStack.setItemMeta(rankItemMeta);
				break;
			case B:
				rankStack=new ItemStack(Material.GOLD_INGOT);
				rankItemMeta=rankStack.getItemMeta();
				rankItemMeta.displayName(Component.text("랭크: "+ChatColor.GREEN+AbilityRank.B.getRank()));
				rankStack.setItemMeta(rankItemMeta);
				break;
			case A:
				rankStack=new ItemStack(Material.EMERALD);
				rankItemMeta=rankStack.getItemMeta();
				rankItemMeta.displayName(Component.text("랭크: "+ChatColor.AQUA+ AbilityRank.A.getRank()));
				rankStack.setItemMeta(rankItemMeta);
				break;
			case S:
				rankStack=new ItemStack(Material.DIAMOND);
				rankItemMeta=rankStack.getItemMeta();
				rankItemMeta.displayName(Component.text("랭크: "+ChatColor.YELLOW+ AbilityRank.S.getRank()));
				rankStack.setItemMeta(rankItemMeta);
				break;
			}
			
			inventory.setItem(4, abilityName);
			inventory.setItem(0, abilityDescription);
			inventory.setItem(8, rankStack);
			
			if(ability.activeType) {
				if(ability.secondSkillCoolTime !=-1) {
					abilityFirstCoolTime=new ItemStack(Material.CLOCK);
					abilitySecondCoolTime=new ItemStack(Material.CLOCK);
					
					List<Component> firstSkillExplain=new ArrayList<Component>();
					abilityFirstCoolTimeItemMeta.displayName(Component.text(ChatColor.GREEN+"~ 일반 능력 | 블레이즈 막대 좌클릭~"));
					firstSkillExplain.add(Component.text(ChatColor.WHITE+"쿨타임: "+ability.firstSkillCoolTime +" 초 소요"));
					firstSkillExplain.add(Component.text(ChatColor.WHITE+"조약돌: "+ability.firstSkillStack +"개 소모"));
					
					abilityFirstCoolTimeItemMeta.lore(firstSkillExplain);
					abilityFirstCoolTime.setItemMeta(abilityFirstCoolTimeItemMeta);
					
					List<Component> secondSkillExplain=new ArrayList<Component>();
					abilitySecondCoolTimeItemMeta.lore(null);
					abilitySecondCoolTimeItemMeta.displayName(Component.text(ChatColor.YELLOW+"~ 고급 능력 | 블레이즈 막대 우클릭 ~"));
					secondSkillExplain.add(Component.text(ChatColor.WHITE+"쿨타임: "+ability.secondSkillCoolTime +" 초 소요"));
					secondSkillExplain.add(Component.text(ChatColor.WHITE+"조약돌: "+ability.secondSkillStack +"개 소모"));
					
					abilitySecondCoolTimeItemMeta.lore(secondSkillExplain);
					abilitySecondCoolTime.setItemMeta(abilitySecondCoolTimeItemMeta);
					
					inventory.setItem(9, abilityFirstCoolTime);
					inventory.setItem(17, abilitySecondCoolTime);
					
				}else {
					abilityFirstCoolTime=new ItemStack(Material.CLOCK);
					
					List<Component> firstSkillExplain=new ArrayList<Component>();
					abilityFirstCoolTimeItemMeta.displayName(Component.text(ChatColor.GREEN+"~ 일반 능력 | 블레이즈 막대 좌클릭 ~"));
					firstSkillExplain.add(Component.text(ChatColor.WHITE+"쿨타임: "+ability.firstSkillCoolTime +" 초 소요"));
					firstSkillExplain.add(Component.text(ChatColor.WHITE+"조약돌: "+ability.firstSkillStack +"개 소모"));
					abilityFirstCoolTimeItemMeta.lore(firstSkillExplain);
					
					abilityFirstCoolTime.setItemMeta(abilityFirstCoolTimeItemMeta);
					
					inventory.setItem(13, abilityFirstCoolTime);
				}
			}if (!ability.activeType && ability.passiveType) {
				abilityFirstCoolTime=new ItemStack(Material.CLOCK);
				
				List<Component> passiveSkillExplain=new ArrayList<Component>();
				abilityFirstCoolTimeItemMeta.displayName(Component.text(ChatColor.GREEN+"~ 패시브 능력 ~"));
				passiveSkillExplain.add(Component.text(ChatColor.WHITE+"능력 설명을 확인하세요."));
				abilityFirstCoolTimeItemMeta.lore(passiveSkillExplain);
				
				abilityFirstCoolTime.setItemMeta(abilityFirstCoolTimeItemMeta);
				
				inventory.setItem(13, abilityFirstCoolTime);
			}
			
			Player p=(Player)sender;
			
			p.openInventory(inventory);
			
		}
		else
			sender.sendMessage("능력이 없습니다.");
	}


}
