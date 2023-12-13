package org.septagram.Theomachy.Handler.CommandModule;

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

import org.septagram.Theomachy.Theomachy;

public class Convi {

	public static void Module(CommandSender sender) {
		Player p=(Player) sender;
		
		if(Theomachy.GAMB) {
			p.openInventory(gui());
		}else {
			p.sendMessage(ChatColor.RED+"이 기능은 잠겨있습니다!");
		}
		
	}
	
	private static Inventory gui() {
		
		Inventory con=Bukkit.createInventory(null, 9, ChatColor.BLACK+":::::::: 편의 기능 ::::::::");
		
		ItemStack gam=new ItemStack(Material.GOLD_INGOT);
		ItemMeta met1=gam.getItemMeta();
		met1.setDisplayName(ChatColor.YELLOW+"가챠"+ChatColor.AQUA+" ★ "+ChatColor.GREEN+"가챠");
		List<String> lor1=new ArrayList<String>();
		lor1.add(ChatColor.WHITE+"조약돌 32개를 소모해 다양한 아이템을");
		lor1.add(ChatColor.WHITE+"뽑을 수 있습니다.");
		met1.setLore(lor1);
		gam.setItemMeta(met1);
		
		con.setItem(4, gam);
		
		return con;
	}
	
}
