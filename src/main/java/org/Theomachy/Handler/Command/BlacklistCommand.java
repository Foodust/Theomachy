package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.BlacklistModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Checker.PermissionChecker;

public class BlacklistCommand  {
    private final BlacklistModule blacklistModule = new BlacklistModule();
    public void module(CommandSender sender) {
        if (PermissionChecker.Sender(sender)) {
            Player player = (Player) sender;
            blacklistModule.openBlackListInventory(player);
        }
    }
}
