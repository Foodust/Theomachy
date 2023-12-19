package org.Theomachy.Handler.Module;

import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Handler.Manager.EntityManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommonModule {
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str); // 숫자로 변환 시도
            return true; // 변환이 성공하면 숫자임
        } catch (NumberFormatException e) {
            return false; // 변환이 실패하면 숫자가 아님
        }
    }
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
            Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + TheomachyMessage.END_WHO_BREAK_DIAMOND);
        }
        Firework firework = event.getPlayer().getWorld().spawn(event.getBlock().getLocation(), Firework.class);
        EntityManager.spawnRandomFirework(firework);
    }
}
