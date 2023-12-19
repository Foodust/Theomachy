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
import java.util.Objects;

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
        for (int itemIndex = 0; itemIndex < length; itemIndex++) {
            ItemStack item = CommonModule.setItem(!blacklist.contains(index) ? Material.WHITE_WOOL : Material.RED_WOOL,
                    1,
                    ChatColor.WHITE + AbilityInfo.getNameByIndex(itemIndex + index) + " : " + (itemIndex + index));
            inventory.setItem(itemIndex, item);
        }

        ItemStack nextItem = CommonModule.setItem(Material.ITEM_FRAME, 1, CommonMessage.NEXT_PAGE.getMessage());
        inventory.setItem(itemsPerPage - 4, nextItem);

        ItemStack currentItem = CommonModule.setItem(Material.STICK, page, CommonMessage.CURRENT_PAGE.getMessage());
        inventory.setItem(itemsPerPage - 5, currentItem);

        ItemStack prevItem = CommonModule.setItem(Material.ITEM_FRAME, 1,CommonMessage.PREV_PAGE.getMessage());
        inventory.setItem(itemsPerPage - 6, prevItem);

        return inventory;
    }

    public static void setAbilityAllow(ItemStack item, ItemMeta meta){
        item.setType(Material.WHITE_WOOL);
        assert meta != null;
        String[] abilityInfo = Objects.requireNonNull(meta.getDisplayName()).split(":");
        Object abilityNumObject = Integer.parseInt(abilityInfo[1].replaceAll(" ",""));
        BlacklistModule.blacklist.remove(abilityNumObject);
        String josa = HangulModule.getJosa(abilityInfo[0].trim());
        Bukkit.broadcastMessage(ChatColor.GREEN + "【 알림 】 " + ChatColor.WHITE + abilityInfo[0].trim() + josa + " " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에서 제거되었습니다.");
    }
    public static void setAbilityExcept(ItemStack item, ItemMeta meta){
        item.setType(Material.RED_WOOL);
        assert meta != null;
        String[] abilityInfo = Objects.requireNonNull(meta.getDisplayName()).split(":");
        int abilityNum = Integer.parseInt(abilityInfo[1].replaceAll(" ",""));
        BlacklistModule.blacklist.add(abilityNum);
        String josa = HangulModule.getJosa(abilityInfo[0].trim());
        Bukkit.broadcastMessage(ChatColor.GREEN + "【 알림 】 " + ChatColor.WHITE + abilityInfo[0].trim() + josa + " " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에 등록되었습니다.");
    }

    public static void movePage(Player player, Inventory inventory ,int slot){
        int index = 0;
        for (; index < BlacklistModule.blackListInventories.size(); index++) {
            if (inventory.equals(BlacklistModule.blackListInventories.get(index))) {
                break;
            }
        }
        // 페이지 이동 처리
        if (slot == BlacklistModule.itemsPerPage - 4 && index != 3) { // 마지막 슬롯 (다음 페이지)
            player.openInventory(BlacklistModule.blackListInventories.get(++index));
        } else if (slot == BlacklistModule.itemsPerPage - 6 && index != 0) { // 첫 번째 슬롯 (이전 페이지)
            player.openInventory(BlacklistModule.blackListInventories.get(--index));
        }
    }
}
