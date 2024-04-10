package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.source.BlacklistModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Checker.PermissionChecker;

public class BlacklistCommand  {
    private final BlacklistModule blacklistModule = new BlacklistModule();
    private final PermissionChecker permissionChecker = new PermissionChecker();
    public void module(CommandSender sender) {
        if (permissionChecker.Player(sender)) {
            Player player = (Player) sender;
            blacklistModule.openBlackListInventory(player);
        }
    }
}
