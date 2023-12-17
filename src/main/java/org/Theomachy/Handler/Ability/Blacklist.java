package org.Theomachy.Handler.Ability;

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

import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Data.AbilityData;
import org.Theomachy.Utility.Checker.PermissionChecker;

public class Blacklist {

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
        int[] god = new int[AbilityData.GOD_ABILITY_NUMBER];
        for (int index = 0; index < AbilityData.GOD_ABILITY_NUMBER; index++)
            god[index] = index + 1;
        int a = 101;
        int[] man = new int[AbilityData.HUMAN_ABILITY_NUMBER];
        for (int index = 0; index < AbilityData.HUMAN_ABILITY_NUMBER; index++) {
            man[index] = a++;
        }
        int[] yaiba = new int[AbilityData.KIMETHU_NO_YAIBA_ABILITY_NUMBER];
        for (int index = 0; index < AbilityData.KIMETHU_NO_YAIBA_ABILITY_NUMBER; index++) {
            yaiba[index] = a++;
        }

        int length = god.length + man.length + yaiba.length + 2;
        ItemStack[] wool = new ItemStack[length];
        ItemMeta[] meta = new ItemMeta[length];

        for (int index = 1; index <= god.length; index++) {
            wool[index - 1] = new ItemStack(Material.WHITE_WOOL);
            meta[index] = wool[index - 1].getItemMeta();
            meta[index].setDisplayName(ChatColor.WHITE + AbilityInfo.getNameByIndex(index) + " : " + String.valueOf(index));
            if (!Blacklist.contains(index)) {
                wool[index - 1].setDurability((short) 5);
            } else {
                wool[index - 1].setDurability((short) 14);
            }
            wool[index - 1].setItemMeta(meta[index]);
        }

        int humanLength = 101;
        for (int index = god.length; index < (god.length + man.length); index++) {
            wool[index] = new ItemStack(Material.WHITE_WOOL);
            meta[index] = wool[index].getItemMeta();
            meta[index].setDisplayName(ChatColor.WHITE + AbilityInfo.getNameByIndex(index) + " : " + humanLength);
            if (!Blacklist.contains(humanLength)) {
                wool[index].setDurability((short) 5);
            } else {
                wool[index].setDurability((short) 14);
            }
            wool[index].setItemMeta(meta[index]);
            humanLength++;
        }

        for (int i = 0; i < god.length + man.length; i++) {
            gui.setItem(i, wool[i]);
        }

        return gui;
    }
}