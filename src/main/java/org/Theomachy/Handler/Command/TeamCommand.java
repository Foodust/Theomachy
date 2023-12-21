package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.TeamModule;
import org.Theomachy.Utility.DefaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.Theomachy.Data.GameData;
import org.Theomachy.Checker.PermissionChecker;

import java.util.*;

public class TeamCommand extends DefaultUtil {
    public void setTeam(CommandSender sender, Command command, String label, String[] data) {
        if (PermissionChecker.Sender(sender)) {
            teamModule.setTeam(sender, data);
        }
    }
    public void listOfTeam(CommandSender sender, Command command, String label, String[] data) {
        teamModule.listTeam(sender,data);
    }
}
