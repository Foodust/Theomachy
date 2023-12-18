package org.Theomachy.Handler.Event;

import org.Theomachy.Enum.CommonMessage;
import org.Theomachy.Handler.Module.BlacklistModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

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
        event.setCancelled(true); // 이벤트 취소 (아이템 이동 방지)
        int index = 0;
        if (BlacklistModule.blackListInventories.contains(event.getClickedInventory())) {
            for (; index < BlacklistModule.blackListInventories.size(); index++) {
                if (event.getInventory().equals(BlacklistModule.blackListInventories.get(index))) {
                    break;
                }
            }
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            // 페이지 이동 처리
            if (slot == BlacklistModule.itemsPerPage && index != 3) { // 마지막 슬롯 (다음 페이지)
                openInventory(player, BlacklistModule.blackListInventories.get(++index));
            } else if (slot == BlacklistModule.itemsPerPage - BlacklistModule.itemsPerPage / 9 && index != 0) { // 첫 번째 슬롯 (이전 페이지)
                openInventory(player, BlacklistModule.blackListInventories.get(--index));
            }
        }
    }

    public void openInventory(Player player, Inventory inventory) {
            player.openInventory(inventory);
    }
}
