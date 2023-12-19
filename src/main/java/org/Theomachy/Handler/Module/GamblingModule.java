package org.Theomachy.Handler.Module;

import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamblingModule {
    static int gamblingSize = 1 * 9;
    public static Inventory gui() {

        Inventory inventory = Bukkit.createInventory(null, gamblingSize, TheomachyMessage.GAMBLING.getMessage());

        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(TheomachyMessage.GAMBLING.getMessage());
        List<String> loreList = new ArrayList<String>();
        loreList.add(ChatColor.WHITE + "조약돌 32개를 소모해 다양한 아이템을");
        loreList.add(ChatColor.WHITE + "뽑을 수 있습니다.");
        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);

        inventory.setItem(4, itemStack);

        return inventory;
    }
    public static void gambling(Player player) {
        Inventory inv = player.getInventory();
        int cobbleStone = inv.all(Material.COBBLESTONE).values().stream()
                .mapToInt(ItemStack::getAmount)
                .sum();
        if (cobbleStone >= 32) {
            Random r = new Random();
            player.getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 32));
            int rn = (int) (r.nextInt(100));
            if (rn <= 4) {
                player.sendMessage(ChatColor.AQUA + "와우! 축하합니다! 다이아몬드 3개입니다!");
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 3));
            } else if (rn <= 19) {
                player.sendMessage(ChatColor.GOLD + "대박! 짜잔! 원목 3개 당첨 축하드립니다!");
                player.getInventory().addItem(new ItemStack(Material.OAK_LOG, 3));
            } else if (rn <= 34) {
                player.sendMessage(ChatColor.RED + "하하하하하하! 이 돌은 서버의 신이 가져간다! 하하하하하!");
                player.sendMessage(ChatColor.BLUE + "서버의 신의 자비로 능력의 막대를 드립니다.");
                player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 1));
            } else if (rn <= 79) {
                player.sendMessage("당신의 운은 평범하군요! 철괴 4개를 드립니다.");
                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 4));
            } else if (rn <= 98) {
                player.sendMessage("당신의 운은 평범하군요! 철괴 6개를 드립니다.");
                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 6));
            } else {
                player.sendMessage(ChatColor.YELLOW + "헐... 대박, 당신의 운은 미쳤군요!");
                player.sendMessage(ChatColor.AQUA + "다이아몬드 10개를 드립니다!");
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 22));
            }
        } else {
            player.sendMessage(ChatColor.RED + "조약돌이 부족합니다!");
        }
    }
}
