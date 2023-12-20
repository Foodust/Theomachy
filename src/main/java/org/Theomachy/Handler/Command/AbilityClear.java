package org.Theomachy.Handler.Command;

import org.Theomachy.Checker.PermissionChecker;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Timer.CoolTimeTimer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AbilityClear {
    public static void module(CommandSender sender, Command command, String label, String[] data)
    {
        if (PermissionChecker.Sender(sender))
        {
            CoolTimeTimer.init =true;
            Bukkit.broadcastMessage(TheomachyMessage.INFO_COOL_TIME_CLEAR.getMessage());
        }
    }
}
