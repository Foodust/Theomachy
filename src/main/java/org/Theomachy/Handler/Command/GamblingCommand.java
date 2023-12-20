package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.GamblingModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Theomachy;

public class GamblingCommand {
    public static void module(CommandSender sender) {
        Player player = (Player) sender;
        if (Theomachy.GAMBLING) {
            GamblingModule.openGamblingInventory(player);
        } else {
            player.sendMessage();
        }
    }
}
