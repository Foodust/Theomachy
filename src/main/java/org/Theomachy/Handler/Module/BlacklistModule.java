package org.Theomachy.Handler.Module;

import org.Theomachy.Data.AbilityData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.CommonMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BlacklistModule {

    public static List<Integer> godCanlist = new ArrayList<>();
    public static List<Integer> humanCanlist = new ArrayList<>();
    public static List<Integer> jujutsuCanList = new ArrayList<>();
    public static List<Integer> kimetsuCanlist = new ArrayList<>();
    public static int availableList;
    public static List<Integer> blacklist = new ArrayList<>();
    public static List<Inventory> blackListInventories = new ArrayList<>();

    public static int itemsPerPage = 6 * 9; // 페이지당 아이템 수

    public static void openBlackListInventory(Player player) {
        int totalPages = 4;
        if (blackListInventories.isEmpty()) {
            for (int i = 1; i <= totalPages; i++) {
                blackListInventories.add(makeInventory(i));
            }
        }
        player.openInventory(blackListInventories.get(0)); // 첫 번째 페이지 인벤토리 열기
    }

    //추가라벨
    public static Inventory makeInventory(int page) {

        Inventory inventory = Bukkit.createInventory(null, itemsPerPage, CommonMessage.BLACKLIST.getMessage());
        int index = 0, length = 0;
        switch (page) {
            case 1 -> {
                index = 1;
                length = index +AbilityData.GOD_ABILITY_NUMBER -1;
            }
            case 2 -> {
                index = 101;
                length = index + AbilityData.HUMAN_ABILITY_NUMBER -1;
            }
            case 3 -> {
                index = 301;
                length = index + AbilityData.JUJUTSU_KAISEN_ABILITY_NUMBER -1;
            }
            case 4 -> {
                index = 401;
                length = index + AbilityData.KIMETSU_NO_YAIBA_ABILITY_NUMBER -1;
            }
        }
        for (int itemIndex = index - 1; itemIndex < length; itemIndex++) {
            ItemStack item = setItem(!blacklist.contains(index) ? Material.WHITE_WOOL : Material.RED_WOOL,
                    1,
                    ChatColor.WHITE + AbilityInfo.getNameByIndex(itemIndex + 1) + " : " + (itemIndex + 1));
            inventory.setItem(itemIndex, item);
        }

        ItemStack nextItem = setItem(Material.ITEM_FRAME, 1, ChatColor.WHITE + CommonMessage.PREV_PAGE.getMessage());
        inventory.setItem(itemsPerPage - 4, nextItem);

        ItemStack currentItem = setItem(Material.STICK, page, ChatColor.WHITE + CommonMessage.CURRENT_PAGE.getMessage());
        inventory.setItem(itemsPerPage - 5, currentItem);

        ItemStack prevItem = setItem(Material.ITEM_FRAME, 1, ChatColor.WHITE + CommonMessage.PREV_PAGE.getMessage());
        inventory.setItem(itemsPerPage - 6, prevItem);

        return inventory;
    }

    public static ItemStack setItem(Material material, int amount, String title) {
        ItemStack prevItem = new ItemStack(material, amount);
        ItemMeta prevItemMeta = prevItem.getItemMeta();
        assert prevItemMeta != null;
        prevItemMeta.setDisplayName(title);
        prevItem.setItemMeta(prevItemMeta);
        return prevItem;
    }
}
