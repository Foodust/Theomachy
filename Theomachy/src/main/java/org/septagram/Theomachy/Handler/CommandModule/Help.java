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
			Inventory inventory= Bukkit.createInventory(null, 18,  ChatColor.BLACK + ":::::::: 능력 정보 ::::::::");
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
			
			abilityNameItemMeta.setDisplayName( ChatColor.AQUA+"【능력 이름】 "+ChatColor.WHITE+ability.abilityName);
			abilityDescriptionItemMeta.setDisplayName( ChatColor.DARK_AQUA+"【능력 설명】");
			
			List<String> descriptions=new ArrayList<String>();
			for(int j=0;j<ability.description.length;j++) {
				descriptions.add(ChatColor.WHITE+ability.description[j]);
			}
			abilityDescriptionItemMeta.setLore(descriptions);;
			
			abilityName.setItemMeta(abilityNameItemMeta);
			abilityDescription.setItemMeta(abilityDescriptionItemMeta);

            switch (ability.rank) {
                case F -> {
                    rankStack = new ItemStack(Material.ROTTEN_FLESH);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.setDisplayName("랭크: " + ChatColor.GRAY + AbilityRank.F.getRank());
                    rankStack.setItemMeta(rankItemMeta);
                }
                case C -> {
                    rankStack = new ItemStack(Material.IRON_INGOT);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.setDisplayName("랭크: " + ChatColor.WHITE + AbilityRank.C.getRank());
                    rankStack.setItemMeta(rankItemMeta);
                }
                case B -> {
                    rankStack = new ItemStack(Material.GOLD_INGOT);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.setDisplayName("랭크: " + ChatColor.GREEN + AbilityRank.B.getRank());
                    rankStack.setItemMeta(rankItemMeta);
                }
                case A -> {
                    rankStack = new ItemStack(Material.EMERALD);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.setDisplayName("랭크: " + ChatColor.AQUA + AbilityRank.A.getRank());
                    rankStack.setItemMeta(rankItemMeta);
                }
                case S -> {
                    rankStack = new ItemStack(Material.DIAMOND);
                    rankItemMeta = rankStack.getItemMeta();
                    rankItemMeta.setDisplayName("랭크: " + ChatColor.YELLOW + AbilityRank.S.getRank());
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
					
					List<String> normalSkillExplain=new ArrayList<String>();
					abilityNormalCoolTimeItemMeta.setDisplayName(ChatColor.GREEN+"~ 일반 능력 | 블레이즈 막대 좌클릭~");
					normalSkillExplain.add(ChatColor.WHITE+"쿨타임: "+ability.normalSkillCoolTime +" 초 소요");
					normalSkillExplain.add(ChatColor.WHITE+"조약돌: "+ability.normalSkillStack +"개 소모");
					
					abilityNormalCoolTimeItemMeta.setLore(normalSkillExplain);
					abilityNormalCoolTime.setItemMeta(abilityNormalCoolTimeItemMeta);
					
					List<String> rareSkillExplain=new ArrayList<String>();
					abilityRareCoolTimeItemMeta.setLore(null);
					abilityRareCoolTimeItemMeta.setDisplayName(ChatColor.YELLOW+"~ 고급 능력 | 블레이즈 막대 우클릭 ~");
					rareSkillExplain.add(ChatColor.WHITE+"쿨타임: "+ability.rareSkillCoolTime +" 초 소요");
					rareSkillExplain.add(ChatColor.WHITE+"조약돌: "+ability.rareSkillStack +"개 소모");
					
					abilityRareCoolTimeItemMeta.setLore(rareSkillExplain);
					abilityRareCoolTime.setItemMeta(abilityRareCoolTimeItemMeta);
					
					inventory.setItem(9, abilityNormalCoolTime);
					inventory.setItem(17, abilityRareCoolTime);
					
				}else {
					abilityNormalCoolTime=new ItemStack(Material.CLOCK);
					
					List<String> normalSkillExplain=new ArrayList<String>();
					abilityNormalCoolTimeItemMeta.setDisplayName(ChatColor.GREEN+"~ 일반 능력 | 블레이즈 막대 좌클릭 ~");
					normalSkillExplain.add(ChatColor.WHITE+"쿨타임: "+ability.normalSkillCoolTime +" 초 소요");
					normalSkillExplain.add(ChatColor.WHITE+"조약돌: "+ability.normalSkillStack +"개 소모");
					abilityNormalCoolTimeItemMeta.setLore(normalSkillExplain);
					
					abilityNormalCoolTime.setItemMeta(abilityNormalCoolTimeItemMeta);
					
					inventory.setItem(13, abilityNormalCoolTime);
				}
			}if (!ability.activeType && ability.passiveType) {
				abilityNormalCoolTime=new ItemStack(Material.CLOCK);
				
				List<String> passiveSkillExplain=new ArrayList<String>();
				abilityNormalCoolTimeItemMeta.setDisplayName(ChatColor.GREEN+"~ 패시브 능력 ~");
				passiveSkillExplain.add(ChatColor.WHITE+"능력 설명을 확인하세요.");
				abilityNormalCoolTimeItemMeta.setLore(passiveSkillExplain);
				
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
