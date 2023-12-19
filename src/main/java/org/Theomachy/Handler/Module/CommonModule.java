package org.Theomachy.Handler.Module;

import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Manager.EntityManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommonModule {
    public static ItemStack setItem(Material material, int amount, String title) {
        ItemStack prevItem = new ItemStack(material, amount);
        ItemMeta prevItemMeta = prevItem.getItemMeta();
        assert prevItemMeta != null;
        prevItemMeta.setDisplayName(title);
        prevItem.setItemMeta(prevItemMeta);
        return prevItem;
    }
    public static void breakDiamond(BlockBreakEvent event){
        for(int i = 0 ; i < 5; i++){
            Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + TheomachyMessage.WHO_BREAK_DIAMOND);
        }
        Firework firework = event.getPlayer().getWorld().spawn(event.getBlock().getLocation(), Firework.class);
        EntityManager.spawnRandomFirework(firework);
    }
}
