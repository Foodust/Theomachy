package org.septagram.Theomachy.Utility;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Message.T_Message;

public class PlayerInventory
{
	public static boolean InHandItemCheck(Player player, Material material)
	{
		if (player.getItemInHand().getType() == material)
			return true;
		else
			return false;
	}
	
	public static boolean ItemCheck(Player player, Material material, int stack)
	{
		Inventory inventory = player.getInventory();
		if (inventory.contains(material, stack))
			return true;
		else
		{
			T_Message.LackItemError(player, material, stack);
			return false;
		}
	}
	
	public static void ItemRemove(Player player, Material material, int stack)
	{
		Inventory inventory = player.getInventory();
		inventory.removeItem(new ItemStack(material, stack));
	}
	
	public static void skyBlockBasicItemAdd(Player player)
	{
		Inventory inventory = player.getInventory();
		if (Theomachy.INVENTORY_CLEAR)
		{
			inventory.clear();
			player.getInventory().setHelmet(new ItemStack(Material.AIR));
			player.getInventory().setChestplate(new ItemStack(Material.AIR));
			player.getInventory().setLeggings(new ItemStack(Material.AIR));
			player.getInventory().setBoots(new ItemStack(Material.AIR));
		}
		if (Theomachy.GIVE_ITEM)
		{
			inventory.addItem(new ItemStack(Material.BLAZE_ROD, 1));
			inventory.addItem(new ItemStack(Material.COBBLESTONE, 100));

			//
			inventory.addItem(new ItemStack(Material.CHEST, 1));
			inventory.addItem(new ItemStack(Material.STICK, 1));
			inventory.addItem(new ItemStack(Material.LAVA_BUCKET, 1));
			inventory.addItem(new ItemStack(Material.ICE, 2));
			inventory.addItem(new ItemStack(Material.OAK_PLANKS, 1));
			inventory.addItem(new ItemStack(Material.WHEAT, 8));
//			inventory.addItem(new ItemStack(Material.INK_SAC, 1, (short)15));
		}
	}
}

