package org.septagram.Theomachy.Handler.CommandModule;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.DB.AbilityData;
import org.septagram.Theomachy.Utility.PermissionChecker;

public class Blacklist {

    public static List<Integer> GodCanlist = new ArrayList<Integer>();
    public static List<Integer> HumanCanlist = new ArrayList<Integer>();
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

        ItemStack[] wool = new ItemStack[god.length + man.length + 2];
        ItemMeta[] meta = new ItemMeta[god.length + man.length + 2];

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

        int b = 101;
        for (int index = god.length; index < (god.length + man.length); index++) {
            wool[index] = new ItemStack(Material.WHITE_WOOL);
            meta[index] = wool[index].getItemMeta();
            meta[index].setDisplayName(ChatColor.WHITE + AbilityInfo.getNameByIndex(index) + " : " + b);
            if (!Blacklist.contains(b)) {
                wool[index].setDurability((short) 5);
            } else {
                wool[index].setDurability((short) 14);
            }
            wool[index].setItemMeta(meta[index]);
            b++;
        }

        for (int i = 0; i < god.length + man.length; i++) {
            gui.setItem(i, wool[i]);
        }

        return gui;
    }
}
