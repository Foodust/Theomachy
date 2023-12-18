package org.Theomachy.Handler.Event;

import org.Theomachy.Enum.CommonMessage;
import org.Theomachy.Handler.Module.BlacklistModule;
import org.Theomachy.Utility.Hangul;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class BlackListEvent implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getView().getTitle().equals(CommonMessage.BLACKLIST.getMessage())) {
            Player player = (Player) event.getPlayer();
            BlacklistModule.openBlackListInventory(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (BlacklistModule.blackListInventories.contains(event.getClickedInventory())) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            assert item != null;
            ItemMeta meta = item.getItemMeta();
            if (ChatColor.stripColor(event.getView().getOriginalTitle()).equals(CommonMessage.BLACKLIST.getMessage())) {
                if (item.getType().equals(Material.WHITE_WOOL)) {
                    item.setType(Material.RED_WOOL);
                    assert meta != null;
                    String[] abilityInfo = Objects.requireNonNull(meta.getDisplayName()).split(":");
                    int abilityNum = Integer.parseInt(abilityInfo[1]);
                    BlacklistModule.blacklist.add(abilityNum);
                    char josa = '가';
                    try {
                        josa = Hangul.getJosa(abilityInfo[0].charAt(abilityInfo[0].toCharArray().length - 1), '이', '가');
                    } catch (Exception ignored) {
                    }
                    Bukkit.broadcastMessage(ChatColor.GREEN + "【 알림 】 " + ChatColor.WHITE + abilityInfo[0] + josa + " " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에 등록되었습니다.");
                } else if (item.getType().equals(Material.RED_WOOL)) {
                    item.setType(Material.WHITE_WOOL);
                    assert meta != null;
                    String[] abilityInfo = Objects.requireNonNull(meta.getDisplayName()).split(":");
                    Object abilityNumObject = Integer.parseInt(abilityInfo[1]);
                    BlacklistModule.blacklist.remove(abilityNumObject);

                    char josa = '가';
                    try {
                        josa = Hangul.getJosa(abilityInfo[0].charAt(abilityInfo[0].toCharArray().length - 1), '이', '가');
                    } catch (Exception ignored) {
                    }
                    Bukkit.broadcastMessage(ChatColor.GREEN + "【 알림 】 " + ChatColor.WHITE + abilityInfo[0] + josa + " " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에서 제거되었습니다.");
                } else if (item.getType().equals(Material.ITEM_FRAME)) {
                    int index = 0;
                    for (; index < BlacklistModule.blackListInventories.size(); index++) {
                        if (event.getInventory().equals(BlacklistModule.blackListInventories.get(index))) {
                            break;
                        }
                    }
                    Player player = (Player) event.getWhoClicked();
                    int slot = event.getSlot();
                    // 페이지 이동 처리
                    if (slot == BlacklistModule.itemsPerPage && index != 3) { // 마지막 슬롯 (다음 페이지)
                        player.openInventory( BlacklistModule.blackListInventories.get(++index));
                    } else if (slot == BlacklistModule.itemsPerPage - BlacklistModule.itemsPerPage / 9 && index != 0) { // 첫 번째 슬롯 (이전 페이지)
                        player.openInventory( BlacklistModule.blackListInventories.get(--index));
                    }
                }
            }
        }
    }
}
