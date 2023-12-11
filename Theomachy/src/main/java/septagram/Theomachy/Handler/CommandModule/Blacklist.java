package septagram.Theomachy.Handler.CommandModule;

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

import septagram.Theomachy.DB.AbilityData;
import septagram.Theomachy.Utility.PermissionChecker;
import septagram.Theomachy.Utility.ReturnAbilityName;

public class Blacklist {
	
	public static List<Integer> GodCanlist=new ArrayList<Integer>();
	public static List<Integer> HumanCanlist=new ArrayList<Integer>();
	public static List<Integer> Blacklist=new ArrayList<Integer>();
	public static int Canlist;
	
	public static void Module(CommandSender sender) {
		if(PermissionChecker.Sender(sender)) {
			Player p=(Player)sender;
			p.openInventory(blackgui());
		}
	}
	
	//추가라벨
	private static Inventory blackgui() {
		Inventory gui=Bukkit.createInventory(null, 54, ChatColor.BLACK+":: 블랙리스트 ::");
		
		
		int[] god=new int[AbilityData.GOD_ABILITY_NUMBER];
		for(int i=0; i<AbilityData.GOD_ABILITY_NUMBER; i++)
			god[i]=i+1;
		int a=101;
		int[] man=new int[AbilityData.HUMAN_ABILITY_NUMBER];
		for(int i=0; i<AbilityData.HUMAN_ABILITY_NUMBER; i++) {
			man[i]=a++;
		}
		
		ItemStack[] wool=new ItemStack[god.length+man.length+2];
		ItemMeta[] meta=new ItemMeta[god.length+man.length+2];
		
		for(int i=1;i<=god.length;i++) {
			wool[i-1]=new ItemStack(Material.WHITE_WOOL);
			meta[i]=wool[i-1].getItemMeta();
			meta[i].setDisplayName(ChatColor.WHITE+ReturnAbilityName.name(i) + " : "+String.valueOf(i));
			if(!Blacklist.contains(i)) {wool[i-1].setDurability((short)5);}
			else {wool[i-1].setDurability((short)14);}
			wool[i-1].setItemMeta(meta[i]);
		} 
		
		int b=101;
		for(int i=god.length;i<(god.length+man.length);i++) {
			wool[i]=new ItemStack(Material.WHITE_WOOL);
			meta[i]=wool[i].getItemMeta();
			meta[i].setDisplayName(ChatColor.WHITE+ReturnAbilityName.name(b) + " : "+b);
			if(!Blacklist.contains(b)) {wool[i].setDurability((short)5);}
			else {wool[i].setDurability((short)14);}
			wool[i].setItemMeta(meta[i]);
			b++;
		}
		
		for(int i=0;i<god.length+man.length;i++) {
			gui.setItem(i, wool[i]);
		}
		
		return gui;
	}
	
}
