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

import org.Theomachy.Theomachy;

public class GamblingCommand {

    public static void Module(CommandSender sender) {
        Player p = (Player) sender;

        if (Theomachy.GAMBLING) {
            p.openInventory(gui());
        } else {
            p.sendMessage(ChatColor.RED + "이 기능은 잠겨있습니다!");
        }

    }

    private static Inventory gui() {

        Inventory con = Bukkit.createInventory(null, 9, CommonMessage.MENU.getMessage());

        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(CommonMessage.GAMBLING.getMessage());
        List<String> loreList = new ArrayList<String>();
        loreList.add(ChatColor.WHITE + "조약돌 32개를 소모해 다양한 아이템을");
        loreList.add(ChatColor.WHITE + "뽑을 수 있습니다.");
        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);

        con.setItem(4, itemStack);

        return con;
    }

}
