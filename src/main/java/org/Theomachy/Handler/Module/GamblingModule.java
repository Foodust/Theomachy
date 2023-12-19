package org.Theomachy.Handler.Module;

import org.Theomachy.Enum.TheomachyMessage;
import org.bukkit.Bukkit;
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
    public static void openGamblingInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, gamblingSize, TheomachyMessage.SETTING_GAMBLING.getMessage());
        ItemStack itemStack = CommonModule.setItem(Material.GOLD_INGOT, 1, TheomachyMessage.SETTING_GAMBLING.getMessage());
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> loreList = new ArrayList<String>();
        loreList.add(TheomachyMessage.INFO_GAMBLING1.getMessage());
        loreList.add(TheomachyMessage.INFO_GAMBLING2.getMessage());
        assert itemMeta != null;
        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(4, itemStack);
        player.openInventory(inventory);
    }
    public static void gambling(Player player) {
        Inventory inventory = player.getInventory();
        int cobbleStone = inventory.all(Material.COBBLESTONE).values().stream()
                .mapToInt(ItemStack::getAmount)
                .sum();
        if (cobbleStone >= 32) {
            Random random = new Random();
            int randomPercentage = 100;
            player.getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 32));
            int randomNumber = (int) random.nextInt(randomPercentage);
            if (randomNumber < 50) {
                player.sendMessage(randomPercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_1.getMessage());
                int cobblestoneNumber = (int)random.nextInt(41) + 20;
                player.getInventory().addItem(new ItemStack(Material.COBBLESTONE, cobblestoneNumber));
            } else if (randomNumber < 80) {
                player.sendMessage(randomPercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_2.getMessage());
                player.getInventory().addItem(new ItemStack(Material.OAK_LOG, 3));
            } else if (randomNumber < 95) {
                player.sendMessage(randomPercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_3.getMessage());
            } else if (randomNumber < 99) {
                player.sendMessage(randomPercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_4.getMessage());
                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 4));
            } else {
                Bukkit.broadcastMessage(player.getName() + "님이" + randomPercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_5.getMessage());
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 10));
            }
        } else {
            player.sendMessage(TheomachyMessage.ERROR_DOES_NOT_HAVE_COBBLE_STONE.getMessage());
        }
    }
}
