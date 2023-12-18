package org.Theomachy.Handler.Command;

import java.util.ArrayList;
import java.util.List;

import org.Theomachy.Handler.Module.BlacklistModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Utility.Checker.PermissionChecker;

public class BlacklistCommand {

    public static List<Integer> godCanlist = new ArrayList<Integer>();
    public static List<Integer> humanCanlist = new ArrayList<Integer>();
    public static List<Integer> jujutsuCanList = new ArrayList<Integer>();
    public static List<Integer> kimetsuCanlist = new ArrayList<Integer>();


    public static int availableList;

    public static void Module(CommandSender sender) {
        if (PermissionChecker.Sender(sender)) {
            Player player = (Player) sender;
//            player.openInventory(BlacklistModule.blacklistGui());
            BlacklistModule.openBlackListInventory(player);
        }
    }
}
