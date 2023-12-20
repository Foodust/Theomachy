package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.TeamModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.Theomachy.Data.GameData;
import org.Theomachy.Checker.PermissionChecker;

import java.util.*;

public class TeamCommand {
    public static void setTeam(CommandSender sender, Command command, String label, String[] data) {
        if (PermissionChecker.Sender(sender)) {
            TeamModule.setTeam(sender, data);
        }
    }
    public static void listOfTeam(CommandSender sender, Command command, String label, String[] data) {
        TeamModule.listTeam(sender,data);
    }
}
