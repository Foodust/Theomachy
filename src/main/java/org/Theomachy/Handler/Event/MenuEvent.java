package org.Theomachy.Handler.Event;

import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Handler.Module.game.GamblingModule;
import org.Theomachy.Handler.Module.source.SettingModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuEvent  implements Listener {
    private final SettingModule settingModule = new SettingModule();
    private final GamblingModule gamblingModule = new GamblingModule();
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
                settingModule.serverSetting(item);
            }
        } else if (event.getView().getTitle().equals(TheomachyMessage.SETTING_GAMBLING.getMessage())) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            if (item == null) return;
            gamblingModule.gambling(player);
        } else if(event.getView().getTitle().equals(TheomachyMessage.SETTING_ABILITY_INFO.getMessage())){
            event.setCancelled(true);
        }

    }
}
