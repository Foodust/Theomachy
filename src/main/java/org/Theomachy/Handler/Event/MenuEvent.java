package org.Theomachy.Handler.Event;

import org.Theomachy.Enum.CommonMessage;
import org.Theomachy.Handler.Command.SettingCommand;
import org.Theomachy.Handler.Module.SettingModule;
import org.Theomachy.Utility.Gambling.Gambling;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class MenuEvent implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(CommonMessage.MENU.getMessage())) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack wool = event.getCurrentItem();
            assert wool != null;
            String menuName = ChatColor.stripColor(Objects.requireNonNull(Objects.requireNonNull(wool.getItemMeta()).getDisplayName()));
            if (menuName.equals(CommonMessage.GAMBLING.getMessage())) {
                Gambling.gambling(player);
            }
        }
        else if (event.getView().getTitle().equals(CommonMessage.SETTING.getMessage())) {
            event.setCancelled(true);
            ItemStack wool = event.getCurrentItem();
            assert wool != null;
            if (event.getView().getTitle().equals(CommonMessage.SETTING.getMessage())) {
                SettingModule.guiListener(wool);
            }
        }
    }
}
