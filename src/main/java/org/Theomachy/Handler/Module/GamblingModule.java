package org.Theomachy.Handler.Module;

import org.Theomachy.Enum.CommonMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GamblingModule {
    static int gamblingSize = 1 * 9;
    public static Inventory gui() {

        Inventory inventory = Bukkit.createInventory(null, gamblingSize, CommonMessage.GAMBLING.getMessage());

        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(CommonMessage.GAMBLING.getMessage());
        List<String> loreList = new ArrayList<String>();
        loreList.add(ChatColor.WHITE + "조약돌 32개를 소모해 다양한 아이템을");
        loreList.add(ChatColor.WHITE + "뽑을 수 있습니다.");
        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);

        inventory.setItem(4, itemStack);

        return inventory;
    }
}
