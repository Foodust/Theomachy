package org.Theomachy.Handler.Event;

import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Handler.Module.GamblingModule;
import org.Theomachy.Handler.Module.SettingModule;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class MenuEvent implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(TheomachyMessage.SETTING_MENU.getMessage())) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            if (item == null) return;

        } else if (event.getView().getTitle().equals(TheomachyMessage.SETTING.getMessage())) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null) return;
            if (event.getView().getTitle().equals(TheomachyMessage.SETTING.getMessage())) {
                SettingModule.serverSetting(item);
            }
        } else if (event.getView().getTitle().equals(TheomachyMessage.SETTING_GAMBLING.getMessage())) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            if (item == null) return;
            GamblingModule.gambling(player);
        }

    }
}
