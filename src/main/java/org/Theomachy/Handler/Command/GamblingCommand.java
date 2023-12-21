package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.GamblingModule;
import org.Theomachy.Utility.DefaultUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Theomachy;

public class GamblingCommand  {
    private final GamblingModule gamblingModule = new GamblingModule();
    public void module(CommandSender sender) {
        Player player = (Player) sender;
        if (Theomachy.GAMBLING) {
            gamblingModule.openGamblingInventory(player);
        } else {
            player.sendMessage();
        }
    }
}
