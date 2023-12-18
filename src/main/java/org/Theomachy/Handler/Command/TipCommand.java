package org.Theomachy.Handler.Command;

import java.util.ArrayList;
import java.util.List;

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
		
		Inventory con=Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN+":::::::: 팁 ::::::::");
		
		ItemStack gam=new ItemStack(Material.BOOK);
		ItemMeta met=gam.getItemMeta();
		met.setDisplayName(ChatColor.YELLOW+"[TIP]");
		List<String> lor1=new ArrayList<String>();
		lor1.add(ChatColor.WHITE+"블레이즈 로드는 제작대에서 나무 막대를 세로로 일렬로");
		lor1.add(ChatColor.WHITE+"놓으면 만들 수 있습니다.");
		met.setLore(lor1);
		gam.setItemMeta(met);
		
		ItemStack t1=new ItemStack(Material.BOOK);
		ItemMeta met2=gam.getItemMeta();
		met2.setDisplayName(ChatColor.YELLOW+"[TIP]");
		List<String> lor2=new ArrayList<String>();
		lor2.add(ChatColor.AQUA+"【일반】"+ChatColor.WHITE+" 능력은 블레이즈 로드를 좌클릭하여,");
		lor2.add(ChatColor.RED+"【고급】"+ChatColor.WHITE+" 능력은 블레이즈 로드를 우클릭하여 이용합니다.");
		lor2.add(ChatColor.WHITE+"자세한 내용은 언제나 능력 설명을 참고합시다!");
		met2.setLore(lor2);
		t1.setItemMeta(met2);
		
		con.setItem(3, gam);
		con.setItem(5, t1);
		
		return con;
	}
}
