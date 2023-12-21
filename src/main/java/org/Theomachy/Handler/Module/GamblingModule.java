package org.Theomachy.Handler.Module;

import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Utility.DefaultUtil;
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
    private final CommonModule commonModule = new CommonModule();

    public void openGamblingInventory(Player player) {
        int gamblingSize = 1 * 9;
        Inventory inventory = Bukkit.createInventory(null, gamblingSize, TheomachyMessage.SETTING_GAMBLING.getMessage());
        ItemStack itemStack = commonModule.setItem(Material.GOLD_INGOT, 1, TheomachyMessage.SETTING_GAMBLING.getMessage());
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
    public void gambling(Player player) {
        Inventory inventory = player.getInventory();
        int cobbleStone = inventory.all(Material.COBBLESTONE).values().stream()
                .mapToInt(ItemStack::getAmount)
                .sum();
        int cobbleCount = 32;
        if (cobbleStone >= cobbleCount) {
            Random random = new Random();
            int cobblePercentage = 50;
            int oakPercentage = 30;
            int nullPercentage = 15;
            int ironPercentage = 4;
            int diamondPercentage = 1;
            int randomPercentage = cobblePercentage + oakPercentage + nullPercentage + ironPercentage + diamondPercentage;
            player.getInventory().removeItem(new ItemStack(Material.COBBLESTONE, cobbleCount));
            int randomNumber = (int) random.nextInt(randomPercentage);
            if (randomNumber < cobblePercentage) {
                player.sendMessage(cobblePercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_1.getMessage());
                int cobblestoneNumber = (int)random.nextInt(41) + 20;
                player.getInventory().addItem(new ItemStack(Material.COBBLESTONE, cobblestoneNumber));
            }
            else if (randomNumber < cobblePercentage + oakPercentage) {
                player.sendMessage(oakPercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_2.getMessage());
                int oakCount = 3;
                player.getInventory().addItem(new ItemStack(Material.OAK_LOG, oakCount));
            }
            else if (randomNumber < cobblePercentage + oakPercentage + nullPercentage) {
                player.sendMessage(nullPercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_3.getMessage());
            }
            else if (randomNumber < cobbleStone + oakPercentage + nullPercentage + ironPercentage) {
                player.sendMessage(ironPercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_4.getMessage());
                int ironCount = 4;
                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, ironCount));
            }
            else {
                Bukkit.broadcastMessage(player.getName() + "님이" + diamondPercentage + TheomachyMessage.INFO_GAMBLING_MESSAGE_5.getMessage());
                int diamondCount = 10;
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, diamondCount));
            }
        } else {
            player.sendMessage(TheomachyMessage.ERROR_DOES_NOT_HAVE_COBBLE_STONE.getMessage());
        }
    }
}
