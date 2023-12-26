package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.SettingModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Checker.PermissionChecker;

public class SettingCommand  {
    private final SettingModule settingModule = new SettingModule();
    private final PermissionChecker permissionChecker = new PermissionChecker();
    public void module(CommandSender sender) {
        Player player = (Player) sender;
        if (permissionChecker.Sender(sender)) {
            settingModule.openSettingInventory(player);
        }
    }
}
