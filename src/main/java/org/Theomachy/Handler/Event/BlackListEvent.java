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
        int index = 0;
        if (event.getClickedInventory() != null) {
            event.setCancelled(true); // 이벤트 취소 (아이템 이동 방지)
            for (; index < BlacklistModule.blackListInventories.size();index++) {
                if (event.getInventory().equals(BlacklistModule.blackListInventories.get(index))) {
                    break;
                }
            }
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            // 페이지 이동 처리
            if (slot == BlacklistModule.itemsPerPage && index != 3) { // 마지막 슬롯 (다음 페이지)
                afterPage(player, BlacklistModule.blackListInventories.get(++index));
            } else if (slot == BlacklistModule.itemsPerPage - BlacklistModule.itemsPerPage / 9 && index != 0) { // 첫 번째 슬롯 (이전 페이지)
                beforePage(player,  BlacklistModule.blackListInventories.get(--index), slot);
            }

        }
    }

    public void afterPage(Player player, Inventory inventory) {
        int nextPage = inventory.firstEmpty();
        if (nextPage != -1) {
            player.openInventory(inventory);
        }
    }

    public void beforePage(Player player, Inventory inventory, int slot) {
        int prevPage = slot - 9;
        if (prevPage >= 0) {
            player.openInventory(inventory);
        }
    }
}
