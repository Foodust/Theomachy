package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.SettingModule;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Checker.PermissionChecker;

public class SettingCommand {
    private final SettingModule settingModule = new SettingModule();
    private final PermissionChecker permissionChecker = new PermissionChecker();

    public void module(CommandSender sender) {
        Player player = (Player) sender;
        if (permissionChecker.Sender(sender)) {
            settingModule.openSettingInventory(player);
        }
    }

    public void resetTimer(CommandSender sender) {
        Player player = (Player) sender;
        if (permissionChecker.Sender(sender)) {
            Theomachy.tasks.forEach(tasks -> {
                Bukkit.getScheduler().cancelTask(tasks.getTaskId());
            });
            player.sendMessage(TheomachyMessage.INFO_RESET_TIMER.getMessage());
        }else{
            player.sendMessage(TheomachyMessage.ERROR_PERMISSION_DENIED.getMessage());
        }
    }
}
