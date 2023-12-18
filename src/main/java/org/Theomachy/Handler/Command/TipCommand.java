package org.Theomachy.Handler.Command;

import java.util.ArrayList;
import java.util.List;

import org.Theomachy.Enum.CommonMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TipCommand {
	public static void Module(CommandSender sender) {
		Player p=(Player) sender;
		p.openInventory(gui());
		
	}
	
	private static Inventory gui() {
		
		Inventory con=Bukkit.createInventory(null, 9, CommonMessage.TIP.getMessage());
		
		ItemStack itemStack1=new ItemStack(Material.BOOK);
		ItemMeta itemMeta1=itemStack1.getItemMeta();
        assert itemMeta1 != null;
        itemMeta1.setDisplayName(ChatColor.YELLOW+"[TIP]");
		List<String> loreList=new ArrayList<String>();
		loreList.add(ChatColor.WHITE+"블레이즈 로드는 제작대에서 나무 막대를 세로로 일렬로");
		loreList.add(ChatColor.WHITE+"놓으면 만들 수 있습니다.");
		itemMeta1.setLore(loreList);
		itemStack1.setItemMeta(itemMeta1);
		
		ItemStack itemStack2=new ItemStack(Material.BOOK);
		ItemMeta itemMeta2=itemStack1.getItemMeta();
		itemMeta2.setDisplayName(ChatColor.YELLOW+"[TIP]");
		List<String> loreList2=new ArrayList<String>();
		loreList2.add(ChatColor.AQUA+"【일반】"+ChatColor.WHITE+" 능력은 블레이즈 로드를 좌클릭하여,");
		loreList2.add(ChatColor.RED+"【고급】"+ChatColor.WHITE+" 능력은 블레이즈 로드를 우클릭하여 이용합니다.");
		loreList2.add(ChatColor.WHITE+"자세한 내용은 언제나 능력 설명을 참고합시다!");
		itemMeta2.setLore(loreList2);
		itemStack2.setItemMeta(itemMeta2);
		
		con.setItem(3, itemStack1);
		con.setItem(5, itemStack2);
		
		return con;
	}
}
