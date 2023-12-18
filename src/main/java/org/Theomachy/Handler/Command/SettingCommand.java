package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.SettingModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Utility.Checker.PermissionChecker;

public class SettingCommand {
    public static void Module(CommandSender sender) {
        Player player = (Player) sender;
        if (PermissionChecker.Sender(sender)) {
            SettingModule.openSettingInventory(player);
        }
    }
}
