package org.Theomachy.Handler.Event;

import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Handler.Module.BlacklistModule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlackListEvent  implements Listener {
    private final BlacklistModule blacklistModule = new BlacklistModule();
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (ChatColor.stripColor(event.getView().getOriginalTitle()).equals(TheomachyMessage.SETTING_BLACKLIST.getMessage())) {
            Player player = (Player) event.getPlayer();
            blacklistModule.openBlackListInventory(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(TheomachyMessage.SETTING_BLACKLIST.getMessage())) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null) return;
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (item.getType().equals(Material.WHITE_WOOL)) {
                blacklistModule.setAbilityExcept(item, meta);
            } else if (item.getType().equals(Material.RED_WOOL)) {
                blacklistModule.setAbilityAllow(item, meta);
            } else if (item.getType().equals(Material.ITEM_FRAME)) {
                blacklistModule.movePage((Player) event.getWhoClicked(), event.getInventory(), event.getSlot());
            }
        }
    }
}
