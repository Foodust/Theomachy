package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.BlacklistModule;
import org.Theomachy.Utility.DefaultUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Checker.PermissionChecker;

public class BlacklistCommand extends DefaultUtil {

    public void module(CommandSender sender) {
        if (PermissionChecker.Sender(sender)) {
            Player player = (Player) sender;
            blacklistModule.openBlackListInventory(player);
        }
    }
}
