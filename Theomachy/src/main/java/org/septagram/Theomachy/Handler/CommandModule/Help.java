package org.septagram.Theomachy.Handler.CommandModule;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
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
			Inventory inventory= Bukkit.createInventory(null, 18, Component.text( NamedTextColor.BLACK + ":::::::: 능력 정보 ::::::::"));
			ItemStack abilityName=new ItemStack(Material.ITEM_FRAME);
			ItemStack abilityDescription=new ItemStack(Material.BOOK);
			ItemStack abilityNormalCoolTime=new ItemStack(Material.CLOCK);
			ItemStack abilityRareCoolTime=new ItemStack(Material.CLOCK);
			ItemStack rankStack = new ItemStack(Material.AIR);
			
			ItemMeta abilityNameItemMeta = abilityName.getItemMeta();
			ItemMeta abilityDescriptionItemMeta = abilityDescription.getItemMeta();
			ItemMeta abilityNormalCoolTimeItemMeta = abilityNormalCoolTime.getItemMeta();
			ItemMeta abilityRareCoolTimeItemMeta = abilityRareCoolTime.getItemMeta();
			ItemMeta rankItemMeta = rankStack.getItemMeta();
			
			abilityNameItemMeta.displayName(Component.text( NamedTextColor.AQUA+"【능력 이름】 "+NamedTextColor.WHITE+ability.abilityName));
			abilityDescriptionItemMeta.displayName(Component.text( NamedTextColor.DARK_AQUA+"【능력 설명】"));
			
			List<Component> descriptions=new ArrayList<Component>();
			for(int j=0;j<ability.description.length;j++) {
				descriptions.add(Component.text(NamedTextColor.WHITE+ability.description[j]));
			}
			abilityDescriptionItemMeta.lore(descriptions);;
			
			abilityName.setItemMeta(abilityNameItemMeta);
			abilityDescription.setItemMeta(abilityDescriptionItemMeta);

            switch (ability.rank) {
                case F -> {
                    rankStack = new ItemStack(Material.ROTTEN_FLESH);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.displayName(Component.text("랭크: " + NamedTextColor.GRAY + AbilityRank.F.getRank()));
                    rankStack.setItemMeta(rankItemMeta);
                }
                case C -> {
                    rankStack = new ItemStack(Material.IRON_INGOT);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.displayName(Component.text("랭크: " + NamedTextColor.WHITE + AbilityRank.C.getRank()));
                    rankStack.setItemMeta(rankItemMeta);
                }
                case B -> {
                    rankStack = new ItemStack(Material.GOLD_INGOT);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.displayName(Component.text("랭크: " + NamedTextColor.GREEN + AbilityRank.B.getRank()));
                    rankStack.setItemMeta(rankItemMeta);
                }
                case A -> {
                    rankStack = new ItemStack(Material.EMERALD);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.displayName(Component.text("랭크: " + NamedTextColor.AQUA + AbilityRank.A.getRank()));
                    rankStack.setItemMeta(rankItemMeta);
                }
                case S -> {
                    rankStack = new ItemStack(Material.DIAMOND);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.displayName(Component.text("랭크: " + NamedTextColor.YELLOW + AbilityRank.S.getRank()));
                    rankStack.setItemMeta(rankItemMeta);
                }
            }
			
			inventory.setItem(4, abilityName);
			inventory.setItem(0, abilityDescription);
			inventory.setItem(8, rankStack);
			
			if(ability.activeType) {
				if(ability.rareSkillCoolTime !=-1) {
					abilityNormalCoolTime=new ItemStack(Material.CLOCK);
					abilityRareCoolTime=new ItemStack(Material.CLOCK);
					
					List<Component> normalSkillExplain=new ArrayList<Component>();
					abilityNormalCoolTimeItemMeta.displayName(Component.text(NamedTextColor.GREEN+"~ 일반 능력 | 블레이즈 막대 좌클릭~"));
					normalSkillExplain.add(Component.text(NamedTextColor.WHITE+"쿨타임: "+ability.normalSkillCoolTime +" 초 소요"));
					normalSkillExplain.add(Component.text(NamedTextColor.WHITE+"조약돌: "+ability.normalSkillStack +"개 소모"));
					
					abilityNormalCoolTimeItemMeta.lore(normalSkillExplain);
					abilityNormalCoolTime.setItemMeta(abilityNormalCoolTimeItemMeta);
					
					List<Component> rareSkillExplain=new ArrayList<Component>();
					abilityRareCoolTimeItemMeta.lore(null);
					abilityRareCoolTimeItemMeta.displayName(Component.text(NamedTextColor.YELLOW+"~ 고급 능력 | 블레이즈 막대 우클릭 ~"));
					rareSkillExplain.add(Component.text(NamedTextColor.WHITE+"쿨타임: "+ability.rareSkillCoolTime +" 초 소요"));
					rareSkillExplain.add(Component.text(NamedTextColor.WHITE+"조약돌: "+ability.rareSkillStack +"개 소모"));
					
					abilityRareCoolTimeItemMeta.lore(rareSkillExplain);
					abilityRareCoolTime.setItemMeta(abilityRareCoolTimeItemMeta);
					
					inventory.setItem(9, abilityNormalCoolTime);
					inventory.setItem(17, abilityRareCoolTime);
					
				}else {
					abilityNormalCoolTime=new ItemStack(Material.CLOCK);
					
					List<Component> normalSkillExplain=new ArrayList<Component>();
					abilityNormalCoolTimeItemMeta.displayName(Component.text(NamedTextColor.GREEN+"~ 일반 능력 | 블레이즈 막대 좌클릭 ~"));
					normalSkillExplain.add(Component.text(NamedTextColor.WHITE+"쿨타임: "+ability.normalSkillCoolTime +" 초 소요"));
					normalSkillExplain.add(Component.text(NamedTextColor.WHITE+"조약돌: "+ability.normalSkillStack +"개 소모"));
					abilityNormalCoolTimeItemMeta.lore(normalSkillExplain);
					
					abilityNormalCoolTime.setItemMeta(abilityNormalCoolTimeItemMeta);
					
					inventory.setItem(13, abilityNormalCoolTime);
				}
			}if (!ability.activeType && ability.passiveType) {
				abilityNormalCoolTime=new ItemStack(Material.CLOCK);
				
				List<Component> passiveSkillExplain=new ArrayList<Component>();
				abilityNormalCoolTimeItemMeta.displayName(Component.text(NamedTextColor.GREEN+"~ 패시브 능력 ~"));
				passiveSkillExplain.add(Component.text(NamedTextColor.WHITE+"능력 설명을 확인하세요."));
				abilityNormalCoolTimeItemMeta.lore(passiveSkillExplain);
				
				abilityNormalCoolTime.setItemMeta(abilityNormalCoolTimeItemMeta);
				
				inventory.setItem(13, abilityNormalCoolTime);
			}
			
			Player p=(Player)sender;
			
			p.openInventory(inventory);
			
		}
		else
			sender.sendMessage("능력이 없습니다.");
	}


}
