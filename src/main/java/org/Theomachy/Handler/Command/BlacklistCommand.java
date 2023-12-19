package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.BlacklistModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Checker.PermissionChecker;

public class BlacklistCommand {

    public static void Module(CommandSender sender) {
        if (PermissionChecker.Sender(sender)) {
            Player player = (Player) sender;
            BlacklistModule.openBlackListInventory(player);
        }
    }
}
