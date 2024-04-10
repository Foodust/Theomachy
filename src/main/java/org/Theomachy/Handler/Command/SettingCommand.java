package org.Theomachy.Handler.Command;

import org.Theomachy.Ability.HUMAN.Meteor;
import org.Theomachy.Handler.Module.source.MessageModule;
import org.Theomachy.Handler.Module.source.SettingModule;
import org.Theomachy.Handler.Module.source.TaskModule;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Checker.PermissionChecker;

public class SettingCommand {
    private final SettingModule settingModule = new SettingModule();
    private final PermissionChecker permissionChecker = new PermissionChecker();
    private final TaskModule taskModule = new TaskModule();
    private final MessageModule messageModule =new MessageModule();

    public void module(CommandSender sender) {
        Player player = (Player) sender;
        if (permissionChecker.Player(sender)) {
            settingModule.openSettingInventory(player);
        }
    }

    public void resetTimer(CommandSender sender) {
        Player player = (Player) sender;
        if (permissionChecker.Player(sender)) {
            Theomachy.tasks.forEach(taskModule::cancelBukkitTask);
            messageModule.sendPlayer(player,TheomachyMessage.INFO_RESET_TIMER.getMessage());
        }
    }
}
