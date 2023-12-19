package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.GamblingModule;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Theomachy;

public class GamblingCommand {
    public static void Module(CommandSender sender) {
        Player p = (Player) sender;
        if (Theomachy.GAMBLING) {
            p.openInventory(GamblingModule.gui());
        } else {
            p.sendMessage(ChatColor.RED + "이 기능은 잠겨있습니다!");
        }
    }
}
