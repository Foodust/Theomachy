package org.Theomachy.Handler.Event;

import org.Theomachy.Enum.CommonMessage;
import org.Theomachy.Handler.Command.SettingCommand;
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
        if (ChatColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(CommonMessage.MENU.getMessage())) {
            Player p = (Player) event.getWhoClicked();
            ItemStack wool = event.getCurrentItem();
            assert wool != null;
            ItemMeta meta = wool.getItemMeta();
            String menuName = ChatColor.stripColor(Objects.requireNonNull(Objects.requireNonNull(wool.getItemMeta()).getDisplayName()));
            if (menuName.equals(CommonMessage.GAMBLING.getMessage())) {
                Gambling.gambling(p);
            }
        }
        if (ChatColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(CommonMessage.SETTING.getMessage())) {
            ItemStack wool = event.getCurrentItem();
            assert wool != null;
            ItemMeta meta = wool.getItemMeta();
            if (ChatColor.stripColor(event.getView().getOriginalTitle()).equals(CommonMessage.SETTING.getMessage())) {
                SettingCommand.guiListener(wool);
            }
        }
    }
}
