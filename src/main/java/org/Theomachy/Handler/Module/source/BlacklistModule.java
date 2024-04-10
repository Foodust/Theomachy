package org.Theomachy.Handler.Module.source;

import org.Theomachy.Data.AbilityData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlacklistModule {
    private final ItemModule itemModule = new ItemModule();
    private final HangulModule hangulModule = new HangulModule();
    private final MessageModule messageModule =new MessageModule();
    public static List<Integer> godCanlist = new ArrayList<>();
    public static List<Integer> humanCanlist = new ArrayList<>();
    public static List<Integer> jujutsuCanList = new ArrayList<>();
    public static List<Integer> kimetsuCanlist = new ArrayList<>();
    public static int availableList;
    public static List<Integer> blacklist = new ArrayList<>();
    public static List<Inventory> blackListInventories = new ArrayList<>();
    public int itemsPerPage = 6 * 9; // 페이지당 아이템 수

    public void openBlackListInventory(Player player) {
        int totalPages = 4;
        if (blackListInventories.isEmpty()) {
            for (int i = 1; i <= totalPages; i++) {
                blackListInventories.add(makeInventory(i));
            }
        }
        player.openInventory(blackListInventories.get(0)); // 첫 번째 페이지 인벤토리 열기
    }

    public Inventory makeInventory(int page) {

        Inventory inventory = Bukkit.createInventory(null, itemsPerPage, TheomachyMessage.SETTING_BLACKLIST.getMessage());
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
            ItemStack item = itemModule.setItem(!blacklist.contains(index) ? Material.WHITE_WOOL : Material.RED_WOOL,
                    ChatColor.WHITE + AbilityInfo.getAbilityByIndex(itemIndex + index).getName() + " : " + (itemIndex + index),1);
            inventory.setItem(itemIndex, item);
        }

        ItemStack nextItem = itemModule.setItem(Material.ITEM_FRAME,  TheomachyMessage.SETTING_NEXT_PAGE.getMessage(),1);
        inventory.setItem(itemsPerPage - 4, nextItem);

        ItemStack currentItem = itemModule.setItem(Material.STICK, TheomachyMessage.SETTING_CURRENT_PAGE.getMessage(),page);
        inventory.setItem(itemsPerPage - 5, currentItem);

        ItemStack prevItem = itemModule.setItem(Material.ITEM_FRAME, TheomachyMessage.SETTING_PREV_PAGE.getMessage(),1);
        inventory.setItem(itemsPerPage - 6, prevItem);

        return inventory;
    }

    public void setAbilityAllow(ItemStack item, ItemMeta meta) {
        item.setType(Material.WHITE_WOOL);
        assert meta != null;
        String[] abilityInfo = Objects.requireNonNull(meta.getDisplayName()).split(":");
        Object abilityNumObject = Integer.parseInt(abilityInfo[1].replaceAll(" ", ""));
        BlacklistModule.blacklist.remove(abilityNumObject);
        String josa = hangulModule.getJosa(abilityInfo[0], HangulModule.Josa.EI_GA);
        messageModule.broadcastMessage(ChatColor.WHITE + abilityInfo[0].trim() + josa + " " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에서 제거되었습니다.");
    }

    public void setAbilityExcept(ItemStack item, ItemMeta meta) {
        item.setType(Material.RED_WOOL);
        assert meta != null;
        String[] abilityInfo = Objects.requireNonNull(meta.getDisplayName()).split(":");
        int abilityNum = Integer.parseInt(abilityInfo[1].replaceAll(" ", ""));
        BlacklistModule.blacklist.add(abilityNum);
        String josa = hangulModule.getJosa(abilityInfo[0], HangulModule.Josa.EI_GA);
        messageModule.broadcastMessage(ChatColor.WHITE + abilityInfo[0].trim() + josa + " " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에 등록되었습니다.");
    }

    public void movePage(Player player, Inventory inventory, int slot) {
        int index = 0;
        for (; index < BlacklistModule.blackListInventories.size(); index++) {
            if (inventory.equals(BlacklistModule.blackListInventories.get(index))) {
                break;
            }
        }
        // 페이지 이동 처리
        if (slot == itemsPerPage - 4 && index != 3) { // 마지막 슬롯 (다음 페이지)
            player.openInventory(BlacklistModule.blackListInventories.get(++index));
        } else if (slot == itemsPerPage - 6 && index != 0) { // 첫 번째 슬롯 (이전 페이지)
            player.openInventory(BlacklistModule.blackListInventories.get(--index));
        }
    }

    public void settingBlackList(File file) {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                BlacklistModule.blacklist.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            messageModule.logInfo(e.toString());
        }
        for (int i = 1; i <= AbilityData.GOD_ABILITY_NUMBER; i++) {
            if (!BlacklistModule.blacklist.contains(i)) BlacklistModule.godCanlist.add(i);
        }
        for (int i = 101; i <= AbilityData.HUMAN_ABILITY_NUMBER + 100; i++) {
            if (!BlacklistModule.blacklist.contains(i)) BlacklistModule.humanCanlist.add(i);
        }
        for (int i = 301; i <= AbilityData.JUJUTSU_KAISEN_ABILITY_NUMBER + 300; i++) {
            if (!BlacklistModule.blacklist.contains(i)) BlacklistModule.jujutsuCanList.add(i);
        }
        for (int i = 401; i <= AbilityData.KIMETSU_NO_YAIBA_ABILITY_NUMBER + 400; i++) {
            if (!BlacklistModule.blacklist.contains(i)) BlacklistModule.kimetsuCanlist.add(i);
        }
    }

    public void freeBlackList(File file) {
        BufferedWriter bufferedWriter;

        for (BukkitTask task : Theomachy.tasks) {
            task.cancel();
        }
        Theomachy.tasks.clear();

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (int blacklistId : BlacklistModule.blacklist) {
                bufferedWriter.write(String.valueOf(blacklistId));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ignored) {
        }
        messageModule.logInfo(TheomachyMessage.ERROR_DOES_NOT_ACCESS_BLACKLIST_FILE.getMessage());
    }
}
