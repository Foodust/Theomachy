package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.AbilityModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AbilityHelpCommand {
    public static void Module(CommandSender sender, Command command, String label, String[] data) {
        AbilityModule.openAbilityHelpInventory((Player) sender);
    }
}
