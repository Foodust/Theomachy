package org.Theomachy.Handler.CommandModule;

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

import org.Theomachy.ENUM.AbilityInfo;
import org.Theomachy.Data.AbilityData;
import org.Theomachy.Utility.Checker.PermissionChecker;

public class BlacklistCommand {

    public static List<Integer> GodCanlist = new ArrayList<Integer>();
    public static List<Integer> HumanCanlist = new ArrayList<Integer>();
    public static List<Integer> KimethuCanList = new ArrayList<Integer>();
    public static List<Integer> JujuthuCanList = new ArrayList<Integer>();
    public static List<Integer> Blacklist = new ArrayList<Integer>();
    public static int availableList;

    public static void Module(CommandSender sender) {
        if (PermissionChecker.Sender(sender)) {
            Player p = (Player) sender;
            p.openInventory(blacklistGui());
        }
    }
    //추가라벨
    private static Inventory blacklistGui() {
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.BLACK + ":: 블랙리스트 ::");
//        int[] god = createAndFillArray(1, AbilityData.GOD_ABILITY_NUMBER);
//        int[] human = createAndFillArray(101, AbilityData.HUMAN_ABILITY_NUMBER);
//        int[] juju = createAndFillArray(301, AbilityData.JUJUTSU_KAISEN_ABILITY_NUMBER);
//        int[] yaiba = createAndFillArray(401, AbilityData.KIMETHU_NO_YAIBA_ABILITY_NUMBER);
//
//        int length = god.length + human.length + juju.length + yaiba.length + 2;
//        ItemStack[] wool = new ItemStack[length];
//        ItemMeta[] meta = new ItemMeta[length];
//
//        createWool(wool, meta, 1, god.length, 1);
//        createWool(wool, meta, god.length, god.length + human.length,101);
//        createWool(wool, meta, human.length, god.length + human.length + juju.length,301);
//        createWool(wool, meta, juju.length, length,401);
//
//        fillInventory(gui, wool);

        return gui;
    }
    public static void createWool(ItemStack[] wool, ItemMeta[] meta,int start ,int length,int checkNum){
        for (int index = start; index <= length; index++) {
            wool[index - 1] = new ItemStack(Material.WHITE_WOOL);
            meta[index] = wool[index - 1].getItemMeta();
            meta[index].setDisplayName(ChatColor.WHITE + AbilityInfo.getNameByIndex(index) + " : " + String.valueOf(index));
            wool[index -1].setDurability((short) (!Blacklist.contains(checkNum) ? 14 : 5));
            wool[index - 1].setItemMeta(meta[index]);
            checkNum++;
        }
    }
    public static int[] createAndFillArray(int startIndex, int arrayLength) {
        int[] array = new int[arrayLength];
        for (int index = 0; index < arrayLength; index++) {
            array[index] = startIndex++;
        }
        return array;
    }

    private static ItemStack createWoolItem(String displayName, boolean isBlacklisted) {
        ItemStack itemStack = new ItemStack(Material.WHITE_WOOL);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        itemStack.setDurability((short) (isBlacklisted ? 14 : 5));
        return itemStack;
    }

    private static void fillInventory(Inventory gui, ItemStack[] items) {
        for (int i = 0; i < items.length; i++) {
            gui.setItem(i, items[i]);
        }
    }
}
