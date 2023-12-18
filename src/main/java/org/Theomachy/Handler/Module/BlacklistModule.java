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
        if (blackListInventories.isEmpty()){
            for(int i = 1 ; i <= totalPages; i++){
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
                length = AbilityData.GOD_ABILITY_NUMBER;
            }
            case 2 -> {
                index = 101;
                length = AbilityData.HUMAN_ABILITY_NUMBER;
            }
            case 3 -> {
                index = 301;
                length = AbilityData.JUJUTSU_KAISEN_ABILITY_NUMBER;
            }
            case 4 -> {
                index = 401;
                length = AbilityData.KIMETSU_NO_YAIBA_ABILITY_NUMBER;
            }
        }
        for (int itemIndex = index; itemIndex <= length; itemIndex++) {
            ItemStack item = !blacklist.contains(index) ? new ItemStack(Material.WHITE_WOOL) : new ItemStack(Material.RED_WOOL);
            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName(ChatColor.WHITE + AbilityInfo.getNameByIndex(index) + " : " + index);
            item.setItemMeta(itemMeta);
            inventory.setItem(itemIndex, item);
        }
        ItemStack itemStack = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.WHITE + CommonMessage.NEXT_PAGE.getMessage());
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(itemsPerPage,itemStack);

        itemMeta.setDisplayName(ChatColor.WHITE + CommonMessage.PREV_PAGE.getMessage());
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(itemsPerPage - itemsPerPage / 9,itemStack);
        return inventory;
    }
}
