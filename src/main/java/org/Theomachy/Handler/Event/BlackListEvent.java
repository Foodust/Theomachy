package org.Theomachy.Handler.Event;

import org.Theomachy.Enum.TheomachyMessage;
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

public class BlackListEvent implements Listener {
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (ChatColor.stripColor(event.getView().getOriginalTitle()).equals(TheomachyMessage.BLACKLIST.getMessage())) {
            Player player = (Player) event.getPlayer();
            BlacklistModule.openBlackListInventory(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(TheomachyMessage.BLACKLIST.getMessage())) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            assert item != null;
            ItemMeta meta = item.getItemMeta();

            if (item.getType().equals(Material.WHITE_WOOL)) {
                BlacklistModule.setAbilityExcept(item, meta);
            } else if (item.getType().equals(Material.RED_WOOL)) {
                BlacklistModule.setAbilityAllow(item, meta);
            } else if (item.getType().equals(Material.ITEM_FRAME)) {
                BlacklistModule.movePage((Player) event.getWhoClicked(), event.getInventory(), event.getSlot());
            }
        }
    }
}
