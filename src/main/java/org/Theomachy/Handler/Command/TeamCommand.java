package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.TeamModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Checker.PermissionChecker;

public class TeamCommand  {
    private final TeamModule teamModule = new TeamModule();
    public void setTeam(CommandSender sender, Command command, String label, String[] data) {
        if (PermissionChecker.Sender(sender)) {
            teamModule.setTeam(sender, data);
        }
    }
    public void listOfTeam(CommandSender sender, Command command, String label, String[] data) {
        teamModule.listTeam(sender,data);
    }
}
